package mycompiler;

public class Return extends Tercet {
    
    public Return(Referenceable op1, Referenceable op2, String sc) {
        super(op1, op2, sc);
        this.operation = "RET";
    }
}
