package com.nedap.archie.aom;

import com.esotericsoftware.kryo.Kryo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/**
 * Common root class for all archetype objects - so we do not have to use java.lang.Object!
 * Created by pieter.bos on 15/10/15.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public abstract class ArchetypeModelObject implements Serializable, Cloneable {


    public ArchetypeModelObject clone() {
        return new Kryo().copy(this);
    }
}
