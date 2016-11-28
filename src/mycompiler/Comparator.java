package mycompiler;

public class Comparator extends Tercet {
    public Comparator(String comparator, Referenceable op1, Referenceable op2, String sc){
        // Primer parametro es la comparacion que se quiere efectuar, 
        // op1 y op2 son ambos lados de las expresiones a comparar (izq y der respectiva y obviamente)
        super(op1,op2,sc);
        this.operation = comparator;
        switch (this.operation){
                    case "<": {break;}
                    case ">": {break;}
                    case "<=": {break;}
                    case ">=": {break;}
                    case "==": {break;}
                    case "!=": {break;}
                }
    }
}
