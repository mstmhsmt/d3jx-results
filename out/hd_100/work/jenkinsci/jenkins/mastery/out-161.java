package jenkins.security;

import hudson.Extension;
import jenkins.util.SystemProperties;
import hudson.Util;
import hudson.model.Descriptor.FormException;
import hudson.model.User;
import hudson.model.UserProperty;
import hudson.model.UserPropertyDescriptor;
import hudson.security.ACL;
import hudson.util.HttpResponses;
import hudson.util.Secret;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import java.io.IOException;
import java.security.SecureRandom;
import javax.annotation.Nonnull;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.interceptor.RequirePOST;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jenkins.security.apitoken.ApiTokenPropertyConfiguration;
import jenkins.security.apitoken.ApiTokenStats;
import jenkins.security.apitoken.ApiTokenStore;
import net.sf.json.JSONArray;
import org.kohsuke.stapler.QueryParameter;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.CheckForNull;
import javax.annotation.concurrent.Immutable;

public class ApiTokenProperty extends UserProperty {

    private volatile Secret apiToken;

    @SuppressFBWarnings(value = "MS_SHOULD_BE_FINAL", justification = "Accessible via System Groovy Scripts")
    private static boolean SHOW_LEGACY_TOKEN_TO_ADMINS = SystemProperties.getBoolean(ApiTokenProperty.class.getName() + ".showTokenToAdmins");

    @DataBoundConstructor
    public ApiTokenProperty() {
    }

    ApiTokenProperty(@CheckForNull String seed) {
        if (seed != null) {
            apiToken = Secret.fromString(seed);
        }
    }

    @Nonnull
    @SuppressFBWarnings("NP_NONNULL_RETURN_VIOLATION")
    public String getApiToken() {
        LOGGER.log(Level.FINE, "Deprecated usage of getApiToken");
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, "Deprecated usage of getApiToken (trace)", new Exception());
        }
        return hasPermissionToSeeToken() ? getApiTokenInsecure() : Messages.ApiTokenProperty_ChangeToken_TokenIsHidden();
    }

    @Nonnull
    @Restricted(NoExternalUse.class)
    String getApiTokenInsecure() {
        if (apiToken == null) {
            return Messages.ApiTokenProperty_NoLegacyToken();
        }
        String p = apiToken.getPlainText();
        if (p.equals(Util.getDigestOf(Jenkins.getInstance().getSecretKey() + ":" + user.getId()))) {
            apiToken = Secret.fromString(p = API_KEY_SEED.mac(user.getId()));
        }
        return Util.getDigestOf(p);
    }

    public boolean matchesPassword(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        ApiTokenStore.HashedToken matchingToken = tokenStore.findMatchingToken(token);
        if (matchingToken == null) {
            return false;
        }
        tokenStats.updateUsageForId(matchingToken.getUuid());
        return true;
    }

    private boolean hasPermissionToSeeToken() {
        if (SHOW_LEGACY_TOKEN_TO_ADMINS && Jenkins.get().hasPermission(Jenkins.ADMINISTER)) {
            return true;
        }
        User current = User.current();
        if (current == null) {
            return false;
        }
        if (Jenkins.getAuthentication() == ACL.SYSTEM) {
            return true;
        }
        return User.idStrategy().equals(user.getId(), current.getId());
    }

    @Deprecated
    public void changeApiToken() throws IOException {
        user.checkPermission(Jenkins.ADMINISTER);
        LOGGER.log(Level.FINE, "Deprecated usage of changeApiToken");
        ApiTokenStore.HashedToken existingLegacyToken = tokenStore.getLegacyToken();
        _changeApiToken();
        tokenStore.regenerateTokenFromLegacy(apiToken);
        if (existingLegacyToken != null) {
            tokenStats.removeId(existingLegacyToken.getUuid());
        }
        user.save();
    }

    @Deprecated
    private void _changeApiToken() {
        byte[] random = new byte[16];
        RANDOM.nextBytes(random);
        apiToken = Secret.fromString(Util.toHexString(random));
    }

    @Override
    public UserProperty reconfigure(StaplerRequest req, @CheckForNull JSONObject form) throws FormException {
        if (form == null) {
            return this;
        }
        Object tokenStoreData = form.get("tokenStore");
        Map<String, JSONObject> tokenStoreTypedData = convertToTokenMap(tokenStoreData);
        this.tokenStore.reconfigure(tokenStoreTypedData);
        return this;
    }

    @Extension
    @Symbol("apiToken")
    static final public class DescriptorImpl extends UserPropertyDescriptor {

        public String getDisplayName() {
            return Messages.ApiTokenProperty_DisplayName();
        }

        @Restricted(NoExternalUse.class)
        public String getNoLegacyToken() {
            return Messages.ApiTokenProperty_NoLegacyToken();
        }

        public ApiTokenProperty newInstance(User user) {
            if (!ApiTokenPropertyConfiguration.get().isTokenGenerationOnCreationEnabled()) {
                return forceNewInstance(user, false);
            }
            return forceNewInstance(user, true);
        }

        private ApiTokenProperty forceNewInstance(User user, boolean withLegacyToken) {
            if (withLegacyToken) {
                return new ApiTokenProperty(API_KEY_SEED.mac(user.getId()));
            } else {
                return new ApiTokenProperty(null);
            }
        }

        @Restricted(NoExternalUse.class)
        public boolean isStatisticsEnabled() {
            return ApiTokenPropertyConfiguration.get().isUsageStatisticsEnabled();
        }

        @Restricted(NoExternalUse.class)
        public boolean mustDisplayLegacyApiToken(User propertyOwner) {
            ApiTokenProperty property = propertyOwner.getProperty(ApiTokenProperty.class);
            if (property != null && property.apiToken != null) {
                return true;
            }
            return ApiTokenPropertyConfiguration.get().isCreationOfLegacyTokenEnabled();
        }

        @Restricted(NoExternalUse.class)
        public boolean hasCurrentUserRightToGenerateNewToken(User propertyOwner) {
            if (ADMIN_CAN_GENERATE_NEW_TOKENS && Jenkins.get().hasPermission(Jenkins.ADMINISTER)) {
                return true;
            }
            User currentUser = User.current();
            if (currentUser == null) {
                return false;
            }
            if (Jenkins.getAuthentication() == ACL.SYSTEM) {
                return true;
            }
            return User.idStrategy().equals(propertyOwner.getId(), currentUser.getId());
        }

        @RequirePOST
        @Deprecated
        public HttpResponse doChangeToken(@AncestorInPath User u, StaplerResponse rsp) throws IOException {
            u.checkPermission(Jenkins.ADMINISTER);
            LOGGER.log(Level.FINE, "Deprecated action /changeToken used, consider using /generateNewToken instead");
            if (!mustDisplayLegacyApiToken(u)) {
                return HttpResponses.html(Messages.ApiTokenProperty_ChangeToken_CapabilityNotAllowed());
            }
            ApiTokenProperty p = u.getProperty(ApiTokenProperty.class);
            if (p == null) {
                p = forceNewInstance(u, true);
                p.setUser(u);
                u.addProperty(p);
            } else {
                p.changeApiToken();
            }
            rsp.setHeader("script", "document.getElementById('apiToken').value='" + p.getApiToken() + "'");
            return HttpResponses.html(p.hasPermissionToSeeToken() ? Messages.ApiTokenProperty_ChangeToken_Success() : Messages.ApiTokenProperty_ChangeToken_SuccessHidden());
        }

        @RequirePOST
        public HttpResponse doGenerateNewToken(@AncestorInPath User u, @QueryParameter String newTokenName) throws IOException {
            if (!hasCurrentUserRightToGenerateNewToken(u)) {
                return HttpResponses.forbidden();
            }
            final String tokenName;
            if (StringUtils.isBlank(newTokenName)) {
                tokenName = String.format("Token created on %s", DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(ZonedDateTime.now()));
            } else {
                tokenName = newTokenName;
            }
            ApiTokenProperty p = u.getProperty(ApiTokenProperty.class);
            if (p == null) {
                p = forceNewInstance(u, false);
                u.addProperty(p);
            }
            ApiTokenStore.TokenUuidAndPlainValue tokenUuidAndPlainValue = p.tokenStore.generateNewToken(tokenName);
            u.save();
            return HttpResponses.okJSON(new HashMap<String, String>() {

                {
                    put("tokenUuid", tokenUuidAndPlainValue.tokenUuid);
                    put("tokenName", tokenName);
                    put("tokenValue", tokenUuidAndPlainValue.plainValue);
                }
            });
        }

        @RequirePOST
        public HttpResponse doRename(@AncestorInPath User u, @QueryParameter String tokenUuid, @QueryParameter String newName) throws IOException {
            u.checkPermission(Jenkins.ADMINISTER);
            if (StringUtils.isBlank(newName)) {
                return HttpResponses.errorJSON("The name cannot be empty");
            }
            if (StringUtils.isBlank(tokenUuid)) {
                return HttpResponses.errorWithoutStack(400, "The tokenUuid cannot be empty");
            }
            ApiTokenProperty p = u.getProperty(ApiTokenProperty.class);
            if (p == null) {
                return HttpResponses.errorWithoutStack(400, "The user does not have any ApiToken yet, try generating one before.");
            }
            boolean renameOk = p.tokenStore.renameToken(tokenUuid, newName);
            if (!renameOk) {
                return HttpResponses.errorJSON("No token found, try refreshing the page");
            }
            u.save();
            return HttpResponses.ok();
        }

        @RequirePOST
        public HttpResponse doRevoke(@AncestorInPath User u, @QueryParameter String tokenUuid) throws IOException {
            u.checkPermission(Jenkins.ADMINISTER);
            if (StringUtils.isBlank(tokenUuid)) {
                return HttpResponses.errorWithoutStack(400, "The tokenUuid cannot be empty");
            }
            ApiTokenProperty p = u.getProperty(ApiTokenProperty.class);
            if (p == null) {
                return HttpResponses.errorWithoutStack(400, "The user does not have any ApiToken yet, try generating one before.");
            }
            ApiTokenStore.HashedToken revoked = p.tokenStore.revokeToken(tokenUuid);
            if (revoked != null) {
                if (revoked.isLegacy()) {
                    p.apiToken = null;
                }
                p.tokenStats.removeId(revoked.getUuid());
            }
            u.save();
            return HttpResponses.ok();
        }
    }

    @Restricted(NoExternalUse.class)
    @Deprecated
    static final public HMACConfidentialKey API_KEY_SEED = new HMACConfidentialKey(ApiTokenProperty.class, "seed", 16);

    @Restricted(NoExternalUse.class)
    public boolean hasLegacyToken() {
        return apiToken != null;
    }

    @Restricted(NoExternalUse.class)
    @Immutable
    static public class TokenInfoAndStats {

        final public String uuid;

        final public String name;

        final public Date creationDate;

        final public long numDaysCreation;

        final public boolean isLegacy;

        final public int useCounter;

        final public Date lastUseDate;

        final public long numDaysUse;

        public TokenInfoAndStats(@Nonnull ApiTokenStore.HashedToken token, @Nonnull ApiTokenStats.SingleTokenStats stats) {
            this.uuid = token.getUuid();
            this.name = token.getName();
            this.creationDate = token.getCreationDate();
            this.numDaysCreation = token.getNumDaysCreation();
            this.isLegacy = token.isLegacy();
            this.useCounter = stats.getUseCounter();
            this.lastUseDate = stats.getLastUseDate();
            this.numDaysUse = stats.getNumDaysUse();
        }
    }

    @Restricted(NoExternalUse.class)
    public ApiTokenStats getTokenStats() {
        return tokenStats;
    }

    @Restricted(NoExternalUse.class)
    public ApiTokenStore getTokenStore() {
        return tokenStore;
    }

    @Restricted(NoExternalUse.class)
    public void deleteApiToken() {
        this.apiToken = null;
    }

    private ApiTokenStore tokenStore;

    private transient ApiTokenStats tokenStats;

    @Override
    protected void setUser(User u) {
        super.setUser(u);
        if (this.tokenStore == null) {
            this.tokenStore = new ApiTokenStore();
        }
        if (this.tokenStats == null) {
            this.tokenStats = ApiTokenStats.load(user.getUserFolder());
        }
        if (this.apiToken != null) {
            this.tokenStore.regenerateTokenFromLegacyIfRequired(this.apiToken);
        }
    }

    private void addJSONTokenIntoMap(Map<String, JSONObject> tokenMap, JSONObject tokenData) {
        String uuid = tokenData.getString("tokenUuid");
        tokenMap.put(uuid, tokenData);
    }

    @Deprecated
    private static final SecureRandom RANDOM = new SecureRandom();

    @SuppressFBWarnings(value = "MS_SHOULD_BE_FINAL", justification = "Accessible via System Groovy Scripts")
    private static boolean ADMIN_CAN_GENERATE_NEW_TOKENS = SystemProperties.getBoolean(ApiTokenProperty.class.getName() + ".adminCanGenerateNewTokens");

    private Map<String, JSONObject> convertToTokenMap(Object tokenStoreData) {
        if (tokenStoreData == null) {
            return Collections.emptyMap();
        } else if (tokenStoreData instanceof JSONObject) {
            JSONObject singleTokenData = (JSONObject) tokenStoreData;
            Map<String, JSONObject> result = new HashMap<>();
            addJSONTokenIntoMap(result, singleTokenData);
            return result;
        } else if (tokenStoreData instanceof JSONArray) {
            JSONArray tokenArray = ((JSONArray) tokenStoreData);
            Map<String, JSONObject> result = new HashMap<>();
            for (int i = 0; i < tokenArray.size(); i++) {
                JSONObject tokenData = tokenArray.getJSONObject(i);
                addJSONTokenIntoMap(result, tokenData);
            }
            return result;
        }
        throw HttpResponses.error(400, "Unexpected class received for the token store information");
    }

    private static final Logger LOGGER = Logger.getLogger(ApiTokenProperty.class.getName());

    @Restricted(NoExternalUse.class)
    public Collection<TokenInfoAndStats> getTokenList() {
        return tokenStore.getTokenListSortedByName().stream().map(token -> {
            ApiTokenStats.SingleTokenStats stats = tokenStats.findTokenStatsById(token.getUuid());
            return new TokenInfoAndStats(token, stats);
        }).collect(Collectors.toList());
    }
}
