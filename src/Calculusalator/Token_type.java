package Calculusalator;

import java.util.Stack;

public enum Token_type {
    // Values
    NUMBER, VARIABLE,

    // Mathematical operations
    PLUS, MINUS, MULTIPLICATION, DIVISION, POWER, SQRT,

    // Functions, grouping, and special tokens
    FUNCTION, LEFT_PAREN, RIGHT_PAREN, EOI, INVALID;

    // FUNCTION is used for trigonometric functions, such as sin, cos and tan.
    // The parenthesis types are there in order to make the input more organized

    public boolean isOperator(){
        return switch (this) {
            case PLUS, MINUS, MULTIPLICATION, DIVISION, POWER, SQRT -> true;
            default -> false;
        };
    }


    public int determinePriority(Token token){
        int precedence = 0;
        switch (token.type){
            case PLUS:
            case MINUS:
                precedence =1;
                break;
            case MULTIPLICATION:
            case DIVISION:
                precedence=2;
                break;
            case POWER:
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
