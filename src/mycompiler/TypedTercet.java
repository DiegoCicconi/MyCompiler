package mycompiler;

import java.util.Objects;

public class TypedTercet extends Tercet{
    protected SymbolItem.ArithmeticType type = SymbolItem.ArithmeticType.NOT_DEF;
    
    public TypedTercet(Referenceable op1, Referenceable op2, String sc) {
        super(op1, op2, sc);
        if(!op1.getArithmeticType().equals(op2.getArithmeticType())){
            // Tipos Distintos ---> verificar flag de conversiones
            if(TercetManager.conversionAllowed()== true){
                this.type = SymbolItem.ArithmeticType.LONG;
                // Because is the only possible conversion
            }
            else{
                TercetManager.getLog().addLog("Incompatibile Types", this.operand1.getLine());
            }
        }
        else{
            this.type = op1.getArithmeticType();            
        }
    }
    @Override
    public void setArithmeticType(SymbolItem.ArithmeticType at){this.type = at;}
    @Override
    public SymbolItem.ArithmeticType getArithmeticType(){
        if(this.operand1.getArithmeticType().equals(this.operand2.getArithmeticType()))
            return this.type;
        else
            return SymbolItem.ArithmeticType.NOT_DEF;
    }
    @Override
    public int hashCode(){
        return 89*super.hashCode() + Objects.hashCode(this.type);
    }
}
