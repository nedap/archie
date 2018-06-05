package com.nedap.archie.serializer.adl;

import com.nedap.archie.aom.TemplateOverlay;

/**
 * @author markopi
 */
class ADLTemplateOverlaySerializer extends ADLArchetypeSerializer<TemplateOverlay> {
    public ADLTemplateOverlaySerializer(TemplateOverlay archetype) {
        super(archetype);
    }

    @Override
    protected String headTag() {
        return "template_overlay";
    }

    @Override
    protected void appendHeaderAttributes() {
    }

    @Override
    protected void appendLanguage() {
    }

    @Override
    protected void appendDescription() {
    }

    @Override
    protected void appendRules() {

    }
}
