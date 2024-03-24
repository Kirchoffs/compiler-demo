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

    public static void testComplexCaseNFA() {
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

        System.out.println("testComplexCaseNFA passed");
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
        testComplexCaseNFA();
    }
}
