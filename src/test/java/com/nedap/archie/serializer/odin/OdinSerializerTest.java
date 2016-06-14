package com.nedap.archie.serializer.odin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.nedap.archie.base.terminology.TerminologyCode;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author markopi
 */
public class OdinSerializerTest {
    @Test
    public void serializeString() throws Exception {
        String actual = OdinSerializer.serialize("Hello");
        assertThat(actual, equalTo("\"Hello\""));
    }

    @Test
    public void serializeStringWithQuotes() throws Exception {
        String actual = OdinSerializer.serialize("Hello \"World\"");

        assertThat(actual, equalTo("\"Hello \\\"World\\\"\""));
    }

    @Test
    public void serializeList() throws Exception {
        String actual = OdinSerializer.serialize(ImmutableList.of("first", "second", "third"));

        assertThat(actual, equalTo("\"first\", \"second\", \"third\""));
    }

    @Test
    public void serializeSingleItemList() throws Exception {
        String actual = OdinSerializer.serialize(ImmutableList.of("single"));

        assertThat(actual, equalTo("\"single\", ..."));
    }

    @Test
    public void serializeEmptyList() throws Exception {
        String actual = OdinSerializer.serialize(ImmutableList.of());

        assertThat(actual, equalTo("..."));
    }

    @Test
    public void serializeMapOfStrings() throws Exception {
        String actual = OdinSerializer.serialize(ImmutableMap.of(
                "first", "one",
                "second", "two"
        )).trim();
        assertThat(actual, equalTo("" +
                "[\"first\"] = <\"one\">\n" +
                "[\"second\"] = <\"two\">"));
    }

    @Test
    public void serializeMapOfMaps() throws Exception {
        String actual = OdinSerializer.serialize(ImmutableMap.of(
                "first", ImmutableMap.of("A1", 1, "A2", 2),
                "second", ImmutableMap.of("B1", 1, "B2", 2)
        )).trim();

        assertThat(actual, equalTo("" +
                "[\"first\"] = <\n" +
                "    [\"A1\"] = <1>\n" +
                "    [\"A2\"] = <2>\n" +
                ">\n" +
                "[\"second\"] = <\n" +
                "    [\"B1\"] = <1>\n" +
                "    [\"B2\"] = <2>\n" +
                ">"));
    }

    @Test
    public void serializeNestedOdinObject() throws Exception {
        OdinObject object = new OdinObject()
                .put("lifecycle_state", "unmanaged")
                .put("original_author", ImmutableMap.of(
                        "name", "Heather Leslie",
                        "organisation", "Ocean Informatics"))
                .put("other_contributors", ImmutableList.of("A", "B"))
                .put("details", ImmutableMap.of(
                        "en", new OdinObject()
                                .put("language", newTerminologyCode("ISO_639-1", "en"))
                                .put("purpose", "test")));

        String actual = OdinSerializer.serialize(object);

        assertThat(actual, equalTo("" +
                "lifecycle_state = <\"unmanaged\">\n" +
                "original_author = <\n" +
                "    [\"name\"] = <\"Heather Leslie\">\n" +
                "    [\"organisation\"] = <\"Ocean Informatics\">\n" +
                ">\n" +
                "other_contributors = <\"A\", \"B\">\n" +
                "details = <\n" +
                "    [\"en\"] = <\n" +
                "        language = <[ISO_639-1::en]>\n" +
                "        purpose = <\"test\">\n" +
                "    >\n" +
                ">"));
    }

    private TerminologyCode newTerminologyCode(String terminologyId, String codeString) {
        TerminologyCode tc = new TerminologyCode();
        tc.setTerminologyId(terminologyId);
        tc.setCodeString(codeString);
        return tc;
    }

}