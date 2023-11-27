package Calculusalator;

import java.util.*;

import static Calculusalator.Token_type.*;
public class Lexer {
    public void outputDerivative(String inputExpr){
        Derivative derivative = new Derivative();
        Lexer lexer = new Lexer(inputExpr);
        List<Token> tokens= lexer.scanTokens();
        Queue<Token> RPN= ScanExprtoRPN(tokens);
        ASTNode AST = lexer.scanRPNtoAST(RPN);
        ASTNode derivedAST = derivative.findDerivedAST(AST);
        System.out.println("AST: ");
        RPNtoAST.printTree(AST," ");
        System.out.println("Derivative of expression: ");
        RPNtoAST.printTree(derivedAST, " ");

        System.out.println("Infix notation simplifcation: ");
        System.out.println(Simplify.simplify(derivedAST));

    }

    public void outputIntegral(String inputExpr) {
        Lexer lexer = new Lexer(inputExpr);
        List<Token> tokens= lexer.scanTokens();
        Queue<Token> RPN= ScanExprtoRPN(tokens);
        ASTNode AST = lexer.scanRPNtoAST(RPN);
        System.out.println("infix expression: " + tokens);
        System.out.println("Expression in RPN: " + RPN);
        System.out.println("AST: ");
        RPNtoAST.printTree(AST," ");

    }

    private static final char NO_FORMER_CHARACTER = '\0';
    static int start =0; // start of the token
    static int current =0; // current position of the token
    private final List<Token> tokens = new ArrayList<>();
    private final String inputExpr;
    Lexer(String inputExpr) {
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
                }
                else {
                    addToken(INVALID);
                    // add code to report error to user
                }
                break;
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
        // in accordance with mathematical notation nx= n*x
        if(peekFormer()!='*' && peekFormer()!=NO_FORMER_CHARACTER) {
            addToken(MULTIPLICATION,"*");
        } // unless n*x was explicitly specified



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



    public static Queue<Token> ScanExprtoRPN(List<Token> tokens){ // to print the expr in RPN
        // we can directly convert infix to an AST, but for comfort purposes I also want access to RPN
        Stack<Token> operations = new Stack<>();
        Queue<Token> queue = new LinkedList<>();
        for(Token token :tokens){
            if (token.type==NUMBER) queue.add(token);
            else if(token.type==FUNCTION) operations.add(token);
            else if(token.type==VARIABLE) queue.add(token);
            else if(token.type.isOperator()){
                while(token.type.isLowerPriority(operations,token)){
                    //System.out.println(token.type + " Has lower priority than " + operations.peek());
                    Token o_2=operations.pop(); // pop the higher precedence operator
                    queue.add(o_2); // and add it to the output queue
                }
                operations.push(token);

            }
            else if(token.type==LEFT_PAREN) operations.push(token);

            else if(token.type==RIGHT_PAREN){
            while(operations.peek().type!=LEFT_PAREN){
                if(operations.isEmpty()){
                    throw new RuntimeException("Unterminated parenthesis encountered.");
                }
                queue.add(operations.pop());
            }
            if(operations.peek().type==LEFT_PAREN) operations.pop();

            }

        }

        while(!operations.isEmpty()){
            if(operations.peek().type==LEFT_PAREN || operations.peek().type==RIGHT_PAREN){
                throw new RuntimeException("Unterminated parenthesis encountered.");
            }
            queue.add(operations.pop());
        }

        return queue;
    }

    public ASTNode scanRPNtoAST(Queue<Token> tokens){ // we can easily obtain an AST by reverse traversing the output queue.
        return RPNtoAST.buildASTFromRPN(tokens);
    }



}
