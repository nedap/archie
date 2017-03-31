package com.nedap.archie.adlchecker;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.adlparser.ADLParserMessage;
import com.nedap.archie.aom.Archetype;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.FileInputStream;
import java.io.IOException;

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

        for (String name : ns.<String> getList("file")) {
            ADLParser adlParser = new ADLParser();
            try (FileInputStream stream = new FileInputStream(name)) {
                try {
                    Archetype parsed = adlParser.parse(stream);
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
}