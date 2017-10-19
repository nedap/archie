package com.nedap.archie.rminfo;

import java.util.List;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.adlparser.modelconstraints.ReflectionConstraintImposer;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.flattener.ArchetypeRepository;
import com.nedap.archie.flattener.Flattener;
import com.nedap.archie.flattener.SimpleArchetypeRepository;
import com.nedap.archie.serializer.adl.ADLArchetypeSerializer;
import org.junit.Test;
import org.openehr.bmm.persistence.PersistedBmmSchema;
import org.openehr.bmm.persistence.PersistedBmmSchemaState;
import org.openehr.bmm.persistence.deserializer.BmmSchemaDeserializer;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.antlr.OdinVisitorImpl;
import org.openehr.odin.loader.OdinLoaderImpl;

import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class BMMModelInfoLookupTest {

    @Test
    public void cimiModel() throws Exception {
        List<PersistedBmmSchema> schemas = new ArrayList<>();
        schemas.add(loadSchema("CIMI_RM_CORE.v.0.0.3.bmm"));
        schemas.add(loadSchema("CIMI_RM_FOUNDATION.v.0.0.3.bmm"));
        schemas.add(loadSchema("CIMI_RM_CLINICAL.v.0.0.3.bmm"));
        BMMModelInfoLookup lookup = new BMMModelInfoLookup(schemas);
        System.out.println(lookup);

        //this class name should be renamed
        ReflectionConstraintImposer constraintImposer = new ReflectionConstraintImposer(lookup);
        ADLParser parser = new ADLParser();
        Archetype statementContext = parser.parse(BMMModelInfoLookupTest.class.getResourceAsStream("CIMI-CORE-StatementContext.statement_context.v1.0.0.adls"));
        assertTrue(parser.getErrors().toString(), parser.getErrors().hasNoErrors());
        Archetype statementContextBinding = parser.parse(BMMModelInfoLookupTest.class.getResourceAsStream("CIMI-CORE-StatementContext.statement_context_bindings.v1.0.0.adls"));
        constraintImposer.imposeConstraints(statementContext.getDefinition());
        constraintImposer.imposeConstraints(statementContextBinding.getDefinition());
        assertTrue(parser.getErrors().toString(), parser.getErrors().hasNoErrors());

        SimpleArchetypeRepository repository = new SimpleArchetypeRepository();
        repository.addArchetype(statementContext);
        repository.addArchetype(statementContextBinding);

        Archetype bindingFlattened = new Flattener(repository).flatten(statementContextBinding);
        System.out.println(ADLArchetypeSerializer.serialize(bindingFlattened));

    }

    private PersistedBmmSchema loadSchema(String name) throws Exception {
        try(InputStream stream = BMMModelInfoLookupTest.class.getResourceAsStream(name)) {
            OdinLoaderImpl loader = new OdinLoaderImpl();
            OdinVisitorImpl visitor = loader.loadOdinFile(stream);
            CompositeOdinObject root = visitor.getAstRootNode();
            BmmSchemaDeserializer deserializer = new BmmSchemaDeserializer();
            PersistedBmmSchema schema = deserializer.deserialize(root);
            return schema;
        }
    }

}
