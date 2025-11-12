package org.jasig.cas.adaptors.jdbc;

import java.security.GeneralSecurityException;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import javax.security.auth.login.FailedLoginException;
import javax.validation.constraints.NotNull;

public class SearchModeSearchDatabaseAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler implements InitializingBean {

    private static final String SQL_PREFIX = "Select count('x') from ";

    @NotNull
    private String fieldUser;

    @NotNull
    private String fieldPassword;

    @NotNull
    private String tableUsers;

    private String sql;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.sql = SQL_PREFIX + this.tableUsers + " WHERE " + this.fieldUser + " = ? AND " + this.fieldPassword + " = ?";
    }

    final public void setFieldPassword(final String fieldPassword) {
        this.fieldPassword = fieldPassword;
    }

    final public void setFieldUser(final String fieldUser) {
        this.fieldUser = fieldUser;
    }

    final public void setTableUsers(final String tableUsers) {
        this.tableUsers = tableUsers;
    }

    @Override
    final protected HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential) throws GeneralSecurityException, PreventedException {
        final String username = credential.getUsername();
        final String encyptedPassword = getPasswordEncoder().encode(credential.getPassword());
        final int count;
        try {
            count = getJdbcTemplate().queryForObject(this.sql, Integer.class, username, encyptedPassword);
        } catch (final DataAccessException e) {
            throw new PreventedException("SQL exception while executing query for " + username, e);
        }
        if (count == 0) {
            throw new FailedLoginException(username + " not found with SQL query.");
        }
        return createHandlerResult(credential, this.principalFactory.createPrincipal(username), null);
    }
}
