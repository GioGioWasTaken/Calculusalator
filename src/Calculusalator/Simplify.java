package Calculusalator;
// this module takes in a mathematical expression transformed to an AST, and returns a string of that expression simplified.

import java.util.Stack;

public class Simplify {
    public static String simplify(ASTNode AST){
        Token type = AST.value;
        Stack<String> tokens = new Stack<>();
        String res;
        switch (type.type){
            case VARIABLE:
            case NUMBER:
                String val =type.lexeme;
                tokens.push(val);
                break;
            case MINUS:
            case PLUS:
            case MULTIPLICATION:
            case DIVISION:
                try {
                    String second = tokens.pop();
                    String first = tokens.pop();

                    int secondInt = Integer.parseInt(second);
                    int firstInt = Integer.parseInt(first);
                    Simplify.performOperation(firstInt,secondInt, operator);
                }

        }
    }
    private int performOperation(int firstInt, int secondInt, Token operator){
        switch (operator) {
            case MINUS:
                return firstInt - secondInt;
            case PLUS:
                return firstInt + secondInt;
            case MULTIPLICATION:
                return firstInt * secondInt;
            case DIVISION:
                // Handle division by zero error if needed
                return firstInt / secondInt;
            default:
                // Handle unexpected operator
                return 0; // Or throw an exception, depending on your design
        }

    }

    private String performOperation()
        // overloaded method for variables. Will implement later.
}
