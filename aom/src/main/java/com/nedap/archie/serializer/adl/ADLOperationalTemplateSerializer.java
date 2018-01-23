package com.nedap.archie.serializer.adl;

import com.nedap.archie.aom.OperationalTemplate;
import com.nedap.archie.aom.Template;
import com.nedap.archie.aom.TemplateOverlay;
import com.nedap.archie.serializer.odin.OdinSerializer;

/**
 * @author markopi
 */
class ADLOperationalTemplateSerializer extends ADLAuthoredArchetypeSerializer<OperationalTemplate> {

    public ADLOperationalTemplateSerializer(OperationalTemplate archetype) {
        super(archetype);
    }

    @Override
    protected String serialize() {
        super.serialize();
        builder.newline().append("component_terminologies").newIndentedLine()
                .append("component_terminologies = < ") //todo: this should perhaps be in the ODIN serializer?
                .indent()
                .odin(archetype.getComponentTerminologies())
                .unindent()
                .append("> ")
                .unindent();
        return builder.toString();
    }

    @Override
    protected String headTag() {
        return "operational_template";
    }
}
