package mycompiler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AssemblerGen {
    private SymbolsTable symTab;
    private RegisterTracing regTra;
    private TercetManager terMan;
    
    public AssemblerGen(SymbolsTable st, TercetManager tm){
        this.symTab = st;
        this.terMan = tm;
        this.regTra = new RegisterTracing();
    }
    public void assembleAndCompile(String name){
	if(name.contains(".txt"))
            name = name.substring(0, name.length()-4);
	PrintWriter writer = null;
	try {
            writer = new PrintWriter(name+".asm", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e1) {
            e1.printStackTrace();
            }
		
	writer.println(".586");
	writer.println(".model flat, stdcall");
	writer.println("option casemap :none");
	writer.println("include \\masm32\\include\\windows.inc");
	writer.println("include \\masm32\\include\\kernel32.inc");
	writer.println("include \\masm32\\include\\masm32.inc");
	writer.println("includelib \\masm32\\lib\\kernel32.lib");
	writer.println("includelib \\masm32\\lib\\masm32.lib");
	writer.println("");
	writer.println(".data");
	writer.println("HelloWorld db \"Hello World!\", 0");
        
        //Carga de la tabla de Simbolos
        ArrayList<SymbolItem> tableOfSymbols = this.symTab.getAll();
        for(int i = 0; i < tableOfSymbols.size(); i++){
            SymbolItem s = tableOfSymbols.get(i);
            if(s.getSymbolUse() == SymbolItem.Use.STR){
                String chain = "STR_" + s.getLex().replaceAll(" ","_");
                writer.println(chain + " db \"" + s.getLex()+"\", 0");
                s.setAssemblerName(chain);
            }
            if(s.getSymbolUse() == SymbolItem.Use.VAR || s.getSymbolUse() == SymbolItem.Use.FUNC){
                writer.println(s.getScopedName() + " dw 0");
                s.setAssemblerName(s.getScopedName());
            }
            if(s.getSymbolUse() == SymbolItem.Use.PARAM){
                writer.println("PARAM_" + s.getScopedName() + " dw 0");
                s.setAssemblerName("PARAM_" + s.getScopedName());
            }
            if(s.getSymbolUse() == SymbolItem.Use.CONST){
                if(s.getArithmeticType() == SymbolItem.ArithmeticType.INT){
                    writer.println("ci" + s.getScopedName().replaceFirst("-", "n") + " dw " + s.getLex());
                    s.setAssemblerName("ci" + s.getScopedName().replaceFirst("-", "n"));
                }
                if(s.getArithmeticType() == SymbolItem.ArithmeticType.LONG){
                    writer.println("cl" + s.getScopedName().replaceFirst("-", "n") + " dw " + s.getLex());
                    s.setAssemblerName("cl" + s.getScopedName().replaceFirst("-", "n"));
                }
            }    
        }
	//fin tabla de simbolos

	writer.println("divCero db \"Error de ejecucion aritmetico: division por cero\", 0");
	writer.println("overflow db \"Error de ejecucion aritmetico: overflow en suma\", 0");
	writer.println("incompatibilidad db \"Error de ejecucion: Perdida de informacion en la conversion de INT\", 0");
	writer.println("newline db ' ',13,10,0");			
	writer.println("");
	writer.println(".code");
	writer.println("JMP start");
        
	/* FUNCIONES */
        ArrayList<Tercet> functions = this.terMan.getFunctions();
        for(int i = 0; i < functions.size(); i++){
            writer.println(functions.get(i).getAssemblerCode(this.regTra,true));
        }
        
	writer.println("start:\n" +
        "invoke StdOut, addr HelloWorld");
	writer.println("invoke StdOut, addr newline");
	
	/* PROGRAMA */
	ArrayList<Tercet> program = this.terMan.getProgram();
        for(int i = 0; i < program.size(); i++){
            writer.println(program.get(i).getAssemblerCode(this.regTra,false));
        }
        int lastTercet = this.terMan.isNextTercetLabel();
        if(lastTercet != 0){
            writer.println("Label_" + lastTercet + ":");
        }
	writer.println("JMP _fin");
	writer.println("_divPorCero:");
	writer.println("invoke StdOut, addr divCero");
	writer.println("JMP _fin");
	writer.println("_errorSuma:");
	writer.println("invoke StdOut, addr overflow");
	writer.println("JMP _fin");
	writer.println("_errorInc:");
	writer.println("invoke StdOut, addr incompatibilidad");
	writer.println("JMP _fin");
	writer.println("_fin: invoke ExitProcess, 0");
	writer.println("end start");
	
	writer.close();
	
	Process p;
	try {
            String command = "\\masm32\\bin\\ml /c /Zd /coff "+name+".asm";
            p = Runtime.getRuntime().exec(command);
            p.waitFor(); 
            BufferedReader reader = 
            new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuffer output = new StringBuffer();
            output.append("\n");
            output.append("\n");
            output.append("Generacion del ejecutable:");
            output.append("\n");
            String line = "";			
            while ((line = reader.readLine())!= null) {
		output.append(line + "\n");
            }
		
            System.out.println(output.toString());
            command = "\\masm32\\bin\\Link /SUBSYSTEM:CONSOLE "+name+".obj";
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            reader = 
            	new BufferedReader(new InputStreamReader(p.getInputStream()));
            output = new StringBuffer();
            line = "";			
            while ((line = reader.readLine())!= null) {
            	output.append(line + "\n");
            }
            System.out.println(output.toString());
            System.out.println("Compilacion realizada exitosamente!");
	} catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
