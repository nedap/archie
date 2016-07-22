package com.nedap.archie.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

/**
 * Created by pieter.bos on 03/11/15.
 */
public class KryoUtil {

    // Build pool with SoftReferences enabled (optional)
    private static KryoPool pool;

    static {
        KryoFactory factory = new KryoFactory() {
            public Kryo create() {
                Kryo kryo = new Kryo();
                return kryo;
            }
        };
        pool = new KryoPool.Builder(factory).softReferences().build();
    }

    public static KryoPool getPool() {
        return pool;
    }
}
