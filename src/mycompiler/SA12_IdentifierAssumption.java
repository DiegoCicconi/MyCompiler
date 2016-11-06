package mycompiler;

public class SA12_IdentifierAssumption extends SemanticAction{
    public SA12_IdentifierAssumption(LexicalAnalyzer lexAna){
        super(lexAna);
    }
    @Override
    public void run(){
        Logger wLog = this.lexAna.getWarnings();
        int line = this.lexAna.getLineNumber();
        wLog.addLog("Invalid character, assuming it's an identifier..", line);
    }
}