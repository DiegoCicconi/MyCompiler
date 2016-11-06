package mycompiler;

public class SA13_IntegerAssumption extends SemanticAction {
    public SA13_IntegerAssumption(LexicalAnalyzer lexAna){
        super(lexAna);
    }
    @Override
    public void run(){
        Logger wLog = this.lexAna.getWarnings();
        int line = this.lexAna.getLineNumber();
        wLog.addLog("Invalid character, assuming it's an integer constant missing the '_i' part..", line);
        String partialLex = this.lexAna.getPartialLexeme();
        partialLex = "_i" + partialLex;
        this.lexAna.setPartialLexeme(partialLex);
    }
}
