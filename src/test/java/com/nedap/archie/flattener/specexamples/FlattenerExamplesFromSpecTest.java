package com.nedap.archie.flattener.specexamples;

import com.google.common.collect.Lists;
import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.base.MultiplicityInterval;
import com.nedap.archie.flattener.Flattener;
import com.nedap.archie.flattener.FlattenerTest;
import com.nedap.archie.flattener.SimpleArchetypeRepository;
import com.nedap.archie.rm.datavalues.DvText;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

public class FlattenerExamplesFromSpecTest {

    private static Archetype labTest;
    private static SimpleArchetypeRepository repository;

    @Before
    public void setup() throws Exception {
        labTest = parse("openEHR-EHR-OBSERVATION.lab-test.v1.0.0.adls");
        repository = new SimpleArchetypeRepository();
        repository.addArchetype(labTest);
    }

    @Test
    public void specializationPaths() throws Exception {
        Archetype specializationPaths = parse("specialization_paths.adls");
        Archetype flattened = new Flattener(repository).flatten(specializationPaths);

        CObject originalConstraint = flattened.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id79]");
        CObject firstAddedConstraint = flattened.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id79.2]");
        CObject secondAddedConstraint = flattened.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id79.7]");

        assertNotNull("first constraint should have been added", firstAddedConstraint);
        assertEquals(new MultiplicityInterval(0, 1), firstAddedConstraint.getOccurrences());
        assertNotNull("added constraint should have a value attribute", firstAddedConstraint.getAttribute("value"));

        assertNotNull("second constraint should have been added", secondAddedConstraint);
        assertEquals(new MultiplicityInterval(0, 1), secondAddedConstraint.getOccurrences());
        assertNull("second added constraint should not have a value attribute", secondAddedConstraint.getAttribute("value"));

        //original constraint should not have been closed and still be present
        assertNotNull("original constraint should not have been removed", originalConstraint);
        assertNull("original constraint should not have been closed", originalConstraint.getOccurrences());
    }

    @Test
    public void redefinitionForRefinement() throws Exception {
        Archetype problem = parse("problem.adls");
        repository.addArchetype(problem);
        Archetype diagnosis = parse("diagnosis.adls");
        Archetype flattenedDiagnosis = new Flattener(repository).flatten(diagnosis);

        assertEquals("Recording of diagnosis", flattenedDiagnosis.getDefinition().getTerm().getText());

        CObject nodeWithId4 = (CObject) flattenedDiagnosis.itemAtPath("/data[id2]/items[id3]/value[id4]");
        assertEquals("DV_CODED_TEXT", nodeWithId4.getRmTypeName());
        assertTrue("dv coded text should now have a terminology code constraint",
                nodeWithId4.getAttribute("defining_code").getChildren().get(0) instanceof CTerminologyCode);


        CObject firstAddedNode = flattenedDiagnosis.itemAtPath("/data/items[id0.32]");
        CObject secondAddedNode = flattenedDiagnosis.itemAtPath("/data/items[id0.35]");
        CObject thirdAddedNode = flattenedDiagnosis.itemAtPath("/data/items[id0.37]");

        assertEquals("Status", firstAddedNode.getTerm().getText());
        assertEquals("Diag. criteria", secondAddedNode.getTerm().getText());
        assertEquals("Clin. staging", thirdAddedNode.getTerm().getText());

        List<CObject> items = flattenedDiagnosis.getDefinition().getAttribute("data").getChild("id2").getAttribute("items").getChildren();

        //verify the ordering specified by before and after. TODO: or is this operational template only?
        assertEquals("id3", items.get(0).getNodeId());
        assertEquals("id0.32", items.get(1).getNodeId());
        assertEquals("id5", items.get(2).getNodeId());
        assertEquals("id0.37", items.get(items.size() -1).getNodeId());
        assertEquals("id0.35", items.get(items.size() -2).getNodeId());

    }

    //9.3.2. Redefinition for Specialisation has been fully covered by specialization paths and is not needed in tests

    //9.3.2.1. Specialisation with Cloning
    @Test
    public void specialisationWithCloning() throws Exception {
        Archetype labTestPanel = parse("openEHR-EHR-CLUSTER.laboratory_test_panel.v1.0.0.adls");
        repository.addArchetype(labTestPanel);
        Archetype lipidStudiesPanel = parse("openEHR-EHR-CLUSTER.lipid_studies_panel.adls");
        Archetype flattenedLipidStudies = new Flattener(repository).flatten(lipidStudiesPanel);

        for(String nodeId: Lists.newArrayList("1", "2", "5")) {

            assertNotNull("node id3." + nodeId + " should have subnode id4",
                    flattenedLipidStudies.itemAtPath(String.format("/items[id3.%s]/items[id4]", nodeId)));

            assertNotNull("node id3." + nodeId + " should have subnode id2." + nodeId,
                    flattenedLipidStudies.itemAtPath(String.format("/items[id3.%s]/items[id2.%s]", nodeId, nodeId)));

            assertNull("node id3." + nodeId + " should have subnode id2." + nodeId,
                    flattenedLipidStudies.itemAtPath(String.format("/items[id3.%s]/items[id2]", nodeId)));

        }

    }


    private Archetype parse(String filename) throws IOException {
        ADLParser parser = ADLParser.withRMConstraintsImposer();
        Archetype result = parser.parse(FlattenerExamplesFromSpecTest.class.getResourceAsStream(filename));
        assertTrue("there should be no errors parsing " + filename + ", but was: " + parser.getErrors(), parser.getErrors().hasNoMessages());
        return result;
    }

}
