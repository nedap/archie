package com.nedap.archie.flattener;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeHRID;
import com.nedap.archie.aom.OperationalTemplate;
import com.nedap.archie.archetypevalidator.ArchetypeValidator;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.archetypevalidator.ValidationResult;
import com.nedap.archie.rminfo.MetaModels;
import com.nedap.archie.rminfo.ReferenceModels;
import org.openehr.bmm.rmaccess.ReferenceModelAccess;

import java.util.List;

public interface FullArchetypeRepository extends ArchetypeRepository {

    Archetype getFlattenedArchetype(String archetypeId);

    ValidationResult getValidationResult(String archetypeId);

    void setValidationResult(ValidationResult result);

    void setFlattenedArchetype(Archetype archetype);

    void setOperationalTemplate(OperationalTemplate template);

    List<ValidationResult> getAllValidationResults();

    default void compile(ReferenceModels models) {
        ArchetypeValidator validator = new ArchetypeValidator(models);
        for(Archetype archetype:getAllArchetypes()) {
            if(getValidationResult(archetype.getArchetypeId().toString()) == null) {
                validator.validate(archetype, this);
            }
        }
    }

    default void compile(MetaModels models) {
        ArchetypeValidator validator = new ArchetypeValidator(models);
        for(Archetype archetype:getAllArchetypes()) {
            if(getValidationResult(archetype.getArchetypeId().toString()) == null) {
                validator.validate(archetype, this);
            }
        }
    }

    default void compile(ReferenceModels models, ReferenceModelAccess bmmModels) {
        ArchetypeValidator validator = new ArchetypeValidator(models, bmmModels);
        for(Archetype archetype:getAllArchetypes()) {
            if(getValidationResult(archetype.getArchetypeId().toString()) == null) {
                validator.validate(archetype, this);
            }
        }
    }
}
