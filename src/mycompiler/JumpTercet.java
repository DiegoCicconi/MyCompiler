package mycompiler;

public class JumpTercet extends Tercet{
    protected int jumpDir = -1;
    public JumpTercet(Referenceable op1, Referenceable op2, String sc) {
        //incorporamos para este tipo de terceto la direccion del salto
        super(op1, null, sc);
    }
    public void setJumpDir(int jd){
        this.jumpDir = jd;
    }
    public int getJumpDir(){
        return this.jumpDir;
    }
    @Override
    public String printTercet(){
        return this.index + " (" + this.operation + ", " + operand1.toString() + ", " + this.jumpDir + ")";
    }
}
