package com.nedap.archie.aom;

import com.nedap.archie.ArchieLanguageConfiguration;
import com.nedap.archie.adlparser.ADLParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by pieter.bos on 20/04/16.
 */
public class CObjectTest {

    private Archetype archetype;

    @Before
    public void setup() throws Exception {
        archetype = new ADLParser().parse(getClass().getResourceAsStream("/basic.adl"));
    }

    @Test
    public void definitionMeaningAndLogicalPath() {
        assertEquals("Prescription", archetype.getDefinition().getMeaning());
        assertEquals("A document authorising supply and administration of one or more medicines, vaccines or other therapeutic goods (as a collection of medication instrations) to be communicated to a dispensing or administration provider.", archetype.getDefinition().getDescription());

        CObject qualitification = archetype.getDefinition().itemAtPath("/context[id11]/other_context[id2]/items[id3]");

        assertEquals("/context[id11]/other_context[id2]/items[Qualification]", qualitification.getLogicalPath());

        ArchieLanguageConfiguration.setThreadLocalDescriptiongAndMeaningLanguage("nl");

        assertEquals("Recept", archetype.getDefinition().getMeaning());
        assertEquals("Een document waarmee uitgifte van een of meerdere medicijnen of hulpmiddel wordt geautoriseerd.", archetype.getDefinition().getDescription());

        assertEquals("/context[id11]/other_context[id2]/items[Qualification]", qualitification.getLogicalPath());


        ArchieLanguageConfiguration.setThreadLocalLogicalPathLanguage("nl");

        assertEquals("/context[id11]/other_context[id2]/items[Kwalificatie]", qualitification.getLogicalPath());
    }

    @Test
    public void testReplaceChild() {
        CObject orderId = archetype.getDefinition().itemAtPath("/context[id11]/other_context[id2]/items[id3]/items[id4]");
        CObject comment = archetype.getDefinition().itemAtPath("/context[id11]/other_context[id2]/items[id3]/items[id7]");
        CAttribute parent = orderId.getParent();
        parent.getChildren().remove(parent.getChildren().size() -1);//don't do this in real code :)
        parent.replaceChild("id4", comment);
        assertEquals(comment, parent.getChildren().get(0));


    }




}
