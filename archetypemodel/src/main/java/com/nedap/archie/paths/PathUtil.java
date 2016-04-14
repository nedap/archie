package com.nedap.archie.paths;

import com.nedap.archie.aom.CPrimitiveObject;

import java.util.List;

/**
 * Created by pieter.bos on 07/04/16.
 */
public class PathUtil {

    public static String getPath(List<PathSegment> pathSegments) {
        StringBuilder result = new StringBuilder();

        if(pathSegments.isEmpty()) {
            return "/";
        }
        for(PathSegment segment: pathSegments) {
            result.append("/");
            result.append(segment.getNodeName());
            if(segment.getNodeId() != null && !segment.getNodeId().equals(CPrimitiveObject.PRIMITIVE_NODE_ID_VALUE)) {
                result.append("[");
                result.append(segment.getNodeId());
                result.append("]");
            }
        }
        return result.toString();
    }
}
