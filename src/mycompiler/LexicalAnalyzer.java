package mycompiler;

import java.io.IOException;

public class LexicalAnalyzer {
    private static final int _INICIAL_STATE = 0;
    private static final int _FINISH_STATE = -1;
    private static final int _SYMBOL_COUNT = 31;
    private static final int _STATE_COUNT = 22;
    String partialLexeme = new String();
    String currentChar = new String();
    int currentState = _INICIAL_STATE;
    int currentCode = 0;
    int currentLine = 1;
    MyReader reader;
    private SymbolsTable symbolsTable; 
    private Logger errorsLog = new Logger("LEXICAL ERROR");
    private Logger warningsLog = new Logger("LEXICAL WARNING");
    
    private final int[][] transitionMatrix = {
                {1,1,1,1,1,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},      // "a..z" - "i","l"
                {1,1,1,1,1,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},      // "A..Z" - "N","C"
                {1,1,3,1,1,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},      // "i"
                {1,1,4,1,1,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},      // "l"
                {1,1,1,1,1,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,19,21,20,21},      // "N"
                {1,1,1,1,1,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,20,20,21},      // "C"
                {7,1,1,7,8,7,8,7,8,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},        // "0..9"
                {2,1,2,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},      // "_"
                {-1,-1,0,5,6,0,0,-1,-1,-1,0,-1,-1,0,15,15,21,21,21,21,20,21},    // "+"
                {9,-1,0,5,6,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},     // "-"
                {-1,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},    // "*"
                {-1,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},    // "/"
                {10,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},    // ":"    
                {-1,-1,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,14,14,21,21,21,21,20,21},  // "="    
                {11,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},    // "<"    
                {12,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},    // ">"    
                {13,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},    // "!"    
                {-1,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},    // "("    
                {-1,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},    // ")"    
                {-1,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},    // "{"    
                {-1,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},    // "}"    
                {-1,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},    // ","    
                {-1,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},    // ";"    
                {16,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,17,21,21,21,20,21},   // "&"    
                {0,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,18,21,21,20,21},     // "@"    
                {14,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,-1,-1,21,21,21,21,20,21},    // """    
                {0,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},     // "space"    
                {0,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,0,0,0,0,-1,0},          // "\n"    
                {0,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},     // "\t"
                {0,-1,0,0,0,0,0,-1,-1,-1,0,-1,-1,0,14,14,21,21,21,21,20,21},     // "other"
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1} // ""$"
    };
    private SemanticAction SA0 = new SA0_DoesNothing(this);
    private SemanticAction SA1 = new SA1_IdentifierOrKeyword(this);
    private SemanticAction SA2 = new SA2_IntegerCheck(this);
    private SemanticAction SA3 = new SA3_LongIntCheck(this);
    private SemanticAction SA4 = new SA4_ReturnAndFlinchOne(this);
    private SemanticAction SA5 = new SA5_ClearToken(this);
    private SemanticAction SA6 = new SA6_ClearLastChar(this);
    private SemanticAction SA7 = new SA7_InvalidCharacter(this);
    private SemanticAction SA8 = new SA8_SetTokenCode(this);
    private SemanticAction SA9 = new SA9_IncreseAndClearToken(this);
    private SemanticAction SA10 = new SA10_SetStringCode(this);
    private SemanticAction SA11 = new SA11_ReturnAndSet(this);
    private SemanticAction SA12 = new SA12_IdentifierAssumption(this);
    private SemanticAction SA13 = new SA13_IntegerAssumption(this);
    private SemanticAction SA14 = new SA14_CommentaryAssumption(this);
    private SemanticAction SA15 = new SA15_Annotation(this);
    
    private final SemanticAction[][] semanticActionsMatrix = new SemanticAction[][]{
        
        {SA0,SA0,SA12,SA12,SA12,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},    // "a..z" - "i","l"
        {SA0,SA0,SA12,SA12,SA12,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},    // "A..Z" - "N","C"
        {SA0,SA0,SA0,SA12,SA12,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},     // "i"
        {SA0,SA0,SA0,SA12,SA12,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},     // "l"
        {SA0,SA0,SA12,SA12,SA12,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},    // "N"
        {SA0,SA0,SA12,SA12,SA12,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},    // "C"
        {SA13,SA0,SA12,SA0,SA0,SA0,SA0,SA0,SA0,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},     // "0..9"
        {SA0,SA0,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // "_"
        {SA8,SA1,SA7,SA0,SA0,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // "+"
        {SA0,SA1,SA7,SA0,SA0,SA7,SA7,SA2,SA3,SA8,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},        // "-"
        {SA8,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // "*"
        {SA8,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // "/"
        {SA0,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // ":"
        {SA8,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA8,SA8,SA8,SA8,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},         // "="
        {SA0,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // "<"
        {SA0,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // ">"
        {SA0,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // "!"
        {SA8,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // "("
        {SA8,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // ")"
        {SA8,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // "{"
        {SA8,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // "}"
        {SA8,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // ","
        {SA8,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // ";"
        {SA0,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA0,SA0,SA0,SA0,SA0,SA0},       // "&"
        {SA7,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // "@"
        {SA0,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA10,SA10,SA14,SA0,SA0,SA0,SA0,SA0},     // """
        {SA5,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // space
        {SA6,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA6,SA6,SA14,SA9,SA9,SA9,SA15,SA9},       // /n
        {SA5,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // /t
        {SA7,SA1,SA7,SA7,SA7,SA7,SA7,SA2,SA3,SA11,SA7,SA11,SA11,SA7,SA0,SA0,SA14,SA0,SA0,SA0,SA0,SA0},       // other
        {SA0,SA0,SA0,SA0,SA0,SA0,SA0,SA0,SA0, SA0,SA0, SA0, SA0,SA0,SA0,SA0,SA0,SA0,SA0,SA0,SA0,SA0}        // "$"
        
    };
    public LexicalAnalyzer(MyReader myR, SymbolsTable st) throws IOException {
        this.reader = myR;
        this.symbolsTable = st;
    }
    public Token getToken() throws IOException {
        this.partialLexeme = new String();        
        this.currentChar = new String(); 
        this.currentState = _INICIAL_STATE;
        while (this.currentState != _FINISH_STATE){
            this.currentChar = this.reader.getChar();
            this.partialLexeme += this.currentChar;
            int row = this.getRow(this.currentChar);
            this.semanticActionsMatrix[row][this.currentState].run();
            currentState = this.transitionMatrix[row][currentState];
        }
        if(this.currentChar.equals("$")){
            this.currentCode = new Integer(Parser._END);
        }
        //System.out.println("Encontrado en linea " + this.getLineNumber() + ": " + this.partialLexeme + " Code: " + this.currentCode);
        return new Token(this.currentCode,this.partialLexeme,this.reader.getLineNumber());
    }
    public int getLineNumber(){
        return this.reader.getLineNumber();
    }
    public void increaseLines(){
        this.currentLine++;
    }
    public Logger getErrors() {
        return this.errorsLog;
    }
    public Logger getWarnings() {
        return this.warningsLog;
    }
    public void setPartialLexeme (String input) {
        this.partialLexeme = input;
    }
    public String getPartialLexeme() {
        return this.partialLexeme;
    }
    public void restartPartialLexeme(){
        this.partialLexeme = new String();
    }
    public void setCode(int inputCode) {
        this.currentCode = inputCode;
    }
    public int getCode(){
        return this.currentCode;
    }
    public void restartCode(){
        this.currentCode = 0;
    }
    public void setCurrentState(int inputState) {
        this.currentState = inputState;
    }
    public void restartState(){
        this.currentState = _INICIAL_STATE;
    }
    public void setCurrentChar(String currentCharInput) {
        this.currentChar = currentCharInput;
    }
    public String getCurrentChar(){
        return this.currentChar;
    }
    public void restartCurrentChar(){
        this.currentChar = new String();
    }
    public void goBackOneReader(){
        this.reader.goToPrevChar();
    }
    public void reset() throws IOException{
        this.reader.reset();
        this.currentChar = "";
        this.currentCode = 0;
        this.currentLine = 1; 
        this.currentState = _INICIAL_STATE;
    }
    private int getRow(String currentSymbol) {
        switch (currentSymbol) {
            case "a":  return 0; case "b":  return 0; case "c":  return 0; case "d":  return 0;
            case "e":  return 0; case "f":  return 0; case "g":  return 0; case "h":  return 0;
            case "i":  return 2; case "j":  return 0; case "k":  return 0; case "l":  return 3;
            case "m":  return 0; case "n":  return 0; case "o":  return 0; case "p":  return 0;
            case "q":  return 0; case "r":  return 0; case "s":  return 0; case "t":  return 0;
            case "u":  return 0; case "v":  return 0; case "w":  return 0; case "x":  return 0;
            case "y":  return 0; case "z":  return 0;
                
            case "A": return 1; case "B": return 1; case "C": return 5; case "D": return 1;
            case "E": return 1; case "F": return 1; case "G": return 1; case "H": return 1;
            case "I": return 1; case "J": return 1; case "K": return 1; case "L": return 1;
            case "M": return 1; case "N": return 4; case "O": return 1; case "P": return 1;
            case "Q": return 1; case "R": return 1; case "S": return 1; case "T": return 1;
            case "U": return 1; case "V": return 1; case "W": return 1; case "X": return 1;
            case "Y": return 1; case "Z": return 1; 
            
            case "0": return 6;  case "1": return 6;  case "2": return 6;  case "3": return 6;
            case "4": return 6;  case "5": return 6;  case "6": return 6;  case "7": return 6;
            case "8": return 6;  case "9": return 6;  
                
            case "_": return 7; 
            case "+": return 8;
            case "-": return 9;  
            case "*": return 10;
            case "/": return 11;  
            case ":": return 12; 
            case "=": return 13;
            case "<": return 14; 
            case ">": return 15;
            case "!": return 16;
            
            case "(": return 17; 
            case ")": return 18; 
            case "{": return 19; 
            case "}": return 20; 
            case ",": return 21; 
            case ";": return 22; 
            case "&": return 23; 
            case "@": return 24;
            case "\"":return 25;
            case " ": return 26; 
            case "/n": return 27;
            case "\t": return 28;

            case "$": return 30; 
                
            default: return 29;
        }
    }
}
