package com.nedap.archie.serializer.adl;

import com.nedap.archie.aom.*;
import com.nedap.archie.aom.primitives.*;
import com.nedap.archie.aom.terminology.ArchetypeTerm;
import com.nedap.archie.base.terminology.TerminologyCode;
import com.nedap.archie.serializer.adl.constraints.*;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

/**
 * @author markopi
 */
public class ADLDefinitionSerializer {
    protected final ADLStringBuilder builder;

    private final Map<Class, ConstraintSerializer> constraintSerializers;

    public ADLDefinitionSerializer(ADLStringBuilder builder) {
        this.builder = builder;

        constraintSerializers = new HashMap<>();
        constraintSerializers.put(ArchetypeSlot.class, new ArchetypeSlotSerializer(this));
        constraintSerializers.put(CArchetypeRoot.class, new CArchetypeRootSerializer(this));
        constraintSerializers.put(CBoolean.class, new CBooleanSerializer(this));
        constraintSerializers.put(CComplexObject.class, new CComplexObjectSerializer(this));
        constraintSerializers.put(CComplexObjectProxy.class, new CComplexObjectProxySerializer(this));
        constraintSerializers.put(CDate.class, new CDateSerializer(this));
        constraintSerializers.put(CDateTime.class, new CDateTimeSerializer(this));
        constraintSerializers.put(CDuration.class, new CDurationSerializer(this));
        constraintSerializers.put(CInteger.class, new CIntegerSerializer(this));
        constraintSerializers.put(CReal.class, new CRealSerializer(this));
        constraintSerializers.put(CString.class, new CStringSerializer(this));
        constraintSerializers.put(CTerminologyCode.class, new CTerminologyCodeSerializer(this));
        constraintSerializers.put(CTime.class, new CTimeSerializer(this));
    }

    public static String serialize(CObject cons) {
        final ADLStringBuilder builder = new ADLStringBuilder();
        ADLDefinitionSerializer serializer = new ADLDefinitionSerializer(builder);
        serializer.appendCObject(cons);
        return builder.toString();
    }

    public ADLStringBuilder getBuilder() {
        return builder;
    }

    public String getTermText(CObject cobj) {
        String originalLanguage = ofNullable(cobj)
                .flatMap(c -> ofNullable(c.getArchetype()))
                .flatMap(a -> ofNullable(a.getOriginalLanguage()))
                .map(TerminologyCode::getCodeString)
                .orElse(null);
        if (originalLanguage == null) return null;

        ArchetypeTerm term = cobj.getArchetype().getTerm(cobj, originalLanguage);
        if (term == null) return null;
        return term.getText();
    }

    public void appendCObject(CObject cobj) {
        ConstraintSerializer<CObject> serializer = getSerializer(cobj);
        if (serializer != null) {
            serializer.serialize(cobj);
        } else {
            throw new AssertionError("Unsupported constraint: " + cobj.getClass().getName());
        }
    }

    public boolean isEmpty(CObject cobj) {
        ConstraintSerializer<CObject> serializer = getSerializer(cobj);
        return serializer != null && serializer.isEmpty(cobj);
    }

    public String getSimpleCommentText(CObject cobj) {
        ConstraintSerializer<CObject> serializer = getSerializer(cobj);
        if (serializer == null) return null;
        return serializer.getSimpleCommentText(cobj);
    }

    @SuppressWarnings("unchecked")
    private ConstraintSerializer<CObject> getSerializer(CObject cobj) {
        return constraintSerializers.get(cobj.getClass());
    }

}
