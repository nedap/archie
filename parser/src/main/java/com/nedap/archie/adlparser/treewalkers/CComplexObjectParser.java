package com.nedap.archie.adlparser.treewalkers;

import com.nedap.archie.adlparser.ADLParserErrors;
import com.nedap.archie.adlparser.antlr.AdlParser.*;
import com.nedap.archie.aom.*;
import com.nedap.archie.base.MultiplicityInterval;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for the definition part of an archetype
 *
 * Created by pieter.bos on 15/10/15.
 */
public class CComplexObjectParser extends BaseTreeWalker {

    private final PrimitivesConstraintParser primitivesConstraintParser;

    public CComplexObjectParser(ADLParserErrors errors) {
        super(errors);
        primitivesConstraintParser = new PrimitivesConstraintParser(errors);
    }

    public RulesSection parseRules(Rules_sectionContext context) {
        RulesSection result = new RulesSection();

        result.setContent(context.getText());
        AssertionsParser assertionsParser = new AssertionsParser(getErrors());
        for(AssertionContext assertion:context.assertion()) {
            result.addRule(assertionsParser.parse(assertion));
        }

        return result;
    }

    public CComplexObject parseComplexObject(C_complex_objectContext context) {
        CComplexObject object = new CComplexObject();
        if(context.rm_type_id() != null) {
            object.setRmTypeName(context.rm_type_id().getText());
        }
        if(context.ID_CODE() != null) {
            object.setNodeId(context.ID_CODE().getText());
        } else if (context.ROOT_ID_CODE() != null) {
            object.setNodeId(context.ROOT_ID_CODE().getText());
        }
        //TODO: object.setDeprecated(context.) ?;
        if (context.c_occurrences() != null) {
            object.setOccurences(parseMultiplicityInterval(context.c_occurrences()));
        }
        for (C_attribute_defContext attribute : context.c_attribute_def()) {
            parseCAttribute(object, attribute);
        }
        return object;
    }

    private void parseCAttribute(CComplexObject parent, C_attribute_defContext attributeDefContext) {

        if (attributeDefContext.c_attribute() != null) {
            CAttribute attribute = new CAttribute();
            C_attributeContext attributeContext = attributeDefContext.c_attribute();
            attribute.setRmAttributeName(attributeContext.rm_attribute_id().getText());
            if (attributeContext.c_existence() != null) {
                attribute.setExistence(parseMultiplicityInterval(attributeContext.c_existence()));
            }
            if(attributeContext.adl_dir() != null) {
                attribute.setDifferentialPath(attributeContext.adl_dir().getText() + attributeContext.rm_attribute_id().getText());
            }

            if (attributeContext.c_cardinality() != null) {
                attribute.setCardinality(this.parseCardinalityInterval(attributeContext.c_cardinality()));
            }
            if (attributeContext.c_objects() != null) {
                attribute.setChildren(parseCObjects(attributeContext.c_objects()));
            } else if (attributeContext.CONTAINED_REGEXP() != null) {
                attribute.addChild(primitivesConstraintParser.parseRegex(attributeContext.CONTAINED_REGEXP()));
            }
            parent.addAttribute(attribute);
        } else if (attributeDefContext.c_attribute_tuple() != null) {
            parent.addAttributeTuple(parseAttributeTuple(parent, attributeDefContext.c_attribute_tuple()));
        }

    }

    private CAttributeTuple parseAttributeTuple(CComplexObject parent, C_attribute_tupleContext attributeTupleContext) {
        List<Rm_attribute_idContext> attributeIdList = attributeTupleContext.rm_attribute_id();
        CAttributeTuple tuple = new CAttributeTuple();

        for(Rm_attribute_idContext idContext:attributeIdList) {
            CAttribute attribute = new CAttribute();
            String id = idContext.getText();//TODO? parse odin string value?
            attribute.setRmAttributeName(id);
            tuple.addMember(attribute);
            parent.addAttribute(attribute);
        }
        List<C_primitive_tupleContext> tupleContexts = attributeTupleContext.c_primitive_tuple();
        for(C_primitive_tupleContext tupleContext:tupleContexts) {
            CPrimitiveTuple primitiveTuple = new CPrimitiveTuple();

            List<C_primitive_tuple_contentContext> primitiveTupleContentContexts = tupleContext.c_primitive_tuple_content();
            int i = 0;
            for(C_primitive_tuple_contentContext tupleObjectContext:primitiveTupleContentContexts) {
                CPrimitiveObject primitiveObject = null;
                if(tupleObjectContext.c_primitive_object() != null) {
                     primitiveObject = primitivesConstraintParser.parsePrimitiveObject(tupleObjectContext.c_primitive_object());
                } else if (tupleObjectContext.CONTAINED_REGEXP() != null) {
                    primitiveObject = primitivesConstraintParser.parseRegex(tupleObjectContext.CONTAINED_REGEXP());
                }
                tuple.getMembers().get(i).addChild(primitiveObject);
                primitiveTuple.addMember(primitiveObject);
                i++;
            }
            tuple.addTuple(primitiveTuple);
        }

        return tuple;
    }

    private List<CObject> parseCObjects(C_objectsContext objectsContext) {
        ArrayList<CObject> result = new ArrayList<>();

        if (objectsContext.c_primitive_object() != null) {
            result.add(primitivesConstraintParser.parsePrimitiveObject(objectsContext.c_primitive_object()));
        } else {
            List<C_non_primitive_object_orderedContext> nonPrimitiveObjectOrderedContext = objectsContext.c_non_primitive_object_ordered();
            if (nonPrimitiveObjectOrderedContext != null) {

                for (C_non_primitive_object_orderedContext object : nonPrimitiveObjectOrderedContext) {

                    CObject cobject = parseNonPrimitiveObject(object.c_non_primitive_object());
                    Sibling_orderContext siblingOrderContext = object.sibling_order();
                    if(siblingOrderContext != null) {
                        SiblingOrder siblingOrder = new SiblingOrder();
                        if(siblingOrderContext.SYM_AFTER() != null) {
                            siblingOrder.setBefore(false);
                        } else if (siblingOrderContext.SYM_BEFORE() != null) {
                            siblingOrder.setBefore(true);
                        }
                        siblingOrder.setSiblingNodeId(siblingOrderContext.ID_CODE().getText());
                        cobject.setSiblingOrder(siblingOrder);
                    }

                    result.add(cobject);
                }
            }
        }
        return result;
    }

    private CObject parseNonPrimitiveObject(C_non_primitive_objectContext objectContext) {
        /*
          c_complex_object
        | c_archetype_root
        | c_complex_object_proxy
        | archetype_slot
        */
        if (objectContext.c_complex_object() != null) {
            return parseComplexObject(objectContext.c_complex_object());
        } else if (objectContext.c_archetype_root() != null) {
            return parseArchetypeRoot(objectContext.c_archetype_root());

        } else if (objectContext.c_complex_object_proxy() != null) {
            return parseCComplexObjectProxy(objectContext.c_complex_object_proxy());
        } else if (objectContext.archetype_slot() != null) {
            return parseArchetypeSlot(objectContext.archetype_slot());

        }
        return null;
    }

    private CComplexObjectProxy parseCComplexObjectProxy(C_complex_object_proxyContext proxyContext) {

        CComplexObjectProxy proxy = new CComplexObjectProxy();
        proxy.setOccurences(this.parseMultiplicityInterval(proxyContext.c_occurrences()));
        proxy.setTargetPath(proxyContext.adl_path().getText());
        proxy.setRmTypeName(proxyContext.rm_type_id().getText());
        proxy.setNodeId(proxyContext.ID_CODE().getText());
        return proxy;
    }

    private CArchetypeRoot parseArchetypeRoot(C_archetype_rootContext archetypeRootContext) {
        CArchetypeRoot root = new CArchetypeRoot();

        root.setRmTypeName(archetypeRootContext.rm_type_id().getText());
        root.setNodeId(archetypeRootContext.ID_CODE().getText());
        root.setArchetypeRef(archetypeRootContext.archetype_ref().getText());

        root.setOccurences(this.parseMultiplicityInterval(archetypeRootContext.c_occurrences()));
//((Archetype_slotContext) slotContext).start.getInputStream().getText(slotContext.getSourceInterval())
        return root;
    }

    private ArchetypeSlot parseArchetypeSlot(Archetype_slotContext slotContext) {
        ArchetypeSlot slot = new ArchetypeSlot();
        slot.setNodeId(slotContext.ID_CODE().getText());
        slot.setRmTypeName(slotContext.rm_type_id().getText());
        if (slotContext.c_occurrences() != null) {
            slot.setOccurences(parseMultiplicityInterval(slotContext.c_occurrences()));
        }
        if(slotContext.SYM_CLOSED() != null) {
            slot.setClosed(true);
        }
        AssertionsParser assertionParser = new AssertionsParser(getErrors());
        if (slotContext.c_excludes() != null) {
            for (AssertionContext assertionContext : slotContext.c_excludes().assertion()) {
                slot.getExcludes().add(assertionParser.parse(assertionContext));
            }
        }
        if (slotContext.c_includes() != null) {
            for (AssertionContext assertionContext : slotContext.c_includes().assertion()) {
                slot.getIncludes().add(assertionParser.parse(assertionContext));
            }
        }
        return slot;
    }


    private Cardinality parseCardinalityInterval(C_cardinalityContext context) {
        Cardinality cardinality = new Cardinality();
        MultiplicityInterval interval = parseMultiplicity(context.cardinality().multiplicity());
        cardinality.setInterval(interval);

        List<Multiplicity_modContext> modContexts = context.cardinality().multiplicity_mod();
        for(Multiplicity_modContext modContext:modContexts) {
            if(modContext.ordering_mod() != null) {
                cardinality.setOrdered(modContext.ordering_mod().SYM_ORDERED() != null);
            }
            if(modContext.unique_mod() != null) {
                cardinality.setUnique(true);
            }

        }
        return cardinality;
    }

    private MultiplicityInterval parseMultiplicityInterval(C_existenceContext existenceContext) {
        MultiplicityInterval interval = new MultiplicityInterval();
        List<TerminalNode> integers = existenceContext.existence().INTEGER();
        if(integers.size() == 1) {
            interval.setLower(Integer.parseInt(integers.get(0).getText()));
            interval.setUpper(interval.getLower());
        } else if (integers.size() == 2) {
            interval.setLower(Integer.parseInt(integers.get(0).getText()));
            interval.setUpper(Integer.parseInt(integers.get(1).getText()));
        }
        return interval;
    }

    private MultiplicityInterval parseMultiplicityInterval(C_occurrencesContext occurrencesContext) {
        if(occurrencesContext == null) {
            return null;
        }

        return parseMultiplicity(occurrencesContext.multiplicity());
    }

    private MultiplicityInterval parseMultiplicity(MultiplicityContext multiplicity) {
        if(multiplicity == null) {
            return null;
        }
        MultiplicityInterval interval = new MultiplicityInterval();
        List<TerminalNode> integers = multiplicity.INTEGER();
        if(multiplicity.SYM_INTERVAL_SEP() != null) {
            if(multiplicity.getText().contains("*")) {
                interval.setLower(Integer.parseInt(integers.get(0).getText()));
                interval.setUpperUnbounded(true);
            } else {
                interval.setLower(Integer.parseInt(integers.get(0).getText()));
                interval.setUpper(Integer.parseInt(integers.get(1).getText()));
            }
        } else {
            //one integer or *
            if(multiplicity.getText().contains("*")) {
                interval.setLowerUnbounded(false);
                interval.setLower(0);
                interval.setUpperUnbounded(true);
            } else {
                interval.setLower(Integer.parseInt(integers.get(0).getText()));
                interval.setUpper(interval.getLower());
            }
        }
        return interval;
    }


}
