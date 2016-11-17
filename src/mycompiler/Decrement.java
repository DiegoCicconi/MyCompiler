package mycompiler;

public class Decrement extends Tercet{
    
    public Decrement(Referenceable op1, Referenceable op2, String sc) {
        super(op1, null, sc);
        this.operation = "--";
    }
    @Override
    public String printTercet(){
        return this.index + " (" + this.operation + " ," + operand1.toString() + ")";
    }
}
