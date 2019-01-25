package org.openehr.bmm.v2.persistence.validation;

import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.bmm.v2.persistence.odin.BmmOdinParser;
import org.openehr.bmm.v2.validation.BmmRepository;
import org.openehr.bmm.v2.validation.BmmSchemaConverter;
import org.openehr.bmm.v2.validation.BmmValidationResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class AbstractSchemaValidationsTest {

    protected BmmValidationResult parseModifyThenConvert(String name, Consumer<PBmmSchema> function) throws IOException {
        try(InputStream stream = getClass().getResourceAsStream(name)) {
            PBmmSchema schema = BmmOdinParser.convert(stream);
            function.accept(schema);

            BmmRepository repo = new BmmRepository();
            repo.addPersistentSchema(schema);
            BmmSchemaConverter converter = new BmmSchemaConverter(repo);
            return converter.validateConvertAndAddToRepo(schema);
        }
    }

    protected BmmValidationResult parseAndConvert(String name) throws IOException {
        try(InputStream stream = getClass().getResourceAsStream(name)) {
            PBmmSchema schema = BmmOdinParser.convert(stream);
            BmmRepository repo = new BmmRepository();
            repo.addPersistentSchema(schema);

            BmmSchemaConverter converter = new BmmSchemaConverter(repo);

            return converter.validateConvertAndAddToRepo(schema);
        }
    }
}
