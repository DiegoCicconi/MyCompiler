package mycompiler;

public class Comparator extends Tercet {
    public Comparator(String comparator, Referenceable op1, Referenceable op2, String sc){
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
