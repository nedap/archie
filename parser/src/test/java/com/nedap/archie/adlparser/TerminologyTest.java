package com.nedap.archie.adlparser;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.terminology.ArchetypeTerm;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by pieter.bos on 19/10/15.
 */
public class TerminologyTest {


    @Test
    public void termDefinitions() throws Exception {
        Archetype archetype = new ADLParser().parse(getClass().getResourceAsStream("/basic.adl"));
        ArchetypeTerm term = archetype.getTerminology().getTermDefinition("en", "id7");
        assertEquals("Comment", term.getText());
        assertEquals("Comment on any qualification.", term.getDescription());
        assertEquals("Extra value", term.get("extra"));
        assertNull(archetype.getTerminology().getTermDefinition("en", "doesnotexist"));
        assertNull(archetype.getTerminology().getTermDefinition("es", "d7"));
    }

    @Test
    public void termBindings() throws Exception {
        /*
        term_bindings = <
		["openehr"] = <
			["at1"] = <http://openehr.org/id/433>
		>
	>
         */
        Archetype archetype = new ADLParser().parse(getClass().getResourceAsStream("/basic.adl"));
        URI uri = archetype.getTerminology().getTermBinding("openehr", "at1");
        assertEquals(new URI("http://openehr.org/id/433"), uri);
        assertNull(archetype.getTerminology().getTermBinding("openehr", "at2"));
        assertNull(archetype.getTerminology().getTermBinding("closedehr", "at1"));
    }
}
