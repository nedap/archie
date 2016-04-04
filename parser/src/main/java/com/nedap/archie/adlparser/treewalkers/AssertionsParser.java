package com.nedap.archie.adlparser.treewalkers;

import com.nedap.archie.adlparser.ADLParserErrors;
import com.nedap.archie.adlparser.antlr.AdlParser;
import com.nedap.archie.adlparser.antlr.AdlParser.*;

import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.rules.*;
import javassist.compiler.ast.Variable;
import org.jetbrains.annotations.NotNull;


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
        } else if (context.adl_path() != null) {
            ExpressionVariable result = new ExpressionVariable();
            setVariableNameAndType(context, result);
            assertion.setExpression(parseModelReference(context.adl_path()));
            return result;
        } else if (context.adl_relative_path() != null) {
            ExpressionVariable result = new ExpressionVariable();
            setVariableNameAndType(context, result);
            assertion.setExpression(parseModelReference(context.adl_relative_path()));
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

        if(context.SYM_OR() != null) {
            BinaryOperator expression = new BinaryOperator();
            expression.setType(ExpressionType.BOOLEAN);
            expression.setOperator(OperatorKind.parse(context.SYM_OR().getText()));
            expression.addOperand(parseExpression(context.boolean_expression()));
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
            expression.addOperand(parseImpliesExpression(context.boolean_implies_expression()));
            expression.addOperand(parseXorExpression(context.boolean_xor_expression()));
            return expression;
        } else {
            return parseImpliesExpression(context.boolean_implies_expression());
        }

    }

    private Expression parseImpliesExpression(Boolean_implies_expressionContext context) {
        if(context.SYM_IMPLIES() != null) {
            BinaryOperator expression = new BinaryOperator();
            expression.setType(ExpressionType.BOOLEAN);
            expression.setOperator(OperatorKind.parse(context.SYM_IMPLIES().getText()));
            expression.addOperand(parseBooleanLeaf(context.boolean_leaf()));
            expression.addOperand(parseImpliesExpression(context.boolean_implies_expression()));
            return expression;
        } else {
            return parseBooleanLeaf(context.boolean_leaf());
        }

    }

    private Expression parseBooleanLeaf(Boolean_leafContext context) {
        if(context.boolean_literal() != null) {
            Constant result = new Constant<Boolean>(ExpressionType.BOOLEAN, context.boolean_literal().SYM_TRUE() != null ? true : false);

            return result;
        }
        if(context.adl_path() != null) {
            ModelReference reference = parseModelReference(context.adl_path());
            if(context.SYM_EXISTS() != null) {
                return new UnaryOperator(ExpressionType.BOOLEAN, OperatorKind.exists, reference);
            } else {
                return reference;
            }
        }
        if(context.boolean_constraint() != null) {
            return parseBooleanConstraint(context.boolean_constraint());
        }
        if(context.boolean_expression() != null) {
            Expression expression = parseExpression(context.boolean_expression());
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

    @NotNull
    private ModelReference parseModelReference(Adl_pathContext context) {
        return new ModelReference(context.getText());
    }

    @NotNull
    private ModelReference parseModelReference(Adl_relative_pathContext context) {
        return new ModelReference(context.getText());
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
        if(context.pow_binop() != null) {
            Expression left = parsePowExpression(context.pow_expression());
            Expression right = parseArithmeticLeaf(context.arithmetic_leaf());
            return new BinaryOperator(right.getType(), OperatorKind.parse(context.pow_binop().getText()), left, right);
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
        if(context.adl_path() != null) {
            return new ModelReference(context.adl_path().getText());//TODO: get type from archetype
        }
        if(context.arithmetic_expression() != null) {
            return parseArithmeticExpression(context.arithmetic_expression());//TODO: precedenceOverridden
        }
        if(context.arithmetic_leaf() != null) {
            Expression expression = parseArithmeticLeaf(context.arithmetic_leaf());
            return new UnaryOperator(expression.getType(), OperatorKind.minus, expression);
        }
        throw new IllegalArgumentException("cannot parse unknown arithmetic leaf type");
    }
}
