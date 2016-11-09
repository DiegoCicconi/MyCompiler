package mycompiler;

import java.util.Objects;

public class SymbolItem extends Referenceable{
    
    private final Token token;
    public enum ArithmeticType {
        INT,LONG,NOT_DEF
    }
    public enum Use {
        VAR,FUNC,STR,CONST,PARAM,NOT_DEF
    }
    private ArithmeticType arithmeticType;
    private Use symbolUse;
    
    public SymbolItem(){
        this.token = null;
    }
    public SymbolItem(Token token){
        this.token = token;
        this.arithmeticType = ArithmeticType.NOT_DEF;
        this.symbolUse = Use.NOT_DEF;
    }
    public SymbolItem(Token token, ArithmeticType at, Use su){
        this.token = token;
        this.arithmeticType = at;
        this.symbolUse = su;
    }
    public void setSymbolUse(Use su){this.symbolUse = su;}
    
    public Use getSymbolUse(){return this.symbolUse;}
    
    @Override
    public void setArithmeticType(ArithmeticType at){this.arithmeticType = at;}
    
    @Override
    public ArithmeticType getArithmeticType(){return this.arithmeticType;}
    @Override
    public int getLine(){return this.token.getLine();}
    
    public Token getToken(){return this.token;}
    
    public String toStringExtended(){
        return this.arithmeticType + " " + this.symbolUse + " " + this.token.getLex();
    }
    
    @Override
    public String toString(){return " " + this.token.getLex();}
    @Override
    public int hashCode(){
        return this.token.hashCode() * Objects.hashCode(this.symbolUse);
    }
    @Override
    public boolean equals(Object s){
        return ((SymbolItem)s).hashCode() == this.hashCode();
    }
}
