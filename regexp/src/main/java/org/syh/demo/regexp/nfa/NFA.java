package org.syh.demo.regexp.nfa;

import org.syh.demo.regexp.State;
import static org.syh.demo.regexp.nfa.Constants.EPSILON;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NFA {
    private State inState;
    public State outState;

    public NFA(State inState, State outState) {
        this.inState = inState;
        this.outState = outState;
    }

    public boolean test(String text) {
        return this.inState.test(text);
    }

    public static NFA charNFA(char ch) {
        State inState = new State(false);
        State outState = new State(true);
        inState.addTransitionForAction(ch, outState);
        return new NFA(inState, outState);
    }

    public static NFA epsilonNFA() {
        return charNFA(EPSILON);
    }

    public static NFA concatNFA(NFA firstNFA, NFA... additionalNFAs) {
        for (NFA additionalNFA : additionalNFAs) {
            firstNFA = concatPairNFA(firstNFA, additionalNFA);
        }
        return firstNFA;
    }

    private static NFA concatPairNFA(NFA firstNFA, NFA secondNFA) {
        firstNFA.outState.setAcceptingState(false);
        firstNFA.outState.addTransitionForAction(EPSILON, secondNFA.inState);
        return new NFA(firstNFA.inState, secondNFA.outState);
    }

    public static NFA altNFA(NFA firstNFA, NFA... additionalNFAs) {
        for (NFA additionalNFA : additionalNFAs) {
            firstNFA = altPairNFA(firstNFA, additionalNFA);
        }
        return firstNFA;
    }

    private static NFA altPairNFA(NFA firstNFA, NFA secondNFA) {
        State inState = new State(false);
        State outState = new State(true);

        inState.addTransitionForAction(EPSILON, firstNFA.inState);
        inState.addTransitionForAction(EPSILON, secondNFA.inState);
        firstNFA.outState.addTransitionForAction(EPSILON, outState);
        secondNFA.outState.addTransitionForAction(EPSILON, outState);

        return new NFA(inState, outState);
    }

    public static NFA naiveStarNFA(NFA nfa) {
        State inState = new State(false);
        State outState = new State(true);

        inState.addTransitionForAction(EPSILON, nfa.inState);
        inState.addTransitionForAction(EPSILON, outState);
        nfa.outState.addTransitionForAction(EPSILON, nfa.inState);
        nfa.outState.addTransitionForAction(EPSILON, outState);

        return new NFA(inState, outState);
    }

    public static NFA starNFA(NFA nfa) {
        nfa.inState.addTransitionForAction(EPSILON, nfa.outState);
        nfa.outState.addTransitionForAction(EPSILON, nfa.inState);
        return nfa;
    }

    public static NFA naivePlusNFA(NFA nfa) {
        return concatPairNFA(copy(nfa), starNFA(nfa));
    }

    public static NFA plusNFA(NFA nfa) {
        nfa.outState.addTransitionForAction(EPSILON, nfa.inState);
        return nfa;
    }

    public static NFA naiveQuestionNFA(NFA nfa) {
        return altPairNFA(epsilonNFA(), nfa);
    }

    public static NFA questionNFA(NFA nfa) {
        nfa.inState.addTransitionForAction(EPSILON, nfa.outState);
        return nfa;
    }

    public static NFA copy(NFA nfa) {
        Map<State, State> mapping = new HashMap<>();
        copy(nfa.inState, mapping);
        return new NFA(mapping.get(nfa.inState), mapping.get(nfa.outState));
    }

    private static State copy(State state, Map<State, State> mapping) {
        if (mapping.containsKey(state)) {
            return mapping.get(state);
        }

        State newState = new State(state.isAcceptingState());
        for (Map.Entry<Character, Set<State>> entry : state.getTransitions().entrySet()) {
            for (State nextState : entry.getValue()) {
                newState.addTransitionForAction(entry.getKey(), copy(nextState, mapping));
            }
        }

        mapping.put(state, newState);
        return newState;
    }
}
