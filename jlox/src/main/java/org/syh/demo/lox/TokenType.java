package org.syh.demo.lox;

public enum TokenType {
    // Single-character tokens.
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, SEMICOLON,
    PLUS, MINUS, STAR, SLASH,

    // One or two character tokens.
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    // Literals.
    IDENTIFIER, STRING, NUMBER,

    // Keywords.
    TRUE, FALSE,
    NIL,
    AND, OR,
    IF, ELSE, 
    FOR, WHILE,
    CLASS, SUPER, THIS,
    FUN, RETURN, VAR,
    PRINT,

    EOF
}
