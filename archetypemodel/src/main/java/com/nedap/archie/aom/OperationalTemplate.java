package com.nedap.archie.aom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class OperationalTemplate extends AuthoredArchetype {

    private Map<String, String> terminologyExtracts = new ConcurrentHashMap<>();//TODO: is this correct?


    public Map<String, String> getTerminologyExtracts() {
        return terminologyExtracts;
    }

    public void setTerminologyExtracts(Map<String, String> terminologyExtracts) {
        this.terminologyExtracts = terminologyExtracts;
    }
}
