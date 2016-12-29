package mycompiler;

public class FunctionCall extends Tercet{
    private String paramName;
    public FunctionCall(Referenceable op1, Referenceable op2, String param, String sc) {
        // 1er operando es la entrada en la tabla de simbolos de la funcion que se invoca
        // 2do operando es paramtro si lo hubiera(variable, funcion o expresion), sino null
        // 3er operando es el parametro con el nombre que tiene en la funcion
        super(op1, op2, sc);
        this.paramName = param;
        this.operation = "FCall";
    }
    @Override
    public String getAssemblerCode(RegisterTracing rt, boolean inFunction){
        String code ="";
        code += "PUSH ax\n";
        code += "PUSH bx\n";
        code += "PUSH cx\n";
        code += "PUSH dx\n";
        // Pregunto por el parametro
        if(this.operand2 != null){
            if(rt.isThisARegister(this.operand2.getAssemblerName())){
                //Si es un registro entonces es una expresion que se resolvio de un terceto => la movemos a la variable del parametro
                code += "MOV " + this.paramName + ", " + this.operand2.getAssemblerName() + "\n";
            }
            else {
                //Si no es un registro entonces es un elemento de la tabla de simbolos => mov a reg y luego a la variable del parametro
                String regAux = rt.getFreeReg();
                code += "MOV " + regAux + ", " + this.operand2.getAssemblerName() + "\n";
                code += "MOV " + this.paramName + ", " + regAux + "\n";
                rt.freeReg(regAux);
            }
        }
        code += "CALL function_" + this.operand1.getAssemblerName() + "\n";
        code += "POP dx\n";
        code += "POP cx\n";
        code += "POP bx\n";
        code += "POP ax\n";
        return code;
    }
    @Override
    public String printTercet(){
        if(this.operand2 != null)
            return super.printTercet();
        else
            return this.index + " (" + this.operation + " , " + operand1.toString() + ")";
    }
}
