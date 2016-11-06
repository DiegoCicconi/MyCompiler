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
        
        Parser p = new Parser(la,st);
        p.Run();
        p.getTokensLog().printLogs();
        System.out.println("--------------------------------------");
        p.getSyntaxStructures().printLogs();
        System.out.println("--------------------------------------");
        la.getWarnings().printLogs();
        System.out.println("--------------------------------------");
        la.getErrors().printLogs();
        System.out.println("--------------------------------------");
        p.getErrors().printLogs();
        System.out.println("--------------------------------------");
        st.printTable();
    }
    
}
