package org.openehr.bmm.v2.validation.converters;

import org.openehr.bmm.v2.persistence.PBmmClass;
import org.openehr.bmm.v2.persistence.PBmmPackage;
import org.openehr.bmm.v2.persistence.PBmmPackageContainer;
import org.openehr.bmm.v2.persistence.PBmmSchema;

import java.util.Map;
import java.util.TreeMap;

public class PreprocessPersistedSchema {
    //convert all maps to case insensitive variants
    public void preprocess(PBmmSchema schema) {
        {
            Map<String, PBmmClass> classDefinitions = schema.getClassDefinitions();
            TreeMap<String, PBmmClass> newClassDefinitions = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            newClassDefinitions.putAll(classDefinitions);
            schema.setClassDefinitions(newClassDefinitions);
        }

        {
            Map<String, PBmmClass> primitiveTypes = schema.getPrimitiveTypes();
            TreeMap<String, PBmmClass> newPrimitiveTypes = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            newPrimitiveTypes.putAll(primitiveTypes);
            schema.setPrimitiveTypes(newPrimitiveTypes);
        }
        {
            convertPackages(schema);

        }



    }

    private void convertPackages(PBmmPackageContainer schema) {
        Map<String, PBmmPackage> packages = schema.getPackages();
        TreeMap<String, PBmmPackage> newpackages = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        newpackages.putAll(packages);
        for(PBmmPackage bmmPackage:newpackages.values()) {
            convertPackages(bmmPackage);
        }
        schema.setPackages(newpackages);
    }
}
