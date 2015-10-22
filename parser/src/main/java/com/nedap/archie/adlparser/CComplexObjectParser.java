package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.AdlParser.*;
import com.nedap.archie.aom.*;
import com.nedap.archie.base.MultiplicityInterval;
import org.antlr.v4.runtime.tree.TerminalNode;

import static com.nedap.archie.adlparser.PrimitivesConstraintParser.*;
import static com.nedap.archie.adlparser.NumberConstraintParser.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for the definition part of an archetype
 *
 * Created by pieter.bos on 15/10/15.
 */
public class CComplexObjectParser {

    private final ADLParserErrors errors;

    public CComplexObjectParser(ADLParserErrors errors) {
        this.errors = errors;

    }

    public RuleStatement parseRules(Rules_sectionContext rulesSectionContext) {
        //TODO: proper solution for this. Works for now.
        RuleStatement statement =new RuleStatement();
        statement.setRuleContent(rulesSectionContext.getText());
        return statement;
    }

    public CComplexObject parseComplexObject(C_complex_objectContext context) {
        CComplexObject object = new CComplexObject();
        if(context.type_id() != null) {
            object.setRmTypeName(context.type_id().getText());
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
            attribute.setRmAttributeName(attributeContext.attribute_id().getText());
            if (attributeContext.c_existence() != null) {
                attribute.setExistence(parseMultiplicityInterval(attributeContext.c_existence()));
            }
            if(attributeContext.adl_dir() != null) {
                attribute.setDifferentialPath(attributeContext.adl_dir().getText() + attributeContext.attribute_id().getText());
            }

            if (attributeContext.c_cardinality() != null) {
                this.parseCardinalityInterval(attributeContext.c_cardinality());//TODO!
            }
            if (attributeContext.c_objects() != null) {
                attribute.setChildren(parseCObjects(attributeContext.c_objects()));
            }
            parent.addAttribute(attribute);
        } else if (attributeDefContext.c_attribute_tuple() != null) {
            parent.addAttributeTuple(parseAttributeTuple(parent, attributeDefContext.c_attribute_tuple()));
        }

    }

    private CAttributeTuple parseAttributeTuple(CComplexObject parent, C_attribute_tupleContext attributeTupleContext) {
        List<Attribute_idContext> attributeIdList = attributeTupleContext.attribute_id();
        CAttributeTuple tuple = new CAttributeTuple();

        for(Attribute_idContext idContext:attributeIdList) {
            CAttribute attribute = new CAttribute();
            String id = idContext.getText();//TODO? parse odin string value?
            attribute.setRmAttributeName(id);
            tuple.addMember(attribute);
            parent.addAttribute(attribute);
        }
        List<C_object_tupleContext> tupleContexts = attributeTupleContext.c_object_tuple();
        for(C_object_tupleContext tupleContext:tupleContexts) {
            CPrimitiveTuple primitiveTuple = new CPrimitiveTuple();

            List<C_primitive_objectContext> primitiveObjectContexts = tupleContext.c_object_tuple_items().c_primitive_object();
            int i = 0;
            for(C_primitive_objectContext primitiveObjectContext:primitiveObjectContexts) {
                CPrimitiveObject primitiveObject = parsePrimitiveObject(primitiveObjectContext);
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
            result.add(parsePrimitiveObject(objectsContext.c_primitive_object()));
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
        proxy.setRmTypeName(proxyContext.type_id().getText());
        proxy.setNodeId(proxyContext.ID_CODE().getText());
        return proxy;
    }

    private CArchetypeRoot parseArchetypeRoot(C_archetype_rootContext archetypeRootContext) {
        CArchetypeRoot root = new CArchetypeRoot();

        root.setRmTypeName(archetypeRootContext.type_id().getText());
        root.setNodeId(archetypeRootContext.ID_CODE().getText());
        root.setArchetypeRef(archetypeRootContext.archetype_ref().getText());

        root.setOccurences(this.parseMultiplicityInterval(archetypeRootContext.c_occurrences()));

        return root;
    }

    private ArchetypeSlot parseArchetypeSlot(Archetype_slotContext slotContext) {
        ArchetypeSlot slot = new ArchetypeSlot();
        C_archetype_slot_headContext headContext = slotContext.c_archetype_slot_head();
        slot.setNodeId(headContext.c_archetype_slot_id().ID_CODE().getText());
        slot.setRmTypeName(headContext.c_archetype_slot_id().type_id().getText());
        if(headContext.c_occurrences() != null) {
            slot.setOccurences(parseMultiplicityInterval(headContext.c_occurrences()));
        }
        if(slotContext.c_excludes() != null) {
            for(AssertionContext assertionContext:slotContext.c_excludes().assertion()) {
                slot.getExcludes().add(new Assertion(assertionContext.getText()));
            }
        }
        if(slotContext.c_includes() != null) {
            for(AssertionContext assertionContext:slotContext.c_includes().assertion()) {
                slot.getIncludes().add(new Assertion(assertionContext.getText()));
            }
        }
        return slot;
    }

    private CPrimitiveObject parsePrimitiveObject(C_primitive_objectContext objectContext) {
        /*c_integer
                | c_real
                | c_date
                | c_time
                | c_date_time
                | c_duration
                | c_string
                | c_terminology_code
                | c_boolean*/
        if(objectContext.c_integer() != null) {
            return parseCInteger(objectContext.c_integer());
        } else if (objectContext.c_real() != null) {
            return parseCReal(objectContext.c_real());
        } else if (objectContext.c_date() != null) {
            return parseCDate(objectContext.c_date());
        } else if (objectContext.c_time() != null) {
            return parseCTime(objectContext.c_time());
        } else if (objectContext.c_date_time() != null) {
            return parseCDateTime(objectContext.c_date_time());
        } else if (objectContext.c_duration() != null) {
            return parseCDuration(objectContext.c_duration());
        } else if (objectContext.c_string() != null) {
            return parseCString(objectContext.c_string());
        } else if (objectContext.c_terminology_code() != null) {
            return parseCTerminologyCode(objectContext.c_terminology_code());
        } else if (objectContext.c_boolean() != null) {
            return parseCBoolean(objectContext.c_boolean());
        }
        return null;
    }

    private Cardinality parseCardinalityInterval(C_cardinalityContext cardinalityContext) {
        Cardinality cardinality = new Cardinality();
        MultiplicityInterval interval = new MultiplicityInterval();
        cardinality.setInterval(interval);

        //TODO: cardinality().cardinatelyMod();
        List<TerminalNode> integers = cardinalityContext.cardinality().multiplicity().INTEGER();
        if(integers.size() == 1) {
            interval.setLower(Integer.parseInt(integers.get(0).getText()));
            interval.setUpper(interval.getLower());
        } else if (integers.size() == 2) {
            interval.setLower(Integer.parseInt(integers.get(0).getText()));
            interval.setUpper(Integer.parseInt(integers.get(1).getText()));
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
        MultiplicityInterval interval = new MultiplicityInterval();
        List<TerminalNode> integers = occurrencesContext.multiplicity().INTEGER();

        if(occurrencesContext.multiplicity().SYM_INTERVAL_SEP() != null) {
            if(occurrencesContext.multiplicity().getText().contains("*")) {
                interval.setLower(Integer.parseInt(integers.get(0).getText()));
                interval.setUpperUnbounded(true);
            } else {
                interval.setLower(Integer.parseInt(integers.get(0).getText()));
                interval.setUpper(Integer.parseInt(integers.get(1).getText()));
            }
        } else {
            //one integer or *
            if(occurrencesContext.multiplicity().getText().contains("*")) {
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
