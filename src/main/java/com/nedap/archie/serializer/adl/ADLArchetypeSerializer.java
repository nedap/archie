package com.nedap.archie.serializer.adl;

import com.nedap.archie.aom.*;

/**
 * @author markopi
 */
abstract public class ADLArchetypeSerializer<T extends Archetype> {
    protected final T archetype;
    protected final ADLStringBuilder builder = new ADLStringBuilder();

    private final ADLDefinitionSerializer definitionSerializer;
    private final ADLRulesSerializer rulesSerializer;


    protected ADLArchetypeSerializer(T archetype) {
        this.archetype = archetype;

        this.definitionSerializer = new ADLDefinitionSerializer(builder);
        this.rulesSerializer = new ADLRulesSerializer(builder, definitionSerializer);
    }

    public static String serialize(Archetype archetype) {
        if (archetype instanceof Template) {
            return new ADLTemplateSerializer((Template) archetype).serialize();
        } else if (archetype instanceof OperationalTemplate) {
            throw new UnsupportedOperationException("Serialization of operational template is not yet supported");
        } else if (archetype instanceof TemplateOverlay) {
            return new ADLTemplateOverlaySerializer((TemplateOverlay) archetype).serialize();
        } else if (archetype instanceof AuthoredArchetype) {
            return new ADLAuthoredArchetypeSerializer<>((AuthoredArchetype) archetype).serialize();
        }
        throw new AssertionError("Could not serialize archetype of class " +
                (archetype == null ? null : archetype.getClass().getName()));
    }

    protected String serialize() {
        appendHead();
        appendSpecialize();
        appendLanguage();
        appendDescription();
        appendDefinition();
        appendTerminology();
        appendRules();
        appendAnnotations();

        return builder.toString();
    }

    protected void appendAnnotations() {
        if (archetype.getAnnotations()==null) return;
        builder.newline().append("annotations").newIndentedLine()
                .odin(archetype.getAnnotations())
                .unindent();
    }

    protected void appendRules() {
        if(archetype.getRules() == null) return;
        builder.newline().append("rules").newIndentedLine();
        rulesSerializer.appendRules(archetype.getRules());
        builder.newUnindentedLine();
    }

    protected void appendTerminology() {
        if (archetype.getTerminology() == null) return;
        builder.newline().append("terminology").newIndentedLine()
                .odin(archetype.getTerminology())
                .unindent();

    }

    protected void appendDefinition() {
        if (archetype.getDefinition() == null) return;

        builder.newline().append("definition");
        definitionSerializer.appendCObject(archetype.getDefinition());
    }

    protected abstract void appendLanguage();

    protected abstract void appendDescription();

    private void appendSpecialize() {
        if (archetype.getParentArchetypeId() == null) return;

        builder.newline().append("specialize").newIndentedLine()
                .append(archetype.getParentArchetypeId())
                .newUnindentedLine();
    }

    abstract protected String headTag();

    private void appendHead() {
        builder.append(headTag());
        appendHeaderAttributes();

        builder.newIndentedLine()
                .append(archetype.getArchetypeId().getFullId())
                .newUnindentedLine();
    }

    abstract protected void appendHeaderAttributes();

    public ADLStringBuilder getBuilder() {
        return builder;
    }

    public T getArchetype() {
        return archetype;
    }


}
