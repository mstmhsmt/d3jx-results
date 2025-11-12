package hudson.model;

import hudson.Extension;
import hudson.ExtensionList;
import hudson.ExtensionPoint;
import hudson.util.FormValidation;
import hudson.util.FormValidation.Kind;
import hudson.util.QuotedStringTokenizer;
import hudson.util.TextFile;
import jenkins.model.Jenkins;
import jenkins.util.JSONSignatureValidator;
import net.sf.json.JSONException;
import org.kohsuke.stapler.Stapler;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import static hudson.util.TimeUnit2.DAYS;
import org.apache.commons.io.IOUtils;
import hudson.ProxyConfiguration;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import jenkins.model.DownloadSettings;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;

@Extension
public class DownloadService extends PageDecorator {

    public String generateFragment() {
        
if (!DownloadSettings.get().isUseBrowser()) {
            return "";
        }

        if (neverUpdate)
            return "";
        if (doesNotSupportPostMessage())
            return "";
        StringBuilder buf = new StringBuilder();
        if (Jenkins.getInstance().hasPermission(Jenkins.READ)) {
            long now = System.currentTimeMillis();
            for (Downloadable d : Downloadable.all()) {
                if (d.getDue() < now && d.lastAttempt + 10 * 1000 < now) {
                    buf.append("<script>").append("Behaviour.addLoadEvent(function() {").append("  downloadService.download(").append(QuotedStringTokenizer.quote(d.getId())).append(',').append(QuotedStringTokenizer.quote(mapHttps(d.getUrl()))).append(',').append("{version:" + QuotedStringTokenizer.quote(Jenkins.VERSION) + '}').append(',').append(QuotedStringTokenizer.quote(Stapler.getCurrentRequest().getContextPath() + '/' + getUrl() + "/byId/" + d.getId() + "/postBack")).append(',').append("null);").append("});").append("</script>");
                    d.lastAttempt = now;
                }
            }
        }
        return buf.toString();
    }

    private boolean doesNotSupportPostMessage() {
        StaplerRequest req = Stapler.getCurrentRequest();
        if (req == null)
            return false;
        String ua = req.getHeader("User-Agent");
        if (ua == null)
            return false;
        return ua.contains("Windows") && (ua.contains(" MSIE 5.") || ua.contains(" MSIE 6.") || ua.contains(" MSIE 7."));
    }

    private String mapHttps(String url) {
        if (url.startsWith("http://updates.jenkins-ci.org/") && Jenkins.getInstance().isRootUrlSecure())
            return "https" + url.substring(4);
        return url;
    }

    public Downloadable getById(String id) {
        for (Downloadable d : Downloadable.all()) if (d.getId().equals(id))
            return d;
        return null;
    }

    public static class Downloadable implements ExtensionPoint {

        private final String id;

        private final String url;

        private final long interval;

        private volatile long due = 0;

        private volatile long lastAttempt = Long.MIN_VALUE;

        public Downloadable(String id, String url, long interval) {
            this.id = id;
            this.url = url;
            this.interval = interval;
        }

        public Downloadable() {
            this.id = getClass().getName().replace('$', '.');
            this.url = this.id + ".json";
            this.interval = DEFAULT_INTERVAL;
        }

        public Downloadable(Class id) {
            this(id.getName().replace('$', '.'));
        }

        public Downloadable(String id) {
            this(id, id + ".json");
        }

        public Downloadable(String id, String url) {
            this(id, url, DEFAULT_INTERVAL);
        }

        public String getId() {
            return id;
        }

        public String getUrl() {
            return Jenkins.getInstance().getUpdateCenter().getDefaultBaseUrl() + "updates/" + url;
        }

        public long getInterval() {
            return interval;
        }

        public TextFile getDataFile() {
            return new TextFile(new File(Jenkins.getInstance().getRootDir(), "updates/" + id));
        }

        public long getDue() {
            if (due == 0)
                due = getDataFile().file.lastModified() + interval;
            return due;
        }

        public JSONObject getData() throws IOException {
            TextFile df = getDataFile();
            if (df.exists())
                try {
                    return JSONObject.fromObject(df.read());
                } catch (JSONException e) {
                    df.delete();
                    throw new IOException("Failed to parse " + df + " into JSON", e);
                }
            return null;
        }

        public void doPostBack(StaplerRequest req, StaplerResponse rsp) throws IOException {
            DownloadSettings.checkPostBackAccess();
            long dataTimestamp = System.currentTimeMillis();
            due = dataTimestamp + getInterval();
            String json = IOUtils.toString(req.getInputStream(), "UTF-8");
            FormValidation e = load(json, dataTimestamp);
            if (e.kind != Kind.OK) {
                LOGGER.severe(e.renderHtml());
                throw e;
            }
            rsp.setContentType("text/plain");
        }

        public static ExtensionList<Downloadable> all() {
            return ExtensionList.lookup(Downloadable.class);
        }

        public static Downloadable get(String id) {
            for (Downloadable d : all()) {
                if (d.id.equals(id))
                    return d;
            }
            return null;
        }

        private final static Logger LOGGER = Logger.getLogger(Downloadable.class.getName());

        private final static long DEFAULT_INTERVAL = Long.getLong(Downloadable.class.getName() + ".defaultInterval", DAYS.toMillis(1));

        @Restricted(NoExternalUse.class)
        public FormValidation updateNow() throws IOException {
            return load(loadJSONHTML(new URL(getUrl() + ".html?id=" + URLEncoder.encode(getId(), "UTF-8") + "&version=" + URLEncoder.encode(Jenkins.VERSION, "UTF-8"))), System.currentTimeMillis());
        }

        @Restricted(NoExternalUse.class)
        public FormValidation updateNow() throws IOException {
            return load(loadJSON(new URL(getUrl() + "?id=" + URLEncoder.encode(getId(), "UTF-8") + "&version=" + URLEncoder.encode(Jenkins.VERSION, "UTF-8"))), System.currentTimeMillis());
        }

        private FormValidation load(String json, long dataTimestamp) throws IOException {
            JSONObject o = JSONObject.fromObject(json);
            if (signatureCheck) {
                FormValidation e = new JSONSignatureValidator("downloadable '" + id + "'").verifySignature(o);
                if (e.kind != Kind.OK) {
                    return e;
                }
            }
            TextFile df = getDataFile();
            df.write(json);
            df.file.setLastModified(dataTimestamp);
            LOGGER.info("Obtained the updated data file for " + id);
            return FormValidation.ok();
        }
    }

    public static boolean neverUpdate = Boolean.getBoolean(DownloadService.class.getName() + ".never");

    public static boolean signatureCheck = !Boolean.getBoolean(DownloadService.class.getName() + ".noSignatureCheck");

    @Restricted(NoExternalUse.class)
    public static String loadJSON(URL src) throws IOException {
        InputStream is = ProxyConfiguration.open(src).getInputStream();
        try {
            String jsonp = IOUtils.toString(is, "UTF-8");
            int start = jsonp.indexOf('{');
            int end = jsonp.lastIndexOf('}');
            if (start >= 0 && end > start) {
                return jsonp.substring(start, end + 1);
            } else {
                throw new IOException("Could not find JSON in " + src);
            }
        } finally {
            is.close();
        }
    }

    @Restricted(NoExternalUse.class)
    public static String loadJSONHTML(URL src) throws IOException {
        InputStream is = ProxyConfiguration.open(src).getInputStream();
        try {
            String jsonp = IOUtils.toString(is, "UTF-8");
            String preamble = "window.parent.postMessage(JSON.stringify(";
            int start = jsonp.indexOf(preamble);
            int end = jsonp.lastIndexOf("),'*');");
            if (start >= 0 && end > start) {
                return jsonp.substring(start + preamble.length(), end).trim();
            } else {
                throw new IOException("Could not find JSON in " + src);
            }
        } finally {
            is.close();
        }
    }
}
