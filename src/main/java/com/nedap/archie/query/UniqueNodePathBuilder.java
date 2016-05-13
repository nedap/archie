package com.nedap.archie.query;

import org.w3c.dom.Node;

/**
 * Constructs unique paths to archetype DOM node objects.
 *
 * Created by pieter.bos on 11/05/16.
 */
public class UniqueNodePathBuilder {

    public static String constructPath(Node node) {
        Node parent = node.getParentNode();
        String path = "";
        if(parent == null || parent.getParentNode() == null) {
            //TODO: this is a bit of a hack, parent.getParentNode() == null means the current node is one below the parent
            //that should not happen, but because of differences between xpath and apath...
            return path;
        } else {
            int index = findNodeIndex(node, parent);
            Node archetypeNodeId = node.getAttributes().getNamedItem("archetype_node_id");
            if(archetypeNodeId != null) {
                return constructPath(parent) + "/" + node.getNodeName() + "[" + archetypeNodeId.getNodeValue() + ", " + index + "]";
            } else {
                return constructPath(parent) + "/" + node.getNodeName() + "[" + index + "]";
            }

        }
    }

    private static int findNodeIndex(Node node, Node parent) {
        int index = 1;
        for(int i = 0; i < parent.getChildNodes().getLength(); i++) {
            //nodes with same name in XML means in the same collection in Java
            if(parent.getChildNodes().item(i).getNodeName().equals(node.getNodeName())) {
                if (parent.getChildNodes().item(i) == node) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }
}
