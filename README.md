# MARIE Simulator in Java

In this code I try to recreate MARIE Simulator using Java. My code would open the file (in this case marie-instruction.txt) which containing MARIE instruction. It would read the instruction one line until the end of the file, while reading the instruction, it would store data declaration in HashMap and store the instruction in LinkedHashMap. After that, my program would close the file and start executing the MARIE instruction stored in the LinkedHashMap while using the data from the HashMap.

This program only limited to LOAD, ADD, SUBT, STORE, CLEAR, INPUT, OUTPUT, and HALT. Other features that I would like implement in the next update would be adding Jump, Skipcond, AddI, JnS X, JumpI X, SotreI, and LoadI function.
