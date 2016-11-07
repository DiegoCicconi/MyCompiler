package mycompiler;

import java.util.ArrayList;

public class TercetManager {
    private ArrayList<Tercet> tercets;
    private ArrayList<Tercet> functions;
    
    public TercetManager(){
        this.tercets = new ArrayList<Tercet>();
        this.functions = new ArrayList<Tercet>();
    }
    public int addTercet(Tercet t){
        this.tercets.add(t);
        return this.tercets.lastIndexOf(t);
    }
}
