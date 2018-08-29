package com.nedap.archie.diff;

import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.SiblingOrder;
import com.nedap.archie.aom.utils.AOMUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiffUtil {

    /**
     * Find the matching CObject given a list of cobject that are either the same or a more specialized specialization depth
     * If the same is found, returns that. Otherwise returns the parent
     * @param specializedNodeId
     * @param parentCObjects
     * @return
     */
    public static CObject findMatchingParentCObject(String specializedNodeId, List<CObject> parentCObjects) {
        for (CObject parentCObject : parentCObjects) {
            if (specializedNodeId.equalsIgnoreCase(parentCObject.getNodeId())) {
                return parentCObject;
            }
        }
        for (CObject parentCObject : parentCObjects) {
            if (AOMUtils.isOverriddenIdCode(specializedNodeId, parentCObject.getNodeId())) {
                return parentCObject;
            }
        }
        return null;
    }



    /**
     * Given an attribute, find the same attribute in the given CComplexObject and return it
     *
     * @return the found attribute, or null if not found
     */
    public static CAttribute getMatchingAttribute(CObject result, CAttribute childAttribute) {
        if(result == null) {
            return null;
        }
        return result.getAttribute(childAttribute.getDifferentialPath() != null ? childAttribute.getDifferentialPath() : childAttribute.getRmAttributeName());
    }

    public static void addSiblingOrder(Map<SiblingOrder, List<CObject>> siblingOrders, SiblingOrder siblingOrder, CObject cObjectInResult) {
        List<CObject> cObjects = siblingOrders.get(siblingOrder);
        if(cObjects == null) {
            //first, find if we can add it below an existing anchor
            SiblingOrder existingSiblingOrder = findSiblingOrder(siblingOrders, siblingOrder.getSiblingNodeId());
            if(existingSiblingOrder != null) {
                siblingOrders.get(existingSiblingOrder).add(cObjectInResult);
                return;
            }
            //not already found, so create a new sibling order
            cObjects = new ArrayList<>();
            siblingOrders.put(siblingOrder, cObjects);
        }
        cObjects.add(cObjectInResult);
    }

    public static void addOrderToAttribute(Map<SiblingOrder, List<CObject>> siblingOrders) {
        for(SiblingOrder order:siblingOrders.keySet()) {
            List<CObject> cObjects = siblingOrders.get(order);
            CObject first = cObjects.get(0);
            first.setSiblingOrder(order);
            CAttribute parent = first.getParent();
            List<CObject> children = parent.getChildren();

            //now move the cObjects to the end of the list
            for(CObject cObject:cObjects) {
                int currentIndex = parent.getIndexOfMatchingCObjectChild(cObject);
                children.remove(currentIndex);
                children.add(cObject);

            }

        }

    }

    public static SiblingOrder findSiblingOrder(Map<SiblingOrder, List<CObject>> siblingOrders, String nodeId) {
        for(SiblingOrder key: siblingOrders.keySet()) {
            List<CObject> nodes = siblingOrders.get(key);
            if(nodes.stream().filter(cObject -> cObject.getNodeId().equalsIgnoreCase(nodeId)).findAny().isPresent()) {
                return key;
            }
        }
        return null;
    }

}
