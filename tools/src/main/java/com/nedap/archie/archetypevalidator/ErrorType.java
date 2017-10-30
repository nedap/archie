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
    VTLC("language consistency. Languages consistent: all term codes and constraint codes exist in all languages"),
    OTHER("an error occurred that has no standard codes"),
    VASID("archetype specialisation parent identifier validity. The archetype identifier stated in the specialise clause must be the identifier of the immediate specialisation parent archetype."),
    VARDT("archetype definition typename validity. The typename mentioned in the outer block of the archetype definition section must match the type mentioned in the first segment of the archetype id"),
    STCNT("Syntax error: terminology not specified"),
    VOLT("Original language not defined in terminology"),
    VOTM("terminology translations validity. Translations must exist for term_definitions and constraint_definitions sections for all languages defined in the description / translations sections."),
    VACSD("The specialisation depth of the archetypes must be one greater than the specialisation depth of the parent archetype"),
    VARCN("The node_id of the root object of the archetype must be of the form id1{.1}*, where the number of .1 components equals the specalisation depth, and must be defined in the terminology"),
    VARRV("The rm_release top-level meta-data item must exist and consist of a valid 3-part version identifier."),
    VARAV("The adl_version top-level meta-data item must exist and consist of a valid 3-part version identifier."),
    VDIFV("archetype attribute differential path validity: an archetype may only have a differential path if it is specialised."),
    VDIFP("specialised archetype attribute differential path validity: if an attribute constraint has a differential path, the path must exist in the flat parent, and also be valid with respect to the reference model, i.e. in the sense that it corresponds to a legal potential construction of objects."),
    VATCV("the give id code is not valid"),
    VTSD("specialisation level of codes. Term or constraint code defined in archetype terminology must be of the same specialisation level as the archetype (differential archetypes), or the same or a less specialised level (flat archetypes)"),
    VTTBK("terminology term binding key valid. Every term binding must be to either a defined archetype term ('at-code') or to a path that is valid in the flat archetype."),
    VATDF("value code validity. Each value code (at-code) used in a term constraint in the archetype definition must be defined in the term_definitions part of the terminology of the flattened form of the current archetype."),
    VACDF("constraint code validity. Each value set code (ac-code) used in a term constraint in the archetype definition must be defined in the term_definitions part of the terminology of the current archetype."),
    VARXRA("Archetype root must reference an existing archetype"),
    VARXTV("external reference type validity: the reference model type of the reference object archetype identifier must be identical, or conform to the type of the slot, if there is one, in the parent archetype, or else to the reference model type of the attribute in the flat parent under which the reference object appears in the child archetype."),
    VCACA("archetype attribute reference model cardinality conformance: the cardinality of an attribute must conform, i.e. be the same or narrower, to the cardinality of the corresponding attribute in the underlying information model."),
    VSONPT("specialised archetype prohibited object node AOM type validity: the occurrences of a redefined object node in a specialised archetype, may only be prohibited (i.e. {0}) if the matching node in the parent is of the same AOM type."),
    VSONPI("specialised archetype prohibited object node AOM node id validity: a redefined object node in a specialised archetype with occurrences matching {0} must have exactly the same node_id as the node in the flat parent being redefined"),
    VSONIN("specialised archetype new object node identifier validity: if an object node in a specialised archetype is a new node with respect to the flat parent, and it carries a node identifier, the identifier must be a 'new' node identifier, specalised at the level of the child archetype."),
    VSONPO("specialised archetype object node prohibited occurrences validity: the occurrences of a new (i.e. having no corresponding node in the parent flat) object node in a specialised archetype, if stated, may not be 'prohibited', i.e. {0}, since prohibition only makes sense for an existing node."),
    VSSM("specialised archetype sibling order validity: the sibling order node id code used in a sibling marker in a specialised archetype must refer to a node found within the same container in the flat parent archetype."),
    VATID("node id must be defined in flat terminology"),
    VATDA("value set assumed value code validity. Each value code (at-code) used as an assumed_value for a value set in a term constraint in the archetype definition must exist in the value set definition in the terminology for the identified value set."),
    VATCD("achetype code specialisation level validity. Each archetype term ('at' code) and constraint code ('ac' code) used in the archetype definition section must have a specialisation level no greater than the specialisation level of the archetype."),
    VRANP("annotation path valid. Each path mentioned in an annotation within the annotations section must either be a valid archetype path, or a 'reference model' path, i.e. a path that is valid for the root class of the archetype."),
    VDEOL("original language specified. An original_language section containing the meta-data of the original authoring language must exist."),
    VRDLA("resource detail key does not match resource detail item language"),
    VTRLA("translations key does not match translations item language");




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
