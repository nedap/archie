package com.nedap.archie.diff;

import com.nedap.archie.adlparser.modelconstraints.BMMConstraintImposer;
import com.nedap.archie.adlparser.modelconstraints.ModelConstraintImposer;
import com.nedap.archie.adlparser.modelconstraints.ReflectionConstraintImposer;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.rminfo.MetaModels;

public class Differentiator {

    private final MetaModels metaModels;

    public Differentiator(MetaModels metaModels) {
        this.metaModels = metaModels;
    }


    public Archetype differentiate(Archetype flatChild, Archetype flatParent) {
        metaModels.selectModel(flatChild);
        ModelConstraintImposer constraintImposer;
        if(metaModels.getSelectedBmmModel() != null) {
            constraintImposer = new BMMConstraintImposer(metaModels.getSelectedBmmModel());
        } else {
            constraintImposer = new ReflectionConstraintImposer(metaModels.getSelectedModelInfoLookup());
        }
        Archetype result = flatChild.clone();


        new LCSOrderingDiff(metaModels).addSiblingOrder(result, flatChild, flatParent);
        new ConstraintDifferentiator(constraintImposer, flatParent).removeUnspecializedConstraints(result, flatChild, flatParent);

        new DifferentialPathGenerator().replace(result);
        new TerminologyDifferentiator().differentiate(result);


        result.setDifferential(true);
        return result;
    }


}
