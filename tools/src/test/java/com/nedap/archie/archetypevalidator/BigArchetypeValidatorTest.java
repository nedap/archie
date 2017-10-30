package com.nedap.archie.archetypevalidator;

import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.adlparser.ADLParserErrors;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.flattener.ArchetypeRepository;
import com.nedap.archie.flattener.SimpleArchetypeRepository;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

/**
 * //TODO: add list of errortypes NOT tested by the testset, so we can add files for that
 *
 * Runs through the full test files of the validity directory of the archetype validator. Every archetype should either
 * fail to parse or fail to validate, unless it has regression = "PASS"
 *
 * Currently skips all unimplemented ErrorTypes. As soon as you add the enum constant, it will also test those
 */
public class BigArchetypeValidatorTest {

    private static final Logger log = LoggerFactory.getLogger(BigArchetypeValidatorTest.class);

    //all archetypes in the validity package that have errors that do not match the grammar go here
    private static final Set<String> archetypeIdsThatShouldHaveParserErrors = new HashSet<>();
    static {
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-CAR.VCOID_uncoded_interior_nodes.v1.0.0");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-ENTRY.FAIL_terminology_extra_end_mark.v1.0.0");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-ENTRY.FAIL_terminology_missing.v1.0.0");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-ENTRY.FAIL_definition_empty.v1.0.0");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-ENTRY.FAIL_archetype_id_empty.v1");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-ENTRY.FAIL_archetype_id_missing.v1");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-EHR-OBSERVATION.FAIL_dadl_spurious_delimiter.v1.0.0");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-ENTRY.VCOID_container_attribute_children_no_node_identifiers.v1.0.0");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-ENTRY.SCOAT_object_empty.v1.0.0");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-ENTRY.SCAS_attribute_empty.v1.0.0");
        archetypeIdsThatShouldHaveParserErrors.add("openehr-TEST_PKG-WHOLE.VCOID_missing_root_node_id.v1.0.0");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-ENTRY.FAIL_definition_missing.v1.0.0");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-ENTRY.VCOID_objects_with_no_node_identifiers.v1.0.0");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-ENTRY.FAIL_terminology_empty.v1.0.0");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-ENTRY.FAIL_terminology_term_definitions_missing.v1.0.0");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-ENTRY.SADF_definition_after_terminology.v1.0.0");
        archetypeIdsThatShouldHaveParserErrors.add("openEHR-TEST_PKG-ENTRY.VCOID_missing_ids_on_alternative_children.v1.0.0");
    }

    private static final Set<String> archetypesTestingNotImplementedFeatures = new HashSet<>();
    static {
        archetypesTestingNotImplementedFeatures.add("openEHR-EHR-OBSERVATION.VCORMT_rm_non_conforming_type1.v1.0.0");//no support for generic type validation yet
    }

    private ArchetypeRepository parseAll() {
        SimpleArchetypeRepository result = new SimpleArchetypeRepository();
        Reflections reflections = new Reflections("adl2-tests", new ResourcesScanner());
        List<String> adlFiles = new ArrayList(reflections.getResources(Pattern.compile(".*\\.adls")));
        for(String file:adlFiles) {
            if (file.contains("legacy_adl_1.4")) {
                continue;
            }
            Archetype archetype = null;
            Exception exception = null;
            ADLParserErrors errors = null;
            try (InputStream stream = getClass().getResourceAsStream("/" + file)) {
                ADLParser parser = new ADLParser();
                parser.setLogEnabled(false);
                archetype = parser.parse(stream);
                errors = parser.getErrors();
                if (errors.hasNoErrors()) {
                    result.addArchetype(archetype);
                }

            } catch (Exception e) {
            }
        }
        return result;
    }


    @Test
    public void testFullValidityPackage() {
        Reflections reflections = new Reflections("adl2-tests.validity", new ResourcesScanner());
        List<String> adlFiles = new ArrayList(reflections.getResources(Pattern.compile(".*\\.adls")));


        int errorCount = 0;
        int correctCount = 0;
        int shouldBeFineButWasinvalid = 0;
        int notImplemented = 0;
        int unexpectedParseErrors = 0;
        ArchetypeValidator validator = new ArchetypeValidator(ArchieRMInfoLookup.getInstance());
        SimpleArchetypeRepository repository = new SimpleArchetypeRepository();
        for(String file:adlFiles) {
            if(file.contains("legacy_adl_1.4")){
                continue;
            }
            Archetype archetype = null;
            Exception exception = null;
            ADLParserErrors errors = null;
            try (InputStream stream = getClass().getResourceAsStream("/" + file)) {
                ADLParser parser = new ADLParser();
                parser.setLogEnabled(false);
                archetype = parser.parse(stream);
                errors = parser.getErrors();
            } catch (Exception e) {
                exception = e;
            }
            if(exception != null || errors.hasErrors()) {
                try {
                    String a = new String(Resources.toByteArray(Resources.getResource(file)));
                    if(a.contains("PASS")) {
                        log.info("{} has parse errors but should not!", file);
                        if(exception != null) {
                            if(errors != null) {
                                log.error(errors.toString());
                            }
                            log.error("exception:", exception);
                        } else {
                            log.error(errors.toString());
                        }
                        unexpectedParseErrors++;
                        errorCount++;
                    } else if (isInOkToHaveErrorsList(file)) {
                        log.info("{} has parse errors, so that's fine", file);
                        correctCount++;
                    } else {
                        log.info("{} has parse errors, but we don't know if it's fine:", file);
                        if(errors != null) {
                            log.error(errors.toString());
                        }
                        log.error("exception:", exception);
                        unexpectedParseErrors++;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                repository.addArchetype(archetype);
            }
        }

        ArchetypeRepository all = parseAll();
        for(Archetype archetype:repository.getAllArchetypes()) {
            List<ValidationMessage> validation = validator.validate(archetype, all);
            String regression = archetype.getDescription().getOtherDetails().get("regression");
            if(regression != null && !regression.equalsIgnoreCase("PASS")) {
                if(!errorTypeImplemented(archetype, regression)) {
                    log.info("regression {} not implemented yet, so not yet tested", regression);
                    notImplemented++;
                } else {
                    if (validation.isEmpty()) {
                        log.error("validation failed: archetype {} valid, it should not", archetype.getArchetypeId());
                        errorCount++;
                    } else {
                        boolean found = false;
                        for (ValidationMessage message : validation) {
                            if (regression.startsWith(message.getType().name())) {
                                found = true;
                                correctCount++;
                            }
                        }
                        if (!found) {
                            log.error("validation failed: archetype {} invalid but with wrong message", archetype.getArchetypeId());
                            printErrors(validation);
                            errorCount++;
                        }

                    }
                }
            } else {
                if(!validation.isEmpty()) {
                    log.error("should validate but failed: {}, {}", archetype.getArchetypeId(), regression);
                    printErrors(validation);
                    shouldBeFineButWasinvalid++;
                } else {
                    correctCount++;
                }
            }
        }
        if(errorCount > 0) {
            Assert.fail(String.format("%s validated but should not, %s correct, %s did not validate but should, %s not yet implemented, %s unexpected parser errors", errorCount, correctCount, shouldBeFineButWasinvalid, notImplemented, unexpectedParseErrors));
        }
        log.info("{} not implemented yet", notImplemented);


    }

    private boolean isInOkToHaveErrorsList(String filename) {
        return archetypeIdsThatShouldHaveParserErrors.contains(FilenameUtils.getBaseName(filename));
    }

    private boolean errorTypeImplemented(Archetype archetype, String regression) {
        if(BigArchetypeValidatorTest.archetypesTestingNotImplementedFeatures.contains(archetype.getArchetypeId().toString())) {
            return false;
        }
        if(regression.equals("FAIL")) {
            return true;//this should not be implemented, but the archetype fixed, or it is a parse error
        }
        for(ErrorType errorType: ErrorType.values()) {
            if(regression.startsWith(errorType.name())) {
                return true;
            }
        }
        return false;
    }

    private void printErrors(List<ValidationMessage> messages) {
        for(ValidationMessage message:messages) {
            log.error(message.toString());
        }
    }
}
