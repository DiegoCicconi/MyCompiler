package mycompiler;

public class Division extends TypedTercet {
    public Division(Referenceable op1, Referenceable op2, String sc) {
        super(op1, op2, sc);
        this.operation = "/";
    }
}
