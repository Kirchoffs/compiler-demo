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
    private boolean isMinimized;

    public DFA(NFA nfa) {
        this.isMinimized = false;
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

    public void minimizeDFA() {
        if (isMinimized) {
            return;
        }

        Map<Integer, Integer> stateToGroupMapping = new HashMap<>();
        Map<Integer, Set<Integer>> groupToStatesMapping = new HashMap<>();
        for (int state : this.transitions.keySet()) {
            stateToGroupMapping.put(state, acceptingStateIds.contains(state) ? 1 : 0);
            groupToStatesMapping.computeIfAbsent(stateToGroupMapping.get(state), param -> new HashSet<>()).add(state);
        }

        boolean iterateFlag = true;
        while (iterateFlag) {
            Map<Integer, Integer> newStateToGroupMapping = new HashMap<>();
            Map<Integer, Set<Integer>> newGroupToStatesMapping = new HashMap<>();
            
            int newGroup = 0;
            for (Set<Integer> states : groupToStatesMapping.values()) {
                Map<Integer, Set<Integer>> tmpGroupToStatesMapping = null;
                for (char action : alphabet) {
                    tmpGroupToStatesMapping = new HashMap<>();
                    for (int state : states) {
                        int group = stateToGroupMapping.getOrDefault(this.transitions.get(state).get(action), -1);
                        tmpGroupToStatesMapping.computeIfAbsent(group, param -> new HashSet<>()).add(state);
                    }
                    if (tmpGroupToStatesMapping.size() > 1) {
                        break;
                    }
                }
                
                for (Set<Integer> newStates : tmpGroupToStatesMapping.values()) {
                    for (int state : newStates) {
                        newStateToGroupMapping.put(state, newGroup);
                    }
                    newGroupToStatesMapping.put(newGroup, newStates);
                    newGroup++;
                }
            }

            iterateFlag = newGroupToStatesMapping.size() != groupToStatesMapping.size();

            stateToGroupMapping = newStateToGroupMapping;
            groupToStatesMapping = newGroupToStatesMapping;
        }

        int newStartStateId = stateToGroupMapping.get(this.startStateId);
        Set<Integer> newAcceptingStateIds = new HashSet<>();
        Map<Integer, Map<Character, Integer>> newTransitions = new HashMap<>();
        for (Map.Entry<Integer, Set<Integer>> groupInfo : groupToStatesMapping.entrySet()) {
            int group = groupInfo.getKey();
            Set<Integer> states = groupInfo.getValue();
            for (int state : states) {
                if (this.acceptingStateIds.contains(state)) {
                    newAcceptingStateIds.add(group);
                }
                Map<Character, Integer> newTransition = new HashMap<>();
                for (char action : alphabet) {
                    if (this.transitions.get(state).containsKey(action)) {
                        newTransition.put(action, stateToGroupMapping.get(this.transitions.get(state).get(action)));
                    }
                }
                newTransitions.put(group, newTransition);
            }
        }

        this.startStateId = newStartStateId;
        this.acceptingStateIds = newAcceptingStateIds;
        this.transitions = newTransitions;

        isMinimized = true;
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
