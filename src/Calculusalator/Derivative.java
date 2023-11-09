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
            case NUMBER:
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
