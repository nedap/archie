package com.nedap.archie.adlparser.treewalkers;

import com.nedap.archie.adlparser.ADLParserErrors;
import com.nedap.archie.adlparser.antlr.AdlParser.*;
import com.nedap.archie.adlparser.odin.OdinObjectParser;
import com.nedap.archie.aom.terminology.ArchetypeTerm;
import com.nedap.archie.aom.terminology.ArchetypeTerminology;

import java.util.Map;

/**
 * Parser for the terminology section of an archetype
 *
 * Created by pieter.bos on 19/10/15.
 */
public class TerminologyParser extends BaseTreeWalker {

    public TerminologyParser(ADLParserErrors errors) {
        super(errors);
    }

    public ArchetypeTerminology parseTerminology(Terminology_sectionContext context) {
        ArchetypeTerminology terminology = OdinObjectParser.convert(context.odin_text(), ArchetypeTerminology.class);

        //codes are in model, but do not appear in odin. Set them here
        fillArchetypeTermCodes(terminology.getTermDefinitions());
        fillArchetypeTermCodes(terminology.getTerminologyExtracts());

        return terminology;
    }

    private void fillArchetypeTermCodes(Map<String, Map<String, ArchetypeTerm>> termSet) {
        if(termSet != null) {
            for(Map<String, ArchetypeTerm> language:termSet.values()) {
                for(String term:language.keySet()) {
                    language.get(term).setCode(term);
                }
            }
        }
    }
}
