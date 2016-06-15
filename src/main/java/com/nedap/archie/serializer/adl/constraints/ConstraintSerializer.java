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


import com.nedap.archie.aom.CObject;
import com.nedap.archie.serializer.adl.ADLDefinitionSerializer;
import com.nedap.archie.serializer.adl.ADLStringBuilder;

/**
 * @author Marko Pipan
 */
public abstract class ConstraintSerializer<T extends CObject> {
    protected final ADLDefinitionSerializer serializer;
    protected final ADLStringBuilder builder;

    public ConstraintSerializer(ADLDefinitionSerializer serializer) {
        this.serializer = serializer;
        this.builder = serializer.getBuilder();
    }

    abstract public void serialize(T cobj);

    public String getSimpleCommentText(T cobj) {
        return null;
    }

    public boolean isEmpty(T cobj) {
        return false;
    }

    public int mark() {
        return builder.mark();
    }

    public void revert(int previousMark) {
        builder.revert(previousMark);
    }
}
