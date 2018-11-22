package org.openehr.bmm.v2.validation.converters;


import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.persistence.PersistedBmmPackage;
import org.openehr.bmm.persistence.PersistedBmmSchema;
import org.openehr.bmm.persistence.PersistedBmmSchemaState;
import org.openehr.bmm.persistence.validation.BmmMessageIds;
import org.openehr.bmm.v2.persistence.BmmIncludeSpec;
import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.bmm.v2.validation.BmmSchemaConverter;
import org.openehr.bmm.v2.validation.BmmSchemaValidationException;
import org.openehr.bmm.v2.validation.BmmSchemaValidator;
import org.openehr.bmm.v2.validation.BmmValidationResult;
import org.openehr.bmm.v2.validation.PSchemaRepository;
import org.openehr.utils.message.MessageLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IncludesProcessor {

    public List<String> getIncludeIds(PBmmSchema schema) {

        return schema.getIncludes().values().stream()
                .map( includeSpec -> includeSpec.getId()).collect(Collectors.toCollection(ArrayList::new));
    }

    public void addIncludes(PBmmSchema schema, BmmModel model, PSchemaRepository repository, MessageLogger logger) throws BmmSchemaValidationException {
        //step 1: check that all includes exist
        for(BmmIncludeSpec include: schema.getIncludes().values()) {
            if(!repository.containsPersistentSchema(include.getId())) {
                logger.addError(BmmMessageIds.ec_bmm_schema_included_schema_not_found, key);
            }
        }
        if(!logger.hasErrors()) {
            //step 2: get all BMM Models for all includes and merge into BmmModel
            for (BmmIncludeSpec include : schema.getIncludes().values()) {
                BmmValidationResult included = repository.getModel(include.getId());
                if(included == null) {
                    PBmmSchema persistentSchema = repository.getPersistentSchema(include.getId());
                    BmmSchemaConverter bmmSchemaConverter = new BmmSchemaConverter(repository);
                    BmmValidationResult bmmValidationResult = bmmSchemaConverter.validateAndConvert(persistentSchema);
                    repository.addModel(bmmValidationResult);
                }
                if(!included.passes()) {
                    logger.addError(BmmMessageIds.ec_bmm_schema_includes_valiidation_failed, schema.getSchemaId(), included.getLogger().toString());
                } else {
                    mergeIncluded(model, included);
                }
            }
        }
    }

    private void mergeIncluded(BmmModel including, BmmValidationResult includedValidation) {
        BmmModel included = includedValidation.getModel();
        //archetype parent class: only merge if nothing already in the higher-level schema
        if(included.getArchetypeParentClass() != null &&  including.getArchetypeParentClass() == null) {
            including.setArchetypeParentClass(included.getArchetypeParentClass());
        }

        //archetype data value parent class: only merge if nothing already in the higher-level schema
        if(included.getArchetypeDataValueParentClass() != null && including.getArchetypeDataValueParentClass() == null) {
            including.setArchetypeDataValueParentClass(including.getArchetypeDataValueParentClass());
        }

        //archetype closures
        LinkedHashSet<String> newClosurePackages = new LinkedHashSet<>();
        newClosurePackages.addAll(included.getArchetypeRmClosurePackages());
        newClosurePackages.addAll(including.getArchetypeRmClosurePackages());
        included.setArchetypeRmClosurePackages(new ArrayList<>(newClosurePackages));

        //TODO: handle canonical packages. If still required
//        for(Map.Entry<String, PersistedBmmPackage> packageEntry:including.getCanonicalPackages().entrySet()) {
//            if(canonicalPackages.containsKey(packageEntry.getKey())) {
//                PersistedBmmPackage persistedBmmPackage = canonicalPackages.get(packageEntry.getKey());
//                persistedBmmPackage.merge(packageEntry.getValue());
//            } else {
//                canonicalPackages.put(packageEntry.getKey(), packageEntry.getValue().deepClone());
//            }
//        }

        //If a package already exist, merge its classes, for each child package repeat...
        //Merge class definitions first. If you see a class with the same name, log it (complain) - OpenEHR has no notion of namespaces. Need to fix spec to support them.
        //TODO: complain about duplicates
        //this automatically includes primitive types
        for(String className:included.getClassDefinitions().keySet()) {
            BmmClass bmmClass = included.getClassDefinitions().get(className);
            including.getClassDefinitions().put(className, (BmmClass) bmmClass.clone());
        }

    }


/*    protected List<> populateLoadListInDependencyOrder() {
        List<String> remaining = new ArrayList<>();
        remaining.addAll(allSchemas.keySet());
        int count = 0;
        while (remaining.size() > 0 && count < numberOfTries) {
            allSchemas.forEach((aSchemaId, aSchemaDescriptor) -> {
                if (remaining.contains(aSchemaId)) {
                    PersistedBmmSchema currentSchema = aSchemaDescriptor.getPersistentSchema();
                    List<String> includes = new ArrayList<>(currentSchema.getIncludes().keySet());
                    if (includes == null || includes.size() == 0) {
                        schemaLoadList.add(aSchemaId);
                        remaining.remove(aSchemaId);
                    } else {
                        boolean allIncludesFound = true;
                        for(int i = 0; i < includes.size(); i++) {
                            String include = includes.get(i);
                            if(!schemaLoadList.contains(include.toLowerCase())) {
                                allIncludesFound = false;
                                break;
                            }
                        }
                        if(allIncludesFound) {
                            schemaLoadList.add(aSchemaId);
                            remaining.remove(aSchemaId);
                        }
                    }
                }
            });
            count++;
        }
    }*/
}
