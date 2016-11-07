package mycompiler;

public class Multiplication extends Tercet {
    public Multiplication(Referenceable op1, Referenceable op2, String sc) {
        super(op1, op2, sc);
        this.operation = "*";
    }
}
