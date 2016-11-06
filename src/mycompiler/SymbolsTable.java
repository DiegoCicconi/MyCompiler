package mycompiler;

import java.util.Collection;
import java.util.HashMap;

public class SymbolsTable {
    HashMap<Integer,SymbolItem> tableOfSymbols;
    public SymbolsTable(){
        this.tableOfSymbols = new HashMap<Integer,SymbolItem>();
    }
    public SymbolItem addSymbol(SymbolItem st){
        if(this.tableOfSymbols.containsKey(st.hashCode()))
            return this.tableOfSymbols.replace(st.hashCode(), st);
        else 
            return this.tableOfSymbols.put(st.hashCode(), st);
    }
    public boolean contains(SymbolItem st){
        return this.tableOfSymbols.containsKey(st.hashCode());
    }
    public SymbolItem getSymbol(SymbolItem st){
        if(this.tableOfSymbols.containsKey(st.hashCode()))
            return this.tableOfSymbols.get(st.hashCode());
        else 
            return null;
    }
    public void printTable(){
        System.out.println("----------SYMBOLS TABLE----------");
        System.out.println("Type----------idType---------Name");
        Collection<SymbolItem> aux = this.tableOfSymbols.values();
        for(SymbolItem si: aux){
            Token toPrint = si.getToken();
            System.out.println(si.getArithmeticType() + "           " + si.getSymbolUse() + "            " + toPrint.getLex());
        }
        
        
    }
    
}
