package com.nedap.archie.query;

import com.google.common.collect.Sets;
import com.nedap.archie.paths.PathSegment;
import com.nedap.archie.xpath.antlr.XPathLexer;
import com.nedap.archie.xpath.antlr.XPathParser;
import com.nedap.archie.xpath.antlr.XPathParser.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converts an APath to an XPath-query
 *
 * Created by pieter.bos on 11/05/16.
 */
public class APathToXPathConverter {

    public static String convertQueryToXPath(String query, String firstNodeName) {
        String convertedQuery = convertWithAntlr(query);
        if(query.startsWith("/")) {
            return "/" + firstNodeName + convertedQuery;
        } else {
            return convertedQuery;
        }
//        Pattern pattern = Pattern.compile("\\[(?<first>[^\\]]*)(?<idnode>id\\d+)(?<last>[^\\]]*)\\]");
//        Matcher m = pattern.matcher(query);
//
//        query = m.replaceAll("[${first}@archetype_node_id='${idnode}'${last}]");

    }


    public static String convertWithAntlr(String query) {


        XPathLexer lexer = new XPathLexer(new ANTLRInputStream(query));
        XPathParser parser = new XPathParser(new CommonTokenStream(lexer));
        MainContext mainCtx = parser.main();
        StringBuilder output = new StringBuilder();
        writeTree(output, mainCtx, false);
        return output.toString();

    }

//    public static void handleOrExpression(StringBuilder output, OrExprContext orExprContext) {
//        boolean firstAndExpr= true;
//        for(AndExprContext andExprContext:orExprContext.andExpr()) {
//            if(!firstAndExpr) {
//                output.append(" or ");
//            }
//            handleAndExpression(output, andExprContext);
//            firstAndExpr = false;
//        }
//    }
//
//    public static void handleAndExpression(StringBuilder output, AndExprContext andExprContext) {
//        boolean firstEqualityExpr = true;
//        for(EqualityExprContext equalityExprContext:andExprContext.equalityExpr()) {
//            if(!firstEqualityExpr) {
//                output.append(" and ");
//            }
//            handleEqualityExpression(output, equalityExprContext);
//            firstEqualityExpr = false;
//        }
//    }
//
//    private static void handleEqualityExpression(StringBuilder output, EqualityExprContext equalityExprContext) {
//        for(int i = 0; i < equalityExprContext.getChildCount(); i++) {
//            ParseTree child = equalityExprContext.getChild(i);
//            if(child instanceof RelationalExprContext) {
//                handleRelationalExpression(output, (RelationalExprContext) child);
//            } else {
//                output.append(child.getText());
//            }
//        }
//    }
//
//    private static void handleRelationalExpression(StringBuilder output, RelationalExprContext relationalExpContext) {
//        for(int i = 0; i < relationalExpContext.getChildCount(); i++) {
//            ParseTree child = relationalExpContext.getChild(i);
//            if(child instanceof AdditiveExprContext) {
//                //handleAdditiveExprContext(output, (AdditiveExprContext) child);
//            } else {
//                output.append(child.getText());
//            }
//        }
//    }

    /**
     * Converts and ANTLR ParseTree of the XPath grammar to APath String output
     * Adds the output to the given StringBuilder.
     *
     * Simple approach that writes every element, adding some whitespace where needed, and converting just a few elements
     * @param output the output to write to
     * @param tree the parse tree
     * @param inPredicate whether we are inside or outside a predicate
     */
    private static void writeTree(StringBuilder output, ParseTree tree, boolean inPredicate) {

        Pattern idCodePattern = Pattern.compile("id\\d+");

        Set<String> literalsThatShouldHaveSpacing = Sets.<String>newHashSet("and", "or", ",", "-", "+", "*", "|", "<", ">", "<=", ">=");
        for(int i = 0; i < tree.getChildCount(); i++) {
            ParseTree child = tree.getChild(i);
            if(child instanceof TerminalNode) {
                boolean shouldAppendSpace = literalsThatShouldHaveSpacing.contains(child.getText().toLowerCase());
                if(shouldAppendSpace) {
                    output.append(" ");
                }
                output.append(child.getText());
                if(shouldAppendSpace) {
                    output.append(" ");
                }
            } else if (child instanceof AndExprContext) {
                for(int j = 0; j < child.getChildCount(); j++) {
                    ParseTree andChild = child.getChild(i);
                    if(andChild instanceof TerminalNode) {
                        output.append(" and "); //this rewrites the "," syntax that is equivalent to 'and'
                    } else {
                        writeTree(output, andChild, inPredicate);
                    }
                }

            } else if (child instanceof PredicateContext) {
                writeTree(output, child, true);
            } else if(inPredicate && child instanceof RelativeLocationPathContext) {
                Matcher idCodeMatcher = idCodePattern.matcher(child.getText());
                if(idCodeMatcher.matches()) {
                    output.append("@archetype_node_id = '");
                    output.append(child.getText());
                    output.append("'");
                } else {
                    writeTree(output, child, inPredicate);
                }
            } else {
                writeTree(output, child, inPredicate);
            } //TODO: rewrite id nodes and some other nodes when we find them in this very simple bit of code :)
        }
    }
}