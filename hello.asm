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
Esto_es_una_cadenabien_definida db "Esto es una cadenabien definida", 0
Mad_King@programName dw 0
Primera_1@programName dw 0
variable_de_function@programName@Mad_King dw 0
PARAM_x@programName@Mad_King dw 0
cin32 dw -32
cl32767 dw 32767
ci1 dw 1
ci20 dw 20
cin32768 dw -32768
entero_largo_y_nomb@programName dw 0
_id_mal_definido@programName dw 0
ci10 dw 10
Esto_es_una_cadenamal_definida db "Esto es una cadenamal definida", 0
Segunda2@programName dw 0
ci32767 dw 32767
ci2 dw 2
x@programName dw 0
ci203 dw 203
ci0 dw 0
divCero db "Error de ejecucion: No se permite la division por cero", 0
overflow db "Error de ejecucion: overflow en operacion de suma", 0
incompatibilidad db "Error de ejecucion: Perdida de informacion, no se puede convertir INT", 0
.code
JMP start
start:
invoke StdOut, addr HelloWorld
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
