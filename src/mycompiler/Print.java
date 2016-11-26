package mycompiler;

public class Print extends Tercet {
    
    public Print(Referenceable op1, Referenceable op2, String sc) {
        super(op1, null, sc);
        this.operation = "PR";
    }
    @Override
    public String printTercet(){
        return this.index + " (" + this.operation + " , \"" + operand1.toString() + "\" )";
    }
}
