package mycompiler;

public class Assignment extends Tercet{
    public Assignment(Referenceable op1, Referenceable op2, String sc) {
        super(op1, op2, sc);
        this.operation = ":=";
    }
}
