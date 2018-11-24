package org.openehr.bmm.v2.validation.converters;

import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmGenericClass;
import org.openehr.bmm.core.BmmGenericParameter;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.core.BmmProperty;
import org.openehr.bmm.v2.persistence.PBmmClass;
import org.openehr.bmm.v2.persistence.PBmmGenericParameter;
import org.openehr.bmm.v2.persistence.PBmmProperty;

public class BmmClassCreator {

    public void populateBmmClass(PBmmClass pBmmClass, BmmModel schema) {
        BmmClass bmmClass = schema.getClassDefinition(pBmmClass.getName());
        if(bmmClass != null) {
            if(pBmmClass.getAncestors() != null) {
                for(String ancestor:pBmmClass.getAncestors()) {
                    if(schema.getClassDefinition(ancestor) != null) {
                        bmmClass.addAncestor(schema.getClassDefinition(ancestor));
                    } else {
                        throw new RuntimeException("Error retrieving class definition for " + ancestor);
                    }
                }
            }
            if(bmmClass instanceof BmmGenericClass && pBmmClass.getGenericParameterDefs() != null) {
                for(PBmmGenericParameter param:pBmmClass.getGenericParameterDefs().values()) {
                    BmmGenericParameter bmmGenericParameter = createBmmGenericParameter(param, schema);
                    ((BmmGenericClass) bmmClass).addGenericParameter(bmmGenericParameter);
                }
            }
            if(pBmmClass.getProperties() != null) {
                for(PBmmProperty property:pBmmClass.getProperties().values()) {
                    BmmProperty propertyDef = new PropertyCreator().createBmmProperty(property, schema, bmmClass);
                    bmmClass.addProperty(propertyDef);
                }
            }
        } else {
            throw new RuntimeException("The class " + pBmmClass.getName() + " is null. It may have been defined as a class or a primitive but not included in a package");
        }
    }



    private BmmGenericParameter createBmmGenericParameter(PBmmGenericParameter param, BmmModel bmmSchema) {
        BmmGenericParameter bmmGenericParameter = new BmmGenericParameter();
        bmmGenericParameter.setName(param.getName());
        bmmGenericParameter.setDocumentation(param.getDocumentation());

        if(param.getConformsToType() != null) {
            BmmClass conformsToTypeClass = bmmSchema.getClassDefinition(param.getConformsToType());
            if(conformsToTypeClass != null) {
                bmmGenericParameter.setConformsToType(conformsToTypeClass);
            }
        } else {
            bmmGenericParameter.setBaseClass(bmmSchema.getAnyClassDefinition());
            bmmGenericParameter.setConformsToType(null);
        }

        return bmmGenericParameter;
    }

    public BmmClass createBmmClass(PBmmClass pBmmClass) {
        BmmClass bmmClass;
        if(pBmmClass.getGenericParameterDefs() != null && pBmmClass.getGenericParameterDefs().size() > 0) {
            bmmClass = new BmmGenericClass(pBmmClass.getName());
        } else {
            bmmClass = new BmmClass(pBmmClass.getName());
        }
        bmmClass.setDocumentation(pBmmClass.getDocumentation());
        bmmClass.setAbstract(pBmmClass.isAbstract());
        return bmmClass;
    }
}
