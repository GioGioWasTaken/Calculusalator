package Calculusalator;

public enum Token_type {
    // Values
    NUMBER, VARIABLE,

    // Mathematical operations
    PLUS, MINUS, MULTIPLICATION, DIVISION, POWER, SQRT,

    // Functions, grouping, and special tokens
    FUNCTION, LEFT_PAREN, RIGHT_PAREN, EOI, INVALID

    // FUNCTION is used for trigonometric functions, such as sin, cos and tan.
    // The parens are there in order to make the input more organized

}
