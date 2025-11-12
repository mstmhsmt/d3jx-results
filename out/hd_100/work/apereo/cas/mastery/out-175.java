package org.jasig.cas.services;

import org.jasig.cas.authentication.principal.Service;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.jasig.cas.authentication.principal.Principal;
import static org.mockito.Mockito.*;

public class AbstractRegisteredServiceTests {

    private final AbstractRegisteredService r = new AbstractRegisteredService() {

        private final static long serialVersionUID = 1L;

        public void setServiceId(final String id) {
            serviceId = id;
        }

        protected AbstractRegisteredService newInstance() {
            return this;
        }

        public boolean matches(final Service service) {
            return true;
        }
    };

    @Test
    public void testAllowToProxyIsFalseByDefault() {
        final RegexRegisteredService regexRegisteredService = new RegexRegisteredService();
        assertFalse(regexRegisteredService.getProxyPolicy().isAllowedToProxy());
        final RegisteredServiceImpl registeredServiceImpl = new RegisteredServiceImpl();
        assertFalse(registeredServiceImpl.getProxyPolicy().isAllowedToProxy());
    }

    @Test
    public void testSettersAndGetters() {
        prepareService();
        this.r.setProxyPolicy(new RefuseRegisteredServiceProxyPolicy());
        assertEquals(ALLOWED_TO_PROXY, this.r.getProxyPolicy().isAllowedToProxy());
        assertEquals(ANONYMOUS_ACCESS, this.r.isAnonymousAccess());
        assertEquals(DESCRIPTION, this.r.getDescription());
        assertEquals(ENABLED, this.r.isEnabled());
        assertEquals(ID, this.r.getId());
        assertEquals(NAME, this.r.getName());
        assertEquals(SERVICEID, this.r.getServiceId());
        assertEquals(SSO_ENABLED, this.r.isSsoEnabled());
        assertEquals(THEME, this.r.getTheme());
        assertFalse(this.r.equals(null));
        assertFalse(this.r.equals(new Object()));
        assertTrue(this.r.equals(this.r));
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(r.equals(r.clone()));
        assertFalse(new RegisteredServiceImpl().equals(null));
        assertFalse(new RegisteredServiceImpl().equals(new Object()));
    }

    private final static boolean SSO_ENABLED = false;

    private final static boolean ANONYMOUS_ACCESS = true;

    private final static boolean ALLOWED_TO_PROXY = false;

    private final static boolean ENABLED = false;

    private final static String NAME = "name";

    private final static String THEME = "theme";

    private void prepareService() {
        this.r.setAllowedToProxy(ALLOWED_TO_PROXY);
        this.r.setAnonymousAccess(ANONYMOUS_ACCESS);
        this.r.setDescription(DESCRIPTION);
        this.r.setEnabled(ENABLED);
        this.r.setId(ID);
        this.r.setName(NAME);
        this.r.setServiceId(SERVICEID);
        this.r.setSsoEnabled(SSO_ENABLED);
        this.r.setTheme(THEME);
    }

    private final static String SERVICEID = "serviceId";

    private final static String DESCRIPTION = "test";

    private final static long ID = 1000;

    @Test
    public void testServiceAttributeFilterAllAttributes() {
        prepareService();
        this.r.setAttributeReleasePolicy(new ReturnAllAttributeReleasePolicy());
        final Principal p = mock(Principal.class);
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("attr1", "value1");
        map.put("attr2", "value2");
        map.put("attr3", Arrays.asList("v3", "v4"));
        when(p.getAttributes()).thenReturn(map);
        when(p.getId()).thenReturn("principalId");
        final Map<String, Object> attr = this.r.getAttributeReleasePolicy().getAttributes(p);
        assertEquals(attr.size(), map.size());
    }

    @Test
    public void testServiceAttributeFilterAllowedAttributes() {
        prepareService();
        final ReturnAllowedAttributeReleasePolicy policy = new ReturnAllowedAttributeReleasePolicy();
        policy.setAllowedAttributes(Arrays.asList("attr1", "attr3"));
        this.r.setAttributeReleasePolicy(policy);
        final Principal p = mock(Principal.class);
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("attr1", "value1");
        map.put("attr2", "value2");
        map.put("attr3", Arrays.asList("v3", "v4"));
        when(p.getAttributes()).thenReturn(map);
        when(p.getId()).thenReturn("principalId");
        final Map<String, Object> attr = this.r.getAttributeReleasePolicy().getAttributes(p);
        assertEquals(attr.size(), 2);
        assertTrue(attr.containsKey("attr1"));
        assertTrue(attr.containsKey("attr3"));
    }

    @Test
    public void testServiceAttributeFilterMappedAttributes() {
        prepareService();
        final ReturnMappedAttributeReleasePolicy policy = new ReturnMappedAttributeReleasePolicy();
        final Map<String, String> mappedAttr = new HashMap<String, String>();
        mappedAttr.put("attr1", "newAttr1");
        policy.setAllowedAttributes(mappedAttr);
        this.r.setAttributeReleasePolicy(policy);
        final Principal p = mock(Principal.class);
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("attr1", "value1");
        map.put("attr2", "value2");
        map.put("attr3", Arrays.asList("v3", "v4"));
        when(p.getAttributes()).thenReturn(map);
        when(p.getId()).thenReturn("principalId");
        final Map<String, Object> attr = this.r.getAttributeReleasePolicy().getAttributes(p);
        assertEquals(attr.size(), 1);
        assertTrue(attr.containsKey("newAttr1"));
    }
}
