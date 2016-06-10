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
