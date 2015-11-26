package com.nedap.archie.adlparser.treewalkers;

import com.nedap.archie.adlparser.ADLParserErrors;
import com.nedap.archie.adlparser.antlr.AdlParser;
import com.nedap.archie.adlparser.antlr.AdlParser.*;

import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.rules.*;


/**
 * Created by pieter.bos on 27/10/15.
 */
public class AssertionsParser extends BaseTreeWalker {

    private PrimitivesConstraintParser primitivesConstraintParser;

    public AssertionsParser(ADLParserErrors errors) {
        super(errors);
        primitivesConstraintParser = new PrimitivesConstraintParser(errors);
    }

    public Assertion parse(AssertionContext assertionContext) {
        Assertion assertion = new Assertion();
        assertion.setStringExpression(assertionContext.getText());//TODO: this has whitespace stripped. Get the Lexer input instead
        if(assertionContext.identifier() != null) {
            assertion.setTag(assertionContext.identifier().getText());
        }
        assertion.setExpression(parseExpression(assertionContext.boolean_expr()));
        return assertion;
    }

    private Expression parseExpression(Boolean_exprContext booleanExpr) {

        if(booleanExpr.boolean_binop() != null) {
            BinaryOperator expression = new BinaryOperator();
            expression.setType(ExpressionType.BOOLEAN);
            expression.setOperator(OperatorKind.parse(booleanExpr.boolean_binop().getText()));
            expression.addOperand(parseExpression(booleanExpr.boolean_expr()));
            expression.addOperand(parseBooleanLeaf(booleanExpr.boolean_leaf()));
            return expression;
        } else {
            return parseBooleanLeaf(booleanExpr.boolean_leaf());
        }

    }

    private Expression parseBooleanLeaf(Boolean_leafContext context) {
        if(context.boolean_literal() != null) {
            Constant result = new Constant<Boolean>(ExpressionType.BOOLEAN, context.boolean_literal().SYM_TRUE() != null ? true : false);

            return result;
        }
        if(context.adl_path() != null) {
            ModelReference reference = new ModelReference();
            reference.setPath(context.adl_path().getText());//TODO: set the type. This must be done by looking up the type. Post-processor work?
            if(context.SYM_EXISTS() != null) {
                return new UnaryOperator(ExpressionType.BOOLEAN, OperatorKind.exists, reference);
            } else {
                return reference;
            }
        }
        if(context.boolean_constraint() != null) {
            return parseBooleanConstraint(context.boolean_constraint());
        }
        if(context.boolean_expr() != null) {
            Expression expression = parseExpression(context.boolean_expr());
            //expression.setPrecedenceOverridden(true);
            return expression;
        }
        if(context.arithmetic_relop_expr() != null) {
            return parseArithmeticRelOpExpression(context.arithmetic_relop_expr());
        }
        if(context.SYM_NOT() != null) {
            return new UnaryOperator(ExpressionType.BOOLEAN, OperatorKind.not, parseBooleanLeaf(context.boolean_leaf()));
        }

        throw new IllegalArgumentException("cannot parse unknown boolean leaf type");
    }

    private Expression parseBooleanConstraint(Boolean_constraintContext context) {
        String path = null;
        if(context.adl_path() != null) {
            path = context.adl_path().getText();
        }
        if(context.adl_relative_path() != null) {
            path = context.adl_relative_path().getText();
        }
        CPrimitiveObject cPrimitiveObject = null;
        if(context.c_primitive_object() != null) {
            cPrimitiveObject = primitivesConstraintParser.parsePrimitiveObject(context.c_primitive_object());
        } else {
            cPrimitiveObject = primitivesConstraintParser.parseRegex(context.CONTAINED_REGEXP());
        }
        return new BinaryOperator(ExpressionType.BOOLEAN, OperatorKind.matches, new ModelReference(path), new Constraint(cPrimitiveObject));
    }

    private Expression parseArithmeticRelOpExpression(Arithmetic_relop_exprContext context) {
        Expression left = parseArithmeticExpression(context.arithmetic_arith_expr(0));
        Expression right = parseArithmeticExpression(context.arithmetic_arith_expr(1));
        if(left.getType() != null && right.getType() != null && left.getType() != right.getType()) {
            throw new IllegalArgumentException("arithmetic relop expression with different types: " + left.getType() + " + " + right.getType());
        }
        return new BinaryOperator(left.getType(), OperatorKind.parse(context.relational_binop().getText()), left, right);
    }

    private Expression parseArithmeticExpression(Arithmetic_arith_exprContext context) {
        Expression leaf = parseArithmeticLeaf(context.arithmetic_leaf());
        if(context.arithmetic_binop() != null) {
            Expression left = parseArithmeticExpression(context.arithmetic_arith_expr());
            return new BinaryOperator(leaf.getType(), OperatorKind.parse(context.arithmetic_binop().getText()), left, leaf);
        }
        else if(context.arithmetic_arith_expr() != null) {
            Expression left = parseArithmeticExpression(context.arithmetic_arith_expr());
            return new BinaryOperator(leaf.getType(), OperatorKind.exponent, left, leaf);
        } else {
            return parseArithmeticLeaf(context.arithmetic_leaf());
        }
    }

    private Expression parseArithmeticLeaf(Arithmetic_leafContext context) {
        if(context.integer_value() != null) {
            return new Constant<>(ExpressionType.INTEGER, Integer.parseInt(context.integer_value().getText()));
        }
        if(context.real_value() != null) {
            return new Constant<>(ExpressionType.REAL, Double.parseDouble(context.real_value().getText()));
        }
        if(context.adl_path() != null) {
            return new ModelReference(context.adl_path().getText());//TODO: get type from archetype
        }
        if(context.arithmetic_arith_expr() != null) {
            return parseArithmeticExpression(context.arithmetic_arith_expr());//TODO: precedenceOverridden
        }
        if(context.arithmetic_leaf() != null) {
            Expression expression = parseArithmeticLeaf(context.arithmetic_leaf());
            return new UnaryOperator(expression.getType(), OperatorKind.minus, expression);
        }
        throw new IllegalArgumentException("cannot parse unknown arithmetic leaf type");
    }
}
