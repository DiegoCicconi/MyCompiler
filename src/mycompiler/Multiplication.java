package mycompiler;

public class Multiplication extends TypedTercet {
    public Multiplication(Referenceable op1, Referenceable op2, String sc) {
        super(op1, op2, sc);
        this.operation = "*";
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
        String op = "IMUL ";
        this.assemblerName = rt.getFreeAX();
        if(rt.isThisARegister(this.operand1.getAssemblerName())){
            code += "MOV " + this.assemblerName + ", " + this.operand2.getAssemblerName() + "\n";
            code += op + this.operand1.getAssemblerName() + "\n";
            rt.freeReg(this.operand1.getAssemblerName());
            if(rt.isThisARegister(this.operand2.getAssemblerName()))
                rt.freeReg(this.operand2.getAssemblerName());
        }
        else 
            if(rt.isThisARegister(this.operand2.getAssemblerName())){
            code += "MOV " + this.assemblerName + ", " + this.operand1.getAssemblerName() + "\n";
            code += op + this.operand2.getAssemblerName() + "\n";
            rt.freeReg(this.operand2.getAssemblerName());
            }     
        else{
            String auxReg = rt.getFreeReg();
            code += "MOV " + this.assemblerName + ", " + this.operand1.getAssemblerName() + "\n";
            code += "MOV " + auxReg + ", " + this.operand2.getAssemblerName() + "\n";
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
