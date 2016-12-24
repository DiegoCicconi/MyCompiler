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
var1@programName dw 0
ci3 dw 3
ci0 dw 0
divCero db "Error de ejecucion: No se permite la division por cero", 0
overflow db "Error de ejecucion: overflow en operacion de suma", 0
incompatibilidad db "Error de ejecucion: Perdida de informacion, no se puede convertir INT", 0
newline db ' ',13,10,0
.code
JMP start
start:
invoke StdOut, addr HelloWorld
invoke StdOut, addr newline
MOV bx , var1@programName
CMP bx , 0
JG Label_4
MOV bx, var2@programName
ADD bx, 3
MOV var1@programName, bx

Label_4:
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
