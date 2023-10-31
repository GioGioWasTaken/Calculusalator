package Calculusalator;

import java.util.HashMap;
import java.util.Stack;

public enum Token_type {
    // Values
    NUMBER, VARIABLE,

    // Mathematical operations
    PLUS, MINUS, MULTIPLICATION, DIVISION, POWER, SQRT,

    // Functions, grouping, and special tokens
    FUNCTION, LEFT_PAREN, RIGHT_PAREN, EOI, INVALID;

    // FUNCTION is used for trigonometric functions, such as sin, cos and tan.
    // The parens are there in order to make the input more organized

    public boolean isOperator(){
        switch (this){
            case PLUS:
            case MINUS:
            case MULTIPLICATION:
            case DIVISION:
            case POWER:
            case SQRT:
                return true;
            default:
                return false;
        }
    }


    public int determinePriority(Token token){
        int precedence = 0;
        switch (token.type){
            case PLUS:
                precedence=1;
                break;
            case MINUS:
                precedence =1;
                break;
            case MULTIPLICATION:
                precedence=2;
                break;
            case DIVISION:
                precedence=2;
                break;
            case POWER:
                precedence=3;
                break;
            case SQRT:
                precedence =3;
                break;
        }
        return precedence;
    }
    public boolean isLowerPriority(Stack<Token> operations, Token token){
        if (operations.isEmpty()) {
            return false; // or handle the case where the stack is empty as needed
        }
        int precedence = determinePriority(token);
        if(operations.peek().type==LEFT_PAREN) return false; // if there is a left parenthesis at the top...
        if(determinePriority(operations.peek())>=precedence) return true;
        else return false;
    }

}
