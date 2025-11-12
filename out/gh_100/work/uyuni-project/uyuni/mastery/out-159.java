package com.redhat.rhn.common.conf.test;

import com.redhat.rhn.common.conf.Config;
import com.redhat.rhn.testing.RhnBaseTestCase;
import com.redhat.rhn.testing.TestUtils;
import java.io.File;
import java.util.Iterator;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import java.util.ArrayList;

public class ConfigTest extends RhnBaseTestCase {

    static final String TEST_KEY = "user";

    static final String TEST_VALUE = "newval";

    private Config c;

    public void setUp() throws Exception {
        c = new Config();
        String confPath = "/tmp/" + TestUtils.randomString();
        new File(confPath + "/conf/default").mkdirs();
        ArrayList<String> paths = new ArrayList<>();
        paths.add("conf/rhn.conf");
        paths.add("conf/default/rhn_web.conf");
        paths.add("conf/default/rhn_prefix.conf");
        paths.add("conf/default/bug154517.conf.rpmsave");
        for (String relPath : paths) {
            try {
                FileUtils.copyURLToFile(TestUtils.findTestData(relPath), new File(confPath, relPath));
            } catch (NullPointerException e) {
                FileUtils.copyFile(new File(TEST_CONF_LOCATION + relPath), new File(confPath, relPath));
            }
        }
        c.addPath(confPath + "/conf");
        c.addPath(confPath + "/conf/default");
        c.parseFiles();
    }

    public void testGetFullyQualified() {
        assertEquals("this is a property with a prefix", c.getString("web.property_with_prefix"));
        assertEquals("this is a property without a prefix", c.getString("web.without_prefix"));
    }

    public void testGetByPropertyNameOnly() {
        assertEquals("this is a property with a prefix", c.getString("property_with_prefix"));
        assertEquals("this is a property without a prefix", c.getString("without_prefix"));
    }

    public void testOverride() throws Exception {
        assertEquals("keep", c.getString("web.to_override"));
    }

    public void testOverride1() throws Exception {
        assertEquals("keep", c.getString("to_override"));
    }

    public void testOverride2() throws Exception {
        assertEquals("1", c.getString("web.fq_to_override"));
    }

    public void testOverride3() throws Exception {
        assertEquals("1", c.getString("fq_to_override"));
    }

    public void testOverride4() {
        assertEquals("overridden", c.getString("web.to_override_without_prefix"));
        assertEquals("overridden", c.getString("to_override_without_prefix"));
    }

    public void testOverride5() {
        assertEquals("overridden", c.getString("to_override_without_prefix1"));
        assertEquals("overridden", c.getString("web.to_override_without_prefix1"));
    }

    public void testCollision() {
        assertEquals("10", c.getString("web.collision"));
        assertEquals("12", c.getString("prefix.collision"));
    }

    public void testPrefixOrder() {
        assertEquals("10", c.getString("collision"));
    }

    public void testGetStringArray1Elem() throws Exception {
        String[] elems = c.getStringArray("prefix.array_one_element");
        assertEquals(1, elems.length);
        assertEquals("some value", elems[0]);
    }

    public void testGetStringArrayNull() throws Exception {
        String[] elems = c.getStringArray("find.this.entry.b****");
        assertNull(elems);
    }

    public void testGetBoolean() throws Exception {
        boolean b = c.getBoolean("prefix.boolean_true");
        assertTrue(b);
        assertFalse(c.getBoolean("prefix.boolean_false"));
        assertTrue(c.getBoolean("prefix.boolean_1"));
        assertFalse(c.getBoolean("prefix.boolean_0"));
        assertTrue(c.getBoolean("prefix.boolean_y"));
        assertTrue(c.getBoolean("prefix.boolean_Y"));
        assertFalse(c.getBoolean("prefix.boolean_n"));
        assertTrue(c.getBoolean("prefix.boolean_on"));
        assertFalse(c.getBoolean("prefix.boolean_off"));
        assertTrue(c.getBoolean("prefix.boolean_yes"));
        assertFalse(c.getBoolean("prefix.boolean_no"));
        assertFalse(c.getBoolean("prefix.boolean_foo"));
        assertFalse(c.getBoolean("prefix.boolean_10"));
        assertFalse(c.getBoolean("prefix.boolean_empty"));
        assertFalse(c.getBoolean("prefix.boolean_not_there"));
        assertTrue(c.getBoolean("prefix.boolean_on"));
        assertFalse(c.getBoolean("prefix.boolean_off"));
    }

    public void testGetIntWithDefault() {
        assertEquals(1000, c.getInt("value.doesnotexist", 1000));
        assertEquals(100, c.getInt("prefix.int_100", 1000));
    }

    public void testGetInt() throws Exception {
        int i = c.getInt("prefix.int_minus10");
        assertEquals(-10, i);
        assertEquals(0, c.getInt("prefix.int_zero"));
        assertEquals(100, c.getInt("prefix.int_100"));
        boolean flag = false;
        try {
            c.getInt("prefix.int_y");
            flag = true;
        } catch (NumberFormatException nfe) {
            assertFalse(flag);
        }
    }

    public void testGetInteger() throws Exception {
        assertEquals(new Integer(-10), c.getInteger("prefix.int_minus10"));
        assertEquals(new Integer(0), c.getInteger("prefix.int_zero"));
        assertEquals(new Integer(100), c.getInteger("prefix.int_100"));
        assertNull(c.getInteger(null));
        assertEquals(c.getInt("prefix.int_100"), c.getInteger("prefix.int_100").intValue());
        boolean flag = false;
        try {
            c.getInteger("prefix.int_y");
            flag = true;
        } catch (NumberFormatException nfe) {
            assertFalse(flag);
        }
    }

    public void testGetStringArrayMultElem() throws Exception {
        String[] elems = c.getStringArray("prefix.comma_separated");
        assertEquals(5, elems.length);
        assertEquals("every", elems[0]);
        assertEquals("good", elems[1]);
        assertEquals("boy", elems[2]);
        assertEquals("does", elems[3]);
        assertEquals("fine", elems[4]);
    }

    public void testGetStringArrayWhitespace() {
        String[] elems = c.getStringArray("prefix.comma_no_trim");
        assertEquals(5, elems.length);
        assertEquals("every", elems[0]);
        assertEquals(" good ", elems[1]);
        assertEquals(" boy ", elems[2]);
        assertEquals(" does", elems[3]);
        assertEquals("fine", elems[4]);
    }

    public void testSetBoolean() throws Exception {
        boolean oldValue = c.getBoolean("prefix.boolean_true");
        c.setBoolean("prefix.boolean_true", Boolean.FALSE.toString());
        assertFalse(c.getBoolean("prefix.boolean_true"));
        assertEquals("0", c.getString("prefix.boolean_true"));
        c.setBoolean("prefix.boolean_true", new Boolean(oldValue).toString());
    }

    public void testSetString() throws Exception {
        String oldValue = c.getString("to_override");
        c.setString("to_override", "newValue");
        assertEquals("newValue", c.getString("to_override"));
        c.setString("to_override", oldValue);
    }

    public void testGetUndefinedInt() throws Exception {
        int zero = c.getInt("Undefined_config_variable");
        assertEquals(0, zero);
    }

    public void testGetUndefinedString() {
        assertNull(c.getString("Undefined_config_variable"));
    }

    public void testNewValue() {
        String key = "newvalue" + TestUtils.randomString();
        c.setString(key, "somevalue");
        assertNotNull(c.getString(key));
    }

    public void testGetUndefinedBoolean() {
        assertFalse(c.getBoolean("Undefined_config_variable"));
    }

    public void testUnprefixedProperty() {
        assertEquals("thirty-three", c.getString("prefix.foo"));
        assertNull(c.getString("foo"));
    }

    public void testNamespaceProperties() throws Exception {
        Properties prop = c.getNamespaceProperties("web");
        assertTrue(prop.size() >= 8);
        for (Iterator i = prop.keySet().iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            assertTrue(key.startsWith("web"));
        }
    }

    public void testBug154517IgnoreRpmsave() {
        assertNull(c.getString("bug154517.conf.betternotfindme"));
        assertNull(c.getString("betternotfindme"));
    }

    public void testDefaultValueQuoteQuote() {
        Config.get().setString("somevalue8923984", "");
        assertNull(Config.get().getString("somevalue8923984"));
        String somevalue = Config.get().getString("somevalue8923984", "xmlrpc.rhn.redhat.com");
        assertNotNull(somevalue);
        assertFalse(somevalue.equals(""));
        assertTrue(somevalue.equals("xmlrpc.rhn.redhat.com"));
    }

    public void testForNull() {
        assertNull(c.getString(null));
        assertNull(c.getInteger(null));
        assertEquals(0, c.getInt(null));
        assertFalse(c.getBoolean(null));
        assertNull(c.getStringArray(null));
    }

    public void testComment() {
        assertEquals("#this will NOT be a comment!", c.getString("server.satellite.key_with_seeming_comment"));
    }

    public void testBackSlashes() {
        assertEquals("we\\have\\backslashes", c.getString("server.satellite.key_with_backslash"));
    }

    static final String TEST_CONF_LOCATION = "/usr/share/rhn/unit-tests/";
}
