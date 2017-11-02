package com.nedap.archie.flattener;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeHRID;
import com.nedap.archie.aom.OperationalTemplate;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.archetypevalidator.ValidationResult;

import java.util.List;

public interface FullArchetypeRepository extends ArchetypeRepository {

    Archetype getFlattenedArchetype(String archetypeId);

    ValidationResult getValidationResult(String archetypeId);

    void setValidationResult(ValidationResult result);

    void setFlattenedArchetype(Archetype archetype);

    void setOperationalTemplate(OperationalTemplate template);
}
