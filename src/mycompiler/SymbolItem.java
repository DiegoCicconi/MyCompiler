package mycompiler;

import java.util.ArrayList;
import java.util.Objects;

public class SymbolItem extends Referenceable{
    
    private final Token token;
    private String scopedName;
    private ArithmeticType arithmeticType;
    private Use symbolUse;
    
    public enum ArithmeticType {
        INT,LONG,NOT_DEF
    }
    public enum Use {
        VAR,FUNC,STR,CONST,PARAM,NOT_DEF
    }
    public SymbolItem(){
        this.token = null;
        this.arithmeticType = ArithmeticType.NOT_DEF;
        this.symbolUse = Use.NOT_DEF;
    }
    public SymbolItem(Token token){
        this.token = token;
        this.scopedName = token.getLex();
        this.arithmeticType = ArithmeticType.NOT_DEF;
        this.symbolUse = Use.NOT_DEF;
    }
    public SymbolItem(Token token, ArithmeticType at, Use su){
        this.token = token;
        this.scopedName = token.getLex(); 
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
    public void setScope(String sc){
        this.scope = sc;
        this.scopedName = this.getLex() + this.scope;
    }
    
    public String getScopedName(){return this.scopedName;}
    
    @Override
    public String getAssemblerName(){
        //De acuerdo a las convenciones, estos son los nombres que tendran en el archivo assembler
        if(this.symbolUse == SymbolItem.Use.CONST){
            return super.getAssemblerName();
        }
        else{
            if(this.symbolUse == SymbolItem.Use.STR)
                return super.getAssemblerName();
        }
        return this.scopedName;
    }
    public Token getToken(){return this.token;}
    @Override
    public int getLine(){return this.token.getLine();}
    @Override
    public String getLex(){return this.token.getLex();}
    
    @Override
    public String toString(){return "" + this.token.getLex();}
    public String toStringExtended(){
        return this.arithmeticType + " " + this.symbolUse + " " + this.token.getLex();
    }
    @Override
    public int hashCode(){
        int hash  = this.token.hashCode();
        hash = 89 * hash + Objects.hashCode(this.symbolUse);
        hash = 89 * hash + Objects.hashCode(this.scope);
        return hash;
    }
    public int arithmeticHashCode(){
        int hash  = this.token.hashCode();
        hash = 89 * hash + Objects.hashCode(this.symbolUse);
        hash = 89 * hash + Objects.hashCode(this.arithmeticType);
        hash = 89 * hash + Objects.hashCode(this.scope);
        return hash;
    }
    public ArrayList<Integer> hashCodes(){
        ArrayList<Integer> codes = new ArrayList<>();
        String upperScope = new String(this.scope);
        codes.add(this.hashCode());
        if(this.scope.length() > 0 ){
            while((upperScope.lastIndexOf("@") != 0)){
	        upperScope = upperScope.substring(0, upperScope.lastIndexOf("@"));
	        int hash  = this.token.hashCode();
	        hash = 89 * hash + Objects.hashCode(this.symbolUse);
	        hash = 89 * hash + Objects.hashCode(upperScope);
	        codes.add(hash);
	    }
        }
        return codes;
    }
}
