package mycompiler;

public class Comparator extends Tercet {
    protected String jumpType;
    protected String jumpCounterType;
    public Comparator(String comparator, Referenceable op1, Referenceable op2, String sc){
        // Primer parametro es la comparacion que se quiere efectuar, 
        // op1 y op2 son ambos lados de las expresiones a comparar (izq y der respectiva y obviamente)
        super(op1,op2,sc);
        this.operation = comparator;
        switch (this.operation){
                    case "<": {this.jumpType = "JL";this.jumpCounterType = "JGE";break;}
                    case ">": {this.jumpType = "JG";this.jumpCounterType = "JLE";break;}
                    case "<=": {this.jumpType = "JLE";this.jumpCounterType = "JG";break;}
                    case ">=": {this.jumpType = "JGE";this.jumpCounterType = "JL";break;}
                    case "=": {this.jumpType = "JE";this.jumpCounterType = "JNE";break;}
                    case "!=": {this.jumpType = "JNE";this.jumpCounterType = "JE";break;}
                }
    }
    public String getJumpType(){
        return this.jumpCounterType;
    }
    @Override
    public String getAssemblerCode(RegisterTracing rt, boolean inFunction){
        String op1 = this.operand1.getAssemblerName();  //Si es una variable o funcion o cte me devuelve su nombre de la seccion .data
        String op2 = this.operand2.getAssemblerName();  //Si es un terceto me devuelve en nombre del registro donde se encuentra la operación
        String code = "";
        if(rt.isThisARegister(op1)){
            if(this.isLabel()){
                if(inFunction)
                    code += "FLabel_" + this.index + ":\n";
                else
                    code += "Label_" + this.index + ":\n";
            }
            return code + "CMP "  + op1 + ", " + op2;
        }
        else{
            if(this.isLabel()){
                if(inFunction)
                    code += "FLabel_" + this.index + ":\n";
                else
                    code += "Label_" + this.index + ":\n";
            }
            String regOP1 = rt.getFreeReg();
            code+= "MOV " + regOP1 + ", " + op1 + "\n";
            code+= "CMP " + regOP1 + ", " + op2 + "\n";  
            rt.freeReg(regOP1);
            return code;
        }
    }
}
