package mycompiler;

import java.util.HashMap;

public class SA1_IdentifierOrKeyword extends SemanticAction{
    private static final int _LENGTH = 20;
    private final HashMap<String,Integer> reservedWords = new HashMap<>();
    public SA1_IdentifierOrKeyword(LexicalAnalyzer lexAna){
        super(lexAna);
        this.reservedWords.put("if", new Integer(Parser._IF));
        this.reservedWords.put("else", new Integer(Parser._ELSE));
        this.reservedWords.put("endif", new Integer(Parser._ENDIF));
        this.reservedWords.put("integer", new Integer(Parser._INTEGER));
        this.reservedWords.put("longint", new Integer(Parser._LONGINTEGER));
        this.reservedWords.put("print", new Integer(Parser._PRINT));
        this.reservedWords.put("function", new Integer(Parser._FUNCTION));
        this.reservedWords.put("return", new Integer(Parser._RETURN));
        this.reservedWords.put("for", new Integer(Parser._FOR));
        this.reservedWords.put("inttolong", new Integer(Parser._INTTOLONG));
        this.reservedWords.put("allow", new Integer(Parser._ALLOW));
        this.reservedWords.put("to", new Integer(Parser._TO));
    }
    @Override
    public void run(){
        //DEVULEVE EL ULTIMO CARACTER
        SemanticAction SA4 = new SA4_ReturnAndFlinchOne(this.lexAna);
        SA4.run();
        Logger wLog = this.lexAna.getWarnings();
        String partialLex = this.lexAna.getPartialLexeme();
        int line = this.lexAna.getLineNumber();
        //SI ES PALABRA RESERVADA
        if(this.reservedWords.containsKey((String)partialLex)){
            this.lexAna.setCode(this.reservedWords.get((String)partialLex));
        }
        else{
            //SI NO ES PALABRA RESERVADA ENTONCES ES IDENTIFICADOR
            this.lexAna.setCode(new Integer(Parser._ID));
            if(partialLex.length() > _LENGTH){
                partialLex = partialLex.substring(0, _LENGTH - 1);
                this.lexAna.setPartialLexeme(partialLex);
                wLog.addLog("Identifier too long..Truncated", line);
            }
        }  
    }
}
