# Notes
Trio:
- RE to NFA: Thompson's Construction
- NFA to DFA: Subset Construction Algorithm
- DFA Minimization: Hopcroft's Algorithm

Both subset construction algorithm and Hopcroft's algorithm are based on fixed-point computations. They involve repeatedly applying a monotone function to a given set.

## NFA
### Basic NFA Machine
- Single character
- Epsilon
- Concatenation
- Alternation
- Kleene closure

### RE to NFA
#### Thompson's Construction
The construction begins by building trivial nfas for each character in the input re. Next, it applies the transformations for alternation, concatenation, and closure to the collection of trivial nfas in the order dictated by precedence and parentheses.

## DFA
### Difference between NFA and DFA
- NFA can have multiple transitions from a state on the same input symbol.
- NFA can have epsilon transitions.

### NFA to DFA
#### Subset Construction Algorithm
Finite Automaton: (S, Sigma, delta, s0, SA)
- S: the finite set of states
- Sigma: the finite alphabet
- delta: the transition function
- s0: the start state
- SA: the set of accept states

(N, Sigma, deltaN, n0, NA) -> (D, Sigma, deltaD, d0, DA)

We can use BFS to traverse the NFA states and generate the corresponding DFA states.  
First we get the closure of the start state of NFA, and put it into the queue.  
Then we have a loop to get the next state of the current state on each input symbol. For the next state, we get the closure of it and put it into the queue. We also build a map to record the transition from the current state closure to the next state closure on the input symbol.  
Finally, we get the DFA states and transitions.

### DFA Minimization
#### Hopcroft's Algorithm
The algorithm is based on the concept of distinguishing states. Two states are said to be distinguishable if there is some input string that leads to an accept state from one state but not from the other. The algorithm starts by partitioning the states into two sets: the accept states and the non-accept states. It then refines the partition by distinguishing states based on the transitions from each state on each input symbol. The algorithm continues to refine the partition until no further distinctions can be made.

## Regular Expression Engine to Scanner
The compiler developer writes a RE for each syntactic category and provides these REs to a scanner generator. The generator builds a NFA for each RE, links them using ε-transitions, forms a corresponding DFA, and then minimizes the DFA. Finally, the scanner generator converts the DFA into executable code.

## Compile and Test
```
>> javac -d target/classes -sourcepath src/main src/main/org/syh/demo/regexp/State.java src/main/org/syh/demo/regexp/nfa/Constants.java src/main/org/syh/demo/regexp/nfa/NFA.java

>> javac -d target/test-classes -cp target/classes -sourcepath src/test src/test/org/syh/demo/regexp/nfa/NFATest.java

>> java -ea -cp target/classes:target/test-classes org.syh.demo.regexp.nfa.NFATest
```

## Shell Knowledge
### Exit Status
In shell scripting, `$?` is a special variable that holds the exit status of the most recently executed command. The exit status is a number that represents the outcome of the command. By convention in Unix and Linux systems:

- An exit status of 0 typically indicates that the command was successful.
- A non-zero exit status usually indicates an error, with different numbers having different meanings depending on the command.
