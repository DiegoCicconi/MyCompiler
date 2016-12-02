package mycompiler;

public class Print extends Tercet {
    
    public Print(Referenceable op1, Referenceable op2, String sc) {
        // 1er operando es el String a imprimir
        super(op1, null, sc);
        this.operation = "PR";
    }
    @Override
    public String printTercet(){
        return this.index + " (" + this.operation + " , \"" + operand1.toString() + "\" )";
    }
    @Override
    public String getAssemblerCode(RegisterTracing rt, boolean inFunction){
        String print ="";
        print += "invoke StdOut, addr " + this.operand1.getAssemblerName() + "\n";
	print += "invoke StdOut, addr newline";
        return print;
    }
}
