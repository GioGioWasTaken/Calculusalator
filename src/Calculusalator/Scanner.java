package Calculusalator;

import java.util.*;

import static Calculusalator.Token_type.*;

public class Scanner {
    private static final char NO_FORMER_CHARACTER = '\0';
    static int start =0; // start of the token
    static int current =0; // current position of the token
    private final List<Token> tokens = new ArrayList<>();
    private final String inputExpr;
    Scanner(String inputExpr) {
        this.inputExpr = inputExpr; // take the input expression
    }
    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            scanToken();
        }
        tokens.add(new Token(EOI, "")); // after all the tokens have been added, add the EOI (end of input) token.
        return tokens;
    }
    private boolean isAtEnd() { // helper function to scanTokens
        return current >= inputExpr.length();
    }
    private void scanToken(){
        char c = advance();
        switch (c){
            case '+': addToken(PLUS); break;
            case '-': addToken(MINUS);break;
            case '*': addToken(MULTIPLICATION);break;
            case '/': addToken(DIVISION);break;
            case '^': addToken(POWER);break;
            case '(': addToken(LEFT_PAREN);break;
            case ')': addToken(RIGHT_PAREN);break;

            default:
                if(isDigit(c)){
                number();
                break;
                }
                if (isVariable(c)){
                variable();
                break;
                }
                else {
                    addToken(INVALID);
                    // add code to report error to user
                    break;
                }
        }
    }

    private char advance() {
        return inputExpr.charAt(current++); // get current char and prepare the next one
    }


    private void addToken(Token_type tokenType) {
    String text = inputExpr.substring(start,current);
    tokens.add(new Token(tokenType, text));
    }


    private void addToken(Token_type tokenType, String text){ //overloaded methods to specify the value added.
        tokens.add(new Token(tokenType,text));
    }


    private void number() {
        // special logic to handle numbers, since they are not necessarily single-token-ed.
        while (isDigit(peek())) advance();
        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance();
            while (isDigit(peek())) advance();
        }
        addToken(NUMBER);
    }

    private void variable(){
        boolean case_1=false; // if 1 multiplication was already added...
        if(peekFormer()==NO_FORMER_CHARACTER){
            addToken(NUMBER,"1"); // x is equivelent to 1*x
            addToken(MULTIPLICATION,"*");
            case_1=true;
        }
        // in accordance with mathematical notation nx= n*x
        if(peekFormer()!='*' && !case_1) {
            addToken(MULTIPLICATION,"*");
        } // unless n*x was explicitly specified



        System.out.println(peekFormer());
        addToken(VARIABLE);
    }


    private char peek() {
        if (isAtEnd()) return '\0';
        return inputExpr.charAt(current); // if the current character is \n it will return it and the tokenizer will stop advancing
    }

    private char peekNext() {
        if (current + 1 >= inputExpr.length()) return '\0';
        return inputExpr.charAt(current + 1);
    }

    private char peekFormer(){
        if(current-1<=0) return '\0';
        return inputExpr.charAt(current-2); // for some reason this needs to be -2, and not -1?
    }


    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
    private boolean isVariable(char c){
        return c>='a' && c<='z' || c>='A' && c<='Z';
    }

    public static Queue<Token> ScanExpr(List<Token> tokens){ // first reorganize the input to RPN, to transform into AST.
        Stack<Token> operations = new Stack<>();
        Queue<Token> queue = new LinkedList<>();
        for(Token token :tokens){
            if (token.type==NUMBER) queue.add(token);
            else if(token.type==FUNCTION) operations.add(token);
            else if(token.type==VARIABLE) queue.add(token);
            else if(token.type.isOperator()){
                while(token.type.isLowerPriority(operations,token)){
                    System.out.println(token.type + " Has lower priority than " + operations.peek());
                    Token o_2=operations.pop(); // pop the higher precedence operator
                    queue.add(o_2); // and add it to the output queue
                }
                operations.push(token);

            }
            else if(token.type==LEFT_PAREN) operations.push(token);

//            else if(token.type==RIGHT_PAREN){
//
//            }

        }

        while(!operations.isEmpty()){
            queue.add(operations.pop());
        }
        return queue;
    }
}
