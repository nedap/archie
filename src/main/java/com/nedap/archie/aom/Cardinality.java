package com.nedap.archie.aom;

import com.google.common.base.Joiner;
import com.nedap.archie.base.MultiplicityInterval;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.nedap.archie.serializer.adl.ArchetypeSerializeUtils.buildOccurrences;

/**
 * Created by pieter.bos on 18/10/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="CARDINALITY", propOrder = {
        "ordered",
        "unique",
        "interval"
})
public class Cardinality extends ArchetypeModelObject {

    private MultiplicityInterval interval;

    @XmlElement(name="is_ordered")
    private boolean ordered = false;
    @XmlElement(name="is_unique")
    private boolean unique = false;

    public Cardinality() {

    }

    public Cardinality(int lower, int higher) {
        ordered = false;
        unique = lower == 1 && higher == 1;
        interval = new MultiplicityInterval(lower, higher);
    }

    public MultiplicityInterval getInterval() {
        return interval;
    }

    public void setInterval(MultiplicityInterval interval) {
        this.interval = interval;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public static Cardinality unbounded() {
        Cardinality result = new Cardinality();
        result.setInterval(MultiplicityInterval.unbounded());
        return result;
    }

    public boolean equals(Object other) {
        if(other instanceof Cardinality) {
            Cardinality otherCardinality = (Cardinality) other;
            return ordered == otherCardinality.ordered &&
                    unique == otherCardinality.unique &&
                    Objects.equals(interval, otherCardinality.interval);
        }
        return false;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("cardinality matches {");
        builder.append(interval.toString());
        builder.append("}");
        List<String> tags = new ArrayList<>();
        if (!isOrdered()) {
            tags.add("unordered");
        }
        if (isUnique()) {
            tags.add("unique");
        }
        if (!tags.isEmpty()) {
            builder.append("; ").append(Joiner.on("; ").join(tags));
        }
        return builder.toString();
    }
}
