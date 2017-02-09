package com.nedap.archie.adlparser.treewalkers;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.adlparser.ADLParserErrors;
import com.nedap.archie.adlparser.antlr.AdlParser.*;

import com.nedap.archie.adlparser.odin.OdinValueParser;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.rules.*;
import org.jetbrains.annotations.NotNull;


/**
 * Created by pieter.bos on 27/10/15.
 */
public class RulesParser extends BaseTreeWalker {

    private PrimitivesConstraintParser primitivesConstraintParser;

    public RulesParser(ADLParserErrors errors) {
        super(errors);
        primitivesConstraintParser = new PrimitivesConstraintParser(errors);
    }

    public RuleStatement parse(AssertionContext assertionContext) {

        Assertion assertion = new Assertion();
        if(assertionContext.booleanAssertion() != null) {
            BooleanAssertionContext context = assertionContext.booleanAssertion();
            assertion.setStringExpression(context.getText());//TODO: this has whitespace stripped. Get the Lexer input instead
            if (context.identifier() != null) {
                assertion.setTag(context.identifier().getText());
            }
            assertion.setExpression(parseExpression(context.expression()));
            return assertion;
        } else if (assertionContext.variableDeclaration() != null) {
            VariableDeclaration declaration = parseVariableDeclaration(assertionContext.variableDeclaration());
            return declaration;
        }
        return assertion;
    }

    private VariableDeclaration parseVariableDeclaration(VariableDeclarationContext context) {
        ExpressionVariable result = new ExpressionVariable();
        setVariableNameAndType(context, result);
        result.setExpression(parseExpression(context.expression()));
        return result;

    }

    private void setVariableNameAndType(VariableDeclarationContext context, ExpressionVariable result) {
        result.setName(context.identifier(0).getText());
        result.setType(ExpressionType.fromString(context.identifier(1).getText()));
    }

    private Expression parseExpression(ExpressionContext context) {

        if(context.SYM_IMPLIES() != null) {
            BinaryOperator expression = new BinaryOperator();
            expression.setType(ExpressionType.BOOLEAN);
            expression.setOperator(OperatorKind.parse(context.SYM_IMPLIES().getText()));
            expression.addOperand(parseExpression(context.expression()));
            expression.addOperand(parseForAllExpression(context.booleanForAllExpression()));
            return expression;
        } else {
            return parseForAllExpression(context.booleanForAllExpression());
        }

    }

    private Expression parseForAllExpression(BooleanForAllExpressionContext context) {
        if(context.SYM_FOR_ALL() != null) {

            Expression pathExpression = null;

            if (context.adlRulesPath() != null) {
                pathExpression = parseModelReference(context.adlRulesPath());
            } else {
                pathExpression = parseVariableReference(context.variableReference());
            }
            String variableName = context.identifier().getText();
            return new ForAllStatement(variableName,
                    pathExpression,
                    parseForAllExpression(context.booleanForAllExpression()));
        } else {
            return parseOrExpression(context.booleanOrExpression());
        }

    }

    private Expression parseOrExpression(BooleanOrExpressionContext context) {
        if(context.SYM_OR() != null) {
            BinaryOperator expression = new BinaryOperator();
            expression.setType(ExpressionType.BOOLEAN);
            expression.setOperator(OperatorKind.parse(context.SYM_OR().getText()));
            expression.addOperand(parseOrExpression(context.booleanOrExpression()));
            expression.addOperand(parseAndExpression(context.booleanAndExpression()));
            return expression;
        } else {
            return parseAndExpression(context.booleanAndExpression());
        }
    }

    private Expression parseAndExpression(BooleanAndExpressionContext context) {
        if(context.SYM_AND() != null) {
            BinaryOperator expression = new BinaryOperator();
            expression.setType(ExpressionType.BOOLEAN);
            expression.setOperator(OperatorKind.parse(context.SYM_AND().getText()));
            expression.addOperand(parseAndExpression(context.booleanAndExpression()));
            expression.addOperand(parseXorExpression(context.booleanXorExpression()));
            return expression;
        } else {
            return parseXorExpression(context.booleanXorExpression());
        }
    }

    private Expression parseXorExpression(BooleanXorExpressionContext context) {
        if(context.SYM_XOR() != null) {
            BinaryOperator expression = new BinaryOperator();
            expression.setType(ExpressionType.BOOLEAN);
            expression.setOperator(OperatorKind.parse(context.SYM_XOR().getText()));
            expression.addOperand(parseBooleanConstraintExpression(context.booleanConstraintExpression()));
            expression.addOperand(parseXorExpression(context.booleanXorExpression()));
            return expression;
        } else {
            return parseBooleanConstraintExpression(context.booleanConstraintExpression());
        }
    }

    private Expression parseBooleanConstraintExpression(BooleanConstraintExpressionContext context) {
        if(context.booleanConstraint() != null) {
            return parseBooleanConstraint(context.booleanConstraint());
        } else {
            return parseEqualityExpression(context.equalityExpression());
        }
    }

    private Expression parseBooleanLiteral(BooleanLiteralContext context) {
        return new Constant<Boolean>(ExpressionType.BOOLEAN, context.SYM_TRUE() != null ? true : false);
    }

    @NotNull
    private ModelReference parseModelReference(AdlRulesPathContext context) {
        String variableReference = null;
        if(context.variableReference() != null) {
            variableReference = context.variableReference().identifier().getText();
        }
        StringBuilder path = new StringBuilder();
        for(AdlRulesPathSegmentContext pathSegment:context.adlRulesPathSegment()){
            path.append(pathSegment.getText());

        }
        return new ModelReference(variableReference, path.toString());
    }

    @NotNull
    private ModelReference parseModelReference(AdlRulesRelativePathContext context) {
        return new ModelReference(context.getText());
    }

    private Expression parseBooleanConstraint(BooleanConstraintContext context) {
        ModelReference modelReference = null;
        if(context.adlRulesPath() != null) {
            modelReference = parseModelReference(context.adlRulesPath());
        }
        if(context.adlRulesRelativePath() != null) {
            modelReference = parseModelReference(context.adlRulesRelativePath());
        }
        CPrimitiveObject cPrimitiveObject = null;
        if(context.c_primitive_object() != null) {
            cPrimitiveObject = primitivesConstraintParser.parsePrimitiveObject(context.c_primitive_object());
        } else {
            cPrimitiveObject = primitivesConstraintParser.parseRegex(context.CONTAINED_REGEXP());
        }
        return new BinaryOperator(ExpressionType.BOOLEAN, OperatorKind.matches, modelReference, new Constraint(cPrimitiveObject));
    }

    private Expression parseEqualityExpression(EqualityExpressionContext context) {
        if(context.equalityBinop() != null) {
            Expression left = parseEqualityExpression(context.equalityExpression());
            Expression right = parseRelOpExpression(context.relOpExpression());
            if(left.getType() != null && right.getType() != null && left.getType() != right.getType()) {
                throw new IllegalArgumentException("arithmetic relop expression with different types: " + left.getType() + " + " + right.getType());
            }
            return new BinaryOperator(left.getType(), OperatorKind.parse(context.equalityBinop().getText()), left, right);
        } else {
            return parseRelOpExpression(context.relOpExpression());
        }
    }

    private Expression parseRelOpExpression(RelOpExpressionContext context) {
        if(context.relationalBinop() != null) {
            Expression left = parseRelOpExpression(context.relOpExpression());
            Expression right = parsePlusExpression(context.plusExpression());
            if(left.getType() != null && right.getType() != null && left.getType() != right.getType()) {
                throw new IllegalArgumentException("arithmetic relop expression with different types: " + left.getType() + " + " + right.getType());
            }
            return new BinaryOperator(left.getType(), OperatorKind.parse(context.relationalBinop().getText()), left, right);
        } else {
            return parsePlusExpression(context.plusExpression());
        }

    }

    private Expression parsePlusExpression(PlusExpressionContext context) {
        if(context.plusMinusBinop() != null) {
            Expression left = parsePlusExpression(context.plusExpression());
            Expression right = parseMultiplyingExpression(context.multiplyingExpression());
            return new BinaryOperator(right.getType(), OperatorKind.parse(context.plusMinusBinop().getText()), left, right);
        } else {
            return parseMultiplyingExpression(context.multiplyingExpression());
        }
    }

    private Expression parseMultiplyingExpression(MultiplyingExpressionContext context) {
        if(context.multBinop() != null) {
            Expression left = parseMultiplyingExpression(context.multiplyingExpression());
            Expression right = parsePowExpression(context.powExpression());
            return new BinaryOperator(right.getType(), OperatorKind.parse(context.multBinop().getText()), left, right);
        } else {
            return parsePowExpression(context.powExpression());
        }
    }

    private Expression parsePowExpression(PowExpressionContext context) {
        if(context.powExpression() != null) {
            Expression left = parsePowExpression(context.powExpression());
            Expression right = parseExpressionLeaf(context.expressionLeaf());
            return new BinaryOperator(right.getType(), OperatorKind.parse("^"), left, right);
        } else {
            return parseExpressionLeaf(context.expressionLeaf());
        }
    }

    private Expression parseExpressionLeaf(ExpressionLeafContext context) {
        if(context.integer_value() != null) {
            return new Constant<>(ExpressionType.INTEGER, Long.parseLong(context.integer_value().getText()));
        }
        else if(context.real_value() != null) {
            return new Constant<>(ExpressionType.REAL, Double.parseDouble(context.real_value().getText()));
        } else if (context.string_value() != null) {
            return new Constant<>(ExpressionType.STRING, OdinValueParser.parseOdinStringValue(context.string_value()));
        }
        else if(context.adlRulesPath() != null) {
            ModelReference reference = parseModelReference(context.adlRulesPath());
            if(context.SYM_EXISTS() != null) {
                return new UnaryOperator(ExpressionType.BOOLEAN, OperatorKind.exists, reference);
            } else {
                return reference;
            }
        }
        else if(context.expression() != null) {
            Expression expression = this.parseExpression(context.expression());
            if(context.SYM_NOT() != null) {
                return new UnaryOperator(ExpressionType.BOOLEAN, OperatorKind.not, expression);
            } else { //'this is '(' boolean_expression ')'
                expression.setPrecedenceOverridden(true);
                return expression;
            }
        }
        else if(context.expressionLeaf() != null) { // - arithmetic expression
            return new UnaryOperator(ExpressionType.REAL, OperatorKind.minus, parseExpressionLeaf(context.expressionLeaf()));
        }
        else if(context.variableReference() != null) {
            return parseVariableReference(context.variableReference());
        }
        else if(context.booleanLiteral() != null) {
            return parseBooleanLiteral(context.booleanLiteral());
        }
        else if(context.variableReference() != null) {
            return parseVariableReference(context.variableReference());
        }
        throw new IllegalArgumentException("cannot parse unknown arithmetic leaf type: " + context.getText());
    }

    @NotNull
    private Expression parseVariableReference(VariableReferenceContext context) {
        VariableReference reference = new VariableReference();
        //TODO: retrieve declaration from actual declaration, instead of just setting the name
        VariableDeclaration declaration = new VariableDeclaration();
        declaration.setName(context.identifier().  getText());
        reference.setDeclaration(declaration);
        return reference;
    }
}
