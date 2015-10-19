package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.AdlBaseListener;
import com.nedap.archie.adlparser.antlr.AdlParser;
import com.nedap.archie.adlparser.antlr.AdlParser.*;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeHRID;
import com.nedap.archie.aom.AuthoredArchetype;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created by pieter.bos on 19/10/15.
 */
public class ADLListener extends AdlBaseListener {

    private ADLParserErrors errors = new ADLParserErrors();

    private Archetype archetype;
    private CComplexObjectParser subTreeWalker;
    private TerminologyParser terminologyParser;

    public ADLListener() {
        subTreeWalker = new CComplexObjectParser(errors);//TODO: rename to ComplexObjectParser?
        terminologyParser = new TerminologyParser(errors);
    }

    /** top-level constructs */
    @Override
    public void enterArchetype(ArchetypeContext ctx) {
        archetype = new Archetype();
        archetype.setDifferential(true);
        parseArchetypeHRID(ctx.ARCHETYPE_HRID());
    }

    @Override
    public void enterTemplate(TemplateContext ctx) {
        archetype = new Archetype();
        archetype.setDifferential(false);
        parseArchetypeHRID(ctx.ARCHETYPE_HRID());

    }

    @Override
    public void enterTemplate_overlay(Template_overlayContext ctx) {
        archetype = new Archetype();
        archetype.setDifferential(false);
        parseArchetypeHRID(ctx.ARCHETYPE_HRID());
    }

    @Override
    public void enterOperational_template(Operational_templateContext ctx) {
        archetype = new Archetype();
        archetype.setDifferential(false);
        parseArchetypeHRID(ctx.ARCHETYPE_HRID());
    }

    private void parseArchetypeHRID(TerminalNode hrId) {
        if(hrId != null) {
            archetype.setArchetypeId(new ArchetypeHRID(hrId.getText()));
        }
    }

    public void enterMeta_data_item(AdlParser.Meta_data_itemContext ctx) {
        /*
         SYM_ADL_VERSION '=' VERSION_ID
        | SYM_UID '=' GUID
        | SYM_BUILD_UID '=' GUID
        | SYM_RM_RELEASE '=' VERSION_ID
        | SYM_IS_CONTROLLED
        | SYM_IS_GENERATED
        | identifier ( '=' meta_data_value )?

         */
        if(archetype instanceof AuthoredArchetype) {
            AuthoredArchetype authoredArchetype = (AuthoredArchetype) archetype;
            if(ctx.SYM_ADL_VERSION() != null) {
                authoredArchetype.setAdlVersion(ctx.VERSION_ID().getText());
            }
            if(ctx.SYM_UID() != null) {
                authoredArchetype.setBuildUid(ctx.GUID().getText());
            }
            if(ctx.SYM_RM_RELEASE() != null) {
                authoredArchetype.setRmRelease(ctx.VERSION_ID().getText());
            }
            if(ctx.SYM_IS_CONTROLLED() != null) {
                //TODO: not in the archetype modeL?
            }
            if(ctx.SYM_IS_GENERATED() != null) {
                authoredArchetype.setGenerated(true);
            }
            else if(ctx.identifier() != null) {
                authoredArchetype.addOtherMetadata(ctx.identifier().getText(), ctx.meta_data_value() == null ? null : ctx.meta_data_value().getText());
            }
        }

    }

    /**
     * one level below: definition, language, etc.
     */
    @Override
    public void enterDefinition_section(Definition_sectionContext ctx) {
        archetype.setDefinition(subTreeWalker.parseComplexObject(ctx.c_complex_object()));
    }

    @Override
    public void enterLanguage_section(Language_sectionContext ctx) {

    }

    @Override
    public void enterTerminology_section(Terminology_sectionContext ctx) {
        archetype.setTerminology(terminologyParser.parseTerminology(ctx));
    }

    public void enterRules_section(Rules_sectionContext ctx) {
        archetype.setRules(subTreeWalker.parseRules(ctx));
    }

    public Archetype getArchetype() {
        return archetype;
    }
}
