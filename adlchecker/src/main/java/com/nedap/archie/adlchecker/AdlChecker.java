package com.nedap.archie.adlchecker;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.antlr.errors.ANTLRParserMessage;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.archetypevalidator.ArchetypeValidator;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.archetypevalidator.ValidationResult;
import com.nedap.archie.flattener.InMemoryFullArchetypeRepository;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import com.nedap.archie.rminfo.ReferenceModels;
import com.nedap.archie.serializer.adl.ADLArchetypeSerializer;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.openehr.referencemodels.BuiltinReferenceModels;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AdlChecker {

    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newArgumentParser("AdlChecker")
                .defaultHelp(true)
                .description("Checks the syntax of ADL files");

        parser.addArgument("path").nargs("*")
                .help("file or directory that contains archetypes to validate");

        parser.addArgument("-f", "--outputFlat")
                .action(Arguments.storeTrue())
                .help("if the --outputFlat flag is present, also output the flat ADL");

        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        if(ns.getList("path").isEmpty()) {
            parser.printUsage();
            parser.printHelp();
        } else {
            validateArchetypes(ns.getList("path"), ns.getBoolean("outputFlat"));
        }
    }

    private static void validateArchetypes(List<String> directories, boolean printFlatAdl) {
        InMemoryFullArchetypeRepository repository = new InMemoryFullArchetypeRepository();
        for (String directory : directories) {
            System.out.println("step 1: parsing archetypes");
            System.out.println();
            try {
                Files.walk(Paths.get(directory)).forEach((path) -> parseArchetype(path, repository));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        repository.compile(BuiltinReferenceModels.getMetaModels());

        System.out.println("step 2: validations");

        for(ValidationResult result:repository.getAllValidationResults()) {
            printValidationResult(result);
            if(printFlatAdl && result.passes()) {
                System.out.println(ADLArchetypeSerializer.serialize(result.getFlattened()));
            }

        }
    }

    private static void printValidationResult(ValidationResult result) {
        System.out.println();
        System.out.print("============= ");
        System.out.print(result.getArchetypeId());
        System.out.print("      ");
        if(result.passes()) {
            System.out.print("PASSED");
        } else {
            System.out.print("FAILED");
        }
        System.out.print(" =============");
        System.out.println();
        for(ValidationMessage error:result.getErrors()) {
            System.out.println(error.toString());
        }
        for(ValidationResult overlayResult:result.getOverlayValidations()) {
            printValidationResult(overlayResult);
        }
    }

    private static void parseArchetype(Path path, InMemoryFullArchetypeRepository repository) {
        File file = path.toFile();
        if(file.isDirectory()) {
            return;
        }
        ADLParser adlParser = new ADLParser();
        try (FileInputStream stream = new FileInputStream(file)) {
            try {
                Archetype parsed = adlParser.parse(stream);
                if(adlParser.getErrors().hasNoErrors()) {
                    repository.addArchetype(parsed);
                }
                if(adlParser.getErrors().hasNoMessages()){
                    System.out.println(path.getFileName() + " has no messages, ok!");
                } else {
                    System.out.println("errors found for " + path.getFileName());

                    for(ANTLRParserMessage message:adlParser.getErrors().getWarnings()) {
                        System.err.println("warning: " + message.getMessage());
                    }
                    for(ANTLRParserMessage message:adlParser.getErrors().getErrors()) {
                        System.err.println("error: " + message.getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.err.println("error opening file");
            e.printStackTrace();
        }
    }

}