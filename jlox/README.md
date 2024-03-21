# Notes

## Reference
Code notes for the book [Crafting Interpreters](http://www.craftinginterpreters.com/)

## Lexer
Lexer / Scanner / Lexical Analysis

Lexemes / Tokens

### Consume Character
- advance: return the current character and advance to the next character
- peek: return the current character without advancing to the next character
- match: return true and advance if the current character matches the expected character, otherwise return false without advancing, a.k.a. lookahead.

## Other Notes
### Difference between ^C and ^D
Ctrl+C sends a SIGINT signal to the currently running process. This signal is typically used to interrupt or terminate the process. It's commonly used to stop a running program or command.

Ctrl+D is used to signal the end of input or "EOF" (End of File). When you press Ctrl+D at an input prompt, it typically signifies that you've finished providing input, and the program or shell should process the input accordingly. In Java, the reader will return null when it encounters an EOF.
