package Calculusalator;
class Token {
    final Token_type type;
    final String lexeme;

    // might add a literal value parameter later, depending on how I implement the derivation rules.
    Token(Token_type type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }
    public String toString() {
        return type + " " + lexeme + " ";
    }
}
