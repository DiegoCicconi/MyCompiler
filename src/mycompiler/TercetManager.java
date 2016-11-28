package mycompiler;

import java.util.ArrayList;

public class TercetManager {
    private ArrayList<Tercet> tercets;
    private ArrayList<Tercet> functions;
    private static boolean conversionAllowance; //flag for the only one for the moment, INT to LONG (hardcoded)
    private static Logger errors;
    private boolean labelNextTercet;    //para funcionalidad de marcar los tercetos que son direcciones de salto
    private boolean inFunction;         //flag que denota si estamos agregando tercetos de una funcion o del programa
    private int functionCount;          //Por si estamos definiendo una funcion adentro de otra, 
                                        //que cuando salgamos no dejemos de agregar en la seccion de funciones
    
    public TercetManager(){
        this.tercets = new ArrayList<Tercet>();
        this.functions = new ArrayList<Tercet>();
        TercetManager.conversionAllowance = false;
        TercetManager.errors = new Logger("TERCET ERROR");
        this.inFunction = false;
        this.functionCount = 0;
        this.labelNextTercet = false;
    }
    public int addTercet(Tercet t){
        if(this.labelNextTercet){
            //concretamos el seteo de label al terceto que es una direccion de salto
            t.activeLabeling();
            this.labelNextTercet = false;
        }
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
        //siempre que se pide por el proximo indice es porque es un direccion de salto posible
        if(this.inFunction){
            this.labelNextTercet = true; // por eso marcamos al proximo terceto para que agregue un label
            return this.functions.size();            
        }
        else{
            this.labelNextTercet = true;
            return this.tercets.size();
        }
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
