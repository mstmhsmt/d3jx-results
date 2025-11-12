package org.jasig.cas.authentication.principal;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

/**
 * @author Misagh Moayyed
 * @since 3.0.0
 */
public class SimplePrincipalFactoryTests {
  @Test public void testPrincipalCreation() {
    final PrincipalFactory fact = new DefaultPrincipalFactory();
    final Map<String, Object> map = new HashMap<String, Object>();
    map.put("a1", "v1");
    map.put("a2", "v3");
    final Principal p = fact.createPrincipal("user", map);
    assertTrue(p instanceof SimplePrincipal);
    assertEquals(p.getAttributes(), map);
  }
}


<<<<<<< Unknown file: This is a bug in JDime.
=======
/**
 * @author Scott Battaglia
 * @since 3.0.0
 */
public final class SimplePrincipalTests {
  @Test public void verifyProperId() {
    final String id = "test";
    assertEquals(id, new SimplePrincipal(id).getId());
  }

  @Test public void verifyEqualsWithNull() {
    assertFalse(new SimplePrincipal("test").equals(null));
  }

  @Test public void verifyEqualsWithBadClass() {
    assertFalse(new SimplePrincipal("test").equals("test"));
  }

  @Test public void verifyEquals() {
    assertTrue(new SimplePrincipal("test").equals(new SimplePrincipal("test")));
  }
}
>>>>>>> commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/B.java
