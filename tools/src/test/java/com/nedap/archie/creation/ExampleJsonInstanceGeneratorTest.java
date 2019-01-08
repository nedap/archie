package com.nedap.archie.creation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.OperationalTemplate;
import com.nedap.archie.archetypevalidator.ValidationResult;
import com.nedap.archie.flattener.Flattener;
import com.nedap.archie.flattener.FullArchetypeRepository;
import com.nedap.archie.flattener.InMemoryFullArchetypeRepository;
import com.nedap.archie.json.JacksonUtil;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.composition.Observation;
import com.nedap.archie.testutil.TestUtil;
import org.everit.json.schema.ValidationException;
import org.junit.Test;
import org.openehr.referencemodels.BuiltinReferenceModels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExampleJsonInstanceGeneratorTest {

    private static Logger logger = LoggerFactory.getLogger(ExampleJsonInstanceGeneratorTest.class);

    @Test
    public void bloodPressure() throws Exception {
        OperationalTemplate opt = createOPT("/ckm-mirror/local/archetypes/entry/observation/openEHR-EHR-OBSERVATION.blood_pressure.v1.1.0.adls");

        ExampleJsonInstanceGenerator structureGenerator = new ExampleJsonInstanceGenerator(BuiltinReferenceModels.getMetaModels(), "en");
        Map<String, Object> structure = structureGenerator.generate(opt);
        String s = serializeToJson(structure, true);
        System.out.println(s);

        Map<String, Object> data = (Map<String, Object>) structure.get("data");
        assertEquals("HISTORY", data.get("@type"));
        Map<String, Object> name = (Map<String, Object>) data.get("name");
        assertEquals("history", name.get("value"));
        assertEquals("id2", data.get("archetype_node_id"));


        //assert that the required encoding property is set, even though not present in archetype
        Map<String, Object> encoding = (Map<String, Object>) structure.get("encoding");
        assertEquals("CODE_PHRASE", encoding.get("@type"));
        Map<String, Object> terminologyId = (Map<String, Object>) encoding.get("terminology_id");
        assertEquals("the default value for a string type should be \"string\"", "string", terminologyId.get("value"));
        List events = (List) data.get("events");
        assertEquals(3, events.size());
        assertEquals("POINT_EVENT<T>", ((Map) events.get(0)).get("@type"));
        assertEquals("POINT_EVENT<T>", ((Map) events.get(1)).get("@type"));
        assertEquals("INTERVAL_EVENT", ((Map) events.get(2)).get("@type"));



    }


    /**
     * Parse into actual RM instances and see if it parses correctly
     * @throws Exception
     */
    @Test
    public void parseBloodPressure() throws Exception {
        //check that the generated blood pressure can be parsed
        OperationalTemplate opt = createOPT("/ckm-mirror/local/archetypes/entry/observation/openEHR-EHR-OBSERVATION.blood_pressure.v1.1.0.adls");
        ExampleJsonInstanceGenerator structureGenerator = new ExampleJsonInstanceGenerator(BuiltinReferenceModels.getMetaModels(), "en");
        Map<String, Object> structure = structureGenerator.generate(opt);
        String s = serializeToJson(structure, true);
        System.out.println(s);
        RMObject rmObject = JacksonUtil.getObjectMapper().readValue(s, RMObject.class);
        assertTrue(rmObject instanceof Observation);
    }

    @Test
    /**
     * Parse into actual RM instances and see if it parses correctly
     * @throws Exception
     */
    public void parseOrdinal() throws Exception {
        //check that the generated blood pressure can be parsed
        OperationalTemplate opt = createOPT("/adl2-tests/features/aom_structures/tuples/openEHR-EHR-OBSERVATION.ordinal_tuple.v1.0.0.adls");
        ExampleJsonInstanceGenerator structureGenerator = new ExampleJsonInstanceGenerator(BuiltinReferenceModels.getMetaModels(), "en");
        Map<String, Object> structure = structureGenerator.generate(opt);
        String s = serializeToJson(structure, false);
        RMObject rmObject = JacksonUtil.getObjectMapper().readValue(s, RMObject.class);
        assertTrue(rmObject instanceof Observation);
    }



    @Test
    public void ordinal() throws Exception {
        //ordinal handling in openEHR RM is a bit tricky, since a CTerminologyCode maps directly to a DV_CODED_TEXT
        OperationalTemplate opt = createOPT("/adl2-tests/features/aom_structures/tuples/openEHR-EHR-OBSERVATION.ordinal_tuple.v1.0.0.adls");
        ExampleJsonInstanceGenerator structureGenerator = new ExampleJsonInstanceGenerator(BuiltinReferenceModels.getMetaModels(), "en");
        Map<String, Object> structure = structureGenerator.generate(opt);
        String s = serializeToJson(structure, false);
        //check the ordinal creation, including correct DV_CODED_TEXT and CODE_PHRASE
        assertTrue(s.contains("{\"@type\":\"DV_ORDINAL\",\"value\":0,\"symbol\":{\"@type\":\"DV_CODED_TEXT\",\"defining_code\":{\"@type\":\"CODE_PHRASE\",\"terminology_id\":{\"@type\":\"TERMINOLOGY_ID\",\"value\":\"local\"},\"code_string\":\"at11\"},\"value\":\"Absent\"}}"));
    }


    @Test
    public void generateAllCKMExamples() throws Exception {
        ExampleJsonInstanceGenerator structureGenerator = new ExampleJsonInstanceGenerator(BuiltinReferenceModels.getMetaModels(), "en");
        FullArchetypeRepository repository = TestUtil.parseCKM();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        int numberCreated = 0, validationFailed = 0, generatedException = 0, jsonSchemaValidationRan = 0, jsonSchemaValidationFailed = 0;
        repository.compile(BuiltinReferenceModels.getMetaModels());
        JsonSchemaValidator jsonSchemaValidator = new JsonSchemaValidator(BuiltinReferenceModels.getBMMReferenceModels().getValidModels().get("openehr_rm_1.0.4"));
        for(ValidationResult result:repository.getAllValidationResults()) {
            if(result.passes()) {
                String json = "";
                try {
                    Flattener flattener = new Flattener(repository, BuiltinReferenceModels.getMetaModels()).createOperationalTemplate(true);
                    OperationalTemplate template = (OperationalTemplate) flattener.flatten(result.getSourceArchetype());
                    Map<String, Object> example = structureGenerator.generate(template);
                    json = mapper.writeValueAsString(example);
                    JacksonUtil.getObjectMapper().readValue(json, RMObject.class);
                    numberCreated++;
                   // if(Sets.newHashSet("COMPOSITION", "OBSERVATION", "EVALUATION", "INSTRUCTION", "SECTION", "ACTION").contains(template.getDefinition().getRmTypeName())) {
                        jsonSchemaValidationRan++;
                        jsonSchemaValidator.validate(template.getDefinition().getRmTypeName(), json);
                   // }


                } catch (ValidationException ex) {
                    logger.error(Joiner.on("\n").join(ex.getAllMessages()));
                    jsonSchemaValidationFailed++;
                } catch (Exception e) {
                    if(generatedException <= 4) {
                        logger.error("error generating example", e);
                        //logger.error(json);
                    }
                    generatedException++;
                }
            } else {
                validationFailed++;
            }


        }
        logger.info("created " + numberCreated + " examples, " + validationFailed + " failed to validate, " + generatedException + " threw exception in test");
        logger.info("failed validation " + jsonSchemaValidationFailed + " of " + jsonSchemaValidationRan);
    }


    private OperationalTemplate createOPT(String s2) throws IOException {
        Archetype archetype = parse(s2);
        InMemoryFullArchetypeRepository repository = new InMemoryFullArchetypeRepository();
        repository.addArchetype(archetype);
        return (OperationalTemplate) new Flattener(repository, BuiltinReferenceModels.getMetaModels()).createOperationalTemplate(true).flatten(archetype);
    }

    private Archetype parse(String filename) throws IOException {
        ADLParser parser = new ADLParser();
        Archetype archetype;
        try(InputStream stream =  getClass().getResourceAsStream(filename)) {
            archetype = parser.parse(stream);
            if(parser.getErrors().hasErrors()) {
                throw new RuntimeException(parser.getErrors().toString());
            }
        }
        return archetype;
    }

    private String serializeToJson(Map<String, Object> structure, boolean indent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        if(indent) {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        } else {
            objectMapper.disable(SerializationFeature.INDENT_OUTPUT);
        }
        return objectMapper.writeValueAsString(structure);
    }
}
