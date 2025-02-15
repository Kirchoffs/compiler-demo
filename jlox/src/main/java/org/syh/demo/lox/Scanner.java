package org.syh.demo.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.syh.demo.lox.TokenType.STRING;
import static org.syh.demo.lox.TokenType.IDENTIFIER;
import static org.syh.demo.lox.TokenType.NUMBER;

public class Scanner {
    private String source;
    private List<Token> tokens;
    private int start;
    private int current;
    private int line;

    private static final Map<String, TokenType> Keywords;
    static {
        Keywords = new HashMap<>();
        Keywords.put("and",    TokenType.AND);
        Keywords.put("class",  TokenType.CLASS);
        Keywords.put("else",   TokenType.ELSE);
        Keywords.put("false",  TokenType.FALSE);
        Keywords.put("for",    TokenType.FOR);
        Keywords.put("fun",    TokenType.FUN);
        Keywords.put("if",     TokenType.IF);
        Keywords.put("nil",    TokenType.NIL);
        Keywords.put("or",     TokenType.OR);
        Keywords.put("print",  TokenType.PRINT);
        Keywords.put("return", TokenType.RETURN);
        Keywords.put("super",  TokenType.SUPER);
        Keywords.put("this",   TokenType.THIS);
        Keywords.put("true",   TokenType.TRUE);
        Keywords.put("var",    TokenType.VAR);
        Keywords.put("while",  TokenType.WHILE);
    }

    public Scanner(String source) {
        reset(source);
    }

    public Scanner() {
        reset(null);
        this.tokens = new ArrayList<>();
    }

    public List<Token> scanTokens() {
        if (source == null || source.isEmpty()) {
            Lox.error(line, "no source code to scan.");
            return tokens;
        }

        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    public List<Token> scanTokens(String source) {
        reset(source);
        return scanTokens();
    }

    private void reset(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
        this.start = 0;
        this.current = 0;
        this.line = 1;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        char ch = advance();

        switch(ch) {
            case '(': addToken(TokenType.LEFT_PAREN); break;
            case ')': addToken(TokenType.RIGHT_PAREN); break;
            case '{': addToken(TokenType.LEFT_BRACE); break;
            case '}': addToken(TokenType.RIGHT_BRACE); break;
            case ',': addToken(TokenType.COMMA); break;
            case '.': addToken(TokenType.DOT); break;
            case '-': addToken(TokenType.MINUS); break;
            case '+': addToken(TokenType.PLUS); break;
            case ';': addToken(TokenType.SEMICOLON); break;
            case '*': addToken(TokenType.STAR); break;
            case '!': addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG); break;
            case '=': addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL); break;
            case '<': addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS); break;
            case '>': addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER); break;
            case '/':
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else if (match('*')) {
                    blockComment();
                } else {
                    addToken(TokenType.SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                line++;
                break;
            case '"':
                string();
                break;
            default:
                if (isDigit(ch)) {
                    number();
                } else if (isAlpha(ch)) {
                    identifier();
                } else {
                    Lox.error(line, "unexpected character.");
                }
                break;
        }
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        if (isAtEnd()) {
            Lox.error(line, "unterminated string.");
            return;
        }

        advance();

        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    private void number() {
        while (isDigit(peek())) advance();

        if (peek() == '.' && isDigit(peekNext())) {
            advance();

            while (isDigit(peek())) advance();
        }

        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();

        String text = source.substring(start, current);
        TokenType type = Keywords.getOrDefault(text, IDENTIFIER);
        addToken(type);
    }

    private void blockComment() {
        int nesting = 1;

        while (nesting > 0) {
            if (isAtEnd()) {
                Lox.error(line, "unterminated block comment.");
                return;
            }

            if (peek() == '\n') line++;

            if (peek() == '/' && peekNext() == '*') {
                advance();
                advance();
                nesting++;
            } else if (peek() == '*' && peekNext() == '/') {
                advance();
                advance();
                nesting--;
            } else {
                advance();
            }
        }
    }

    private char advance() {
        if (isAtEnd()) return '\0';
        return source.charAt(current++);
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private boolean isAlpha(char ch) {
        return (ch >= 'a' && ch <= 'z') ||
               (ch >= 'A' && ch <= 'Z') ||
               (ch == '_');
    }

    private boolean isAlphaNumeric(char ch) {
        return isAlpha(ch) || isDigit(ch);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
