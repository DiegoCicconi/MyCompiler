package mycompiler;

public class BranchUnconditional extends JumpTercet {
    
    public BranchUnconditional(Referenceable op1, Referenceable op2, String sc) {
        super(null, null, sc);
        this.operation = "BI";
    }
    @Override
    public String printTercet(){
        return this.index + " (" + this.operation + " ,"  + this.jumpDir + ")";
    }
}
