package mycompiler;

public abstract class SemanticAction {
    LexicalAnalyzer lexAna;
    public SemanticAction(LexicalAnalyzer lA){
        this.lexAna = lA;
    }
    public abstract void run();
}
