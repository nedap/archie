package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.AdlBaseListener;
import com.nedap.archie.adlparser.antlr.AdlParser;
import com.nedap.archie.adlparser.antlr.AdlParser.*;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeHRID;
import com.nedap.archie.aom.AuthoredArchetype;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.OperationalTemplate;
import com.nedap.archie.aom.Template;
import com.nedap.archie.aom.TemplateOverlay;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * ANTLR listener for an ADLS file. Uses the listener construction for the topmost elements, switches to custom treewalker
 * for elements lower in the tree. This approach saves some code and complexity.
 *
 * Created by pieter.bos on 19/10/15.
 */
public class ADLListener extends AdlBaseListener {

    private ADLParserErrors errors = new ADLParserErrors();

    private Archetype rootArchetype;

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
        rootArchetype = new AuthoredArchetype();
        rootArchetype.setDifferential(true);
        archetype = rootArchetype;
        parseArchetypeHRID(ctx.ARCHETYPE_HRID());
    }

    @Override
    public void enterTemplate(TemplateContext ctx) {
        rootArchetype = new Template();
        rootArchetype.setDifferential(false);
        archetype = rootArchetype;
        parseArchetypeHRID(ctx.ARCHETYPE_HRID());

    }

    @Override
    public void enterTemplate_overlay(Template_overlayContext ctx) {
        TemplateOverlay overlay =  new TemplateOverlay();
        overlay.setDifferential(true);
        if(rootArchetype != null) {
            if(rootArchetype instanceof Template) {
                ((Template) rootArchetype).addTemplateOverlay(overlay);
            } else {
                throw new IllegalArgumentException("Template overlay in a non-template archetype is not allowed. This sounds like a grammar problem.");
            }
        } else {
            rootArchetype = overlay;
        }
        archetype = overlay;
        parseArchetypeHRID(ctx.ARCHETYPE_HRID());
    }

    @Override
    public void enterOperational_template(Operational_templateContext ctx) {
        rootArchetype = new OperationalTemplate();
        rootArchetype.setDifferential(false);
        archetype = rootArchetype;
        parseArchetypeHRID(ctx.ARCHETYPE_HRID());
    }

    private void parseArchetypeHRID(TerminalNode hrId) {
        if(hrId != null) {
            ArchetypeHRID archetypeHRID = new ArchetypeHRID(hrId.getText());
            archetype.setArchetypeId(archetypeHRID);
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

            if(ctx.adl_version_keyword() != null) {
                authoredArchetype.setAdlVersion(ctx.VERSION_ID().getText());
            }
            if(ctx.uid_keyword() != null) {
                authoredArchetype.setBuildUid(ctx.GUID().getText());
            }
            if(ctx.rm_release_keyword() != null) {
                authoredArchetype.setRmRelease(ctx.VERSION_ID().getText());
            }
            if(ctx.is_controlled_keyword() != null) {
                authoredArchetype.setControlled(true);
            }
            if(ctx.is_generated_keyword() != null) {
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
        CComplexObject definition = subTreeWalker.parseComplexObject(ctx.c_complex_object());
        archetype.setDefinition(definition);
    }

    @Override
    public void enterLanguage_section(Language_sectionContext ctx) {

    }

    @Override
    public void enterTerminology_section(Terminology_sectionContext ctx) {
        archetype.setTerminology(terminologyParser.parseTerminology(ctx));
    }

    @Override
    public void enterSpecialization_section(Specialization_sectionContext ctx) {
        if(ctx != null && ctx.archetype_ref() != null) {
            archetype.setParentArchetypeId(ctx.archetype_ref().getText());
        }
    }

    public void enterRules_section(Rules_sectionContext ctx) {
        archetype.setRules(subTreeWalker.parseRules(ctx));
    }


    /* getters for result */
    public Archetype getArchetype() {
        return rootArchetype;
    }
}
