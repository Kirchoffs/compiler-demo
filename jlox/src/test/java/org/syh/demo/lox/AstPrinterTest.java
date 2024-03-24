package org.syh.demo.lox;

public class AstPrinterTest {
    private static void test() {
        Expr expression = new Expr.Binary(
            new Expr.Unary(
                new Token(TokenType.MINUS, "-", null, 1),
                new Expr.Literal(123)
            ),
            new Token(TokenType.STAR, "*", null, 1),
            new Expr.Grouping(
                new Expr.Literal(45.67)
            )
        );

        System.out.println(new AstPrinter().print(expression));
    }

    public static void main(String[] args) {
        test();
    }
}
