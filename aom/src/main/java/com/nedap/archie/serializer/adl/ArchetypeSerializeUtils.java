package com.nedap.archie.serializer.adl;


import com.nedap.archie.base.MultiplicityInterval;

/**
 * @author Marko Pipan
 */
public class ArchetypeSerializeUtils {
    public static void buildOccurrences(ADLStringBuilder builder, MultiplicityInterval occ) {
        if (occ.getLower() == null && occ.getUpper() == null) {
            builder.append("*");
        } else if (occ.getLower() != null && occ.getLower().equals(occ.getUpper())) {
            builder.append(occ.getLower());
        } else {
            builder.append(occ.getLower() != null ? occ.getLower() : 0);
            builder.append("..");
            builder.append(occ.getUpper() != null ? occ.getUpper() : "*");
        }
    }
}
