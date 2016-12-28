.586
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\masm32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\masm32.lib

.data
HelloWorld db "Hello World!", 0
var2@programName dw 0
ci2 dw 2
var1@programName dw 0
STR_TRUE db "TRUE", 0
ci1 dw 1
STR_FALSE db "FALSE", 0
divCero db "Error de ejecucion aritmetico: division por cero", 0
overflow db "Error de ejecucion aritmetico: overflow en suma", 0
incompatibilidad db "Error de ejecucion: Perdida de informacion en la conversion de INT", 0
newline db ' ',13,10,0

.code
JMP start
start:
invoke StdOut, addr HelloWorld
invoke StdOut, addr newline
MOV bx, ci1
MOV var2@programName, bx

MOV dx, 0
CMP var2@programName, 0
JE _divPorCero
MOV ax, ci2
CWD
MOV bx, var2@programName
IDIV bx
MOV bx, ax 

MOV var1@programName, bx

MOV bx, var1@programName
CMP bx, ci2

JNE Label_7
invoke StdOut, addr STR_TRUE
invoke StdOut, addr newline

JMP Label_8
Label_7:
invoke StdOut, addr STR_FALSE
invoke StdOut, addr newline

Label_8:
JMP _fin
_divPorCero:
invoke StdOut, addr divCero
JMP _fin
_errorSuma:
invoke StdOut, addr overflow
JMP _fin
_errorInc:
invoke StdOut, addr incompatibilidad
JMP _fin
_fin: invoke ExitProcess, 0
end start
