package com.nedap.archie.query;


import com.nedap.archie.adlparser.antlr.XPathParser;
import com.nedap.archie.adlparser.antlr.XPathParser.*;
import com.nedap.archie.adlparser.antlr.XPathLexer;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CObject;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;


import java.util.ArrayList;
import java.util.List;

/**
 * For now only accepts rather simple xpath-like expressions. TODO: get a proper parser library, there should be one out there, like jaxen (seems old?)
 *
 * The only queries fully supported at the moment are absolute queries with node ids, such as '/items[id1]/content[id2]/value'.
 *
 * Any expression after the ID-code, such as in '[id1 and name="ignored"] are currently ignored, but they parse and function
 * as long as you add the id-code as first part of the expression.
 *
 * Created by pieter.bos on 19/10/15.
 */
public class APathQuery {

    private List<PathSegment> pathSegments = new ArrayList<>();

    public APathQuery(String query) {
        XPathLexer lexer = new XPathLexer(new ANTLRInputStream(query));
        XPathParser parser = new XPathParser(new CommonTokenStream(lexer));
        LocationPathContext locationPathContext = parser.locationPath();
        AbsoluteLocationPathNorootContext absoluteLocationPathNorootContext = locationPathContext.absoluteLocationPathNoroot();
        if(absoluteLocationPathNorootContext == null) {
            throw new UnsupportedOperationException("relative xpath expressions not yet supported");
        }
        if(!absoluteLocationPathNorootContext.getTokens(XPathLexer.ABRPATH).isEmpty()) {
            throw new UnsupportedOperationException("absolute path starting with // not yet supported");
        }
        RelativeLocationPathContext relativeLocationPathContext = absoluteLocationPathNorootContext.relativeLocationPath();

        if(!relativeLocationPathContext.getTokens(XPathLexer.ABRPATH).isEmpty()) {
            throw new UnsupportedOperationException("relative path with // between steps not yet supported");
        }
        List<StepContext> stepContexts = relativeLocationPathContext.step();
        for(StepContext stepContext:stepContexts) {
            String nodeName = stepContext.nodeTest().getText();
            List<PredicateContext> predicateContexts = stepContext.predicate();
            String nodeId = null;
            for(PredicateContext predicateContext:predicateContexts) {
                //TODO: this is not a full parser. Find one because writing an XPath parser seems like a thing that's been done before.
                //this is a bit of a hack, but it makes sure that /items[id2 and name="ignored"] works. Only in that order
                nodeId = predicateContext.expr().orExpr().andExpr(0).equalityExpr().get(0).getText();
            }
            pathSegments.add(new PathSegment(nodeName, nodeId));
        }
    }

    public ArchetypeModelObject find(CComplexObject root) {
        ArchetypeModelObject currentObject = root;
        int i =0;
        for(PathSegment segment:pathSegments) {
            if(i >= pathSegments.size()) {
                return currentObject;
            }
            CAttribute attribute = null;
            if(currentObject instanceof CComplexObject) {
                attribute = ((CComplexObject) currentObject).getAttribute(segment.getNodeName());
            } else {
                //erm.. right. we could do something with reflection and return a bean property/field/whatever?
                return null;
            }
            if(attribute == null) {
                return null;
            }
            currentObject = attribute;
            if(segment.getNodeId() == null) {
                if(i == pathSegments.size()-1) {
                    return attribute;
                }
                continue;//??
            }
            currentObject = attribute.getChild(segment.getNodeId());
            if(currentObject == null) {
                return null;
            }

            i++;
        }
        return currentObject;
    }
}
