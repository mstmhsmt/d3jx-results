/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.cas.authentication.principal;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Misagh Moayyed
 * @since 3.0.0
 */
<<<<<<< commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/A.java
public class SimplePrincipalFactoryTests {
||||||| commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/O.java
public final class SimplePrincipalTests  {

    @Test
    public void testProperId() {
        final String id = "test";
        assertEquals(id, new SimplePrincipal(id).getId());
    }

    @Test
    public void testEqualsWithNull() {
        assertFalse(new SimplePrincipal("test").equals(null));
    }

    @Test
    public void testEqualsWithBadClass() {
        assertFalse(new SimplePrincipal("test").equals("test"));
    }

=======
public final class SimplePrincipalTests  {

    @Test
    public void verifyProperId() {
        final String id = "test";
        assertEquals(id, new SimplePrincipal(id).getId());
    }

    @Test
    public void verifyEqualsWithNull() {
        assertFalse(new SimplePrincipal("test").equals(null));
    }

    @Test
    public void verifyEqualsWithBadClass() {
        assertFalse(new SimplePrincipal("test").equals("test"));
    }

>>>>>>> commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/B.java
    @Test
<<<<<<< commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/A.java
    public void testPrincipalCreation() {
        final PrincipalFactory fact = new DefaultPrincipalFactory();
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("a1", "v1");
        map.put("a2", "v3");

        final Principal p = fact.createPrincipal("user", map);
        assertTrue(p instanceof SimplePrincipal);
        assertEquals(p.getAttributes(), map);
||||||| commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/O.java
    public void testEquals() {
        assertTrue(new SimplePrincipal("test").equals(new SimplePrincipal(
            "test")));
=======
    public void verifyEquals() {
        assertTrue(new SimplePrincipal("test").equals(new SimplePrincipal(
            "test")));
>>>>>>> commits-hd_100/apereo/cas/838c1ed338f73117b3ed51f763d0a498d5364331-5774d7d4f3fa15b76f9eb10accf88fa4b6b18b73/B.java
    }
}
