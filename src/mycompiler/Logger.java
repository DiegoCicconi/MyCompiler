package mycompiler;

import java.util.ArrayList;

public class Logger {
    protected final ArrayList<String> logs;
    protected String typeOfLog;
    public Logger(String typeOfLog){
        this.typeOfLog = typeOfLog;
        logs = new ArrayList();
    }
    public ArrayList<String>getLogs(){
        ArrayList<String> logOutput = new ArrayList();
        for(int i=0; i<logs.size();i++)
            logOutput.add(typeOfLog + ": "+ logs.get(i));
        return logOutput;
    }
    public void printLogs(){
        if(logs.size() > 0){
            for(int i=0; i < logs.size(); i++){
                System.out.println(typeOfLog + ": "+ logs.get(i));
            }    
        }
    }
    public void addLog(String log, int line){
        logs.add(log + " at line " + line);
    }
}