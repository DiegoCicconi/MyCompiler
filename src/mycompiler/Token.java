package mycompiler;

import java.util.Objects;

public class Token {
    private int line = -1;
    private int code = -1;
    private String lex = new String();
    public enum tokenType{
        CONST,STRING,ID,KEYWORD,OTHER
    }
    private tokenType type;
    public Token(){
    }
    public Token(Token t){
        this.code = t.getCode();
        this.type = t.getType();
        this.lex = t.getLex();
        this.line = t.getLine();
    }
    public Token(int code, String lex, int line){
        this.code = code; 
        this.line = line;
        this.lex = lex; 
        switch(code){
            case  Parser._INT:
                this.type = tokenType.CONST;
                break;
            case Parser._LONGINT:
                this.type = tokenType.CONST;
                break;
            case Parser._ID:
                this.type = tokenType.ID;
                break;
            case Parser._STRING:
                this.type = tokenType.STRING;
                break;
            
            default:
                if(Parser._IF <= code && code <= Parser._LONGINT){
                    this.type = tokenType.KEYWORD;
                }
                else {
                    this.type = tokenType.OTHER;
                }
                break;
        }
    }
    public int getLine(){
        return this.line;
    }
    public int getCode(){
        return this.code;
    }
    public String getLex(){
        return this.lex;
    }
    public void setLex(String lex){
        this.lex = lex;
    }
    public tokenType getType(){
        return this.type;
    }
    
    @Override
    public boolean equals(Object t){
        return ((Token)t).hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.code;
        hash = 89 * hash + Objects.hashCode(this.type);
        hash = 89 * hash + Objects.hashCode(this.lex);
        return hash;
    }
    @Override
    public String toString(){
        return this.code + " " +  this.type + " " + this.lex;
    }
}
