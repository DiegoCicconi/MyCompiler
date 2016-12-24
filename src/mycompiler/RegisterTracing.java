package mycompiler;

public class RegisterTracing {
    private boolean[] busy;
    private final int regCount = 4;

    public RegisterTracing(){
        this.busy = new boolean[this.regCount];
        for(int i=0; i < this.regCount; i++){
            this.busy[i] = false; // they are all free at the beginning
        }
    }
    
    public String getFreeReg(){
        int freeReg = -1;
        for(int i=1; i < this.regCount; i++){
            if(!this.busy[i]){
                freeReg = i;
                this.busy[i] = true;
                break;
            }
        }
        return mapReg(freeReg);
    }
    
    public String getFreeAX(){
        if(!busy[0]) return mapReg(0);
        return "";
    }
    
    public String getFreeCX(){
        if(!busy[2]) return mapReg(2);
        return "";
    }
    
    public String getFreeDX(){
        if(!busy[3]) return mapReg(3);
        return "";
    }
    
    private String mapReg(int reg){
        if(reg == 0)
            return "ax";
	if(reg == 1)
            return "bx";
	if(reg == 2)
            return "cx";
	if(reg == 3)
            return "dx";
	return null;
    }
    
    public void freeReg(String r){
	if(r.equals("ax"))
            this.busy[0] = false;
	if(r.equals("bx"))
            this.busy[1] = false;
	if(r.equals("cx"))
            this.busy[2] = false;
	if(r.equals("dx"))
            this.busy[3] = false;        
    }
    
    public void emptyRegisters(){
        for(int i=0; i < this.regCount; i++){
            this.busy[i] = false;
        }
    }
    
    public boolean isThisARegister(String reg){
        return (reg.equals("ax") || reg.equals("bx") || reg.equals("cx") || reg.equals("dx"));
    }
}
