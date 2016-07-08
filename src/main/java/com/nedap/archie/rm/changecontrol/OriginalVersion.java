package com.nedap.archie.rm.changecontrol;

import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.generic.Attestation;
import com.nedap.archie.rm.support.identification.HierObjectId;
import com.nedap.archie.rm.support.identification.ObjectVersionId;


import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 08/07/16.
 */
public class OriginalVersion<Type> extends Version<Type> {

    private ObjectVersionId uid;
    private ObjectVersionId precedingVersionUid;
    @Nullable
    private List<ObjectVersionId> otherInputVersionIds = new ArrayList<>();

    private DvCodedText lifecycleState;
    private List<Attestation> attestations = new ArrayList<>();
    private Type data;


    @Override
    public ObjectVersionId getUid() {
        return uid;
    }

    @Override
    public ObjectVersionId getPrecedingVersionUid() {
        return precedingVersionUid;
    }

    public void setUid(ObjectVersionId uid) {
        this.uid = uid;
    }

    public void setPrecedingVersionUid(ObjectVersionId precedingVersionUid) {
        this.precedingVersionUid = precedingVersionUid;
    }

    @Nullable
    public List<ObjectVersionId> getOtherInputVersionIds() {
        return otherInputVersionIds;
    }

    public void setOtherInputVersionIds(@Nullable List<ObjectVersionId> otherInputVersionIds) {
        this.otherInputVersionIds = otherInputVersionIds;
    }

    @Override
    public DvCodedText getLifecycleState() {
        return lifecycleState;
    }

    @Override
    public String getCanonicalForm() {
        return null;//TODO no idea what this should do
    }

    @Override
    public boolean isBranch() {
        return false;
    }

    public void setLifecycleState(DvCodedText lifecycleState) {
        this.lifecycleState = lifecycleState;
    }

    public List<Attestation> getAttestations() {
        return attestations;
    }

    public void setAttestations(List<Attestation> attestations) {
        this.attestations = attestations;
    }

    @Override
    public Type getData() {
        return data;
    }

    public void setData(Type data) {
        this.data = data;
    }
}
