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

import com.google.common.base.Joiner;
import com.nedap.archie.aom.*;
import com.nedap.archie.base.Cardinality;
import com.nedap.archie.serializer.adl.ADLDefinitionSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.nedap.archie.serializer.adl.ArchetypeSerializeUtils.buildOccurrences;


/**
 * @author Marko Pipan
 */
public class CComplexObjectSerializer<T extends CComplexObject> extends ConstraintSerializer<T> {
    public CComplexObjectSerializer(ADLDefinitionSerializer serializer) {
        super(serializer);
    }

    @Override
    public void serialize(T cobj) {
        builder.indent().newline();
        appendSiblingOrder(cobj);
        builder.append(cobj.getRmTypeName());
        if (cobj.getNodeId() != null) {
            builder.append("[").append(cobj.getNodeId()).append("]");
        }
        builder.append(" ");
        appendOccurrences(cobj);
        if (cobj.getAttributes().isEmpty() && cobj.getAttributeTuples().isEmpty()) {
            builder.lineComment(serializer.getTermText(cobj));
        } else {
            builder.append("matches {");
            builder.lineComment(serializer.getTermText(cobj));
            buildAttributesAndTuples(cobj);
            builder.append("}");
        }
        builder.unindent();
    }

    protected void buildAttributesAndTuples(T cobj) {
        builder.indent().newline();
        Set<String> tupleAttributes = getTupleAttributeNames(cobj);

        cobj.getAttributes().stream()
                .filter(a -> !tupleAttributes.contains(a.getRmAttributeName()))
                .forEach(this::buildAttribute);

        cobj.getAttributeTuples().forEach(this::buildTuple);

        builder.unindent().newline();
    }

    private Set<String> getTupleAttributeNames(T cobj) {
        return cobj.getAttributeTuples().stream()
                    .flatMap(cat -> cat.getMembers().stream())
                    .map(CAttribute::getRmAttributeName)
                    .collect(Collectors.toSet());
    }


    private void buildAttribute(CAttribute cattr) {
        builder.tryNewLine();
        if (cattr.getDifferentialPath() == null) {
            builder.append(cattr.getRmAttributeName());
        } else {
            builder.append(cattr.getDifferentialPath());
        }
        if (cattr.getExistence() != null) {
            builder.append(" existence matches {");
            buildOccurrences(builder, cattr.getExistence());
            builder.append("}");
        }
        if (cattr.getCardinality() != null) {
            builder.append(" cardinality matches {");
            appendCardinality(cattr.getCardinality());
            builder.append("}");
        }
        if (!cattr.getChildren().isEmpty()) {
            buildAttributeChildConstraints(cattr);
        }
    }


    private void buildTuple(CAttributeTuple cAttributeTuple) {
        builder.tryNewLine();
        builder.append("[");
        List<String> members = cAttributeTuple.getMembers().stream()
                .map(CAttribute::getRmAttributeName)
                .collect(Collectors.toList());
        builder.append(Joiner.on(", ").join(members));
        builder.append("] matches {");
        builder.indent();
        for (int i = 0; i < cAttributeTuple.getTuples().size(); i++) {
            CPrimitiveTuple cObjectTuple = cAttributeTuple.getTuples().get(i);
            builder.newline();
            builder.append("[");
            for (int j = 0; j < cObjectTuple.getMembers().size(); j++) {
                builder.append("{");
                serializer.appendCObject(cObjectTuple.getMembers().get(j));
                builder.append("}");
                if (j < cObjectTuple.getMembers().size() - 1) {
                    builder.append(", ");
                }
            }
            builder.append("]");
            if (i < cAttributeTuple.getTuples().size() - 1) {
                builder.append(",");
            }

        }
        builder.unindent().newline();
        builder.append("}");
    }

    private void buildAttributeChildConstraints(CAttribute cattr) {
        List<CObject> children = filterNonEmptyChildren(cattr.getChildren());
        if(children.isEmpty()) {
            return;
        }

        builder.append(" matches ");
        boolean indent = !children.isEmpty() &&
                (children.size() > 1 || !(children.get(0) instanceof CPrimitiveObject));
        builder.append("{");
        children.forEach(serializer::appendCObject);

        if (indent) {
            builder.newline();
        }

        builder.append("}");

        if (!indent && !children.isEmpty()) {
            String commentText = serializer.getSimpleCommentText(children.get(0));
            if (commentText != null) {
                builder.lineComment(commentText);
            }
        }
    }

    private List<CObject> filterNonEmptyChildren(List<CObject> children) {
        return children.stream()
                .filter(child -> !serializer.isEmpty(child))
                .collect(Collectors.toList());
    }

    private void appendCardinality(Cardinality card) {
        buildOccurrences(builder, card.getInterval());
        List<String> tags = new ArrayList<>();
        if (!card.isOrdered()) {
            tags.add("unordered");
        }
        if (card.isUnique()) {
            tags.add("unique");
        }
        if (!tags.isEmpty()) {
            builder.append("; ").append(Joiner.on("; ").join(tags));
        }
    }
}
