package mycompiler;

public class SA15_Annotation extends SemanticAction{
    public SA15_Annotation(LexicalAnalyzer lexAna){
        super(lexAna);
    }
    @Override
    public void run(){
        this.lexAna.setCode(new Integer(Parser._ANNOT));
        this.lexAna.setPartialLexeme("&&@NC");
    }
}
