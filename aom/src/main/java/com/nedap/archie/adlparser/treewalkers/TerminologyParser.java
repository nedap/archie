package com.nedap.archie.adlparser.treewalkers;

import com.nedap.archie.adlparser.ADLParserErrors;
import com.nedap.archie.adlparser.antlr.AdlParser.*;
import com.nedap.archie.aom.utils.ArchetypeParsePostProcesser;
import com.nedap.archie.serializer.odin.OdinObjectParser;
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
        return terminology;
    }

}
