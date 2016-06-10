/*
 * ADL2-core
 * Copyright (c) 2013-2014 Marand d.o.o. (www.marand.com)
 *
 * This file is part of ADL2-core.
 *
 * ADL2-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nedap.archie.serializer.adl.constraints;


import com.nedap.archie.aom.ArchetypeSlot;
import com.nedap.archie.rules.Assertion;
import com.nedap.archie.serializer.adl.ADLDefinitionSerializer;
import com.nedap.archie.serializer.adl.ADLExpressionSerializer;
import com.nedap.archie.serializer.adl.ArchetypeSerializeUtils;

/**
 * Created by bna on 27.01.2015.
 * <p>
 * allow_archetype CLUSTER[at1030] occurrences matches {0..1} matches {	-- Exertion
 * include
 * archetype_id/value matches {/openEHR-EHR-CLUSTER\.level_of_exertion(-[a-zA-Z0-9_]+)*\.v1/}
 * }
 */
public class ArchetypeSlotSerializer extends ConstraintSerializer<ArchetypeSlot> {

    public ArchetypeSlotSerializer(ADLDefinitionSerializer serializer) {
        super(serializer);
    }

    @Override
    public void serialize(ArchetypeSlot cobj) {
        builder.indent().newline()
                .append("allow_archetype")
                .append(" ")
                .append(cobj.getRmTypeName()).append("[").append(cobj.getNodeId()).append("]");
        if (cobj.getOccurences() != null) {
            builder.append(" occurrences matches {");
            ArchetypeSerializeUtils.buildOccurrences(builder, cobj.getOccurences());
            builder.append("}");
        }
        if (cobj.isClosed()) {
            builder.append(" closed");
        } else {
            appendMatches(cobj);
        }
        builder.unindent();
    }

    private void appendMatches(ArchetypeSlot cobj) {
        int mark = builder.mark();
        builder.append(" matches { ");
        boolean hasContent = false;

        if (cobj.getIncludes() != null && cobj.getIncludes().size() > 0) {
            hasContent = true;
            builder.indent().newline()
                    .append("include")
                    .indent();
            for (Assertion a : cobj.getIncludes()) {
                builder.newline().append(ADLExpressionSerializer.serialize(a.getExpression()));
            }
            builder.unindent().unindent();
        }
        if (cobj.getExcludes() != null && cobj.getExcludes().size() > 0) {
            hasContent = true;
            builder.indent().newline()
                    .append("exclude")
                    .indent();
            for (Assertion a : cobj.getExcludes()) {
                builder.newline().append(ADLExpressionSerializer.serialize(a.getExpression()));
            }
            builder.unindent().unindent();
        }

        if (hasContent) {
            builder.newline().append("}");
        } else {
            builder.revert(mark);
        }
    }
}
