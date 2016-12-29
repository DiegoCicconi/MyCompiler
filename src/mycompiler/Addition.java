package mycompiler;

public class Addition extends TypedTercet{
    public Addition(Referenceable op1, Referenceable op2, String sc) {
        super(op1, op2, sc);
        this.operation = "+";
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
        if(rt.isThisARegister(this.operand1.getAssemblerName())){
            code += "ADD " + this.operand1.getAssemblerName() + ", " + this.operand2.getAssemblerName();
            this.assemblerName = this.operand1.getAssemblerName();
            if(rt.isThisARegister(this.operand2.getAssemblerName()))
                rt.freeReg(this.operand2.getAssemblerName());
        }
        else{
            if(rt.isThisARegister(this.operand2.getAssemblerName())){
                code += "ADD " + this.operand2.getAssemblerName() + ", " + this.operand1.getAssemblerName();
                this.assemblerName = this.operand2.getAssemblerName();
            }
            else{
                this.assemblerName = rt.getFreeReg();
                code += "MOV " + this.assemblerName + ", " + this.operand1.getAssemblerName() + "\n";
                code += "ADD " + this.assemblerName + ", " + this.operand2.getAssemblerName() + "\n";  
            }
        }
        code += "JO _errorSuma";
        return code;
    }
}

