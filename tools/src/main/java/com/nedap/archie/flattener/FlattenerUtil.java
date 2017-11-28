package com.nedap.archie.flattener;

import com.nedap.archie.aom.CObject;
import com.nedap.archie.rules.Assertion;

import java.util.List;

public class FlattenerUtil {

    public static boolean isOverridenCObject(CObject specialized, CObject parent) {
        return isOverriddenIdCode(specialized.getNodeId(), parent.getNodeId());
    }

    public static boolean isOverriddenIdCode(String specializedNodeId, String parentNodeId) {
        if(specializedNodeId.equalsIgnoreCase(parentNodeId)) {
            return true;
        }
        if(specializedNodeId.lastIndexOf('.') > 0) {
            specializedNodeId = specializedNodeId.substring(0, specializedNodeId.lastIndexOf('.'));//-1?
        }
        return specializedNodeId.equals(parentNodeId) || specializedNodeId.startsWith(parentNodeId + ".");
    }

    public static List<Assertion> getPossiblyOverridenListValue(List<Assertion> parent, List<Assertion> child) {
        if(child != null && !child.isEmpty()) {
            return child;
        }
        return parent;
    }

    public static <T> T getPossiblyOverridenValue(T parent, T specialized) {
        if(specialized != null) {
            return specialized;
        }
        return parent;
    }
}
