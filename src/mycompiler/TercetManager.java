package mycompiler;

import java.util.ArrayList;

public class TercetManager {
    private ArrayList<Tercet> tercets;
    private ArrayList<Tercet> functions;
    private static boolean conversionAllowance; //the only one for the moment is INT to LONG (hardcoded)
    private static Logger errors;
    private boolean inFunction;
    private int functionCount;
    
    public TercetManager(){
        this.tercets = new ArrayList<Tercet>();
        this.functions = new ArrayList<Tercet>();
        TercetManager.conversionAllowance = false;
        TercetManager.errors = new Logger("TERCET ERROR");
        this.inFunction = false;
        this.functionCount = 0;
    }
    public int addTercet(Tercet t){
        if(this.inFunction){
            this.functions.add(t);
            return this.functions.lastIndexOf(t);
        }
        else{
            this.tercets.add(t);
            return this.tercets.lastIndexOf(t);
        }
    }
    public int getNextIndex(){
        if(this.inFunction)
            return this.functions.size();
        else
            return this.tercets.size();
    }
    public void inFunction(){
        this.functionCount++;
        this.inFunction = true;
    }
    public void outOfFunction(){
        this.functionCount--;
        if(this.functionCount == 0)
            this.inFunction = false;
    }
    public void enableConversion(){
        TercetManager.conversionAllowance = true;
    }
    public static boolean conversionAllowed(){
        return TercetManager.conversionAllowance;
    }
    public static Logger getLog(){
        return TercetManager.errors;
    }
    public void printTercets(){
        for (Tercet tercet : this.tercets) {
            System.out.println(tercet.printTercet());
        }
        System.out.println("FUNCTIONS");
        for (Tercet function : this.functions) {
            System.out.println(function.printTercet());
        }
    }
}
