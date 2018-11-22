package org.openehr.bmm.v2.validation.validators;

import org.apache.commons.lang.StringUtils;
import org.openehr.bmm.persistence.validation.BmmDefinitions;
import org.openehr.bmm.persistence.validation.BmmMessageIds;
import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.bmm.v2.validation.BmmValidation;
import org.openehr.bmm.v2.validation.PSchemaRepository;
import org.openehr.utils.message.MessageLogger;

import java.util.ArrayList;
import java.util.List;

public class CreatedSchemaValidation implements BmmValidation {
    @Override
    public void validate(PBmmSchema schema, PSchemaRepository repository, MessageLogger logger) {
        List<String> packageNames = new ArrayList<>();

        //check top-level names - package names cannot contain each other and be siblings
        packageNames.addAll(schema.getPackages().keySet());
        schema.getPackages().keySet().forEach(name1 -> {
            boolean invalidSiblings = packageNames.stream().filter(name2 ->
                    (!name1.equalsIgnoreCase(name2)) && (name1.startsWith(name2) || name2.startsWith(name1))
            ).count() > 0;
            if(invalidSiblings) {
                logger.addError(BmmMessageIds.ec_BMM_PKGTL, schema.getSchemaId());
            }
        });

        //duplicate properties don't have to be checked - Jackson already does this

        //validate package & class structure
        schema.doRecursivePackages(persistedBmmPackage -> {
            //check for lower-down qualified names
            if((!schema.getPackages().containsKey(persistedBmmPackage.getName().toUpperCase())) && persistedBmmPackage.getName().indexOf(BmmDefinitions.PACKAGE_NAME_DELIMITER) >=0) {
                logger.addError(BmmMessageIds.ec_BMM_PKGQN,
                        schema.getSchemaId(),
                        persistedBmmPackage.getName());
            }
            persistedBmmPackage.getClasses().forEach(persistedBmmClass -> {
                if(StringUtils.isEmpty(persistedBmmClass)) {
                    logger.addError(BmmMessageIds.ec_BMM_class_name_empty,
                            schema.getSchemaId(),
                            persistedBmmPackage.getName());
                } else if(!schema.hasClassOrPrimitiveDefinition(persistedBmmClass)) {
                    logger.addError(BmmMessageIds.ec_BMM_class_not_in_definitions,
                            schema.getSchemaId(),
                            persistedBmmClass,
                            persistedBmmPackage.getName());
                }
            });
        });

        if(!logger.hasErrors()) {
            logger.addInfo(BmmMessageIds.SCHEMA_CREATED,schema.getSchemaId(),
                    ""+schema.getPrimitiveTypes().size(),
                    ""+schema.getClassDefinitions().size());

        }
    }

}
