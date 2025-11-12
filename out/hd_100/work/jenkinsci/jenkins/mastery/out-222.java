package hudson.security;

import hudson.model.User;
import jenkins.model.Jenkins;
import hudson.util.Scrambler;
import org.acegisecurity.context.SecurityContextHolder;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.Authentication;
import jenkins.security.SecurityListener;
import jenkins.security.BasicApiTokenHelper;

public class BasicAuthenticationFilter implements Filter {

    private ServletContext servletContext;

    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
    }

    @SuppressWarnings("ACL.impersonate")
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rsp = (HttpServletResponse) response;
        String authorization = req.getHeader("Authorization");
        String path = req.getServletPath();
        if (authorization == null || req.getUserPrincipal() != null || path.startsWith("/secured/") || !Jenkins.getInstance().isUseSecurity()) {
            if (req.getUserPrincipal() != null) {
                SecurityContextHolder.getContext().setAuthentication(new ContainerAuthentication(req));
            }
            try {
                chain.doFilter(request, response);
            } finally {
                SecurityContextHolder.clearContext();
            }
            return;
        }
        String username = null;
        String password = null;
        String uidpassword = Scrambler.descramble(authorization.substring(6));
        int idx = uidpassword.indexOf(':');
        if (idx >= 0) {
            username = uidpassword.substring(0, idx);
            password = uidpassword.substring(idx + 1);
        }
        if (username == null) {
            rsp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            rsp.setHeader("WWW-Authenticate", "Basic realm=\"Jenkins user\"");
            return;
        }
        {
            User u = BasicApiTokenHelper.isConnectingUsingApiToken(username, password);
            if (u != null) {
                SecurityContextHolder.getContext().setAuthentication(u.impersonate());
                try {
                    chain.doFilter(request, response);
                } finally {
                    SecurityContextHolder.clearContext();
                }
                return;
            }
            
<<<<<<< commits-hd_100/jenkinsci/jenkins/386599c4f47164b40ab8f4dcbd3a1ff9c6970bec-11709bcf58f1be4f1b311758dd8e2b1eddc3e057/A.java
if (t != null && t.matchesPassword(password)) {
                UserDetails userDetails = u.getUserDetailsForImpersonation();
                Authentication auth = u.impersonate(userDetails);
                SecurityListener.fireAuthenticated(userDetails);
                SecurityContextHolder.getContext().setAuthentication(auth);
                try {
                    chain.doFilter(request, response);
                } finally {
                    SecurityContextHolder.clearContext();
                }
                return;
            }
=======

>>>>>>> commits-hd_100/jenkinsci/jenkins/386599c4f47164b40ab8f4dcbd3a1ff9c6970bec-11709bcf58f1be4f1b311758dd8e2b1eddc3e057/B.java

        }
        path = req.getContextPath() + "/secured" + path;
        String q = req.getQueryString();
        if (q != null)
            path += '?' + q;
        rsp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        rsp.setHeader("Location", path);
        RequestDispatcher d = servletContext.getRequestDispatcher("/j_security_check?j_username=" + URLEncoder.encode(username, "UTF-8") + "&j_password=" + URLEncoder.encode(password, "UTF-8"));
        d.include(req, rsp);
    }

    public void destroy() {
    }
}
