package mycompiler;

import java.util.Objects;

public class Tercet extends Referenceable {
    protected String operation = "";
    protected Referenceable operand1;
    protected Referenceable operand2;
    protected int index = -1;
    protected boolean labelTercet;  //intended to mark tercets that are a jump direction
    
    public Tercet(Referenceable op1, Referenceable op2, String sc){
        this.operand1 = op1;
        this.operand2 = op2;
        this.scope = sc;
        this.labelTercet = false;
    }
    public void setIndex(int i){
        this.index = i;
    }
    public void activeLabeling(){
        this.labelTercet = true;
    }
    public boolean isLabel(){
        return this.labelTercet;
    }
    @Override
    public String toString(){
        return "[" + this.index +  "]";
    }
    @Override
    public int getLine(){return this.operand1.getLine();}
    
    public String printTercet(){
        return this.index + " (" + this.operation + " , " + operand1.toString() + ", " + operand2.toString() + ")";
    }
    @Override
    public int hashCode(){
        int hash = 7;
        hash = 89 * hash + this.scope.hashCode();
        hash = 89 * hash + Objects.hashCode(this.operation);
        hash = 89 * hash + Objects.hashCode(this.operand1);
        hash = 89 * hash + Objects.hashCode(this.operand2);
        return hash;
    }
}
