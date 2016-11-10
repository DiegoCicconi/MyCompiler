package mycompiler;

public class Referenceable {
    protected String scope = new String();
    public Referenceable(){
    }
    public void setScope(String sc){
        this.scope = sc;
    }
    public String getScope(){
        return this.scope;
    }
    public String toString(){
        return "";
    }
    public String getLex(){
        return "";
    }
    public int getLine(){
        return 0;
    }
    public  SymbolItem.ArithmeticType getArithmeticType(){
        return SymbolItem.ArithmeticType.NOT_DEF;
    }
    public void setArithmeticType(SymbolItem.ArithmeticType at){
    }
}
