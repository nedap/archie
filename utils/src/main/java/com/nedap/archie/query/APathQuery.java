package com.nedap.archie.query;


import com.google.common.collect.Lists;
import com.nedap.archie.paths.PathSegment;

import com.nedap.archie.adlparser.antlr.XPathLexer;
import com.nedap.archie.adlparser.antlr.XPathParser;
import com.nedap.archie.adlparser.antlr.XPathParser.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * For now only accepts rather simple xpath-like expressions.
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
        if(!query.equals("/")) {
            XPathLexer lexer = new XPathLexer(new ANTLRInputStream(query));
            XPathParser parser = new XPathParser(new CommonTokenStream(lexer));
            LocationPathContext locationPathContext = parser.locationPath();
            AbsoluteLocationPathNorootContext absoluteLocationPathNorootContext = locationPathContext.absoluteLocationPathNoroot();
            if (absoluteLocationPathNorootContext == null) {
                throw new UnsupportedOperationException("relative xpath expressions not yet supported: " + query);
            }
            if (!absoluteLocationPathNorootContext.getTokens(XPathLexer.ABRPATH).isEmpty()) {
                throw new UnsupportedOperationException("absolute path starting with // not yet supported");
            }
            RelativeLocationPathContext relativeLocationPathContext = absoluteLocationPathNorootContext.relativeLocationPath();

            if (!relativeLocationPathContext.getTokens(XPathLexer.ABRPATH).isEmpty()) {
                throw new UnsupportedOperationException("relative path with // between steps not yet supported");
            }
            Pattern isDigit = Pattern.compile("\\d+");

            List<StepContext> stepContexts = relativeLocationPathContext.step();
            for (StepContext stepContext : stepContexts) {
                String nodeName = stepContext.nodeTest().getText();
                List<PredicateContext> predicateContexts = stepContext.predicate();
                PathSegment pathSegment = new PathSegment(nodeName);
                for (PredicateContext predicateContext : predicateContexts) {
                    //TODO: this is not a full parser. We really need one. Find one because writing an XPath parser seems like a thing that's been done before.

                    AndExprContext andExpressionContext = predicateContext.expr().orExpr().andExpr(0);
                    for (EqualityExprContext equalityExprContext : andExpressionContext.equalityExpr()) {
                        if (equalityExprContext.relationalExpr().size() == 1) { //do not yet support equals or not equals operator, ignore for now
                            String expression = equalityExprContext.getText();
                            if (isDigit.matcher(expression).matches()) {
                                pathSegment.setIndex(Integer.parseInt(expression));
                            } else {
                                pathSegment.setNodeId(expression);
                            }
                        }

                    }
                }
                pathSegments.add(pathSegment);
            }
        }
    }

    public List<PathSegment> getPathSegments() {
        return pathSegments;
    }

}
