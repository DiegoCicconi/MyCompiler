package mycompiler;

import java.util.LinkedList;

public class Scope {
    private LinkedList<String> scope;
    public Scope(String s){
        this.scope = new LinkedList<>();
        this.scope.push("@" + s);
    }
    public Scope(){
        this.scope = new LinkedList<>();
    }
    public String getScope(){
        String result = new String();
        for(int i = 0; i < this.scope.size(); i++){
            result += this.scope.get(i);
        }
        return result;
    }
    public void pushScope(String s){
        this.scope.addLast("@" + s);
    }
    public void popScope(){
        this.scope.removeLast();
    }
}
