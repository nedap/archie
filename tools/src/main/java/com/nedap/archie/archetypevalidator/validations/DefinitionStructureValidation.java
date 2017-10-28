package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.flattener.ArchetypeRepository;
import com.nedap.archie.query.AOMPathQuery;
import com.nedap.archie.rminfo.ModelInfoLookup;

import java.util.ArrayList;
import java.util.List;

public class DefinitionStructureValidation extends ValidatingVisitor {


    public DefinitionStructureValidation(ModelInfoLookup lookup) {
        super(lookup);
    }

    protected void validate(CAttribute cAttribute) {

        if (cAttribute.getDifferentialPath() != null) {
            if (archetype.getParentArchetypeId() == null) {
                //differential paths can only occur in specialized archetypes, so not in this one
                addMessage(ErrorType.VDIFV, cAttribute.path());
            } else if (repository != null) {
                if (flatParent != null) {
                    //adl workbench deviates from spec by only allowing differential paths at root, we allow them everywhere, according to spec
                    ArchetypeModelObject parentAOMObject = flatParent.itemAtPath(cAttribute.getParent().getPath());
                    if (parentAOMObject != null && parentAOMObject instanceof CComplexObject) {
                        CComplexObject parentObject = (CComplexObject) parentAOMObject;
                        ArchetypeModelObject pathInParent =parentObject.itemAtPath(cAttribute.getDifferentialPath());
                        if (pathInParent == null) {
                            addPathNotFoundInParentError(cAttribute);
                        }
                    } else {
                        addPathNotFoundInParentError(cAttribute);
                    }
                }
            }
        }
        //TODO: check if ADL workbench checks more than this
    }

    private void addPathNotFoundInParentError(CAttribute cAttribute) {
        addMessageWithPath(ErrorType.VDIFP, cAttribute.getDifferentialPath());
    }
}
