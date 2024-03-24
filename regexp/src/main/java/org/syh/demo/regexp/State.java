package org.syh.demo.regexp;

import static org.syh.demo.regexp.nfa.Constants.EPSILON;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class State {
    private boolean accepting;
    private Map<Character, Set<State>> transitions;
    private Map<Character, Set<State>> unmodifiableTransitions;

    public int stateId;

    public State(boolean accepting) {
        this.accepting = accepting;
        this.transitions = new HashMap<>();
        this.unmodifiableTransitions = new HashMap<>();
        this.stateId = -1;
    }

    public void addTransitionForAction(char action, State state) {
        transitions.computeIfAbsent(action, param -> new HashSet<>()).add(state);
        if (!unmodifiableTransitions.containsKey(action)) {
            unmodifiableTransitions.put(action, Collections.unmodifiableSet(transitions.get(action)));
        }
    }

    public Set<State> getTransistionForAction(char action) {
        return unmodifiableTransitions.getOrDefault(action, Collections.unmodifiableSet(new HashSet<>()));
    }

    public Map<Character, Set<State>> getTransitions() {
        return unmodifiableTransitions;
    }

    public boolean isAcceptingState() {
        return accepting;
    }

    public void setAcceptingState(boolean accepting) {
        this.accepting = accepting;
    }

    public boolean test(String text) {
        return test(text, 0, new HashMap<>());
    }

    private boolean test(String text, int index, Map<Integer, Set<State>> visited) {
        if (visited.computeIfAbsent(index, param -> new HashSet<>()).contains(this)) {
            return false;
        }
        visited.get(index).add(this);

        if (index == text.length()) {
            if (accepting) {
                return true;
            }
        } else {
            char action = text.charAt(index);
            for (State state : getTransistionForAction(action)) {
                if (state.test(text, index + 1, visited)) {
                    return true;
                }
            }
        }

        for (State state : getTransistionForAction(EPSILON)) {
            if (state.test(text, index, visited)) {
                return true;
            }
        }

        return false;
    }
}
