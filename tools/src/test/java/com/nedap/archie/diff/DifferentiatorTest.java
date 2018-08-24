package com.nedap.archie.diff;

import com.nedap.archie.diff.Differentiator;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.flattener.Flattener;
import com.nedap.archie.flattener.InMemoryFullArchetypeRepository;
import com.nedap.archie.serializer.adl.ADLArchetypeSerializer;
import org.junit.Before;
import org.junit.Test;
import org.openehr.referencemodels.BuiltinReferenceModels;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DifferentiatorTest {

    private Archetype archetype;
    private Archetype specializedArchetype;
    private InMemoryFullArchetypeRepository repository;
    private Archetype flatChild;
    private Archetype flatParent;

    @Before
    public void setup() throws Exception {
        try(InputStream stream = getClass().getResourceAsStream( "openEHR-EHR-CLUSTER.test.v0.0.1.adls"))  {
            ADLParser parser = new ADLParser();
            archetype = parser.parse(stream);
            if(parser.getErrors().hasErrors()) {
                throw new RuntimeException(parser.getErrors().toString());
            }
        }

        try(InputStream stream = getClass().getResourceAsStream( "openEHR-EHR-CLUSTER.test_specialized.v0.0.1.adls"))  {
            ADLParser parser = new ADLParser();
            specializedArchetype = parser.parse(stream);
            if(parser.getErrors().hasErrors()) {
                throw new RuntimeException(parser.getErrors().toString());
            }
        }

        repository = new InMemoryFullArchetypeRepository();
        repository.addArchetype(archetype);
        repository.addArchetype(specializedArchetype);

        Flattener flattener = new Flattener(repository, BuiltinReferenceModels.getMetaModels());
        flatParent = flattener.flatten(archetype);
        flatChild = flattener.flatten(specializedArchetype);
    }

    @Test
    public void simpleDiff() throws Exception {
        Archetype diffed = new Differentiator(BuiltinReferenceModels.getMetaModels()).differentiate(flatChild, flatParent);
        System.out.println(ADLArchetypeSerializer.serialize(diffed));
        assertTrue(diffed.getDefinition().getAttributes().isEmpty());
        assertEquals("openEHR-EHR-CLUSTER.test.v0.0.1", diffed.getParentArchetypeId());
        //TODO: terminology should be nearly empty, no value sets
    }
}
