package com.nedap.archie.serializer.adl;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.query.APathQuery;
import com.nedap.archie.testutil.TestUtil;
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
        Archetype basic = loadRoot("basic.adl");
        Archetype archetype = roundtrip(basic);

        CAttribute defining_code = archetype.itemAtPath("/category[id10]/defining_code");
        CTerminologyCode termCode = (CTerminologyCode) defining_code.getChildren().get(0);

        assertThat(termCode.getConstraint(), hasItem("at1"));
        assertThat(archetype.getDescription().getDetails().get("en").getKeywords(), hasItems("ADL", "test"));
        assertThat(archetype.getTerminology().getTermBinding("openehr", "at1"),
                equalTo(URI.create("http://openehr.org/id/433")));

        assertThat(archetype.getAnnotations().getDocumentation().get("en").get("/context/start_time").get("local_name"),
                equalTo("consultation start time"));

        TestUtil.assertCObjectEquals(basic.getDefinition(), archetype.getDefinition());
    }

    @Test
    public void device() throws Exception {
        Archetype archetype = roundtrip(load("openEHR-EHR-CLUSTER.device.v1.adls"));

        CComplexObject dvDeviceId = archetype.itemAtPath("/items[id22]/value[id32]");
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
