package com.nedap.archie.aom;

import com.esotericsoftware.kryo.Kryo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.guestful.jsr310.kryo.KryoJsr310;

import java.io.Serializable;

/**
 * Common root class for all archetype objects - so we do not have to use java.lang.Object!
 * Created by pieter.bos on 15/10/15.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public abstract class ArchetypeModelObject implements Serializable, Cloneable {


    public ArchetypeModelObject clone() {
        Kryo kryo = new Kryo();
        KryoJsr310.addJsr310Serializers(kryo); //needed for all java.time types
        return kryo.copy(this);
    }
}
