package mycompiler;

public class BranchUnconditional extends JumpTercet {
    
    public BranchUnconditional(Referenceable op1, Referenceable op2, String sc) {
        //Solo nos importa la direccion a donde tiene que saltar, que la implementa la clase padre
        super(null, null, sc);
        this.operation = "BI";
    }
    @Override
    public String getAssemblerCode(RegisterTracing rt, boolean inFunction){
        //Le pasamos al metodo si pertenece a una funcion o no, lo demas es convencion de labels
        if(inFunction)
            return "JMP " + "FLabel_" + this.jumpDir;
        else
            return "JMP " + "Label_" + this.jumpDir;
    }
    @Override
    public String printTercet(){
        return this.index + " (" + this.operation + " ,"  + this.jumpDir + ")";
    }
}
