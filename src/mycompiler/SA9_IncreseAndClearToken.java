package mycompiler;

public class SA9_IncreseAndClearToken extends SemanticAction {
    public SA9_IncreseAndClearToken(LexicalAnalyzer lexAna){
        super(lexAna);
    }
    @Override
    public void run(){
        this.lexAna.increaseLines();
        SemanticAction SA5 = new SA5_ClearToken(this.lexAna);
        SA5.run();
    }
}
