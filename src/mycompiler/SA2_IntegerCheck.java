package mycompiler;


public class SA2_IntegerCheck extends SemanticAction{
    public SA2_IntegerCheck(LexicalAnalyzer lexAna){
        super(lexAna);
    }
    @Override
    public void run(){
        Logger eLog = this.lexAna.getErrors();
        int line = this.lexAna.getLineNumber();
        //DEVUELVE EL ULTIMO CARACTER
        SemanticAction SA4 = new SA4_ReturnAndFlinchOne(this.lexAna);
        SA4.run();
        //REMUEVE EL _i
        String partialLex = this.lexAna.getPartialLexeme();
        partialLex = partialLex.substring(2);       
        //CHEQUEA EL RANGO
        try{
            int integerValueToCheck = Integer.valueOf(partialLex); //EL TRY ESTA POR ESTA LINEA DE CODIGO
            short maxShort = Short.MAX_VALUE;
            short minShort = Short.MIN_VALUE;
            if ((integerValueToCheck >= minShort)&&(integerValueToCheck <= maxShort)){
                this.lexAna.setPartialLexeme(Integer.toString(integerValueToCheck));
                this.lexAna.setCode(new Integer(Parser._INT));
            }
            else {
                if(integerValueToCheck < 0){
                    this.lexAna.setPartialLexeme(Integer.toString(minShort));
                    this.lexAna.setCode(new Integer(Parser._INT));
                    eLog.addLog("Constant Out of Range. Setting constant to minimum value", line);
                }
                else {
                    if(integerValueToCheck > 0){            
                        this.lexAna.setPartialLexeme(Integer.toString(maxShort));
                        this.lexAna.setCode(new Integer(Parser._INT));
                        eLog.addLog("Constant Out of Range. Setting constant to maximun value", line);
                    }
                }
            }
        }
        catch (NumberFormatException e){
            eLog.addLog("Constant Waaaay Out of Range. Setting constant to 1", line);
            this.lexAna.setPartialLexeme(Integer.toString(1));
            this.lexAna.setCode(new Integer(Parser._INT));
        }
    }
}
