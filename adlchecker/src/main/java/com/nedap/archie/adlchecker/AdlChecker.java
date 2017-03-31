package com.nedap.archie.adlchecker;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.adlparser.ADLParserMessage;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.archetypevalidator.ArchetypeValidator;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class AdlChecker {

    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newArgumentParser("AdlChecker")
                .defaultHelp(true)
                .description("Checks the syntax of ADL files");

        parser.addArgument("file").nargs("*")
                .help("File to calculate checksum");

        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        if(ns.getList("file").isEmpty()) {
            parser.printUsage();
            parser.printHelp();
        }

        validateArchetypes(ns.getList("file"));
    }

    private static void validateArchetypes(List<String> files) {
        for (String name : files) {
            ADLParser adlParser = new ADLParser();
            try (FileInputStream stream = new FileInputStream(name)) {
                try {
                    Archetype parsed = adlParser.parse(stream);
                    check(parsed);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                System.err.println("error opening file");
                e.printStackTrace();
            }
            if(adlParser.getErrors().hasNoMessages()){
                System.out.println(name + " has no messages, ok!");
            } else {
                System.out.println("errors found for " + name);

                for(ADLParserMessage message:adlParser.getErrors().getWarnings()) {
                    System.err.println("warning: " + message.getMessage());
                }
                for(ADLParserMessage message:adlParser.getErrors().getErrors()) {
                    System.err.println("error: " + message.getMessage());
                }
            }

        }
    }

    public static void check(Archetype archetype) {
        ArchetypeValidator validator = new ArchetypeValidator();
        List<ValidationMessage> messages = validator.validate(archetype);
        for(ValidationMessage message:messages) {
            System.err.println(message);
        }
    }


}