package com.nedap.archie.rm;

import com.esotericsoftware.kryo.Kryo;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.base.OpenEHRBase;
import com.nedap.archie.util.KryoUtil;

import java.io.Serializable;

/**
 * Common base class for all RM Objects. Should this be an interface instead?
 *
 * Created by pieter.bos on 01/03/16.
 */
public abstract class RMObject extends OpenEHRBase implements Serializable, Cloneable {

    public RMObject clone() {
        Kryo kryo = null;
        try {
            kryo = KryoUtil.getPool().borrow();
            return kryo.copy(this);
        } finally {
            KryoUtil.getPool().release(kryo);
        }
    }

}
