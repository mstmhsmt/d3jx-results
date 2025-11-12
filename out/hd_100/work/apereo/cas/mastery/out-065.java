package org.jasig.cas.authentication.principal;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class SimplePrincipalFactoryTests {

    
<<<<<<< commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/A.java

=======
@Test
    public void verifyProperId() {
        final String id = "test";
        assertEquals(id, new SimplePrincipal(id).getId());
    }
>>>>>>> commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/B.java


    
<<<<<<< commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/A.java

=======
@Test
    public void verifyEqualsWithNull() {
        assertFalse(new SimplePrincipal("test").equals(null));
    }
>>>>>>> commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/B.java


    
<<<<<<< commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/A.java

=======
@Test
    public void verifyEqualsWithBadClass() {
        assertFalse(new SimplePrincipal("test").equals("test"));
    }
>>>>>>> commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/B.java


    
<<<<<<< commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/A.java

=======
@Test
    public void verifyEquals() {
        assertTrue(new SimplePrincipal("test").equals(new SimplePrincipal("test")));
    }
>>>>>>> commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/B.java


    @Test
    public void testPrincipalCreation() {
        final PrincipalFactory fact = new DefaultPrincipalFactory();
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("a1", "v1");
        map.put("a2", "v3");
        final Principal p = fact.createPrincipal("user", map);
        assertTrue(p instanceof SimplePrincipal);
        assertEquals(p.getAttributes(), map);
    }
}
