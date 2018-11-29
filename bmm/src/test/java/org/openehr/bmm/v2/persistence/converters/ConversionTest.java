package org.openehr.bmm.v2.persistence.converters;

import org.junit.Test;
import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.bmm.v2.persistence.json.BmmOdinParser;
import org.openehr.bmm.v2.validation.BmmSchemaConverter;
import org.openehr.bmm.v2.validation.BmmValidationResult;
import org.openehr.bmm.v2.validation.BmmRepository;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

public class ConversionTest {

    @Test
    public void test() throws Exception {
        BmmRepository repo = new BmmRepository();
        repo.addPersistentSchema(parse("/openehr/openehr_basic_types_102.bmm"));
        repo.addPersistentSchema(parse("/openehr/openehr_demographic_102.bmm"));
        repo.addPersistentSchema(parse("/openehr/openehr_ehr_102.bmm"));
        repo.addPersistentSchema(parse("/openehr/openehr_primitive_types_102.bmm"));
        repo.addPersistentSchema(parse("/openehr/openehr_rm_102.bmm"));
        repo.addPersistentSchema(parse("/openehr/openehr_structures_102.bmm"));

        BmmSchemaConverter converter = new BmmSchemaConverter(repo);
        converter.validateAndConvertRepository();;
        for(BmmValidationResult validationResult:repo.getModels()) {
            System.out.println(validationResult.getLogger());
            assertTrue("the OpenEHR RM 1.0.2 BMM files should pass validation", validationResult.passes());
        }
    }

    private PBmmSchema parse(String name) throws IOException  {
        try(InputStream stream = getClass().getResourceAsStream(name)) {//"/testbmm/TestBmm1.bmm")) {
            return BmmOdinParser.convert(stream);
        }
    }
}
