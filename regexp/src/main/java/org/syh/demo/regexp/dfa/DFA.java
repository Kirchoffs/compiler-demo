package org.syh.demo.regexp.dfa;

import static org.syh.demo.regexp.nfa.Constants.EPSILON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.syh.demo.regexp.FA;
import org.syh.demo.regexp.nfa.NFA;

public class DFA implements FA {
    private int startStateId;
    private Set<Integer> acceptingStateIds;
    private Set<Character> alphabet;
    private Map<Integer, Map<Character, Integer>> transitions;

    public DFA(NFA nfa) {
        buildDFA(nfa);
    }

    private void buildDFA(NFA nfa) {
        nfa.buildNFAGrammarTuple();

        int startStateId = nfa.startStateId;
        Set<Integer> acceptingStateIdsSet = nfa.acceptingStateIds;
        Set<Character> alphabet = nfa.alphabet;
        Map<Integer, Map<Character, List<Integer>>> transitions = nfa.transitions;

        Set<Integer> startStateIdSet = getEpsilonClosure(startStateId, transitions);
        List<Integer> startStateIdList = fromSetToSortedList(startStateIdSet);

        Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(startStateIdList);
        
        Map<String, Integer> oldKeyToNewKey = new HashMap<>();
        int newKey = 0;
        int newStartStateId = newKey;
        oldKeyToNewKey.put(fromListToString(startStateIdList), newKey);

        Map<Integer, Map<Character, Integer>> newTransitions = new HashMap<>();
        Set<Integer> newAcceptingStateIds = new HashSet<>();

        while (!queue.isEmpty()) {
            List<Integer> curStateIdList = queue.poll();
            String curStateIdListKey = fromListToString(curStateIdList);
        
            if (curStateIdList.stream().anyMatch(acceptingStateIdsSet::contains)) {
                newAcceptingStateIds.add(oldKeyToNewKey.get(fromListToString(curStateIdList)));
            }

            for (Character action : alphabet) {
                Set<Integer> nxtStateIdSet = new HashSet<>();
                for (int curStateId : curStateIdList) {
                    for (int nxtStateId : transitions.get(curStateId).getOrDefault(action, new ArrayList<>())) {
                        nxtStateIdSet.addAll(getEpsilonClosure(nxtStateId, transitions));
                    }
                }

                List<Integer> nxtStateIdList = fromSetToSortedList(nxtStateIdSet);
                String nxtStateIdListKey = fromListToString(nxtStateIdList);

                if (!oldKeyToNewKey.containsKey(nxtStateIdListKey)) {
                    oldKeyToNewKey.put(nxtStateIdListKey, ++newKey);
                    queue.add(nxtStateIdList);
                }

                newTransitions.computeIfAbsent(
                    oldKeyToNewKey.get(curStateIdListKey), 
                    param -> new HashMap<>()
                ).put(action, oldKeyToNewKey.get(nxtStateIdListKey));
            }
        }

        this.startStateId = newStartStateId;
        this.acceptingStateIds = newAcceptingStateIds;
        this.alphabet = alphabet;
        this.transitions = newTransitions;
    }

    private Set<Integer> getEpsilonClosure(int stateId, Map<Integer, Map<Character, List<Integer>>> transitions) {
        Set<Integer> res = new HashSet<>();
        dfs(stateId, transitions, res);
        return res;
    }

    private void dfs(int stateId, Map<Integer, Map<Character, List<Integer>>> transitions, Set<Integer> visited) {
        if (visited.contains(stateId)) {
            return;
        }

        visited.add(stateId);

        for (int nextState : transitions.get(stateId).getOrDefault(EPSILON, new ArrayList<>())) {
            dfs(nextState, transitions, visited);
        }
    }

    private List<Integer> fromSetToSortedList(Set<Integer> stateIdSet) {
        List<Integer> stateIdList = new ArrayList<>(stateIdSet);
        Collections.sort(stateIdList);
        return stateIdList;
    }

    private String fromListToString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int stateId : list) {
            sb.append(stateId).append(",");
        }
        return sb.toString();
    }

    public boolean test(String text) {
        int curStateId = startStateId;
        for (int i = 0; i < text.length(); i++) {
            char action = text.charAt(i);
            curStateId = transitions.get(curStateId).getOrDefault(action, -1);
            if (curStateId == -1) {
                return false;
            }
        }
        return acceptingStateIds.contains(curStateId);
    }
}
