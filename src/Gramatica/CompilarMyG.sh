#!/bin/bash
echo "Hello, compiling now..."
./yacc.exe -Jnorun -Jnodebug -Jthrows=IOException -Jsemantic=SymbolItem MyGrammar.y
echo "done"
read -s -n 1 -p "Press any key to continue . . ."