package Calculusalator;

import static Calculusalator.Token_type.*;

public class Derivative {

    public ASTNode findDerivedAST(ASTNode AST) {
        ASTNode DerivedAST = new ASTNode(AST.value);
        switch (AST.value.type) {
            case PLUS:
                // The sum rule
            case MINUS:
                // The difference rule
                DerivedAST.left = findDerivedAST(AST.left);
                DerivedAST.right = findDerivedAST(AST.right);
                break;
            case MULTIPLICATION:
                // The product rule
                Token leftValue = new Token(MULTIPLICATION, "*");
                Token rightValue = new Token(MULTIPLICATION, "*");

                ASTNode MultiLEFT = new ASTNode(leftValue);
                ASTNode MultiRIGHT = new ASTNode(rightValue);

                MultiLEFT.left = findDerivedAST(AST.left);
                MultiLEFT.right = AST.right;

                MultiRIGHT.left = findDerivedAST(AST.right);
                MultiRIGHT.right = AST.left;

                DerivedAST.value = new Token(PLUS, "+");
                DerivedAST.left = MultiLEFT;
                DerivedAST.right = MultiRIGHT;
                break;
            case POWER:
                // The power rule (assume ^ is only applied to expressions with x. Apply the logic that makes that happen later).
                // the power of any number is always the right node
                // d(T) =  right* (left^ (right-1))
                Token coefficient = new Token(NUMBER, AST.right.value.lexeme);
                ASTNode multiplier_Node = new ASTNode(coefficient);
                multiplier_Node.left = null;
                multiplier_Node.right= null;


                int derivedExpo = Integer.parseInt(AST.right.value.lexeme) -1; // power -1
                Token Exponent = new Token(NUMBER,String.valueOf(derivedExpo));
                Token base = new Token(VARIABLE,AST.left.value.lexeme);

                DerivedAST.value = new Token(PLUS, "*");
                DerivedAST.left = multiplier_Node;
                DerivedAST.right = new ASTNode(new Token(POWER,"^"));
                DerivedAST.right.left = new ASTNode(base);
                DerivedAST.right.right = new ASTNode(Exponent);

            case NUMBER:
                // Constant term rule
                DerivedAST.value = new Token(NUMBER, "0");
                DerivedAST.right = null;
                DerivedAST.left = null;
                break;
            case VARIABLE:
                // The derivative of a variable is 1
                DerivedAST.value = new Token(NUMBER, "1");
                DerivedAST.right = null;
                DerivedAST.left = null;
                break;
        }
        return DerivedAST;
    }
}
