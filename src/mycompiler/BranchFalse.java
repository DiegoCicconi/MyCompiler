package mycompiler;

public class BranchFalse extends JumpTercet{
    public BranchFalse(Referenceable op1, Referenceable op2, String sc) {
        // Primer operando es un Comparator
        super(op1, null, sc);
        this.operation = "BF";
    }
}
