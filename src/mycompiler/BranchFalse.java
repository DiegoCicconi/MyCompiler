package mycompiler;

public class BranchFalse extends JumpTercet{
    public BranchFalse(Referenceable op1, Referenceable op2, String sc) {
        // Primer operando es un Comparador
        super(op1, null, sc);
        this.operation = "BF";
    }
    @Override
    public String getAssemblerCode(RegisterTracing rt, boolean inFunction){
        // Los labels son por convencion si se encuentran en una funcion o no
        if(inFunction)
            return ((Comparator)this.operand1).getJumpType() + " FLabel_" + this.jumpDir;
        else
            return ((Comparator)this.operand1).getJumpType() + " Label_" + this.jumpDir;
    }
}
