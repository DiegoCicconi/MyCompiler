package mycompiler;

public class SA10_SetStringCode extends SemanticAction {
    public SA10_SetStringCode(LexicalAnalyzer lexAna){
        super(lexAna);
    }
    @Override
    public void run(){
        String partialLex = this.lexAna.getPartialLexeme();
        partialLex = partialLex.substring(1, partialLex.length() - 1);
        this.lexAna.setPartialLexeme(partialLex); 
        this.lexAna.setCode(new Integer(Parser._STRING)); 
    }
}
