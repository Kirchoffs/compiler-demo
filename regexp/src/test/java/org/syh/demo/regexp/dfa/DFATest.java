package org.syh.demo.regexp.dfa;

import org.syh.demo.regexp.nfa.NFA;

public class DFATest {
    public static void testCharDFA() {
        NFA nfa = NFA.charNFA('x');
        DFA dfa = new DFA(nfa);

        assert dfa.test("x");
        assert !dfa.test("");
        assert !dfa.test("y");

        System.out.println("testCharDFA passed");
    }

    public static void testEpsilonDFA() {
        NFA nfa = NFA.epsilonNFA();
        DFA dfa = new DFA(nfa);

        assert dfa.test("");
        assert !dfa.test("x");

        System.out.println("testEpsilonDFA passed");
    }

    public static void testConcatDFA() {
        NFA nfa = NFA.concatNFA(NFA.charNFA('x'), NFA.charNFA('y'), NFA.charNFA('z'));
        DFA dfa = new DFA(nfa);

        assert dfa.test("xyz");
        assert !dfa.test("");
        assert !dfa.test("xy");
        assert !dfa.test("x");
        assert !dfa.test("y");

        System.out.println("testConcatDFA passed");
    }

    public static void testAltDFA() {
        NFA nfa = NFA.altNFA(NFA.charNFA('x'), NFA.charNFA('y'), NFA.charNFA('z'));
        DFA dfa = new DFA(nfa);

        assert dfa.test("x");
        assert dfa.test("y");
        assert dfa.test("z");
        assert !dfa.test("");
        assert !dfa.test("xy");
        assert !dfa.test("yz");
        assert !dfa.test("xz");
        assert !dfa.test("xyz");
        System.out.println("testAltDFA passed");
    }

    public static void testStarDFA() {
        NFA nfa = NFA.starNFA(NFA.charNFA('x'));
        DFA dfa = new DFA(nfa);

        assert dfa.test("");
        assert dfa.test("x");
        assert dfa.test("xx");
        assert dfa.test("xxx");
        assert !dfa.test("y");
        assert !dfa.test("xy");
        assert !dfa.test("yx");

        System.out.println("testStarDFA passed");
    }

    public static void testNaivePlusDFA() {
        NFA nfa = NFA.naivePlusNFA(NFA.charNFA('x'));
        DFA dfa = new DFA(nfa);

        assert dfa.test("x");
        assert dfa.test("xx");
        assert dfa.test("xxx");
        assert !dfa.test("");
        assert !dfa.test("y");
        assert !dfa.test("xy");
        assert !dfa.test("yx");

        System.out.println("testNaivePlusDFA passed");
    }

    public static void testPlusDFA() {
        NFA nfa = NFA.plusNFA(NFA.charNFA('x'));
        DFA dfa = new DFA(nfa);

        assert dfa.test("x");
        assert dfa.test("xx");
        assert dfa.test("xxx");
        assert !dfa.test("");
        assert !dfa.test("y");
        assert !dfa.test("xy");
        assert !dfa.test("yx");
        System.out.println("testPlusDFA passed");
    }

    public static void testQuestionDFA() {
        NFA nfa = NFA.questionNFA(NFA.charNFA('x'));
        DFA dfa = new DFA(nfa);

        assert dfa.test("");
        assert dfa.test("x");
        assert !dfa.test("xx");
        assert !dfa.test("xxx");
        assert !dfa.test("y");
        assert !dfa.test("xy");
        assert !dfa.test("yx");

        System.out.println("testQuestionDFA passed");
    }

    public static void testComplexCaseDFA() {
        NFA nfa = NFA.altNFA(
            NFA.concatNFA(NFA.charNFA('a'), NFA.starNFA(NFA.charNFA('b')), NFA.charNFA('c')),
            NFA.concatNFA(NFA.charNFA('x'), NFA.altNFA(NFA.charNFA('p'), NFA.charNFA('q')), NFA.charNFA('z'))
        );
        DFA dfa = new DFA(nfa);

        assert dfa.test("ac");
        assert dfa.test("abc");
        assert dfa.test("abbc");
        assert dfa.test("abbbc");
        assert dfa.test("xpz");
        assert dfa.test("xqz");
        assert !dfa.test("");
        assert !dfa.test("a");
        assert !dfa.test("x");
    }

    public static void main(String[] args) {
        testCharDFA();
        testEpsilonDFA();
        testConcatDFA();
        testAltDFA();
        testStarDFA();
        testNaivePlusDFA();
        testPlusDFA();
        testQuestionDFA();
        testComplexCaseDFA();
    }
}
