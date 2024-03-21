package org.syh.demo.lox;

public class ScannerTest {
    private static void test() {
        Scanner scanner = new Scanner();
        
        System.out.println(scanner.scanTokens("var a = 1;"));
        System.out.println(scanner.scanTokens("var a = 1; var b = 2;"));
        System.out.println(scanner.scanTokens("var name = \"syh\";"));
    }

    public static void main(String[] args) {
        test();
    }
}
