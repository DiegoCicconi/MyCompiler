#!/bin/bash
echo "Hello, compiling now..."
./yacc.exe -Jnorun -Jnodebug -Jthrows=IOException -Jsemantic=SymbolItem MyGrammar.y
cp Parser.java ../mycompiler/Parser.java
echo "done"
read -s -n 1 -p "Press any key to continue . . ."