package com.nedap.archie.flattener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CComplexObjectProxy;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.query.APathQuery;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

/**
 * TODO: all this tests is that the flattener runs, with some diagnostic JSON-output. Implement way more tests.
 *
 *
 * Created by pieter.bos on 21/10/15.
 */
public class FlattenerTest {

    Archetype report;
    Archetype device;
    Archetype bloodPressureObservation;
    Archetype reportResult;
    Archetype bloodPressureComposition;
    Archetype height;
    Archetype heightTemplate;
    SimpleArchetypeRepository repository;

    Flattener flattener;

    @Before
    public void setup() throws Exception {

        // reportresult specializes report.
        // blood pressure composition specializes report result.

        // it adds a blood pressure observation
        // it also adds a device
        // it contains specific template overlays for both blood pressure observation and device
        report = new ADLParser().parse(FlattenerTest.class.getResourceAsStream("openEHR-EHR-COMPOSITION.report.v1.adls"));
        reportResult = new ADLParser().parse(FlattenerTest.class.getResourceAsStream("openEHR-EHR-COMPOSITION.report-result.v1.adls"));
        device = new ADLParser().parse(FlattenerTest.class.getResourceAsStream("openEHR-EHR-CLUSTER.device.v1.adls"));

        bloodPressureObservation = new ADLParser().parse(FlattenerTest.class.getResourceAsStream("openEHR-EHR-OBSERVATION.blood_pressure.v1.adls"));
        bloodPressureComposition = new ADLParser().parse(FlattenerTest.class.getResourceAsStream("openEHR-EHR-COMPOSITION.blood_pressure.v1.0.0.adlt"));


        height = new ADLParser().parse(FlattenerTest.class.getResourceAsStream("openEHR-EHR-OBSERVATION.height.v1.adls"));
        heightTemplate = new ADLParser().parse(FlattenerTest.class.getResourceAsStream("openEHR-EHR-COMPOSITION.length.v1.0.0.adlt"));

        repository = new SimpleArchetypeRepository();
        repository.addArchetype(report);
        repository.addArchetype(device);
        repository.addArchetype(bloodPressureComposition);
        repository.addArchetype(bloodPressureObservation);
        repository.addArchetype(reportResult);
        repository.addArchetype(height);
        repository.addArchetype(heightTemplate);

        flattener = new Flattener(repository).createOperationalTemplate(true);
    }

    @Test
    public void reportResult() throws Exception {
        Archetype flattened = flattener.flatten(reportResult);
        //TODO: nice to know there are no exceptions. Now add assertions
    }


    @Test
    public void checkParentReplacement() throws Exception {
        Archetype flattened = flattener.flatten(bloodPressureComposition);

        Stack<CObject> worklist = new Stack();
        worklist.add(flattened.getDefinition());

        while(!worklist.isEmpty()) {
            CObject object = worklist.pop();
            if(object.getArchetype() != flattened) {
                fail("wrong parent found!");
            }
            for(CAttribute attr:object.getAttributes()) {
                if(attr.getParent() != object) {
                    fail("wrong parent found!");
                }
                worklist.addAll(attr.getChildren());
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        System.out.println(objectMapper.writeValueAsString(flattened));

    }


    @Test
    public void useNodeReplacement() throws Exception {
        Archetype flattened = flattener.flatten(bloodPressureComposition);

        Stack<CObject> worklist = new Stack();
        worklist.add(flattened.getDefinition());
        boolean proxyFound = false;
        while(!worklist.isEmpty()) {
            CObject object = worklist.pop();
            if(object.getNodeId().equals("id1065")) {
                assertFalse(object instanceof CComplexObjectProxy);
                proxyFound = true;
                assertNotNull(object.getAttribute("items").getChild("id5"));
            }
            for(CAttribute attr:object.getAttributes()) {
                if(attr.getParent() != object) {
                    fail("wrong parent found!");
                }
                worklist.addAll(attr.getChildren());
            }
        }
        assertTrue("a prox object should have been found", proxyFound);
    }

    @Test
    public void testComponentTerminologies() throws Exception {
        Archetype flattened = flattener.flatten(bloodPressureComposition);
        CComplexObject definition = flattened.getDefinition();
        //you definately need component terminologies to translate this :)
        CObject object = (CObject)
                new APathQuery("/content[openEHR-EHR-OBSERVATION.ovl-blood_pressure-blood_pressure-001.v1.0.0]/protocol[id12]/items[id1011]")
                .find(definition);
        assertNull(flattened.getTerminology().getTermDefinition("en", "id1011"));
        assertEquals("Diastolic endpoint", flattened.getTerm(object, "en").getText());
    }

    @Test
    public void height() throws Exception {
        Archetype flattened = flattener.flatten(heightTemplate);

        CObject object = (CObject) new APathQuery("/content[openEHR-EHR-OBSERVATION.ovl-length-height-001.v1.0.0]").find(flattened.getDefinition());
        assertNotNull(object);
        assertNotNull(flattened.getTerm(object, "nl"));
        assertEquals("Lengte", flattened.getTerm(object, "nl").getText());
/*
data matches {
			HISTORY[id2] matches {
				events cardinality matches {1..*; unordered} matches {
					EVENT[id3] occurrences matches {1..*} matches {	-- Any event
						data matches {
							ITEM_TREE[id4] matches {
								items cardinality matches {1..*; unordered} matches {
									ELEMENT[id5] matches {	-- Height/Length
										value matches {
											DV_QUANTITY[id21] matches {
 */
        CObject quantity = (CObject) new APathQuery("/content[openEHR-EHR-OBSERVATION.ovl-length-height-001.v1.0.0]/data[id2]/events[id3]/data[id4]/items[id5]/value[id21]").find(flattened.getDefinition());
        assertEquals("DV_QUANTITY", quantity.getRmTypeName());


    }
}
