package com.nedap.archie.adlparser;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CAttributeTuple;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.primitives.CString;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.query.APathQuery;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by pieter.bos on 19/10/15.
 */
public class DefinitionTest {

    @Test
    public void definition() throws Exception {
        Archetype archetype = new ADLParser().parse(getClass().getResourceAsStream("/basic.adl"));
        CComplexObject definition = archetype.getDefinition();
        assertEquals("COMPOSITION", definition.getRmTypeName());
        assertEquals("id1", definition.getNodeId());
        assertNull(definition.getOccurrences());

        List<CAttribute> attributes = definition.getAttributes();
        assertEquals(3, attributes.size());
        CAttribute category = definition.getAttribute("category");
        assertNull(category.getCardinality());
        List<CObject> categoryChildren = category.getChildren();
        assertEquals(1, categoryChildren.size());
        CComplexObject categoryDefinition = (CComplexObject) categoryChildren.get(0);
        assertEquals("DV_CODED_TEXT", categoryDefinition.getRmTypeName());
        assertEquals("id10", categoryDefinition.getNodeId());
        assertNull(categoryDefinition.getOccurrences());
        assertNull(categoryDefinition.getDefaultValue());
        CTerminologyCode code = (CTerminologyCode) categoryDefinition.getAttribute("defining_code").getChildren().get(0);

        assertNull(code.getAssumedValue());
        assertEquals(1, code.getConstraint().size());
        assertEquals("at1", code.getConstraint().get(0));
    }

    @Test
    public void deeperPath() throws Exception {
        /*
        context matches {
			EVENT_CONTEXT[id11] matches {
				other_context matches {
					ITEM_TREE[id2] matches {
						items matches {
							CLUSTER[id3] occurrences matches {0..*} matches {	-- Qualification
								items matches {
									ELEMENT[id4] occurrences matches {0..1} matches {	-- OrderID
										value matches {
											DV_EHR_URI[id12]
											DV_IDENTIFIER[id13]
         */
        Archetype archetype = new ADLParser().parse(getClass().getResourceAsStream("/basic.adl"));

        APathQuery query =
                new APathQuery("/context[id11]/other_context[id2]/items[id3]/items[id4]/value");

        ArchetypeModelObject archetypeModelObject = query.find(archetype.getDefinition());

        assertTrue(archetypeModelObject instanceof CAttribute);
        CAttribute attribute = (CAttribute) archetypeModelObject;
        assertEquals(2, attribute.getChildren().size());

        CObject child0 = attribute.getChildren().get(0);
        assertEquals("DV_EHR_URI", child0.getRmTypeName());
        assertEquals("id12", child0.getNodeId());

        CObject child1 = attribute.getChildren().get(1);
        assertEquals("DV_IDENTIFIER", child1.getRmTypeName());
        assertEquals("id13", child1.getNodeId());
    }

    @Test
    public void stringConstraint() throws Exception {
        Archetype archetype = new ADLParser().parse(getClass().getResourceAsStream("/basic.adl"));

        APathQuery query =
                new APathQuery("/context[id11]/other_context[id2]/items[id3]/items[id5]/value[id14]/value");

        ArchetypeModelObject archetypeModelObject = query.find(archetype.getDefinition());

        assertEquals(CAttribute.class, archetypeModelObject.getClass());
        CAttribute stringAttribute = (CAttribute) archetypeModelObject;
        assertEquals(1, stringAttribute.getChildren().size());
        CString stringConstraint = (CString) stringAttribute.getChildren().get(0);
        List<String> namesList = new ArrayList<>();
        namesList.add("Robert");
        namesList.add("Rick");
        namesList.add("Clara");
        assertEquals(namesList, stringConstraint.getConstraint());

    }

    @Test
    public void tupleConstraint() throws Exception {
        Archetype archetype = new ADLParser().parse(getClass().getResourceAsStream("/basic.adl"));

        APathQuery query =
                new APathQuery("/context[id11]/other_context[id2]/items[id3]/items[id7]/value[id16]");

        ArchetypeModelObject archetypeModelObject = query.find(archetype.getDefinition());

        assertEquals(CComplexObject.class, archetypeModelObject.getClass());
        CComplexObject rootObject = (CComplexObject) archetypeModelObject;
        assertEquals("DV_QUANTITY", rootObject.getRmTypeName());
        assertEquals(1, rootObject.getAttributeTuples().size());

        List<CAttribute> attributes = rootObject.getAttributes();
        assertEquals(2, attributes.size());
        assertEquals("units", attributes.get(0).getRmAttributeName());
        assertEquals("magnitude", attributes.get(1).getRmAttributeName());

        CAttributeTuple attributeTuple = rootObject.getAttributeTuples().get(0);
        assertEquals(2, attributeTuple.getTuples().size());
        CString kilograms = (CString) attributeTuple.getTuples().get(0).getMembers().get(0);
        assertEquals("kg", kilograms.getConstraint().get(0));

    }
}