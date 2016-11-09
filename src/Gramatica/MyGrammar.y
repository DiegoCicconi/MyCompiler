/* Declarations Section */
%{
package mycompiler;

import java.io.IOException;

%}

/* YACC Declarations */

/* RESERVED WORDS */
%token _IF
%token _ELSE
%token _ENDIF
%token _INTEGER
%token _LONGINTEGER
%token _PRINT
%token _FUNCTION
%token _RETURN
%token _FOR
%token _INTTOLONG
%token _ALLOW
%token _TO
%token _ID
%token _INT
%token _LONGINT
/*---------------*/
%token _ANNOT
%token _STRING
%token _PLUS
%token _MINUS
%token _MULT
%token _DIV
%token _COLON
%token _EQUAL
%token _ASSIGN
%token _LESSER
%token _LESSER_OR_EQUAL
%token _GREATER
%token _GREATER_OR_EQUAL
%token _UNEQUAL
%token _MINUS_ONE
%token _LPAREN
%token _RPAREN
%token _LCBRACE
%token _RCBRACE
%token _COMMA
%token _SEMICOLON
%token _END

%right _MINUS_ONE
%right _PLUS _MINUS
%right _MULT _DIV

%%
/* Grammar follows */

program:                 	_ID declaration_sentences _LCBRACE executable_sentences _RCBRACE 
									{$$ = $1; notify($$.toString() + " Compilated Successfully!", ((SymbolItem)$5).getToken().getLine() + 1);}
									|				
									_ID _LCBRACE executable_sentences _RCBRACE 
									{$$ = $1; notify($$.toString() + " Compilated Successfully!", ((SymbolItem)$4).getToken().getLine() + 1);}
									;

declaration_sentences:     declaration_sentences type_var_list _SEMICOLON
									|
									declaration_sentences function
									|
									type_var_list _SEMICOLON 
									|
									function
									|
									_ALLOW _INTEGER _TO _LONGINTEGER { this.terManager.enableConversion();}
									|	
									_ALLOW error 							{ yyerror("Conversion invalid");}
									|
									sentence 								{yyerror("Sentences can't be used in the declarative portion of the code!");}
									;

type_var_list:					type var_list
									|
									type error 			{yyerror("Identifier(s) expected at declaration");}
									;

type:								_INTEGER 			{this.currentType = SymbolItem.ArithmeticType.INT;}
									|
									_LONGINTEGER 		{this.currentType = SymbolItem.ArithmeticType.LONG;}

var_list:                  var_list _COMMA _ID 	{$$ = $3;
																((SymbolItem)$$).setSymbolUse(SymbolItem.Use.VAR); 
																((SymbolItem)$$).setArithmeticType(this.currentType);
																if(!this.symTable.contains((SymbolItem)$$)){
																	this.symTable.addSymbol((SymbolItem)$$);	
																}
																else {
																	yyerror("Variable already declared!");
																}
																notify("Found variable declaration: " + $$.toString());}
									|
									_ID 						{$$ = $1;
																((SymbolItem)$$).setSymbolUse(SymbolItem.Use.VAR); 
																((SymbolItem)$$).setArithmeticType(this.currentType);
																if(!this.symTable.contains((SymbolItem)$$)){
																	this.symTable.addSymbol((SymbolItem)$$);	
																}
																else {
																	yyerror("Variable already declared!");
																}
																notify("Found variable declaration: " + $$.toString());}
									;

executable_sentences:      executable_sentences sentence
									|
									sentence
									;

sentence:               	selection  _SEMICOLON			{notify("Found Selection sentence ", this.currentLine);}
									|		
									iteration  							{notify("Found Iteration sentence ", this.currentLine);}
									|		
									print _SEMICOLON 					{$$ = $1; notify("Found Print sentence: " + $$.toString(), this.currentLine); this.symTable.addSymbol((SymbolItem)$$);}
									|		
									assignment _SEMICOLON 			{$$ = $1; notify("Found Assignment of: " + $$.toString(), this.currentLine);}
									|
									assignment _SEMICOLON _ANNOT 	{$$ = $1; notify("Found Assignment with annotation of: " + $$.toString(), this.currentLine);}
									;

assignment:             	_ID _ASSIGN expression 	{$$ = $1; this.currentLine = ((SymbolItem)$1).getToken().getLine();
																	((SymbolItem)$$).setSymbolUse(SymbolItem.Use.VAR);
																	if(!this.symTable.contains((SymbolItem)$$)){ 
																		yyerror("Variable not declared!");
																	}
																	else {
																		$$ = this.symTable.getSymbol((SymbolItem)$$);
																		Tercet toAdd = new Assignment($1,$3,"");
																		int index = this.terManager.addTercet(toAdd);
																		toAdd.setIndex(index);
																	}}
									|
									_ID _MINUS_ONE				{$$ = $1;
																	((SymbolItem)$$).setSymbolUse(SymbolItem.Use.VAR); 
																	if(!this.symTable.contains((SymbolItem)$$)){ 
																		yyerror("Variable not declared!");
																	}
																	else {
																		$$ = this.symTable.getSymbol((SymbolItem)$$);
																	}}
									;

expression:             	expression _PLUS term	{$$ = new Addition($1,$3,"");
																	int index = this.terManager.addTercet((Tercet)$$);
																	((Tercet)$$).setIndex(index);}
									|
									expression _MINUS term	{$$ = new Subtraction($1,$3,""); 
																	int index = this.terManager.addTercet((Tercet)$$);
																	((Tercet)$$).setIndex(index);}
									|
									term							{$$ = $1;}

									|
									_INTTOLONG _LPAREN expression _RPAREN
																	{$$ = $3; 
																	if(terManager.conversionAllowed()){
																		$$.setArithmeticType(SymbolItem.ArithmeticType.LONG);}
																	}
																	else{
																		terManager.getLog().addLog("Conversion not allowed");
																	}
									;

term:                      term _MULT factor		{$$ = new Multiplication($1,$3,""); 
																int index = this.terManager.addTercet((Tercet)$$);
																((Tercet)$$).setIndex(index);}
									|
									term _DIV factor		{$$ = new Division($1,$3,"");
																int index = this.terManager.addTercet((Tercet)$$);
																((Tercet)$$).setIndex(index);}
									|
									factor					{$$ = $1;}
									;

factor:                    _ID 						{$$ = $1; ((SymbolItem)$$).setSymbolUse(SymbolItem.Use.VAR); 
																if(!this.symTable.contains((SymbolItem)$$)){ yyerror("Variable not declared!");}}
									|
									constant					{$$ = $1; ((SymbolItem)$$).setSymbolUse(SymbolItem.Use.CONST); 
																((SymbolItem)$$).setArithmeticType(this.currentType);
																this.symTable.addSymbol((SymbolItem)$$);}
									|
									_ID _MINUS_ONE			{$$ = $1; ((SymbolItem)$$).setSymbolUse(SymbolItem.Use.VAR); 
																if(!this.symTable.contains((SymbolItem)$$)){ yyerror("Variable not declared!");}}
									;

constant:                  _INT 					{this.currentType = SymbolItem.ArithmeticType.INT;}
									|
									_LONGINT 			{this.currentType = SymbolItem.ArithmeticType.LONG;}
									;

selection:                 _IF _LPAREN condition _RPAREN sentence_block _ENDIF					{this.currentLine = ((SymbolItem)$1).getToken().getLine();}
									|
									_IF _LPAREN condition _RPAREN sentence_block else_section _ENDIF 	{this.currentLine = ((SymbolItem)$1).getToken().getLine();}
									|
									_IF _LPAREN condition _RPAREN sentence_block else_section error 	{this.currentLine = ((SymbolItem)$1).getToken().getLine(); 
																															yyerror("Expected endif;");}
									|
									_IF _LPAREN condition error 													{this.currentLine = ((SymbolItem)$1).getToken().getLine();
																															yyerror("Expected ')'", this.currentLine);}
									|
									_IF _LPAREN condition _RPAREN error 										{this.currentLine = ((SymbolItem)$1).getToken().getLine();
																															yyerror("Wrong if statement, sentence(s) expected");}
									;

else_section:              _ELSE sentence_block
									|
									_ELSE error 	{yyerror("Wrong else statement, sentence(s) expected");}
									;

condition:                	expression comparator expression
                           ;

comparator:             	_LESSER
									|
									_GREATER
									|
									_LESSER_OR_EQUAL
									|
									_GREATER_OR_EQUAL
									|
									_EQUAL
									|
									_UNEQUAL
									|
									error 			{yyerror("Invalid operator, comparator expected");}
									;

sentence_block:     			_LCBRACE executable_sentences _RCBRACE
									|
									sentence
									|
									_LCBRACE executable_sentences error {yyerror("Expected '}'");}
									;

iteration:                 _FOR _LPAREN _ID _ASSIGN expression _SEMICOLON condition _SEMICOLON _ID _ASSIGN expression _RPAREN sentence_block	
									{this.currentLine = ((SymbolItem)$1).getToken().getLine(); checkControlVars((SymbolItem)$3,(SymbolItem)$9);}
									|
									_FOR _LPAREN _ID _ASSIGN expression _SEMICOLON condition _SEMICOLON _ID _MINUS_ONE _RPAREN sentence_block 
									{this.currentLine = ((SymbolItem)$1).getToken().getLine(); checkControlVars((SymbolItem)$3,(SymbolItem)$9);}
									|
									_FOR _LPAREN _ID _ASSIGN expression _SEMICOLON condition _SEMICOLON _ID _ASSIGN expression _RPAREN error {yyerror("Missing 'for' body.");}
									|
									_FOR _LPAREN _ID _ASSIGN expression _SEMICOLON condition _SEMICOLON _ID _MINUS_ONE _RPAREN error {yyerror("Missing 'for' body.");}
									|
									_FOR _LPAREN _ID _ASSIGN expression _SEMICOLON error {yyerror("Missing condition.");}
									|
									_FOR _LPAREN error {yyerror("Missing assignment. What were you planning on iterating on?");}
									;

print:                     _PRINT _LPAREN _STRING _RPAREN 			
									{$$ = $3; ((SymbolItem)$$).setSymbolUse(SymbolItem.Use.STR); this.currentLine = ((SymbolItem)$1).getToken().getLine();}
									;

function:						type _FUNCTION _ID _LPAREN parameter _RPAREN	function_body 	{$$ = $3;
																													((SymbolItem)$$).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)$$).setArithmeticType(this.currentType);
																													this.currentLine = ((SymbolItem)$2).getToken().getLine();
																													if(!this.symTable.contains((SymbolItem)$$)){
																														this.symTable.addSymbol((SymbolItem)$$);
																													}
																													else{
																														yyerror("Function name already used!", this.currentLine);
																													}
																													notify("Found function declaration: " + $$.toString(), currentLine);}
									|
									type _FUNCTION _ID _LPAREN _RPAREN 	function_body				{$$ = $3;
																													((SymbolItem)$$).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)$$).setArithmeticType(this.currentType);
																													this.currentLine = ((SymbolItem)$2).getToken().getLine();
																													if(!this.symTable.contains((SymbolItem)$$)){
																														this.symTable.addSymbol((SymbolItem)$$);
																													}
																													else{
																														yyerror("Function name already used!", this.currentLine);
																													}
																													notify("Found function declaration: " + $$.toString(), currentLine);}
									|
									type _FUNCTION _ID _LPAREN parameter _RPAREN error 			{yyerror("Missing function body.");}
									|
									type _FUNCTION _ID _LPAREN _RPAREN error 							{yyerror("Missing function body.");}
									|
									_FUNCTION _ID 	_LPAREN parameter _RPAREN 	function_body		{this.currentLine = ((SymbolItem)$1).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
									|
									_FUNCTION _ID 	_LPAREN _RPAREN 	function_body					{this.currentLine = ((SymbolItem)$1).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
									;

parameter:						_FUNCTION _ID
									|
									_INTEGER _ID
									|
									_LONGINTEGER _ID
									;

function_body:					declaration_sentences function_sentence_block 
									|
									function_sentence_block 
									;

function_sentence_block:   _LCBRACE executable_sentences return_sentence _RCBRACE
									|
									_LCBRACE return_sentence _RCBRACE
									|
									_LCBRACE error {yyerror("Expected sentence or return sentence.");}
									|
									_LCBRACE executable_sentences error {yyerror("Expected return sentence.");}
									|
									_LCBRACE executable_sentences return_sentence error {yyerror("Expected '}'.");}
									|
									_LCBRACE return_sentence {yyerror("Expected '}'.");}
									;

return_sentence:				_RETURN expression _SEMICOLON
									|
									_RETURN expression error {yyerror("Expected ';' after return statement");}
									|
									_RETURN error {yyerror("Expected something to return");}
									;

%%

LexicalAnalyzer lexAnalyzer;
SymbolsTable symTable;
TercetManager terManager;
Logger syntaxLog;
Logger syntaxErrLog;
Logger tokensLog;
Token currentToken;
SymbolItem.ArithmeticType currentType;
int currentLine;

public void notify(String msg){
	this.syntaxLog.addLog(msg, lexAnalyzer.getLineNumber());
}

public void notify(String msg, int line){
	this.syntaxLog.addLog(msg, line);
}

public void tokenfy(String msg, int line){
	this.tokensLog.addLog(msg, line);
}

public void yyerror(String error){
	this.syntaxErrLog.addLog(error, lexAnalyzer.getLineNumber());
}

public void yyerror(String error, int line){
	this.syntaxErrLog.addLog(error, line);
}

public void checkControlVars(SymbolItem s1, SymbolItem s2){
	if(!s1.getToken().getLex().equals(s2.getToken().getLex()))
		yyerror("Control variable must be the same at for Iteration",this.currentLine);
}

public int yylex() throws IOException{
	this.currentToken = lexAnalyzer.getToken();
	tokenfy(this.currentToken.toString(), this.currentToken.getLine());
	yylval = new SymbolItem(this.currentToken);
	if(this.currentToken.getCode() == _END)
		return 0;
	return this.currentToken.getCode();
}

public Logger getSyntaxStructures(){
    return this.syntaxLog;
}

public Logger getErrors(){
    return this.syntaxErrLog;
}

public Logger getTokensLog(){
    return this.tokensLog;
}

public Parser(LexicalAnalyzer la, SymbolsTable st, TercetManager tm){
	this.lexAnalyzer = la;
	this.symTable = st;
	this.terManager = tm;
	this.syntaxLog = new Logger("SYNTAX");
	this.syntaxErrLog = new Logger("SYNTAX ERROR");
	this.tokensLog = new Logger("TOKENS");
}
public void Run() throws IOException{
  yyparse();
}