
package mycompiler;

public class SA3_LongIntCheck extends SemanticAction{
    public SA3_LongIntCheck(LexicalAnalyzer lexAna){
        super(lexAna);
    }
    @Override
    public void run(){
        Logger eLog = this.lexAna.getErrors();
        int line = this.lexAna.getLineNumber();
        //DEVUELVE EL ULTIMO CARACTER
        SemanticAction SA4 = new SA4_ReturnAndFlinchOne(this.lexAna);
        SA4.run();
        //REMUEVE EL _l
        String partialLex = this.lexAna.getPartialLexeme();
        partialLex = partialLex.substring(2);       
        //CHEQUEA EL RANGO
        try {
            int longIntValueToCheck = Integer.valueOf(partialLex);  //EL TRY ESTA POR ESTA LINEA DE CODIGO
            int maxInt = Integer.MAX_VALUE;
            int minInt = Integer.MIN_VALUE;
            if ((longIntValueToCheck >= minInt)&&(longIntValueToCheck <= maxInt)){
                this.lexAna.setPartialLexeme(Integer.toString(longIntValueToCheck));
                this.lexAna.setCode(new Integer(Parser._LONGINT));
            }
            else {
                if(longIntValueToCheck < 0){
                    this.lexAna.setPartialLexeme(Integer.toString(minInt));
                    this.lexAna.setCode(new Integer(Parser._LONGINT));
                    eLog.addLog("Constant Out of Range. Setting constant to minimum value", line);
                }
                else {
                    if(longIntValueToCheck > 0){            
                        this.lexAna.setPartialLexeme(Integer.toString(maxInt));
                        this.lexAna.setCode(new Integer(Parser._LONGINT));
                        eLog.addLog("Constant Out of Range. Setting constant to maximun value", line);
                    }
                }
            }
        }
        catch (NumberFormatException e) {
            eLog.addLog("Constant Waaaay Out of Range. Setting constant to 1", line);
            this.lexAna.setPartialLexeme(Integer.toString(1));
            this.lexAna.setCode(new Integer(Parser._LONGINT));
        }
    }
}
