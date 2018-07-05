package com.nedap.archie.serializer.odin;


import com.nedap.archie.adlparser.antlr.AdlLexer;
import com.nedap.archie.adlparser.antlr.AdlParser;
import com.nedap.archie.antlr.errors.ArchieErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;
import org.antlr.v4.runtime.CharStreams;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OdinToJsonConverterTest {

    @Test
    public void convertURI() throws Exception {
        assertConvertedEqual("original_resource_uri = < [\"resource A\"] = <http://test.example.com> >",
                "{\"original_resource_uri\":{\"resource A\":\"http://test.example.com\"}}");
        assertConvertedEqual("original_resource_uri = < [\"resource A\"] = <http://test.example.com/> >",
                "{\"original_resource_uri\":{\"resource A\":\"http://test.example.com/\"}}");

        assertConvertedEqual("original_resource_uri = < [\"resource A\"] = <http://test.example.com/aa/bb> >",
                "{\"original_resource_uri\":{\"resource A\":\"http://test.example.com/aa/bb\"}}");

        assertConvertedEqual("original_resource_uri = < [\"resource A\"] = <http://test.example.com/aa/bb/> >",
                "{\"original_resource_uri\":{\"resource A\":\"http://test.example.com/aa/bb/\"}}");
    }

    private void assertConvertedEqual(String odin, String json) {
        String input = odin;
        AdlLexer adlLexer = new AdlLexer(CharStreams.fromString(input));
        AdlParser parser = new AdlParser(new CommonTokenStream(adlLexer));
        ArchieErrorListener errorListener = new ArchieErrorListener();
        parser.addErrorListener(errorListener);
        OdinToJsonConverter converter = new OdinToJsonConverter();
        String result = converter.convert(parser.odin_text());
        assertTrue(errorListener.getErrors().toString(), errorListener.getErrors().hasNoErrors());

        assertEquals("the converted json should be equal to the expected", json, result);
    }
}
