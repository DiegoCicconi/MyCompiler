package mycompiler;

public class SA8_SetTokenCode extends SemanticAction {
    public SA8_SetTokenCode(LexicalAnalyzer lexAna){
        super(lexAna);
    }
    @Override
    public void run(){
        String partialLex = this.lexAna.getPartialLexeme();
        switch(partialLex) {
            case "+" : { this.lexAna.setCode(new Integer(Parser._PLUS)); break;}
            case "-" : { this.lexAna.setCode(new Integer(Parser._MINUS)); break;}
            case "*" : { this.lexAna.setCode(new Integer(Parser._MULT)); break;}
            case "/" : { this.lexAna.setCode(new Integer(Parser._DIV)); break;}
            case ":" : { this.lexAna.setCode(new Integer(Parser._COLON)); break;}
            case "=" : { this.lexAna.setCode(new Integer(Parser._EQUAL)); break;}
            case ":=" : { this.lexAna.setCode(new Integer(Parser._ASSIGN)); break;}
            case "<" : { this.lexAna.setCode(new Integer(Parser._LESSER)); break;}
            case "<=" : { this.lexAna.setCode(new Integer(Parser._LESSER_OR_EQUAL)); break;}
            case ">" : { this.lexAna.setCode(new Integer(Parser._GREATER)); break;}
            case ">=" : { this.lexAna.setCode(new Integer(Parser._GREATER_OR_EQUAL)); break;}
            case "!=" : { this.lexAna.setCode(new Integer(Parser._UNEQUAL)); break;}
            case "--" : { this.lexAna.setCode(new Integer(Parser._MINUS_ONE)); break;}
            case "(" : { this.lexAna.setCode(new Integer(Parser._LPAREN)); break;}
            case ")" : { this.lexAna.setCode(new Integer(Parser._RPAREN)); break;}
            case "{" : { this.lexAna.setCode(new Integer(Parser._LCBRACE)); break;}
            case "}" : { this.lexAna.setCode(new Integer(Parser._RCBRACE)); break;}
            case "," : { this.lexAna.setCode(new Integer(Parser._COMMA)); break;}
            case ";" : { this.lexAna.setCode(new Integer(Parser._SEMICOLON)); break;}
        }
    }
    
}
