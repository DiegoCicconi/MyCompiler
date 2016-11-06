package mycompiler;

public class SA14_CommentaryAssumption extends SemanticAction {
    public SA14_CommentaryAssumption(LexicalAnalyzer lexAna){
        super(lexAna);
    }
    @Override
    public void run(){
        Logger wLog = this.lexAna.getWarnings();
        int line = this.lexAna.getLineNumber();
        if(this.lexAna.getPartialLexeme().equals("/n")){ 
            // SI SE ASUME COMENTARIO E INMEDIATAMENTE DESPUES VIENE UN SALTO DE LINEA
            SemanticAction SA9 = new SA9_IncreseAndClearToken(this.lexAna);
            SA9.run();
        }
        wLog.addLog("Wrong commentary definition, assuming it is one", line);
    }
}
