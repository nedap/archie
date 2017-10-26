package org.openehr.bmm.persistence;

import org.junit.Before;
import org.junit.Test;
import org.openehr.bmm.persistence.validation.BmmSchemaValidator;

import static org.junit.Assert.*;

public class PersistedBmmSchemaTest {

    @Before
    public void setup() {}

    @Test
    public void loadFinalize() throws Exception {
        PersistedBmmSchema schema = buildSimpleSchema();
        schema.getBmmSchemaValidator().validateCreated();
        schema.loadFinalize();
        assertEquals("parent", schema.getCanonicalPackages().get("PARENT").getName());
        assertEquals("sibling", schema.getCanonicalPackages().get("SIBLING").getName());
    }

    @Test
    public void hasCanonicalPackagePath() throws Exception {
        PersistedBmmSchema schema = buildSimpleSchema();
        schema.getBmmSchemaValidator().validateCreated();
        schema.loadFinalize();
        assertTrue(schema.hasCanonicalPackagePath("sibling"));
        assertTrue(schema.hasCanonicalPackagePath("parent"));
        assertTrue(schema.hasCanonicalPackagePath("parent.child1"));
        assertTrue(schema.hasCanonicalPackagePath("parent.child2"));
        assertTrue(schema.hasCanonicalPackagePath("parent.child2.grandchild2_1"));
        assertFalse(schema.hasCanonicalPackagePath(""));
        assertFalse(schema.hasCanonicalPackagePath(null));
        assertFalse(schema.hasCanonicalPackagePath("child1"));
        assertFalse(schema.hasCanonicalPackagePath("idontexist1"));

    }

    /**
     * Creates a schema with the following package path
     * /parent/child1
     * /parent/child2
     * /sibling
     *
     * @return
     */
    public PersistedBmmSchema buildSimpleSchema() {
        PersistedBmmSchema schema = new PersistedBmmSchema();
        PersistedBmmPackage parent = new PersistedBmmPackage("parent");
        PersistedBmmPackage child1 = new PersistedBmmPackage("child1");
        PersistedBmmPackage child2 = new PersistedBmmPackage("child2");
        PersistedBmmPackage grandchild2_1 = new PersistedBmmPackage("grandchild2_1");
        PersistedBmmPackage sibling = new PersistedBmmPackage("sibling");
        schema.addPackage(parent);
        schema.addPackage(sibling);
        parent.addPackage(child1);
        parent.addPackage(child2);
        child2.addPackage(grandchild2_1);
        BmmSchemaValidator validator = new BmmSchemaValidator(schema);
        schema.setBmmSchemaValidator(validator);

        return schema;
    }

}