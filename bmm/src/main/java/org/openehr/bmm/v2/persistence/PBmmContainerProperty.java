package org.openehr.bmm.v2.persistence;

import com.nedap.archie.base.Interval;

public final class PBmmContainerProperty extends PBmmProperty<PBmmContainerType> {

    private Interval<Integer> cardinality;
    
    public Interval<Integer> getCardinality() {
        return cardinality;
    }

    public void setCardinality(Interval<Integer> cardinality) {
        this.cardinality = cardinality;
    }

}
