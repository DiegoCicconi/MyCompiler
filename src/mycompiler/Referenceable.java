package mycompiler;

public class Referenceable {
    public Referenceable(){
    }
    public String toString(){
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
