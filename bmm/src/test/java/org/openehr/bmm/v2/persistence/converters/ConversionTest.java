package org.openehr.bmm.v2.persistence.converters;

import org.junit.Test;
import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.bmm.v2.persistence.json.BmmJacksonUtil;
import org.openehr.bmm.v2.persistence.json.BmmOdinParser;
import org.openehr.bmm.v2.validation.BmmSchemaConverter;
import org.openehr.bmm.v2.validation.BmmSchemaValidator;
import org.openehr.bmm.v2.validation.BmmValidationResult;
import org.openehr.bmm.v2.validation.PSchemaRepository;

import java.io.IOException;
import java.io.InputStream;

public class ConversionTest {

    @Test
    public void test() throws Exception {
        PSchemaRepository repo = new PSchemaRepository();
        repo.addPersistentSchema(parse("/openehr/openehr_basic_types_102.bmm"));
        repo.addPersistentSchema(parse("/openehr/openehr_demographic_102.bmm"));
        repo.addPersistentSchema(parse("/openehr/openehr_ehr_102.bmm"));
        repo.addPersistentSchema(parse("/openehr/openehr_primitive_types_102.bmm"));
        repo.addPersistentSchema(parse("/openehr/openehr_rm_102.bmm"));
        repo.addPersistentSchema(parse("/openehr/openehr_structures_102.bmm"));

        BmmSchemaConverter converter = new BmmSchemaConverter(repo);
        for(PBmmSchema schema:repo.getPersistentSchemas()) {
            if(repo.getModel(schema.getSchemaId()) == null) {
                BmmValidationResult bmmValidationResult = converter.validateAndConvert(schema);
                repo.addModel(bmmValidationResult);
                System.out.println(bmmValidationResult.getLogger());
            }
        }
    }

    private PBmmSchema parse(String name) throws IOException  {
        try(InputStream stream = getClass().getResourceAsStream(name)) {//"/testbmm/TestBmm1.bmm")) {
            return BmmOdinParser.convert(stream);
        }
    }
}
