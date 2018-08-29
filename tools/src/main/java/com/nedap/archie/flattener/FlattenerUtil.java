package com.nedap.archie.flattener;

import com.nedap.archie.aom.CObject;
import com.nedap.archie.rules.Assertion;

import java.util.List;

public class FlattenerUtil {



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
