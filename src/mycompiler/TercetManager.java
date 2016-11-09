package mycompiler;

import java.util.ArrayList;

public class TercetManager {
    private ArrayList<Tercet> tercets;
    private ArrayList<Tercet> functions;
    private static boolean conversionAllowance; //the only one for the moment is INT to LONG (hardcoded)
    private static Logger errors;
    
    public TercetManager(){
        this.tercets = new ArrayList<Tercet>();
        this.functions = new ArrayList<Tercet>();
        TercetManager.conversionAllowance = false;
        TercetManager.errors = new Logger("TERCET ERROR");
    }
    public int addTercet(Tercet t){
        this.tercets.add(t);
        return this.tercets.lastIndexOf(t);
    }
    public void enableConversion(){
        TercetManager.conversionAllowance = true;
    }
    public static Logger getLog(){
        return TercetManager.errors;
    }
    public static boolean conversionAllowed(){
        return TercetManager.conversionAllowance;
    }
}
