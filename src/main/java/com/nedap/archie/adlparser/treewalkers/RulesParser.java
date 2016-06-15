package com.nedap.archie.adlparser.treewalkers;

import com.nedap.archie.adlparser.ADLParserErrors;
import com.nedap.archie.adlparser.antlr.AdlParser;
import com.nedap.archie.adlparser.antlr.AdlParser.*;

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

    public Assertion parse(AssertionContext assertionContext) {

        Assertion assertion = new Assertion();
        if(assertionContext.boolean_assertion() != null) {
            Boolean_assertionContext context = assertionContext.boolean_assertion();
            assertion.setStringExpression(context.getText());//TODO: this has whitespace stripped. Get the Lexer input instead
            if (context.identifier() != null) {
                assertion.setTag(context.identifier().getText());
            }
            assertion.setExpression(parseExpression(context.boolean_expression()));
        } else if (assertionContext.variable_declaration() != null) {
            VariableDeclaration declaration = parseVariableDeclaration(assertion, assertionContext.variable_declaration());
            assertion.addVariable(declaration);
        }
        return assertion;
    }

    private VariableDeclaration parseVariableDeclaration(Assertion assertion, Variable_declarationContext context) {

        if(context.boolean_expression() != null) {
            ExpressionVariable result = new ExpressionVariable();
            setVariableNameAndType(context, result);
            result.setExpression(parseExpression(context.boolean_expression()));
            return result;

        } else if (context.arithmetic_expression() != null) {
            ExpressionVariable result = new ExpressionVariable();
            setVariableNameAndType(context, result);
            result.setExpression(parseArithmeticExpression(context.arithmetic_expression()));
            return result;

        }
        return null;
    }

    private void setVariableNameAndType(AdlParser.Variable_declarationContext context, ExpressionVariable result) {
        result.setName(context.identifier(0).getText());
        result.setType(ExpressionType.fromString(context.identifier(1).getText()));
    }

    private Expression parseExpression(Boolean_expressionContext context) {

        if(context.SYM_IMPLIES() != null) {
            BinaryOperator expression = new BinaryOperator();
            expression.setType(ExpressionType.BOOLEAN);
            expression.setOperator(OperatorKind.parse(context.SYM_IMPLIES().getText()));
            expression.addOperand(parseExpression(context.boolean_expression()));
            expression.addOperand(parseForAllExpression(context.boolean_for_all_expression()));
            return expression;
        } else {
            return parseForAllExpression(context.boolean_for_all_expression());
        }

    }

    private Expression parseForAllExpression(Boolean_for_all_expressionContext context) {
        if(context.SYM_FOR_ALL() != null) {

            Expression pathExpression = null;

            if (context.adl_rules_path() != null) {
                pathExpression = parseModelReference(context.adl_rules_path());
            } else {
                pathExpression = parseVariableReference(context.variable_reference());
            }
            String variableName = context.identifier().getText();
            return new ForAllStatement(variableName,
                    pathExpression,
                    parseForAllExpression(context.boolean_for_all_expression()));
        } else {
            return parseOrExpression(context.boolean_or_expression());
        }

    }

    private Expression parseOrExpression(Boolean_or_expressionContext context) {
        if(context.SYM_OR() != null) {
            BinaryOperator expression = new BinaryOperator();
            expression.setType(ExpressionType.BOOLEAN);
            expression.setOperator(OperatorKind.parse(context.SYM_OR().getText()));
            expression.addOperand(parseOrExpression(context.boolean_or_expression()));
            expression.addOperand(parseAndExpression(context.boolean_and_expression()));
            return expression;
        } else {
            return parseAndExpression(context.boolean_and_expression());
        }
    }

    private Expression parseAndExpression(Boolean_and_expressionContext context) {
        if(context.SYM_AND() != null) {
            BinaryOperator expression = new BinaryOperator();
            expression.setType(ExpressionType.BOOLEAN);
            expression.setOperator(OperatorKind.parse(context.SYM_AND().getText()));
            expression.addOperand(parseAndExpression(context.boolean_and_expression()));
            expression.addOperand(parseXorExpression(context.boolean_xor_expression()));
            return expression;
        } else {
            return parseXorExpression(context.boolean_xor_expression());
        }
    }

    private Expression parseXorExpression(Boolean_xor_expressionContext context) {
        if(context.SYM_XOR() != null) {
            BinaryOperator expression = new BinaryOperator();
            expression.setType(ExpressionType.BOOLEAN);
            expression.setOperator(OperatorKind.parse(context.SYM_XOR().getText()));
            expression.addOperand(parseBooleanConstraintExpression(context.boolean_constraint_expression()));
            expression.addOperand(parseXorExpression(context.boolean_xor_expression()));
            return expression;
        } else {
            return parseBooleanConstraintExpression(context.boolean_constraint_expression());
        }
    }

    private Expression parseBooleanConstraintExpression(Boolean_constraint_expressionContext context) {
        if(context.boolean_constraint() != null) {
            return parseBooleanConstraint(context.boolean_constraint());
        } else {
            return parseBooleanLeaf(context.boolean_leaf());
        }
    }

    private Expression parseBooleanLeaf(Boolean_leafContext context) {
        if(context.boolean_literal() != null) {
            Constant result = new Constant<Boolean>(ExpressionType.BOOLEAN, context.boolean_literal().SYM_TRUE() != null ? true : false);

            return result;
        }
        if(context.adl_rules_path() != null) {
            ModelReference reference = parseModelReference(context.adl_rules_path());
            if(context.SYM_EXISTS() != null) {
                return new UnaryOperator(ExpressionType.BOOLEAN, OperatorKind.exists, reference);
            } else {
                return reference;
            }
        }
        if(context.boolean_expression() != null) {
            Expression expression = parseExpression(context.boolean_expression());
            expression.setPrecedenceOverridden(true);
            return expression;
        }
        if(context.arithmetic_relop_expr() != null) {
            return parseArithmeticRelOpExpression(context.arithmetic_relop_expr());
        }
        if(context.SYM_NOT() != null) {
            return new UnaryOperator(ExpressionType.BOOLEAN, OperatorKind.not, parseBooleanLeaf(context.boolean_leaf()));
        }
        if(context.variable_reference() != null) {
            return parseVariableReference(context.variable_reference());
        }

        throw new IllegalArgumentException("cannot parse unknown boolean leaf type");
    }

    @NotNull
    private ModelReference parseModelReference(Adl_rules_pathContext context) {
        String variableReference = null;
        if(context.variable_reference() != null) {
            variableReference = context.variable_reference().identifier().getText();
        }
        StringBuilder path = new StringBuilder();
        for(Adl_rules_path_segmentContext pathSegment:context.adl_rules_path_segment()){
            path.append(pathSegment.getText());

        }
        return new ModelReference(variableReference, path.toString());
    }

    @NotNull
    private ModelReference parseModelReference(Adl_rules_relative_pathContext context) {
        return new ModelReference(context.getText());
    }

    private Expression parseBooleanConstraint(Boolean_constraintContext context) {
        ModelReference modelReference = null;
        if(context.adl_rules_path() != null) {
            modelReference = parseModelReference(context.adl_rules_path());
        }
        if(context.adl_rules_relative_path() != null) {
            modelReference = parseModelReference(context.adl_rules_relative_path());
        }
        CPrimitiveObject cPrimitiveObject = null;
        if(context.c_primitive_object() != null) {
            cPrimitiveObject = primitivesConstraintParser.parsePrimitiveObject(context.c_primitive_object());
        } else {
            cPrimitiveObject = primitivesConstraintParser.parseRegex(context.CONTAINED_REGEXP());
        }
        return new BinaryOperator(ExpressionType.BOOLEAN, OperatorKind.matches, modelReference, new Constraint(cPrimitiveObject));
    }

    private Expression parseArithmeticRelOpExpression(Arithmetic_relop_exprContext context) {
        Expression left = parseArithmeticExpression(context.arithmetic_expression(0));
        Expression right = parseArithmeticExpression(context.arithmetic_expression(1));
        if(left.getType() != null && right.getType() != null && left.getType() != right.getType()) {
            throw new IllegalArgumentException("arithmetic relop expression with different types: " + left.getType() + " + " + right.getType());
        }
        return new BinaryOperator(left.getType(), OperatorKind.parse(context.relational_binop().getText()), left, right);
    }

    private Expression parseArithmeticExpression(Arithmetic_expressionContext context) {
        if(context.plus_minus_binop() != null) {
            Expression left = parseArithmeticExpression(context.arithmetic_expression());
            Expression right = parseMultiplyingExpression(context.multiplying_expression());
            return new BinaryOperator(right.getType(), OperatorKind.parse(context.plus_minus_binop().getText()), left, right);
        } else {
            return parseMultiplyingExpression(context.multiplying_expression());
        }
    }

    private Expression parseMultiplyingExpression(Multiplying_expressionContext context) {
        if(context.mult_binop() != null) {
            Expression left = parseMultiplyingExpression(context.multiplying_expression());
            Expression right = parsePowExpression(context.pow_expression());
            return new BinaryOperator(right.getType(), OperatorKind.parse(context.mult_binop().getText()), left, right);
        } else {
            return parsePowExpression(context.pow_expression());
        }
    }

    private Expression parsePowExpression(Pow_expressionContext context) {
        if(context.pow_expression() != null) {
            Expression left = parsePowExpression(context.pow_expression());
            Expression right = parseArithmeticLeaf(context.arithmetic_leaf());
            return new BinaryOperator(right.getType(), OperatorKind.parse("^"), left, right);
        } else {
            return parseArithmeticLeaf(context.arithmetic_leaf());
        }
    }

    private Expression parseArithmeticLeaf(Arithmetic_leafContext context) {
        if(context.integer_value() != null) {
            return new Constant<>(ExpressionType.INTEGER, Long.parseLong(context.integer_value().getText()));
        }
        if(context.real_value() != null) {
            return new Constant<>(ExpressionType.REAL, Double.parseDouble(context.real_value().getText()));
        }
        if(context.adl_rules_path() != null) {
            return parseModelReference(context.adl_rules_path());
        }
        if(context.arithmetic_expression() != null) {
            Expression expression = parseArithmeticExpression(context.arithmetic_expression());
            expression.setPrecedenceOverridden(true);
            return expression;
        }
        if(context.arithmetic_leaf() != null) {
            return new UnaryOperator(ExpressionType.REAL, OperatorKind.minus, parseArithmeticLeaf(context.arithmetic_leaf()));

        }
        if(context.variable_reference() != null) {
            return parseVariableReference(context.variable_reference());
        }
        throw new IllegalArgumentException("cannot parse unknown arithmetic leaf type: " + context.getText());
    }

    @NotNull
    private Expression parseVariableReference(Variable_referenceContext context) {
        VariableReference reference = new VariableReference();
        //TODO: retrieve declaration from actual declaration, instead of just setting the name
        VariableDeclaration declaration = new VariableDeclaration();
        declaration.setName(context.identifier().  getText());
        reference.setDeclaration(declaration);
        return reference;
    }
}
