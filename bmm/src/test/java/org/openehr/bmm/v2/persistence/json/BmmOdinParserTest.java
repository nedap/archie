package org.openehr.bmm.v2.persistence.json;

import org.junit.Test;
import org.openehr.bmm.v2.persistence.PBmmSchema;

import java.io.IOException;
import java.io.InputStream;

public class BmmOdinParserTest {

    @Test
    public void parseTestBmm1() throws IOException {
        try(InputStream stream = getClass().getResourceAsStream("/testbmm/TestBmm1.bmm")) {//"/testbmm/TestBmm1.bmm")) {
            PBmmSchema schema = BmmOdinParser.convert(stream);

            String s = BmmJacksonUtil.getObjectMapper().writeValueAsString(schema);
            System.out.println(s);

        }
    }
}
