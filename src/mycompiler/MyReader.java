package mycompiler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MyReader {
    private int currentIndex;
    private int line = 1;
    private BufferedReader buffRdr;   
    private final String fileName;
    private String currentLine;
    private String previousLine;    
    
    public MyReader(String fileName) throws FileNotFoundException, IOException{  
        this.fileName = fileName;
        this.buffRdr = new BufferedReader(new FileReader(this.fileName));                
        this.currentIndex =  0;
        this.currentLine  = buffRdr.readLine();
        this.previousLine = "";         
    }
        
    private void readLine(){
		try{
                    String newLine = this.buffRdr.readLine();			  
                    this.previousLine = this.currentLine;
                    this.currentLine = newLine;
                    this.line++;
		} 
		catch(IOException e) {	
                    System.out.println(e);
		}	
	}
    
    public String getChar() throws IOException
    {
        String result = "";
        if(this.currentLine != null){
            if (this.currentIndex > this.currentLine.length()){
                this.readLine();							
                this.currentIndex = 0;
                return this.getChar();
            }
            if (this.currentIndex == this.currentLine.length())                 
                result = "/n";                  
            else{
                char justReadChar = (this.currentLine.charAt(currentIndex));
                result = "" + justReadChar;
                switch (justReadChar){
                  //case '\t': result="/t"; break;
                    case ' ': result = " "; break;
                }                       
            }           
            this.currentIndex++;  
        }                
        else{
           result = "$"; 
        }                          
        return result;		                                             
    }                                        
            
    public void goToPrevChar(){
        if (currentIndex - 1 < 0){
            currentIndex = 0;
            this.line --;                
            currentLine = previousLine;
        }
        else {
            currentIndex--;        
        }
    }
    
    public int getLineNumber(){
        return this.line;
    }
    
    public void reset() throws IOException{
        this.buffRdr.close();
        this.buffRdr = new BufferedReader(new FileReader(this.fileName));
        this.currentIndex =  0;
        this.line = 0;
        this.currentLine  = buffRdr.readLine();
        this.previousLine = "";
    }
}
