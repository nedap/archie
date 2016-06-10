package com.nedap.archie.serializer.adl;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.query.APathQuery;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author markopi
 */
public class ADLArchetypeSerializerParserRoundtripTest {
    @Test
    public void basic() throws Exception {
        Archetype archetype = roundtrip(loadRoot("basic.adl"));

        CAttribute defining_code = new APathQuery("/category[id10]/defining_code").find(archetype.getDefinition());
        CTerminologyCode termCode = (CTerminologyCode) defining_code.getChildren().get(0);

        assertThat(termCode.getConstraint(), hasItem("at1"));
        assertThat(archetype.getDescription().getDetails().get("en").getKeywords(), hasItems("ADL", "test"));
        assertThat(archetype.getTerminology().getTermBinding("openehr", "at1"),
                equalTo(URI.create("http://openehr.org/id/433")));

        assertThat(archetype.getAnnotations().getDocumentation().get("en").get("/context/start_time").get("local_name"),
                equalTo("consultation start time"));
    }

    @Test
    public void device() throws Exception {
        Archetype archetype = roundtrip(load("openEHR-EHR-CLUSTER.device.v1.adls"));

        CComplexObject dvDeviceId = new APathQuery("/items[id22]/value[id32]").find(archetype.getDefinition());
        assertThat(dvDeviceId.getRmTypeName(), equalTo("DV_IDENTIFIER"));

        assertThat(archetype.getDescription().getOriginalAuthor().get("name"), equalTo("Heather Leslie"));
    }

    private Archetype roundtrip(Archetype archetype) throws IOException {
        String serialized = ADLArchetypeSerializer.serialize(archetype);
        return new ADLParser().parse(serialized);
    }

    private Archetype load(String resourceName) throws IOException {
        return new ADLParser().parse(ADLArchetypeSerializerTest.class.getResourceAsStream(resourceName));
    }

    private Archetype loadRoot(String resourceName) throws IOException {
        return new ADLParser().parse(ADLArchetypeSerializerTest.class.getClassLoader().getResourceAsStream(resourceName));
    }

}
