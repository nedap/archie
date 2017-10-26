package org.openehr.bmm.persistence.deserializer;

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

import org.openehr.bmm.persistence.*;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.OdinAttribute;
import org.openehr.odin.OdinObject;
import org.openehr.odin.antlr.OdinVisitorImpl;
import org.openehr.odin.loader.OdinLoaderImpl;

import java.util.List;

public class BmmSchemaDeserializer {

    public PersistedBmmSchema deserialize(String filePath) {
        OdinLoaderImpl loader = new OdinLoaderImpl();
        OdinVisitorImpl visitor = loader.loadOdinFile(filePath);
        CompositeOdinObject root = visitor.getAstRootNode();
        BmmSchemaDeserializer deserializer = new BmmSchemaDeserializer();
        return deserializer.deserialize(root);
    }

    public PersistedBmmSchema deserialize(CompositeOdinObject modelNode) {
        PersistedBmmSchema schema = new PersistedBmmSchema();

        deserializeSchemaHeader(modelNode, schema);
        deserializeSchemaIncludes(modelNode, schema);
        deserializePackages(modelNode, schema);
        deserializeClassDefinitions(modelNode, schema);
        deserializePrimitives(modelNode, schema);

        return schema;
    }

    protected void deserializeSchemaHeader(CompositeOdinObject modelNode, PersistedBmmSchema schema) {
        if(modelNode.getAttribute("rm_publisher") != null) {
            schema.setRmPublisher(modelNode.getAttribute("rm_publisher").getStringValue());
        }
        if(modelNode.getAttribute("bmm_version")!= null) {
            schema.setBmmVersion(modelNode.getAttribute("bmm_version").getStringValue());
        }
        if(modelNode.getAttribute("rm_release") != null) {
            schema.setRmRelease(modelNode.getAttribute("rm_release").getStringValue());
        }
        if(modelNode.getAttribute("schema_name") != null) {
            schema.setSchemaName(modelNode.getAttribute("schema_name").getStringValue());
        }
        if(modelNode.getAttribute("schema_revision") != null) {
            schema.setSchemaRevision(modelNode.getAttribute("schema_revision").getStringValue());
        }
        if(modelNode.getAttribute("schema_lifecycle_state") != null) {
            schema.setSchemaLifecycleState(modelNode.getAttribute("schema_lifecycle_state").getStringValue());
        }
        if(modelNode.getAttribute("schema_author") != null) {
            schema.setSchemaAuthor(modelNode.getAttribute("schema_author").getStringValue());
        }
        if(modelNode.getAttribute("schema_description") != null) {
            schema.setSchemaDescription(modelNode.getAttribute("schema_description").getStringValue());
        }
        if(modelNode.getAttribute("schema_contributors") != null) {
            schema.setSchemaContributors(modelNode.getAttribute("schema_contributors").getChildrenAsStringList());
        }
        if(modelNode.getAttribute("archetype_parent_class") != null) {
            schema.setArchetypeParentClass(modelNode.getAttribute("archetype_parent_class").getStringValue());
        }
        if(modelNode.getAttribute("archetype_data_value_parent_class") != null) {
            schema.setArchetypeDataValueParentClass(modelNode.getAttribute("archetype_data_value_parent_class").getStringValue());
        }
        if(modelNode.getAttribute("archetype_rm_closure_packages") != null) {
            schema.addArchetypeRmClosurePackage(modelNode.getAttribute("archetype_rm_closure_packages").getStringValueAt(0));
        }
        if(modelNode.getAttribute("archetype_visualize_descendants_of") != null) {
            schema.setArchetypeVisualizeDescendantsOf(modelNode.getAttribute("archetype_visualize_descendants_of").getStringValue());
        }
    }

    protected void deserializeSchemaIncludes(CompositeOdinObject modelNode, PersistedBmmSchema schema) {
        BmmIncludeDeserializer deserializer = new BmmIncludeDeserializer();
        List<BmmIncludeSpecification> includes = deserializer.deserialize(modelNode);
        schema.setIncludes(includes);
    }

    protected void deserializePackages(CompositeOdinObject modelNode, PersistedBmmSchema schema) {
        CompositeOdinObject packages = modelNode.getAttribute("packages").getSoleCompositeObjectBody();
        processPackages(schema, packages);
    }

    protected void deserializeClassDefinitions(CompositeOdinObject modelNode, PersistedBmmSchema schema) {
        CompositeOdinObject classDefinitions = modelNode.getAttribute("class_definitions").getSoleCompositeObjectBody();
        processClassDefinitions(schema, classDefinitions);
    }

    protected void deserializePrimitives(CompositeOdinObject modelNode, PersistedBmmSchema schema) {
        OdinAttribute primitiveTypeDef = modelNode.getAttribute("primitive_types");
        if(primitiveTypeDef != null) {
            CompositeOdinObject primitiveTypes = primitiveTypeDef.getSoleCompositeObjectBody();
            processPrimitiveTypes(schema, primitiveTypes);
        }
    }

    protected void processPackages(IPersistedBmmPackageContainer model, CompositeOdinObject packageDefinitions) {
        BmmPackageContainerDeserializer deserializer = new BmmPackageContainerDeserializer();
        List<PersistedBmmPackage> packages = deserializer.deserialize(packageDefinitions);
        model.setPackages(packages);
    }

    protected void processClassDefinitions(PersistedBmmSchema model, CompositeOdinObject classDefinitions) {
        List<OdinObject> classDefinitionTypes = classDefinitions.getKeyedObjects();
        classDefinitionTypes.forEach(classDefinition -> {
            if(classDefinition.getType() == null) {
                BmmClassDeserializer classDeserializer = new BmmClassDeserializer();
                PersistedBmmClass bmmClass = classDeserializer.deserialize((CompositeOdinObject) classDefinition);
                model.addClassDefinition(bmmClass);
                bmmClass.setSourceSchemaId(model.getSchemaId());
            } else {
                if(classDefinition.getType().equalsIgnoreCase("P_BMM_ENUMERATION_INTEGER")) {
                    BmmEnumerationIntegerDeserializer deserializer = new BmmEnumerationIntegerDeserializer();
                    PersistedBmmEnumerationInteger integerEnumeration = deserializer.deserialize((CompositeOdinObject)classDefinition);
                    model.addClassDefinition(integerEnumeration);
                    integerEnumeration.setSourceSchemaId(model.getSchemaId());
                }
                if(classDefinition.getType().equalsIgnoreCase("P_BMM_ENUMERATION_STRING")) {
                    BmmEnumerationStringDeserializer deserializer = new BmmEnumerationStringDeserializer();
                    PersistedBmmEnumerationString stringEnumeration = deserializer.deserialize((CompositeOdinObject)classDefinition);
                    model.addClassDefinition(stringEnumeration);
                    stringEnumeration.setSourceSchemaId(model.getSchemaId());
                }
            }
        });

//        List<PersistedBmmClass> classDefs = model.getClassDefinitions();
//        for (PersistedBmmClass definition : classDefs){
//            List<String> ancestors = definition.getAncestors();
//            for(Map.Entry<String, BmmClass> ancestor : ancestors.entrySet()){
//                String keyName = ancestor.getKey();
//                boolean isClassFound = false;
//                for(BmmClass classDef : classDefs){
//                    if(classDef.getName().equals(keyName)){
//                        ancestor.setValue(classDef);
//                        isClassFound = true;
//                    }
//                }
//                if(!isClassFound && !keyName.equals("...")){
//                    throw new RuntimeException("Ancestor BMM Class " + keyName + " is not defined");
//                }
//            }
//        }
    }

    protected void processPrimitiveTypes(PersistedBmmSchema model, CompositeOdinObject primitiveTypes) {
        List<OdinObject> primitiveTypeDefs = primitiveTypes.getKeyedObjects();
        primitiveTypeDefs.forEach(classDefinition -> {
            BmmClassDeserializer classDeserializer = new BmmClassDeserializer();
            PersistedBmmClass bmmClass = classDeserializer.deserialize((CompositeOdinObject) classDefinition);
            model.addPrimitive(bmmClass);
        });
    }
}
