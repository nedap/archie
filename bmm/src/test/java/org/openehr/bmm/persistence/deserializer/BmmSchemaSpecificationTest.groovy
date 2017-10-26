package org.openehr.bmm.persistence.deserializer

/*
 * #%L
 * OpenEHR - Java Model Stack
 * %%
 * Copyright (C) 2016 - 2017  Cognitive Medical Systems, Inc (http://www.cognitivemedicine.com).
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 * Author: Claude Nanjo
 */

import org.openehr.bmm.core.BmmClass
import org.openehr.bmm.core.BmmContainerProperty
import org.openehr.bmm.core.BmmPackageContainer
import org.openehr.bmm.core.BmmProperty
import org.openehr.bmm.persistence.PersistedBmmSchema
import org.openehr.odin.loader.OdinLoaderImpl
import org.openehr.odin.CompositeOdinObject

import java.lang.reflect.Field

import spock.lang.Specification

class BmmSchemaSpecificationTest extends Specification {
//    PersistedBmmSchema bmmSchema
//    Map<String, BmmPackageContainer> bmmPackageContainers
//    Map<String, BmmClass> bmmPrimitiveTypes
//    List<BmmClass> pkgCoreRootClasses
//
//
//    def setup() {
//        OdinLoaderImpl loader = new OdinLoaderImpl();
//        org.openehr.odin.antlr.OdinVisitorImpl visitor = loader.loadOdinFile(BmmSchemaSpecificationTest.class.getResourceAsStream("/cimi/CIMI-RM-3.0.5.bmm"));
//        CompositeOdinObject root = visitor.getAstRootNode();
//        BmmSchemaDeserializer deserializer = new BmmSchemaDeserializer();
//        bmmSchema = deserializer.deserialize(root);
//
//        bmmPackageContainers = bmmSchema.packageContainers
//        BmmPackageContainer bmmPackageContainer = bmmPackageContainers.get("CIMI_Reference_Model")
//        def pkgCore = bmmPackageContainer.packages.get("Core")
//
//        pkgCoreRootClasses = pkgCore.getRootClasses()
//
//        bmmPrimitiveTypes = bmmSchema.primitives
//    }
//
//    def "bmm version"() {
//        expect:
//        bmmSchema.getBmmInternalVersion() == "2.0"
//    }
//
//    def "schema id"() {
//        expect:
//        bmmSchema.getRmPublisher() == "CIMI"
//        bmmSchema.getSchemaName() == "RM"
//        bmmSchema.getRmRelease() == "3.0.5"
//    }
//
//    def "schema documentation"() {
//        expect:
//        bmmSchema.getSchemaRevision() == "Monday, October 19, 2015"
//        bmmSchema.getSchemaLifecycleState() == "dstu"
//        bmmSchema.getSchemaDescription() == "CIMI_Reference_Model v3.0.5 schema generated from UML"
//    }
//
//    def "archetyping"() {
//        expect:
//        bmmSchema.getArchetypeRmClosurePackages().get(0) == "CIMI_Reference_Model.Core"
//    }
//
//    def "packages"() {
//        expect:
//        bmmPackageContainers.keySet().size() == 1
//        bmmPackageContainers.containsKey("CIMI_Reference_Model") == true
//        BmmPackageContainer bmmPackageContainer = bmmPackageContainers.get("CIMI_Reference_Model")
//        bmmPackageContainer.name == "CIMI_Reference_Model"
//        def map = bmmPackageContainer.packages
//        map.size() == 4
//
//        def pkgCore = map.get("Core")
//        pkgCore.name == "Core"
//        List<BmmClass> pkgCoreRootClasses = pkgCore.getRootClasses()
//        pkgCoreRootClasses.name as Set == [
//                "ARCHETYPED",
//                "ELEMENT",
//                "ITEM",
//                "ITEM_GROUP",
//                "LINK",
//                "LOCATABLE",
//                "PARTICIPATION"] as Set
//
//        def pkgDataValueTypes = map.get("Data_Value_Types")
//        pkgDataValueTypes.name == "Data_Value_Types"
//        List<BmmClass> pkgDataValueTypesRootClasses = pkgDataValueTypes.getRootClasses()
//        pkgDataValueTypesRootClasses.name as Set == [
//                "AMOUNT",
//                "CODED_TEXT",
//                "COUNT",
//                "DATA_VALUE",
//                "DATE",
//                "DATE_TIME",
//                "DURATION",
//                "EHR_URI",
//                "ENCAPSULATED",
//                "IDENTIFIER",
//                "INTERVAL_VALUE",
//                "MULTIMEDIA",
//                "ORDERED_VALUE",
//                "ORDINAL",
//                "PARSABLE",
//                "PLAIN_TEXT",
//                "PROPORTION",
//                "QUANTIFIED",
//                "QUANTITY",
//                "TERM_MAPPING",
//                "TEXT",
//                "TIME",
//                "URI_VALUE",
//                "YESNO"] as Set
//
//        def pkgParty = map.get("Party")
//        pkgParty.name == "Party"
//        List<BmmClass> pkgPartyRootClasses = pkgParty.getRootClasses()
//        pkgPartyRootClasses.name as Set == [
//                "ACTOR",
//                "PARTY",
//                "PARTY_RELATIONSHIP",
//                "ROLE"] as Set
//
//        def pkgPrimitiveTypes = map.get("Primitive_Types")
//        pkgPrimitiveTypes.name == "Primitive_Types"
//        List<BmmClass> pkgPrimitiveTypesRootClasess = pkgPrimitiveTypes.getRootClasses()
//        pkgPrimitiveTypesRootClasess.name as Set == [
//                "Any",
//                "Array",
//                "List",
//                "Boolean",
//                "Byte",
//                "Character",
//                "Integer",
//                "Real",
//                "String",
//                "URI"] as Set
//    }
//
//    // Classes
//
//    def "archetyped"() {
//        when:
//        Map<String, BmmProperty> props
//        pkgCoreRootClasses.each {
//            if (it.name == "ARCHETYPED") {
//                BmmClass bmmClass = it
//                Field field = BmmClass.class.getDeclaredField("properties")
//                field.setAccessible(true)
//                props = (Map<String, BmmProperty<?>>) field.get(it)
//            }
//        }
//
//        then:
//        def achetypeId = props.get("archetype_id")
//        achetypeId.name == "archetype_id"
//        achetypeId.bmmType == "String"
//        achetypeId.isMandatory == true
//        achetypeId.isImInfrastructure == true
//
//        def rmVersion = props.get("rm_version")
//        rmVersion.name == "rm_version"
//        rmVersion.bmmType == "String"
//        achetypeId.isMandatory == true
//        achetypeId.isImInfrastructure == true
//
//    }
//
//    def "element"() {
//        when:
//        Map<String, BmmProperty> props
//        Map<String, BmmClass> ancestorsMap
//        pkgCoreRootClasses.each {
//            if (it.name == "ELEMENT") {
//                BmmClass bmmClass = it
//                ancestorsMap = it.getAncestors()
//                Field field = BmmClass.class.getDeclaredField("properties")
//                field.setAccessible(true)
//                props = (Map<String, BmmProperty<?>>) field.get(it)
//            }
//        }
//
//        BmmClass itemClass = ancestorsMap.get("ITEM")
//
//        def nullFlavor = props.get("null_flavor")
//        def value = props.get("value")
//
//        then:
//        nullFlavor.class == BmmProperty //P_BMM_SINGLE_PROPERTY
//
//        nullFlavor.name == "null_flavor"
//        nullFlavor.bmmType == "CODED_TEXT"
//
//        value.name == "value"
//        value.bmmType == "DATA_VALUE"
//
//        itemClass.name == "ITEM"
//
//    }
//
//    def "item"() {
//        when:
//        boolean isAbstract
//        Map<String, BmmClass> ancestorsMap
//        pkgCoreRootClasses.each {
//            if (it.name == "ITEM") {
//                ancestorsMap = it.getAncestors()
//                isAbstract = it.isAbstract
//            }
//        }
//
//        BmmClass locatableClass = ancestorsMap.get("LOCATABLE")
//
//        then:
//        ancestorsMap.containsKey("LOCATABLE")
//        ancestorsMap.containsKey("...")
//        isAbstract == true
//        locatableClass.name == "LOCATABLE"
//
//
//    }
//
//    def "item group"() {
//        when:
//        Map<String, BmmProperty> props
//        Map<String, BmmClass> ancestorsMap
//        pkgCoreRootClasses.each {
//            if (it.name == "ITEM_GROUP") {
//                ancestorsMap = it.getAncestors()
//                Field field = BmmClass.class.getDeclaredField("properties")
//                field.setAccessible(true)
//                props = (Map<String, BmmProperty<?>>) field.get(it)
//            }
//        }
//
//        BmmClass itemgroupClass = ancestorsMap.get("ITEM")
//
//        def item = props.get("item")
//        def participation = props.get("participation")
//
//        then:
//        item.class == BmmContainerProperty //P_BMM_CONTAINER_PROPERTY
//        item.getTypeDef().getContainerType() == "List"
//        item.getTypeDef().getBmmType() == "ITEM"
//        item.getCardinality().intervalExpression == "|>=1|"
//        item.getMandatory() == true
//
//        participation.class == BmmContainerProperty
//        participation.getTypeDef().getContainerType() == "List"
//        participation.getBmmType() == "PARTICIPATION"
//        participation.getCardinality().intervalExpression == "|>=0|"
//
//        itemgroupClass.name == "ITEM"
//
//    }
//
//    // primitive types
//
//    def "primitive types"() {
//        expect:
//        bmmPrimitiveTypes.keySet().size() == 10
//    }
//
//    def "Any"() {
//        expect:
//        bmmPrimitiveTypes.containsKey("Any") == true
//        BmmClass bmmClass = bmmPrimitiveTypes.get("Any")
//        bmmClass.name == "Any"
//    }
//
//    def "Array"() {
//        expect:
//        bmmPrimitiveTypes.containsKey("Array") == true
//        BmmClass bmmClass = bmmPrimitiveTypes.get("Array")
//        bmmClass.name == "Array"
//        def genericParameters = bmmClass.genericParameters
//        genericParameters.get(0).getName() == "T"
//
//    }
}
