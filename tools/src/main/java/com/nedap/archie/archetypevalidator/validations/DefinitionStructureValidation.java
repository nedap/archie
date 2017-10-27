package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.flattener.ArchetypeRepository;

import java.util.ArrayList;
import java.util.List;

public class DefinitionStructureValidation extends ValidatingVisitor {

    private Archetype archetype;
    private ArchetypeRepository repository;

    @Override
    protected void beginValidation(Archetype archetype, Archetype flatParent, ArchetypeRepository repository) {
        this.archetype = archetype;
        this.repository = repository;
    }

    protected List<ValidationMessage> validate(CAttribute cAttribute) {
        List<ValidationMessage> result = new ArrayList<>();
        if (cAttribute.getDifferentialPath() != null) {
            if (archetype.getParentArchetypeId() == null) {
                //differential paths can only occur in specialized archetypes, so not in this one
                result.add(new ValidationMessage(ErrorType.VDIFV, cAttribute.path()));
            } else if (repository != null) {
                Archetype parent = repository.getArchetype(archetype.getParentArchetypeId());
                if (parent != null) {
                    //adl workbench deviates from spec by only allowing differential paths at root, we allow them everywhere, according to spec
                    ArchetypeModelObject parentAttributeObject = parent.itemAtPath(cAttribute.getDifferentialPath());
                    if (parentAttributeObject != null && parentAttributeObject instanceof CAttribute) {
                        CAttribute parentAttribute = (CAttribute) parentAttributeObject;
                        if (parentAttribute.getParent() instanceof CComplexObject) {
                            ArchetypeModelObject pathInParent = ((CComplexObject) parentAttribute.getParent()).itemAtPath(cAttribute.getDifferentialPath());
                            if (pathInParent == null) {
                                addPathNotFoundInParentError(cAttribute, result);
                            }
                        } else {
                            addPathNotFoundInParentError(cAttribute, result);
                        }
                    } else {
                        addPathNotFoundInParentError(cAttribute, result);
                    }
                }
            }
        }
        //TODO: check if ADL workbench checks more than this
        return result;
    }

    private void addPathNotFoundInParentError(CAttribute cAttribute, List<ValidationMessage> result) {
        result.add(new ValidationMessage(ErrorType.VDIFP, cAttribute.getDifferentialPath()));
    }
}
