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
import com.nedap.archie.aom.primitives.CBoolean;
import com.nedap.archie.serializer.adl.ADLDefinitionSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Marko Pipan
 */
public class CBooleanSerializer extends ConstraintSerializer<CBoolean> {
    public CBooleanSerializer(ADLDefinitionSerializer serializer) {
        super(serializer);
    }

    public static String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return Character.toString(Character.toUpperCase(str.charAt(0))) + str.substring(1);
    }

    @Override
    public void serialize(CBoolean cobj) {
        boolean constrained = false;

        List<String> valids = new ArrayList<>();
        if (!cobj.getConstraint().isEmpty()) {
            valids.addAll(cobj.getConstraint().stream()
                    .map(aBoolean -> capitalize(aBoolean.toString().toLowerCase())).collect(Collectors.toList()));
        }


        if (!valids.isEmpty()) {
            builder.append(Joiner.on(", ").join(valids));
            constrained = true;
        }

        if (cobj.getAssumedValue() != null) {
            builder.append("; ").append(capitalize(cobj.getAssumedValue().toString()));
            constrained = true;
        }

        if (!constrained) {
            builder.append("*");
        }


    }

    @Override
    public boolean isEmpty(CBoolean cobj) {
        return cobj.getConstraint() == null || cobj.getConstraint().isEmpty();
    }

}
