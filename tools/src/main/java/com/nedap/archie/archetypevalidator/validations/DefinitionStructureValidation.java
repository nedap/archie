package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.utils.AOMUtils;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.flattener.ArchetypeRepository;
import com.nedap.archie.query.AOMPathQuery;
import com.nedap.archie.rminfo.ModelInfoLookup;

import java.util.ArrayList;
import java.util.List;

public class DefinitionStructureValidation extends ValidatingVisitor {


    public DefinitionStructureValidation() {
        super();
    }

    protected void validate(CAttribute cAttribute) {

        if (cAttribute.getDifferentialPath() != null) {
            if (archetype.getParentArchetypeId() == null) {
                //differential paths can only occur in specialized archetypes, so not in this one
                addMessage(ErrorType.VDIFV, cAttribute.path());
            } else if (repository != null) {
                if (flatParent != null) {
                    //adl workbench deviates from spec by only allowing differential paths at root, we allow them everywhere, according to spec
                    ArchetypeModelObject differentialPathFromParent = AOMUtils.getDifferentialPathFromParent(flatParent, cAttribute);
                    if(differentialPathFromParent == null) {
                        addPathNotFoundInParentError(cAttribute);
                    } else if (!(differentialPathFromParent instanceof CAttribute)) {
                        addMessageWithPath(ErrorType.VDIFP, cAttribute.getDifferentialPath(), "differential path must point to an attribute in the flat parent, but it pointed instead to a " + differentialPathFromParent.getClass());
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
