package com.nedap.archie.serializer.adl.constraints;

import com.nedap.archie.aom.CComplexObjectProxy;
import com.nedap.archie.serializer.adl.ADLDefinitionSerializer;

/**
 * @author markopi
 */
public class CComplexObjectProxySerializer extends ConstraintSerializer<CComplexObjectProxy> {
    public CComplexObjectProxySerializer(ADLDefinitionSerializer serializer) {
        super(serializer);
    }

    @Override
    public void serialize(CComplexObjectProxy cobj) {
        builder.newIndentedLine()
                .append("use_node ")
                .append(cobj.getRmTypeName())
                .append(nodeIdString(cobj.getNodeId()))
                .append(" ")
                .append(cobj.getTargetPath())
                //.lineComment("Should be comment here - not implemented")
                .unindent();
    }

    private String nodeIdString(String nodeId) {
        return nodeId == null ? "" : "[" + nodeId + "]";
    }
}
