package org.syh.demo.regexp.nfa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.syh.demo.regexp.FA;
import org.syh.demo.regexp.State;
import static org.syh.demo.regexp.nfa.Constants.EPSILON;

public class NFA implements FA {
    private State inState;
    private State outState;
    
    public int startStateId;
    public Set<Integer> acceptingStateIds;
    public Set<Character> alphabet;
    public Map<Integer, Map<Character, List<Integer>>> transitions;

    public NFA(State inState, State outState) {
        this.inState = inState;
        this.outState = outState;
    }

    public boolean test(String text) {
        return this.inState.test(text);
    }

    public void buildNFAGrammarTuple() {
        startStateId = 0;
        alphabet = new HashSet<>();
        acceptingStateIds = new HashSet<>();
        transitions = new HashMap<>();
        
        Set<State> visited = new HashSet<>();
        dfs(inState, visited);
    }

    private void dfs(State state, Set<State> visited) {
        if (visited.contains(state)) {
            return;
        }
        
        int stateId = visited.size();
        state.stateId = stateId;
        visited.add(state);

        if (state.isAcceptingState()) {
            acceptingStateIds.add(stateId);
        }

        transitions.put(stateId, new HashMap<>());
        for (Map.Entry<Character, Set<State>> transition : state.getTransitions().entrySet()) {
            alphabet.add(transition.getKey());
            List<Integer> nextStateIds = new ArrayList<>();
            for (State nextState : transition.getValue()) {
                dfs(nextState, visited);
                nextStateIds.add(nextState.stateId);
            }
            Collections.sort(nextStateIds);
            transitions.get(stateId).put(transition.getKey(), nextStateIds);
        }
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
