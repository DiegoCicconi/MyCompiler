package mycompiler;

public class Subtraction extends Tercet {
    public Subtraction(Referenceable op1, Referenceable op2, String sc) {
        super(op1, op2, sc);
        this.operation = "-";
    }
}
