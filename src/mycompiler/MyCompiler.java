package mycompiler;

import java.io.IOException;

/**
 *
 * @author DiegoMSI
 */
public class MyCompiler {

    public static void main(String[] args) throws IOException {
        SymbolsTable st = new SymbolsTable();
        String filePath = "testcompile.txt";
        if(args.length!=0)
            filePath = (String)args[0];
        MyReader myReader = new MyReader(filePath);
        LexicalAnalyzer la = new LexicalAnalyzer(myReader, st);
        TercetManager tm = new TercetManager();
        Parser p = new Parser(la,st,tm);
        p.Run();
        //p.getTokensLog().printLogs();
        System.out.println("--------------------------------------");
        //p.getSyntaxStructures().printLogs();
        System.out.println("--------------------------------------");
        //la.getWarnings().printLogs();
        System.out.println("--------------------------------------");
        //la.getErrors().printLogs();
        System.out.println("--------------------------------------");
        p.getErrors().printLogs();
        System.out.println("--------------------------------------");
        tm.printTercets();
        st.printTable();
        AssemblerGen ag = new AssemblerGen(st);
        ag.assembleAndCompile("hello");
    }
    
}
