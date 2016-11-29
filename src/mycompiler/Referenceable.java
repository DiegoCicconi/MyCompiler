package mycompiler;

public class Referenceable {
    protected String scope = new String();
    protected String assemblerName;    //nombre que posee en el codigo assembler, ya sea una variable, una constante,
                                       //o un terceto que esta en un registro, será entonces el nombre del registro
    
    public Referenceable(){
    }
    public void setScope(String sc){
        this.scope = sc;
    }
    public String getScope(){
        return this.scope;
    }
    public void setAssemblerName(String name){
        this.assemblerName = name;
    }
    public String getAssemblerName(){
        return this.assemblerName;
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
