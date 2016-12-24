package mycompiler;

public class Comparator extends Tercet {
    protected String jumpType;
    
    public Comparator(String comparator, Referenceable op1, Referenceable op2, String sc){
        // Primer parametro es la comparacion que se quiere efectuar, 
        // op1 y op2 son ambos lados de las expresiones a comparar (izq y der respectiva y obviamente)
        super(op1,op2,sc);
        this.operation = comparator;
        switch (this.operation){
                    case "<": {this.jumpType = "JL";break;}
                    case ">": {this.jumpType = "JG";break;}
                    case "<=": {this.jumpType = "JLE";break;}
                    case ">=": {this.jumpType = "JGE";break;}
                    case "==": {this.jumpType = "JE";break;}
                    case "!=": {this.jumpType = "JNE";break;}
                }
    }
    public String getJumpType(){
        return this.jumpType;
    }
    @Override
    public String getAssemblerCode(RegisterTracing rt, boolean inFunction){
        String op1 = this.operand1.getAssemblerName();  //Si es una variable o funcion o cte me devuelve su nombre de la seccion .data
        String op2 = this.operand2.getAssemblerName();  //Si es un terceto me devuelve en nombre del registro donde se encuentra la operación
        String code = "";
        if(rt.isThisARegister(op1))
            return "CMP "  + op1 + " , " + op2;
        else{
            String regOP1 = rt.getFreeReg();
            code+= "MOV " + regOP1 + " , " + op1 + "\n";
            code+= "CMP " + regOP1 + " , " + op2;  
            rt.freeReg(regOP1);
            return code;
        }
    }
}
