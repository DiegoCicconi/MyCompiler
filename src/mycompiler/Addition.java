package mycompiler;

public class Addition extends TypedTercet{
    public Addition(Referenceable op1, Referenceable op2, String sc) {
        super(op1, op2, sc);
        this.operation = "+";
    }
}

