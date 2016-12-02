package mycompiler;

public class Decrement extends Tercet{
    
    public Decrement(Referenceable op1, Referenceable op2, String sc) {
        // 1er operando es la variable que debemos decrementar
        super(op1, null, sc);
        this.operation = "--";
    }
    @Override
    public String printTercet(){
        return this.index + " (" + this.operation + " ," + operand1.toString() + ")";
    }
    @Override
    public String getAssemblerCode(RegisterTracing rt, boolean inFunction){
        //Mueve la variable a un reg, la decrementa, y la asigna nuevamente
        String code = "";
        this.assemblerName = rt.getFreeReg();
        code += "MOV " + this.assemblerName + ", " + this.operand1.getAssemblerName() + "\n";
        code += "DEC " + this.assemblerName + "\n";
        code += "MOV " + this.operand1.getAssemblerName() + ", " + this.assemblerName;
        rt.freeReg(this.assemblerName);
        return code;
    }
}
