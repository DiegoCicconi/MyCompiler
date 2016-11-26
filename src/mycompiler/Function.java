package mycompiler;

public class Function extends Tercet{
    private String label;
    private SymbolItem parameter;
    public Function(Referenceable op1, Referenceable op2, String sc) {
        super(op1, op2, sc);
        this.parameter = (SymbolItem)op2;
        this.operation = "FN";
        this.label = ((SymbolItem)op1).getScopedName();
    }
    public String getLabel(){
        this.label = ((SymbolItem)this.operand1).getScopedName();
        return this.label;
    }
    @Override
    public String printTercet(){
        if(this.parameter == null)
            return this.index + " (" + this.operation + ", " + this.label + ")";
        else
            return this.index + " (" + this.operation + ", " + this.label + ", " + this.parameter.getScopedName() + ")";
    }
}
