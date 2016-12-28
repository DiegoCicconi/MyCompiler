package mycompiler;

public class Division extends TypedTercet {
    public Division(Referenceable op1, Referenceable op2, String sc) {
        super(op1, op2, sc);
        this.operation = "/";
    }
    @Override
    public String getAssemblerCode(RegisterTracing rt, boolean inFunction){
        String code ="";
        if(this.isLabel()){
            if(inFunction)
                code += "FLabel_" + this.index + ":\n";
            else
                code += "Label_" + this.index + ":\n";
        }
        String op = "IDIV ";
        String conversion = "CWD\n";
        code += "MOV dx, 0\n"; // Limpia el resto
        this.assemblerName = rt.getFreeAX();
        /* Checking division by 0 */
        code += "CMP " + this.operand2.getAssemblerName() + ", 0\n";
        code += "JE _divPorCero\n";
        if(rt.isThisARegister(this.operand1.getAssemblerName()) &&
           rt.isThisARegister(this.operand2.getAssemblerName())){
            code += "MOV " + this.assemblerName + ", " + this.operand1.getAssemblerName() + "\n";
            code += conversion + op + this.operand2.assemblerName + "\n";
            rt.freeReg(this.operand1.getAssemblerName());
            rt.freeReg(this.operand2.getAssemblerName());
        }
        else if(rt.isThisARegister(this.operand1.getAssemblerName())){
            code += "MOV " + this.assemblerName + ", " + this.operand1.getAssemblerName() + "CWD\n";
            rt.freeReg(this.operand1.getAssemblerName());
            String auxReg = rt.getFreeReg();
            code += "MOV " + auxReg + ", " + this.operand2.getAssemblerName() + "\n";
            code += op + auxReg + "\n";
            rt.freeReg(auxReg);
            }
        else if(rt.isThisARegister(this.operand2.getAssemblerName())){
            code += "MOV " + this.assemblerName + ", " + this.operand1.getAssemblerName() + "\n";
            code += conversion + op + this.operand2.getAssemblerName() + "\n";
            rt.freeReg(this.operand2.getAssemblerName());
        }
        else {
            String auxReg = rt.getFreeReg();
            code += "MOV " + this.assemblerName + ", " + this.operand1.getAssemblerName() + "\n";
            code += conversion + "MOV " + auxReg + ", " + this.operand2.getAssemblerName() + "\n";
            code += op + auxReg + "\n";
            rt.freeReg(auxReg);
        }
        if(this.assemblerName.equals("ax")){
            this.assemblerName = rt.getFreeReg();
            code += "MOV " + this.assemblerName + ", ax \n";
            rt.freeReg("ax");
        }
        return code;
    }
}
