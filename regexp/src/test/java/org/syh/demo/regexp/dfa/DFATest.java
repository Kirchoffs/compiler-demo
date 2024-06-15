package org.syh.demo.regexp.dfa;

import org.syh.demo.regexp.nfa.NFA;

public class DFATest {
    public static void testCharOriginalDFA() {
        testCharDFA(false);
    }

    public static void testCharMinimizedDFA() {
        testCharDFA(true);
    }

    public static void testCharDFA(boolean isMinimized) {
        NFA nfa = NFA.charNFA('x');
        DFA dfa = new DFA(nfa);
        if (isMinimized) {
            dfa.minimizeDFA();
        }

        assert dfa.test("x");
        assert !dfa.test("");
        assert !dfa.test("y");

        System.out.println(String.format("testCharDFA %s passed", isMinimized ? "Minimized" : "Original"));
    }

    public static void testEpsilonOriginalDFA() {
        testEpsilonDFA(false);
    }

    public static void testEpsilonMinimizedDFA() {
        testEpsilonDFA(true);
    }

    public static void testEpsilonDFA(boolean isMinimized) {
        NFA nfa = NFA.epsilonNFA();
        DFA dfa = new DFA(nfa);
        if (isMinimized) {
            dfa.minimizeDFA();
        }

        assert dfa.test("");
        assert !dfa.test("x");

        System.out.println(String.format("testEpsilonDFA %s passed", isMinimized ? "Minimized" : "Original"));
    }

    public static void testConcatOriginalDFA() {
        testConcatDFA(false);
    }

    public static void testConcatMinimizedDFA() {
        testConcatDFA(true);
    }

    public static void testConcatDFA(boolean isMinimized) {
        NFA nfa = NFA.concatNFA(NFA.charNFA('x'), NFA.charNFA('y'), NFA.charNFA('z'));
        DFA dfa = new DFA(nfa);
        if (isMinimized) {
            dfa.minimizeDFA();
        }

        assert dfa.test("xyz");
        assert !dfa.test("");
        assert !dfa.test("xy");
        assert !dfa.test("x");
        assert !dfa.test("y");

        System.out.println(String.format("testConcatDFA %s passed", isMinimized ? "Minimized" : "Original"));
    }

    public static void testAltOriginalDFA() {
        testAltDFA(false);
    }

    public static void testAltMinimizedDFA() {
        testAltDFA(true);
    }

    public static void testAltDFA(boolean isMinimized) {
        NFA nfa = NFA.altNFA(NFA.charNFA('x'), NFA.charNFA('y'), NFA.charNFA('z'));
        DFA dfa = new DFA(nfa);
        if (isMinimized) {
            dfa.minimizeDFA();
        }

        assert dfa.test("x");
        assert dfa.test("y");
        assert dfa.test("z");
        assert !dfa.test("");
        assert !dfa.test("xy");
        assert !dfa.test("yz");
        assert !dfa.test("xz");
        assert !dfa.test("xyz");

        System.out.println(String.format("testAltDFA %s passed", isMinimized ? "Minimized" : "Original"));
    }

    public static void testStarOriginalDFA() {
        testStarDFA(false);
    }

    public static void testStarMinimizedDFA() {
        testStarDFA(true);
    }

    public static void testStarDFA(boolean isMinimized) {
        NFA nfa = NFA.starNFA(NFA.charNFA('x'));
        DFA dfa = new DFA(nfa);
        if (isMinimized) {
            dfa.minimizeDFA();
        }

        assert dfa.test("");
        assert dfa.test("x");
        assert dfa.test("xx");
        assert dfa.test("xxx");
        assert !dfa.test("y");
        assert !dfa.test("xy");
        assert !dfa.test("yx");

        System.out.println(String.format("testStarDFA %s passed", isMinimized ? "Minimized" : "Original"));
    }

    public static void testNaivePlusOriginalDFA() {
        testNaivePlusDFA(false);
    }

    public static void testNaivePlusMinimizedDFA() {
        testNaivePlusDFA(true);
    }

    public static void testNaivePlusDFA(boolean isMinimized) {
        NFA nfa = NFA.naivePlusNFA(NFA.charNFA('x'));
        DFA dfa = new DFA(nfa);
        if (isMinimized) {
            dfa.minimizeDFA();
        }

        assert dfa.test("x");
        assert dfa.test("xx");
        assert dfa.test("xxx");
        assert !dfa.test("");
        assert !dfa.test("y");
        assert !dfa.test("xy");
        assert !dfa.test("yx");

        System.out.println(String.format("testNaivePlusDFA %s passed", isMinimized ? "Minimized" : "Original"));
    }

    public static void testPlusOriginalDFA() {
        testPlusDFA(false);
    }

    public static void testPlusMinimizedDFA() {
        testPlusDFA(true);
    }

    public static void testPlusDFA(boolean isMinimized) {
        NFA nfa = NFA.plusNFA(NFA.charNFA('x'));
        DFA dfa = new DFA(nfa);
        if (isMinimized) {
            dfa.minimizeDFA();
        }

        assert dfa.test("x");
        assert dfa.test("xx");
        assert dfa.test("xxx");
        assert !dfa.test("");
        assert !dfa.test("y");
        assert !dfa.test("xy");
        assert !dfa.test("yx");

        System.out.println(String.format("testPlusDFA %s passed", isMinimized ? "Minimized" : "Original"));
    }

    public static void testQuestionOriginalDFA() {
        testQuestionDFA(false);
    }

    public static void testQuestionMinimizedDFA() {
        testQuestionDFA(true);
    }

    public static void testQuestionDFA(boolean isMinimized) {
        NFA nfa = NFA.questionNFA(NFA.charNFA('x'));
        DFA dfa = new DFA(nfa);
        if (isMinimized) {
            dfa.minimizeDFA();
        }

        assert dfa.test("");
        assert dfa.test("x");
        assert !dfa.test("xx");
        assert !dfa.test("xxx");
        assert !dfa.test("y");
        assert !dfa.test("xy");
        assert !dfa.test("yx");

        System.out.println(String.format("testQuestionDFA %s passed", isMinimized ? "Minimized" : "Original"));
    }

    public static void testComplexCaseAlphaOriginalDFA() {
        testComplexCaseAlphaDFA(false);
    }

    public static void testComplexCaseAlphaMinimizedDFA() {
        testComplexCaseAlphaDFA(true);
    }

    public static void testComplexCaseAlphaDFA(boolean isMinimized) {
        NFA nfa = NFA.altNFA(
            NFA.concatNFA(NFA.charNFA('a'), NFA.starNFA(NFA.charNFA('b')), NFA.charNFA('c')),
            NFA.concatNFA(NFA.charNFA('x'), NFA.altNFA(NFA.charNFA('p'), NFA.charNFA('q')), NFA.charNFA('z'))
        );
        DFA dfa = new DFA(nfa);
        if (isMinimized) {
            dfa.minimizeDFA();
        }

        assert dfa.test("ac");
        assert dfa.test("abc");
        assert dfa.test("abbc");
        assert dfa.test("abbbc");
        assert dfa.test("xpz");
        assert dfa.test("xqz");
        assert !dfa.test("");
        assert !dfa.test("a");
        assert !dfa.test("x");

        System.out.println(String.format("testComplexCaseAlphaDFA %s passed", isMinimized ? "Minimized" : "Original"));
    }

    public static void testComplexCaseBetaOriginalDFA() {
        testComplexCaseBetaDFA(false);
    }

    public static void testComplexCaseBetaMinimizedDFA() {
        testComplexCaseBetaDFA(true);
    }

    public static void testComplexCaseBetaDFA(boolean isMinimized) {
        NFA nfa = NFA.concatNFA(
            NFA.starNFA(NFA.altNFA(NFA.charNFA('a'), NFA.charNFA('b'))),
            NFA.starNFA(NFA.altNFA(NFA.charNFA('c'), NFA.charNFA('d'))),
            NFA.questionNFA(NFA.charNFA('e')),
            NFA.plusNFA(NFA.altNFA(
                NFA.charNFA('f'), 
                NFA.concatNFA(NFA.charNFA('g'), NFA.charNFA('h'), NFA.starNFA(NFA.charNFA('i')))
            ))
        );
        DFA dfa = new DFA(nfa);
        if (isMinimized) {
            dfa.minimizeDFA();
        }

        assert !dfa.test("");
        assert !dfa.test("a");
        assert !dfa.test("b");
        assert !dfa.test("c");
        assert !dfa.test("d");
        assert !dfa.test("ac");
        assert !dfa.test("g");
        assert !dfa.test("h");
        assert !dfa.test("i");
        assert dfa.test("gh");
        assert dfa.test("gh");
        assert dfa.test("ghi");
        assert dfa.test("ghii");
        assert dfa.test("ghiii");
        assert dfa.test("ghiiighiii");
        assert !dfa.test("ghiiighiij");
        assert dfa.test("acef");
        assert dfa.test("acef");
        assert dfa.test("adeff");
        assert dfa.test("bcef");
        assert dfa.test("ef");
        assert dfa.test("egh");
        assert dfa.test("eghgh");
        assert dfa.test("aceff");
        assert dfa.test("aceghi");

        System.out.println(String.format("testComplexCaseBetaDFA %s passed", isMinimized ? "Minimized" : "Original"));
    }

    public static void main(String[] args) {
        testCharOriginalDFA();
        testCharMinimizedDFA();
        testEpsilonOriginalDFA();
        testEpsilonMinimizedDFA();
        testConcatOriginalDFA();
        testConcatMinimizedDFA();
        testAltOriginalDFA();
        testAltMinimizedDFA();
        testStarOriginalDFA();
        testStarMinimizedDFA();
        testNaivePlusOriginalDFA();
        testNaivePlusMinimizedDFA();
        testPlusOriginalDFA();
        testPlusMinimizedDFA();
        testQuestionOriginalDFA();
        testQuestionMinimizedDFA();
        testComplexCaseAlphaOriginalDFA();
        testComplexCaseAlphaMinimizedDFA();
        testComplexCaseBetaOriginalDFA();
        testComplexCaseBetaMinimizedDFA();
    }
}
