package mycompiler;

public class SA6_ClearLastChar extends SemanticAction{
    public SA6_ClearLastChar(LexicalAnalyzer lexAna){
        super(lexAna);
    }
    @Override
    public void run(){
        Logger wLog = this.lexAna.getWarnings();
        int line = this.lexAna.getLineNumber();
        String partialLex = this.lexAna.getPartialLexeme();
        String currentChar = this.lexAna.getCurrentChar();
        if(currentChar.equals("/n")){
            this.lexAna.increaseLines();    
            if(this.lexAna.currentState == 14){
                wLog.addLog("Multiline String missing the '+' token", line);
            }
        }
        partialLex = partialLex.substring(0, partialLex.length()-currentChar.length());
        if(partialLex.length() > 0){
            char aux = partialLex.charAt(partialLex.length() - 1);
            if(String.valueOf(aux).equals("+")){
                partialLex = partialLex.substring(0,partialLex.length()-1);
            }
        }
        this.lexAna.setPartialLexeme(partialLex);
    }
}
