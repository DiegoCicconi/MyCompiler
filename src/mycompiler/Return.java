package mycompiler;

public class Return extends Tercet {
    
    public Return(Referenceable op1, Referenceable op2, String sc) {
        // El 1er operando es la funcion a la cual le corresponde el Return
        // Y el 2do operando es la Expresion del Return
        super(op1, op2, sc);
        this.operation = "RET";
    }
    @Override
    public String getAssemblerCode(RegisterTracing rt, boolean inFunction){
        String code = "";
        if(rt.isThisARegister(this.operand2.getAssemblerName())){
            code += "MOV " + this.operand1.getAssemblerName() + ", " + this.operand2.getAssemblerName() + "\n";
        }
        else{
            String regAux = rt.getFreeReg();
            code += "MOV " + regAux + ", " + this.operand2.getAssemblerName() + " \n";
            code += "MOV " + this.operand1.getAssemblerName() + ", " + regAux + " \n";  
            rt.freeReg(regAux);
        }
        code += "RET";
        return code;
    }
}
