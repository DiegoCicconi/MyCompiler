package mycompiler;

public class SA4_ReturnAndFlinchOne extends SemanticAction {
    public SA4_ReturnAndFlinchOne(LexicalAnalyzer lexAna){
        super(lexAna);
    }
    @Override
    public void run(){
        String partialLex = this.lexAna.getPartialLexeme();
        String currentChar = this.lexAna.getCurrentChar();
        this.lexAna.goBackOneReader();
        partialLex = partialLex.substring(0, partialLex.length()-currentChar.length());
        this.lexAna.setPartialLexeme(partialLex); 
        this.lexAna.restartCurrentChar();
    }
}
