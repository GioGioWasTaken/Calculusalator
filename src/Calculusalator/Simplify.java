package Calculusalator;

import java.util.HashMap;
import java.util.Stack;

public class Simplify {

        public static String simplify(ASTNode AST) {
            if (AST == null) {
                return ""; // Return an empty string for null nodes
            }

            Token type = AST.value;
            Stack<String> tokens = new Stack<>();
            switch (type.type) {
                case VARIABLE:
                case NUMBER:
                    tokens.push(type.lexeme);
                    break;
                case MINUS:
                case PLUS:
                case MULTIPLICATION:
                case DIVISION:
                case POWER:
                    if (AST.left != null && AST.right != null) {
                        String second = simplify(AST.right);
                        String first = simplify(AST.left);
                        String res = performOperation(first, second, type.type);
                        tokens.push(res); // add the result to the stack
                    }
                    break;
            }
            return tokens.isEmpty() ? "" : tokens.pop();
        }



    private static String performOperation(String first, String second, Token_type operator) {
            // some simplification logic
            // upon some research, I found out that the way things are simplified in actual Computer Algebra Systems (CAS)
            // is actually a relatively complex and non-trivial process, so for now I just have these rules.
        if ( operator == Token_type.PLUS && second.equals("0") || operator == Token_type.MINUS && second.equals("0") )  {
            return first;
        }
        if(operator==Token_type.PLUS && first.equals("0") || first.isEmpty()){
            return second;
        }
        if(operator==Token_type.PLUS && second.isEmpty()){
            return first;
        }

        if (operator == Token_type.MULTIPLICATION && (first.equals("0") || second.equals("0") || second.isEmpty())) {
            return "";
        }

        if(operator==Token_type.POWER && second.equals("1")){
            return first;
        }


        // For other operators or non-numeric terms, return the expression as is
        return first + map(operator) + second;
    }

    private static String map(Token_type operator){
        HashMap<Token_type, String> map = new HashMap<>();
        map.put(Token_type.PLUS,"+");
        map.put(Token_type.MINUS,"-");
        map.put(Token_type.MULTIPLICATION,"*");
        map.put(Token_type.DIVISION,"/");
        map.put(Token_type.POWER,"^");

        return map.get(operator);
    }
}
