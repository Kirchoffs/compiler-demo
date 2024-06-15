package org.syh.demo.regexp.nfa;

public class NFATest {
    public static void testCharNFA() {
        NFA nfa = NFA.charNFA('x');

        assert nfa.test("x");
        assert !nfa.test("");
        assert !nfa.test("y");   

        System.out.println("testCharNFA passed");
    }

    public static void testEpsilonNFA() {
        NFA nfa = NFA.epsilonNFA();

        assert nfa.test("");
        assert !nfa.test("x");

        System.out.println("testEpsilonNFA passed");
    }

    public static void testConcatNFA() {
        NFA nfa = NFA.concatNFA(NFA.charNFA('x'), NFA.charNFA('y'), NFA.charNFA('z'));

        assert nfa.test("xyz");
        assert !nfa.test("");
        assert !nfa.test("xy");
        assert !nfa.test("x");
        assert !nfa.test("y");

        System.out.println("testConcatNFA passed");
    }

    public static void testAltNFA() {
        NFA nfa = NFA.altNFA(NFA.charNFA('x'), NFA.charNFA('y'), NFA.charNFA('z'));

        assert nfa.test("x");
        assert nfa.test("y");
        assert nfa.test("z");
        assert !nfa.test("");
        assert !nfa.test("xy");
        assert !nfa.test("yz");
        assert !nfa.test("xz");
        assert !nfa.test("xyz");

        System.out.println("testAltNFA passed");
    }

    public static void testStarNFA() {
        NFA nfa = NFA.starNFA(NFA.charNFA('x'));

        assert nfa.test("");
        assert nfa.test("x");
        assert nfa.test("xx");
        assert nfa.test("xxx");
        assert !nfa.test("y");
        assert !nfa.test("xy");
        assert !nfa.test("yx");

        System.out.println("testStarNFA passed");
    }

    public static void testNaivePlusNFA() {
        NFA nfa = NFA.naivePlusNFA(NFA.charNFA('x'));

        assert nfa.test("x");
        assert nfa.test("xx");
        assert nfa.test("xxx");
        assert !nfa.test("");
        assert !nfa.test("y");
        assert !nfa.test("xy");
        assert !nfa.test("yx");

        System.out.println("testNaivePlusNFA passed");
    }

    public static void testPlusNFA() {
        NFA nfa = NFA.plusNFA(NFA.charNFA('x'));

        assert nfa.test("x");
        assert nfa.test("xx");
        assert nfa.test("xxx");
        assert !nfa.test("");
        assert !nfa.test("y");
        assert !nfa.test("xy");
        assert !nfa.test("yx");

        System.out.println("testPlusNFA passed");
    }

    public static void testQuestionNFA() {
        NFA nfa = NFA.questionNFA(NFA.charNFA('x'));

        assert nfa.test("");
        assert nfa.test("x");
        assert !nfa.test("xx");
        assert !nfa.test("xxx");
        assert !nfa.test("y");
        assert !nfa.test("xy");
        assert !nfa.test("yx");

        System.out.println("testQuestionNFA passed");
    }

    public static void testComplexCaseAlphaNFA() {
        NFA nfa = NFA.altNFA(
            NFA.concatNFA(NFA.charNFA('a'), NFA.starNFA(NFA.charNFA('b')), NFA.charNFA('c')),
            NFA.concatNFA(NFA.charNFA('x'), NFA.altNFA(NFA.charNFA('p'), NFA.charNFA('q')), NFA.charNFA('z'))
        );

        assert nfa.test("ac");
        assert nfa.test("abc");
        assert nfa.test("abbc");
        assert nfa.test("abbbc");
        assert nfa.test("xpz");
        assert nfa.test("xqz");
        assert !nfa.test("");
        assert !nfa.test("a");
        assert !nfa.test("x");

        System.out.println("testComplexCaseAlphaNFA passed");
    }

    public static void testComplexCaseBetaNFA() {
        NFA nfa = NFA.concatNFA(
            NFA.altNFA(NFA.charNFA('a'), NFA.charNFA('b')),
            NFA.charNFA('c')
        );

        assert !nfa.test("a");
        assert !nfa.test("b");
        assert !nfa.test("c");
        assert nfa.test("ac");
        assert nfa.test("bc");

        System.out.println("testComplexCaseBetaNFA passed");
    }

    public static void testComplexCaseGammaNFA() {
        NFA nfa = NFA.concatNFA(
            NFA.starNFA(NFA.altNFA(NFA.charNFA('a'), NFA.charNFA('b'))),
            NFA.starNFA(NFA.altNFA(NFA.charNFA('c'), NFA.charNFA('d'))),
            NFA.questionNFA(NFA.charNFA('e')),
            NFA.plusNFA(NFA.altNFA(
                NFA.charNFA('f'), 
                NFA.concatNFA(NFA.charNFA('g'), NFA.charNFA('h'), NFA.starNFA(NFA.charNFA('i')))
            ))
        );

        assert !nfa.test("");
        assert !nfa.test("a");
        assert !nfa.test("b");
        assert !nfa.test("c");
        assert !nfa.test("d");
        assert !nfa.test("ac");
        assert !nfa.test("ace");
        assert nfa.test("acef");
        assert nfa.test("adeff");
        assert nfa.test("bcef");
        assert nfa.test("ef");
        assert nfa.test("egh");
        assert nfa.test("eghgh");
        assert nfa.test("aceff");
        assert nfa.test("aceghi");

        System.out.println("testComplexCaseGammaNFA passed");
    }


    public static void main(String[] args) {
        testCharNFA();
        testEpsilonNFA();
        testConcatNFA();
        testAltNFA();
        testStarNFA();
        testNaivePlusNFA();
        testPlusNFA();
        testQuestionNFA();
        testComplexCaseAlphaNFA();
        testComplexCaseBetaNFA();
        testComplexCaseGammaNFA();
    }
}
