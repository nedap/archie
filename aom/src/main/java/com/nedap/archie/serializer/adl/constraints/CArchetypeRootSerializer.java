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


import com.nedap.archie.aom.CArchetypeRoot;
import com.nedap.archie.serializer.adl.ADLDefinitionSerializer;

/**
 * @author Marko Pipan
 */
public class CArchetypeRootSerializer extends CComplexObjectSerializer<CArchetypeRoot> {
    public CArchetypeRootSerializer(ADLDefinitionSerializer serializer) {
        super(serializer);
    }

    @Override
    public void serialize(CArchetypeRoot cobj) {
        builder.indent().newline();
        builder.append("use_archetype");
        builder.append(" ").append(cobj.getRmTypeName());
        builder.append("[");
        boolean nodeIdAppended = false;
        if (cobj.getNodeId() != null) {
            nodeIdAppended = true;
            builder.append(cobj.getNodeId());
        }
        if(cobj.getArchetypeRef() != null) {
            if(nodeIdAppended) {
                builder.append(", ");
            }
            builder.append(cobj.getArchetypeRef());
        }
        builder.append("]");

        appendOccurrences(cobj);
        //this is not according to the grammar, won't parse and not according to the AOM, but according to specs.
        //it only occurs when you flatten a use_archetype node. You can change it into complex objects with an option in the flattener
        //See https://openehr.atlassian.net/browse/SPECPR-217 and https://openehr.atlassian.net/browse/SPECPR-218
        //on why this is a good idea
        if(cobj.getAttributes() != null && !cobj.getAttributes().isEmpty()) {
            builder.ensureSpace();
            builder.append("matches {");
            builder.lineComment(serializer.getTermText(cobj));
            buildAttributesAndTuples(cobj);
            builder.append("}");
        }


        builder.unindent();
    }
}
