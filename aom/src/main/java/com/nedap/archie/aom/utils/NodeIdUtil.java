package com.nedap.archie.aom.utils;

import com.nedap.archie.definitions.AdlCodeDefinitions;

import java.util.List;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NodeIdUtil {


    private String prefix;
    private List<Integer> codes = new ArrayList<>();;

    public NodeIdUtil(String nodeId) {
        if(AOMUtils.isValidIdCode(nodeId)) {
            String[] split = nodeId.substring(2).split("\\" + AdlCodeDefinitions.SPECIALIZATION_SEPARATOR);
            prefix = nodeId.substring(0, 2);
            for (int i = 0; i < split.length; i++) {
                codes.add(Integer.parseInt(split[i]));
            }
        }
    }

    public boolean isRedefined() {
        if(!isValid()) {
            return false;
        }
        if(codes.size() > 1) {
            for(int i = 0; i < codes.size()-1;i++) {
                if(codes.get(i) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValid() {
        return prefix != null;
    }


    public boolean isIdCode() {
        return AdlCodeDefinitions.ID_CODE_LEADER.equals(prefix);
    }

    public boolean isValueCode() {
        return AdlCodeDefinitions.VALUE_CODE_LEADER.equals(prefix);
    }

    public boolean isValueSetCode() {
        return AdlCodeDefinitions.VALUE_SET_CODE_LEADER.equals(prefix);
    }

    public String getPrefix() {
        return prefix;
    }

    public List<Integer> getCodes() {
        return codes;
    }


}
