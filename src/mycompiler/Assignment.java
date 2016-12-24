package mycompiler;

public class Assignment extends TypedTercet{
    public Assignment(Referenceable op1, Referenceable op2, String sc) {
        super(op1, op2, sc);
        this.operation = ":=";
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
        if(rt.isThisARegister(this.operand2.getAssemblerName())){
            code += "MOV " + this.operand1.getAssemblerName() + ", " + this.operand2.getAssemblerName() + "\n";
            rt.freeReg(this.operand2.getAssemblerName());
        }
        else {
            String auxReg = rt.getFreeReg();
            code += "MOV " + auxReg + ", " + this.operand2.getAssemblerName() + "\n";
            code += "MOV " + this.operand1.getAssemblerName() + ", " + auxReg  + "\n";
            rt.freeReg(auxReg);
        }
        return code;
    }
}
