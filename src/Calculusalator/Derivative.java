package Calculusalator;

public class Derivative {




    public ASTNode calcDerivative(ASTNode AST){
        Token derivative;
        switch (Token.type){
            case NUMBER -> derivative =new Token(Token_type.NUMBER,"0");
            case VARIABLE -> derivative=deriVariable();
        }
    }
    // we will convert the tree to infix notation for the user in the scanner module.

}
