package org.openehr.bmm.v2.validation.converters;

import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmGenericClass;
import org.openehr.bmm.core.BmmGenericParameter;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.core.BmmPackage;
import org.openehr.bmm.core.BmmProperty;
import org.openehr.bmm.core.BmmType;
import org.openehr.bmm.persistence.PersistedBmmClass;
import org.openehr.bmm.persistence.PersistedBmmPackage;
import org.openehr.bmm.v2.persistence.PBmmClass;
import org.openehr.bmm.v2.persistence.PBmmGenericParameter;
import org.openehr.bmm.v2.persistence.PBmmPackage;
import org.openehr.bmm.v2.persistence.PBmmProperty;
import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.bmm.v2.validation.BmmValidationResult;

import java.util.ArrayList;

public class BmmModelCreator {

    private BmmClassCreator classCreator = new BmmClassCreator();

    public BmmModel create(BmmValidationResult validationResult) {
        PBmmSchema schema = validationResult.getSchemaWithMergedIncludes();
        BmmModel model = new BmmModel();
        model.setRmPublisher(schema.getRmPublisher());
        model.setRmRelease(schema.getRmRelease());
        //TODO: model.setBmmVersion(schema.getBmmVersion());
        model.setModelName(schema.getModelName());
        model.setSchemaName(schema.getSchemaName());
        model.setSchemaRevision(schema.getSchemaRevision());
        model.setSchemaAuthor(schema.getSchemaAuthor());
        model.setSchemaDescription(schema.getSchemaDescription());
        model.setSchemaLifecycleState(schema.getSchemaLifecycleState());
        //model.setDocumentation(schema.getDo);TODO: possible?
        model.setSchemaContributors(schema.getSchemaContributors() == null ? new ArrayList() : new ArrayList<>(schema.getSchemaContributors()));



        //Add packages first
        for(PBmmPackage pBmmPackage:validationResult.getCanonicalPackages().values()) {
            BmmPackage bmmPackage = createBmmPackageDefinition(pBmmPackage, null, null);

            model.addPackage(bmmPackage);

            pBmmPackage.doRecursiveClasses((p, s) -> {
                PBmmClass persistedBmmClass = schema.findClassOrPrimitiveDefinition(s);
                if (persistedBmmClass != null) {
                    BmmClass bmmClass = classCreator.createBmmClass(persistedBmmClass);
//TODO: create temporary map from package name to BmmPackageDefinition, or something like it.
                    if (bmmClass != null && bmmPackage != null) {
                        if (schema.getPrimitiveTypes().get(bmmClass.getName()) != null) {
                            bmmClass.setPrimitiveType(true);
                        }
                        if (persistedBmmClass.isOverride()) {
                            bmmClass.setOverride(true);
                        }
                        model.addClassDefinition(bmmClass, bmmPackage);
                    }
                }
            });
        }

        model.setArchetypeParentClass(schema.getArchetypeParentClass());
        model.setArchetypeDataValueParentClass(schema.getArchetypeDataValueParentClass());
        model.setArchetypeVisualizeDescendantsOf(schema.getArchetypeVisualizeDescendantsOf());
        model.setArchetypeRmClosurePackages(schema.getArchetypeRmClosurePackages() == null ? new ArrayList<>() : new ArrayList<>(schema.getArchetypeRmClosurePackages()));


        // The basics have been created. Now populate the classes with properties
        ProcessClassesInOrder processClassesInOrder = new ProcessClassesInOrder();
        processClassesInOrder.doAllClassesInOrder(schema, bmmClass -> {
            classCreator.populateBmmClass(bmmClass, model);
        }, new ArrayList<>(schema.getPrimitiveTypes().values()));
        processClassesInOrder.doAllClassesInOrder(schema, bmmClass -> {
            classCreator.populateBmmClass(bmmClass, model);
        }, new ArrayList<>(schema.getClassDefinitions().values()));

        return model;
    }



    private BmmPackage createBmmPackageDefinition(PBmmPackage p, PBmmPackage parent, BmmPackage parentPackageDefinition) {
        BmmPackage bmmPackageDefinition = new BmmPackage(p.getName());
        bmmPackageDefinition.setDocumentation(p.getDocumentation());
        if(parent != null) {
            bmmPackageDefinition.appendToPath(parent.getName());
            bmmPackageDefinition.setParent(parentPackageDefinition);
        }
        bmmPackageDefinition.appendToPath(p.getName());
        p.getPackages().values().forEach(childPackage -> {
            BmmPackage bmmChildPackageDefinition = createBmmPackageDefinition(childPackage, p, bmmPackageDefinition);
            bmmPackageDefinition.addPackage(bmmChildPackageDefinition);
        });
        return bmmPackageDefinition;
    }
}
