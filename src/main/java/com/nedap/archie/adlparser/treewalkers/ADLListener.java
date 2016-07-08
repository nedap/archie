package com.nedap.archie.adlparser.treewalkers;

import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.nedap.archie.adlparser.ADLParserErrors;
import com.nedap.archie.adlparser.antlr.AdlBaseListener;
import com.nedap.archie.adlparser.antlr.AdlParser;
import com.nedap.archie.adlparser.antlr.AdlParser.*;
import com.nedap.archie.adlparser.odin.OdinObjectParser;
import com.nedap.archie.adlparser.odin.OdinToJsonConverter;
import com.nedap.archie.aom.*;
import com.nedap.archie.aom.terminology.ArchetypeTerminology;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ANTLR listener for an ADLS file. Uses the listener construction for the topmost elements, switches to custom treewalker
 * for elements lower in the tree. This approach saves some code and complexity.
 *
 * Created by pieter.bos on 19/10/15.
 */
public class ADLListener extends AdlBaseListener {

    private ADLParserErrors errors;

    private Archetype rootArchetype;

    private Archetype archetype;
    private CComplexObjectParser subTreeWalker;
    private TerminologyParser terminologyParser;

    public ADLListener(ADLParserErrors errors) {
        this.errors = errors;
        subTreeWalker = new CComplexObjectParser(errors);
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
            ArchetypeHRID archetypeID = new ArchetypeHRID(hrId.getText());
            archetype.setArchetypeId(archetypeID);
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

            if(ctx.meta_data_tag_adl_version() != null) {
                authoredArchetype.setAdlVersion(ctx.VERSION_ID().getText());
            }
            if(ctx.meta_data_tag_build_uid() != null) {
                authoredArchetype.setBuildUid(ctx.GUID().getText());
            }
            if(ctx.meta_data_tag_rm_release() != null) {
                authoredArchetype.setRmRelease(ctx.VERSION_ID().getText());
            }
            if(ctx.meta_data_tag_is_controlled() != null) {
                authoredArchetype.setControlled(true);
            }
            if(ctx.meta_data_tag_is_generated() != null) {
                authoredArchetype.setGenerated(true);
            }
            if(ctx.meta_data_tag_uid() != null) {
                authoredArchetype.setUid(ctx.GUID().getText());
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
        archetype.setAuthoredResourceContent(OdinObjectParser.convert(ctx.odin_text(), LanguageSection.class));
    }

    @Override
    public void enterTerminology_section(Terminology_sectionContext ctx) {
        archetype.setTerminology(terminologyParser.parseTerminology(ctx));
    }

    @Override
    public void enterDescription_section(AdlParser.Description_sectionContext ctx) {
        archetype.setDescription(OdinObjectParser.convert(ctx.odin_text(), ResourceDescription.class));
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


    public void enterAnnotations_section(AdlParser.Annotations_sectionContext ctx) {
        archetype.setAnnotations(OdinObjectParser.convert(ctx.odin_text(), ResourceAnnotations.class));
    }

    public void enterComponent_terminologies_section(AdlParser.Component_terminologies_sectionContext ctx) {
        if(archetype instanceof OperationalTemplate) {
            OperationalTemplate template = (OperationalTemplate) archetype;

            TypeFactory typeFactory = OdinToJsonConverter.getObjectMapper().getTypeFactory();
            MapType mapType = typeFactory.constructMapType(ConcurrentHashMap.class, String.class, ArchetypeTerminology.class);

            template.setComponentTerminologies(OdinObjectParser.convert(ctx.odin_text(), mapType));
        } else {
            throw new IllegalArgumentException("cannot add component terminologies to anything but an operational template");
        }
    }


    /* getters for result */
    public Archetype getArchetype() {
        return rootArchetype;
    }

    public ADLParserErrors getErrors() {
        return errors;
    }


}
