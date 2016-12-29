.586
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\masm32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\masm32.lib

.data
STR_var2_es_4 db "var2 es 4", 0
PARAM_x@Test@Holi dw 0
var2@Test dw 0
Holi@Test dw 0
STR_TRUE db "TRUE", 0
var1@Test dw 0
ci22 dw 22
STR_Giving_power db "Giving power", 0
ci10 dw 10
STR_FALSE db "FALSE", 0
ci4 dw 4
ci0 dw 0
ci2 dw 2
divCero db "Error de ejecucion aritmetico: division por cero", 0
overflow db "Error de ejecucion aritmetico: overflow en suma", 0
incompatibilidad db "Error de ejecucion: Perdida de informacion en la conversion de INT", 0
newline db ' ',13,10,0

.code
JMP start
function_Holi@Test:
invoke StdOut, addr STR_Giving_power
invoke StdOut, addr newline

MOV ax, PARAM_x@Test@Holi
MOV bx, PARAM_x@Test@Holi
IMUL bx
MOV bx, ax 

MOV Holi@Test, bx
RET

start:
MOV cx, ci2
MOV var2@Test, cx

PUSH ax
PUSH bx
PUSH cx
PUSH dx
MOV cx, var2@Test
MOV PARAM_x@Test@Holi, cx
CALL function_Holi@Test
POP dx
POP cx
POP bx
POP ax

MOV cx, Holi@Test
MOV var2@Test, cx

MOV cx, var2@Test
CMP cx, ci4

JNE Label_6
invoke StdOut, addr STR_var2_es_4
invoke StdOut, addr newline

Label_6:
MOV cx, ci2
MOV var1@Test, cx

MOV cx, ci10
MOV var2@Test, cx

Label_8:
MOV cx, var2@Test
CMP cx, ci0

JLE Label_14
MOV cx, var1@Test
ADD cx, ci2
JO _errorSuma
MOV var1@Test, cx

MOV cx, var2@Test
DEC cx
MOV var2@Test, cx
JMP Label_8
Label_14:
MOV cx, var1@Test
CMP cx, ci22

JNE Label_18
invoke StdOut, addr STR_TRUE
invoke StdOut, addr newline

JMP Label_19
Label_18:
invoke StdOut, addr STR_FALSE
invoke StdOut, addr newline

Label_19:
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
