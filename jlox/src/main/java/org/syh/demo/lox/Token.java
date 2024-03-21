package org.syh.demo.lox;

public class Token {
    public final TokenType type;
    public final String lexeme;
    public final Object literal;
    public final int line;

    // Difference between lexeme and literal:
    // - lexeme: the string representation of the token
    // - literal: the value of the token, if it's a literal (string or number), otherwise null
    public Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    public String toString() {
        return "(" + type + " " + lexeme + " " + literal + ")";
    }
}
