package mycompiler;

public class Function extends Tercet{
    private String functionName;
    private SymbolItem parameter;
    public Function(Referenceable op1, Referenceable op2, String sc){
        //El 1er operando es el nombre de la funcion, y el 2do es el parametro si lo hubiese, sino null.
        super(op1, op2, sc);
        this.operation = "FN";
        this.functionName = ((SymbolItem)op1).getScopedName();
        this.parameter = (SymbolItem)op2;
    }
    public String getLabel(){
        this.functionName = ((SymbolItem)this.operand1).getScopedName();
        return this.functionName;
    }
    @Override
    public String getAssemblerCode(RegisterTracing rt, boolean inFunction){
        String code = "";
        code += "function_" + functionName + ": ";		
        return code;
    }
    @Override
    public String printTercet(){
        if(this.parameter == null)
            return this.index + " (" + this.operation + ", " + this.functionName + ")";
        else
            return this.index + " (" + this.operation + ", " + this.functionName + ", " + this.parameter.getScopedName() + ")";
    }
}
