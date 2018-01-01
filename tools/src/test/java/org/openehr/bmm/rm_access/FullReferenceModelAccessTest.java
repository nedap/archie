package org.openehr.bmm.rm_access;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.rmaccess.ReferenceModelAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FullReferenceModelAccessTest {

    private List<String> schemaDirectories;

    @Before
    public void setup() {
        schemaDirectories = new ArrayList<>();
        String path = getClass().getResource("/bmm/placeholder.txt").getFile();
        path = path.substring(0, path.lastIndexOf('/'));
        schemaDirectories.add(path);
    }

    @Test
    public void getValidModels() throws Exception {
        ReferenceModelAccess access = new ReferenceModelAccess();
        access.initializeAll(schemaDirectories);
        Map<String, BmmModel> models = access.getValidModels();
        assertFalse(access.getValidator().hasErrors());
        //if we don't set the top level schema, it has warnings, apparently
        assertTrue(access.getValidator().hasWarnings());
        assertEquals(2, models.size());
        assertEquals(Sets.newHashSet("cimi_rm_3.0.5", "cimi_rm_clinical_0.0.2"), models.keySet());
    }
}
