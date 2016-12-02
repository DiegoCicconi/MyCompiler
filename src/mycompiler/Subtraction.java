package mycompiler;

public class Subtraction extends TypedTercet {
    public Subtraction(Referenceable op1, Referenceable op2, String sc) {
        super(op1, op2, sc);
        this.operation = "-";
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
        if(rt.isThisARegister(this.operand1.getAssemblerName()) && rt.isThisARegister(this.operand2.getAssemblerName())){
            code += "SUB " + this.operand1.getAssemblerName() + ", " + this.operand2.getAssemblerName();
            this.assemblerName = this.operand1.getAssemblerName();
            rt.freeReg(this.operand2.getAssemblerName());
        }
        else if(rt.isThisARegister(this.operand1.getAssemblerName()) && !rt.isThisARegister(this.operand2.getAssemblerName())){
            this.assemblerName = this.operand1.getAssemblerName();
            code += "SUB " + this.operand1.getAssemblerName() + ", " + this.operand2.getAssemblerName();
        }
        else{
            this.assemblerName = rt.getFreeReg();
            code += "MOV " + this.assemblerName + ", " + this.operand1.getAssemblerName() + "\n";
            code += "SUB " + this.assemblerName + ", " + this.operand2.getAssemblerName();
            if(rt.isThisARegister(this.operand2.getAssemblerName())){
                rt.freeReg(this.operand2.getAssemblerName());
            }
        }
        return code;
    }
}
