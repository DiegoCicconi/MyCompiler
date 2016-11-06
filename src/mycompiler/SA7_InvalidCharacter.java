package mycompiler;

public class SA7_InvalidCharacter extends SemanticAction{
    public SA7_InvalidCharacter(LexicalAnalyzer lexAna){
        super(lexAna);
    }
    @Override
    public void run(){
        Logger eLog = this.lexAna.getErrors();
        int line = this.lexAna.getLineNumber();
        eLog.addLog("Invalid Character: " + this.lexAna.getCurrentChar(), line);
        if(this.lexAna.getCurrentChar().equals("/n")){
            this.lexAna.increaseLines();
        }
        String partialLex = this.lexAna.getPartialLexeme();
        String currentChar = this.lexAna.getCurrentChar();
        partialLex = partialLex.substring(0, partialLex.length()-currentChar.length());
        this.lexAna.setPartialLexeme(partialLex); 
        this.lexAna.restartCode();
        this.lexAna.restartCurrentChar();
    }
}
