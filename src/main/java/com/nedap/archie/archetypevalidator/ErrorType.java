package com.nedap.archie.archetypevalidator;

/**
 * Created by pieter.bos on 31/03/2017.
 */
public enum ErrorType {
    VCOSU("object node identifier validity: every object node must be unique within the archetype"),
    VCOID("object node identifier validity: every object node must have a node identifier"),
    VCORM("object constraint type name existence: a type name introducing an object constraint block must be defined in the underlying information model"),
    VCORMT("object constraint type validity: a type name introducing an object constraint block must be the same as or conform to the type stated in the underlying information model of its owning attribute"),
    VCARM("attribute name reference model validity: an attribute name introducing an attribute constraint block must be defined in the underlying information model as an attribute (stored or computed) of the type which introduces the enclosing object block"),
    VCAEX("archetype attribute reference model existence conformance: the existence of an attribute, if set, must conform, i.e. be the same or narrower, to the existence of the corresponding attribute in the underlying information model"),
    VCAM("archetype attribute reference model multiplicity conformance: the multiplicity, i.e. whether an attribute is multiply- or single-valued, of an attribute must conform to that of the corresponding attribute in the underlying information model"),
    VCATU("attribute uniqueness: sibling attributes occurring within an object node must be uniquely named with respect to each other, in the same way as for class definitions in an object reference model"),
    //terminology validity rules
    VTVSID("value-set id defined. The identifying code of a value set must be defined in the term definitions of the terminology of the current archetype"),
    VTVSMD("value-set members defined. The member codes of a value set must be defined in the term definitions of the terminology of the flattened form of the current archetype"),
    VTVSUQ("value-set members unique. The member codes of a value set must be unique within the value set"),
    VTLC("language consistency. Languages consistent: all term codes and constraint codes exist in all languages"),
    OTHER("an error occurred that has no standard codes");




    private final String description;

    ErrorType(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return name() + ": " + description;
    }
}
