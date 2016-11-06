package mycompiler;

public class SA5_ClearToken extends SemanticAction {
    public SA5_ClearToken(LexicalAnalyzer lexAna){
        super(lexAna);
    }
    @Override
    public void run(){
        this.lexAna.restartCode();
        this.lexAna.restartCurrentChar();
        this.lexAna.restartPartialLexeme();
        this.lexAna.restartState();
    }
}
