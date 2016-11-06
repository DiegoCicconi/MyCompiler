package mycompiler;

public class SA11_ReturnAndSet extends SemanticAction {
    public SA11_ReturnAndSet(LexicalAnalyzer lexAna){
        super(lexAna);
    }
    @Override
    public void run(){
        SemanticAction SA4 = new SA4_ReturnAndFlinchOne(this.lexAna);
        SemanticAction SA8 = new SA8_SetTokenCode(this.lexAna);
        SA4.run();
        SA8.run();
    }
}
