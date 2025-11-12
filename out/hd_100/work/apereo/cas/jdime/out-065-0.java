package org.jasig.cas.authentication.principal;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;


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
