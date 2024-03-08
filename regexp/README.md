# Notes
## NFA
### Basic NFA Machine
- Single character
- Epsilon
- Concatenation
- Alternation
- Kleene closure

### Regexp to NFA
#### Thompson's Construction

## Compile and Test
```
>> javac -d target/classes -sourcepath src/main src/main/org/syh/demo/regexp/State.java src/main/org/syh/demo/regexp/nfa/Constants.java src/main/org/syh/demo/regexp/nfa/NFA.java

>> javac -d target/test-classes -cp target/classes -sourcepath src/test src/test/org/syh/demo/regexp/nfa/NFATest.java

>> java -ea -cp target/classes:target/test-classes org.syh.demo.regexp.nfa.NFATest
```

## Shell Knowledge
### Exit Status
In shell scripting, $? is a special variable that holds the exit status of the most recently executed command. The exit status is a number that represents the outcome of the command. By convention in Unix and Linux systems:

- An exit status of 0 typically indicates that the command was successful.
- A non-zero exit status usually indicates an error, with different numbers having different meanings depending on the command.

## DFA
### NFA to DFA
#### Subset Construction Algorithm
Finite Automaton: (S, Sigma, delta, s0, SA)
- S: the finite set of states
- Sigma: the finite alphabet
- delta: the transition function
- s0: the start state
- SA: the set of accept states

(N, Sigma, deltaN, n0, NA) -> (D, Sigma, deltaD, d0, DA)
