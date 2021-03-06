package mycompiler;

import java.util.ArrayList;
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
    public boolean containsArithmetic(SymbolItem st){
        if(this.tableOfSymbols.containsKey(st.hashCode()))
            if(this.tableOfSymbols.get(st.hashCode()).arithmeticHashCode() == st.arithmeticHashCode())
                return true;
        return false;
    }
    public SymbolItem getSymbol(SymbolItem st){
        ArrayList<Integer> codes = st.hashCodes();
            for(int i = 0; i < codes.size(); i++){
                if(this.tableOfSymbols.containsKey(codes.get(i)))
                    return this.tableOfSymbols.get(codes.get(i));
            }
        return null;
    }
    public String getFunctionParam(String functionParamScope){
        //nos devuelve el nombre que tiene el parametro en la funcion que nos pasan
        Collection<SymbolItem> valuesCollection = this.tableOfSymbols.values();
        for(SymbolItem si: valuesCollection){
            if(si.getSymbolUse() == SymbolItem.Use.PARAM){
                String name = si.getScopedName();
                if(name.contains(functionParamScope))
                    return "PARAM_" + name;
            }
        }  
        return null;
    }
    public ArrayList<SymbolItem> getAll(){
        ArrayList<SymbolItem> ret = new ArrayList<>();
        ret.addAll(this.tableOfSymbols.values());
        return ret;
    }
    
    public void printTable(){
        System.out.println();
        System.out.println("----------SYMBOLS TABLE----------");
        System.out.println("Type----------idType---------Name");
        System.out.println("---------------------------------"); 
        Collection<SymbolItem> valuesCollection = this.tableOfSymbols.values();
        for(SymbolItem si: valuesCollection){
            String print = "" + si.getArithmeticType();
            while(print.length() < 15){
                print+="_";
            }
            print += si.getSymbolUse();
            while(print.length() < 30){
                print+="_";
            }
            print += si.getScopedName();
            System.out.println(print);
        }   
    }
    
}
