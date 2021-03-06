/* Declarations Section */
%{
package mycompiler;

import java.io.IOException;
import java.util.Stack;
import java.util.LinkedList;

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

program:                 	_ID {this.currentScope = new Scope($1.getLex());}
									declaration_sentences executabe_part 
									{$$ = $1; notify($$.toString() + " End Of File Reached!", ((SymbolItem)$4).getToken().getLine());}
									|				
									_ID {this.currentScope = new Scope($1.getLex());}
									executabe_part
									{$$ = $1; notify($$.toString() + " End Of File Reached!", ((SymbolItem)$3).getToken().getLine());}
									;

executabe_part: 				_LCBRACE executable_sentences _RCBRACE 	{$$ = $3;}
									|
									_LCBRACE _RCBRACE		{$$ = $2;}
									;

declaration_sentences:     declaration_sentences type_var_list _SEMICOLON
									|
									declaration_sentences function
									|
									type_var_list _SEMICOLON 
									|
									function
									|
									declaration_sentences allow_conversion		 	
									|
									allow_conversion
									|
									sentence 			{yyerror("Sentences can't be used in the declarative portion of the code!");}
									;

allow_conversion: 			_ALLOW _INTEGER _TO _LONGINTEGER _SEMICOLON 	{notify("Conversion int to long allowed"); this.terManager.enableConversion();}
									;

type_var_list:					type var_list
									|
									type error 			{yyerror("Identifier(s) expected at declaration");}
									;

type:								_INTEGER 			{this.currentType = SymbolItem.ArithmeticType.INT;}
									|
									_LONGINTEGER 		{this.currentType = SymbolItem.ArithmeticType.LONG;}
									;

var_list:                  var_list _COMMA _ID 	{$$ = $3;
																((SymbolItem)$$).setSymbolUse(SymbolItem.Use.VAR); 
																((SymbolItem)$$).setArithmeticType(this.currentType);
																((SymbolItem)$$).setScope(this.currentScope.getScope());
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
																((SymbolItem)$$).setScope(this.currentScope.getScope());
																if(!this.symTable.contains((SymbolItem)$$)){
																	this.symTable.addSymbol((SymbolItem)$$);	
																}
																else {
																	yyerror("Variable already declared!");
																}
																notify("Found variable declaration: " + $$.toString());}
									;

function:						type _FUNCTION _ID _LPAREN parameter _RPAREN						{//pusheamos la scope para el contenido de adetro de la funcion
																													this.currentScope.pushScope(((SymbolItem)$3).getLex());
																													//le avisamos al manager que estamos adentro de una funcion
																													this.terManager.inFunction();
																													((SymbolItem)$5).setScope(this.currentScope.getScope());
																													Tercet function = new Function($3,$5,this.currentScope.getScope());
																													function.setIndex(this.terManager.addTercet(function));}
																								function_body 	{$$ = $3;
																													//el $8 es el function_body, que recursivamente nos devuelve la sentencia del return
																													Tercet ret = new Return($3,$8,this.currentScope.getScope());
																													ret.setIndex(this.terManager.addTercet(ret));
																													//avisamos que salimos de la funcion y sacamos la Scope del stack
																													this.terManager.outOfFunction();
																													this.currentScope.popScope();
																													//chequeamos que el nombre de la funcion no este usado ya
																													((SymbolItem)$$).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)$$).setArithmeticType(this.currentType);
																													((SymbolItem)$$).setScope(this.currentScope.getScope());
																													if(!this.symTable.contains((SymbolItem)$$)){
																														this.symTable.addSymbol((SymbolItem)$$);
																													}
																													else{
																														this.currentLine = ((SymbolItem)$2).getToken().getLine();
																														yyerror("Function name already used!", this.currentLine);
																													}
																													notify("Found function declaration: " + $$.toString(), currentLine);}
									|
									type _FUNCTION _ID _LPAREN _RPAREN									{this.currentScope.pushScope(((SymbolItem)$3).getLex());
																													this.terManager.inFunction();
																													Tercet function = new Function($3,null,this.currentScope.getScope());
																													function.setIndex(this.terManager.addTercet(function));}
															 						function_body				{$$ = $3;
															 														Tercet ret = new Return($3,$7,this.currentScope.getScope());
																													ret.setIndex(this.terManager.addTercet(ret));
															 														this.terManager.outOfFunction();
																													this.currentScope.popScope();
																													((SymbolItem)$$).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)$$).setArithmeticType(this.currentType);
																													((SymbolItem)$$).setScope(this.currentScope.getScope());
																													if(!this.symTable.contains((SymbolItem)$$)){
																														this.symTable.addSymbol((SymbolItem)$$);
																													}
																													else{
																														this.currentLine = ((SymbolItem)$2).getToken().getLine();
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

parameter:						type _FUNCTION _ID _LPAREN _RPAREN									{$$ = $3; 
																													// comprobamos que exista la funcion del parametro..
																													((SymbolItem)$$).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)$$).setArithmeticType(this.currentType);
																													((SymbolItem)$$).setScope(this.currentScope.getScope());
																													// ..y que sea del mismo tipo aritmetico
																													if(!this.symTable.containsArithmetic((SymbolItem)$$)){ 
																														this.currentLine = ((SymbolItem)$3).getToken().getLine();
																														yyerror("Function not declared!" , this.currentLine);
																													}
																													else {
																														((SymbolItem)$$).setSymbolUse(SymbolItem.Use.PARAM);
																														((SymbolItem)$$).setArithmeticType(this.currentType);
																														this.symTable.addSymbol((SymbolItem)$$);
																													}}
									|
									type _ID 																	{$$ = $2;
																													((SymbolItem)$$).setSymbolUse(SymbolItem.Use.PARAM); 
																													((SymbolItem)$$).setArithmeticType(this.currentType);
																													((SymbolItem)$$).setScope(this.currentScope.getScope());
																													this.symTable.addSymbol((SymbolItem)$$);}
									;

function_body:					declaration_sentences function_sentence_block 				{$$ = $2;}
									|
									function_sentence_block 											{$$ = $1;}
									;

function_sentence_block:   _LCBRACE executable_sentences return_sentence _RCBRACE 	{$$ = $3;}
									|
									_LCBRACE return_sentence _RCBRACE								{$$ = $2;}
									|
									_LCBRACE error 													{yyerror("Expected sentence or return sentence.");}
									|
									_LCBRACE executable_sentences error 						{yyerror("Expected return sentence.");}
									|
									_LCBRACE executable_sentences return_sentence error 	{yyerror("Expected '}'.");}
									|
									_LCBRACE return_sentence 										{yyerror("Expected '}'.");}
									;

return_sentence:				_RETURN expression _SEMICOLON			{$$ = $2;}
									|
									_RETURN expression error 				{yyerror("Expected ';' after return statement");}
									;

executable_sentences:      executable_sentences sentence
									|
									sentence
									;

sentence:               	selection  _SEMICOLON			{notify("Found Selection sentence ", this.currentLine);}
									|		
									iteration  							{notify("Found Iteration sentence ", this.currentLine);}
									|		
									print _SEMICOLON 					{$$ = $1; notify("Found Print sentence: " + $$.toString(), this.currentLine);}
									|		
									assignment _SEMICOLON 			{$$ = $1; notify("Found Assignment of: " + $$.toString(), this.currentLine);}
									|
									assignment _SEMICOLON _ANNOT 	{$$ = $1; notify("Found Assignment with annotation of: " + $$.toString(), this.currentLine);}
									|
									functionCall _SEMICOLON			{$$ = $1; notify("Found Function Invocation");}
									;

assignment:             	_ID _ASSIGN expression 	{$$ = $1; 
																	((SymbolItem)$$).setSymbolUse(SymbolItem.Use.VAR);
																	((SymbolItem)$$).setScope(this.currentScope.getScope());
																	SymbolItem variable = this.symTable.getSymbol((SymbolItem)$$);
																	if(variable == null){ 
																		this.currentLine = ((SymbolItem)$1).getToken().getLine();
																		yyerror("Variable not declared!" , this.currentLine);
																	}
																	else {
																		$$ = variable;
																		Tercet toAdd = new Assignment($1,$3,"");
																		toAdd.setIndex(this.terManager.addTercet(toAdd));
																	}}
									|
									decrement
									;

expression:             	expression _PLUS term	{$$ = new Addition($1,$3,"");
																	((Tercet)$$).setIndex(this.terManager.addTercet((Tercet)$$));}
									|
									expression _MINUS term	{$$ = new Subtraction($1,$3,"");
																	((Tercet)$$).setIndex(this.terManager.addTercet((Tercet)$$));}
									|
									term							{$$ = $1;}

									|
									_INTTOLONG _LPAREN expression _RPAREN
									{$$ = $3; 
									//preguntamos al manager si fueron habilitadas las conversiones
									if(terManager.conversionAllowed()){$$.setArithmeticType(SymbolItem.ArithmeticType.LONG);}
									else {yyerror("Conversion not allowed", ((SymbolItem)$1).getToken().getLine());}}
									;

term:                      term _MULT factor		{$$ = new Multiplication($1,$3,"");
																((Tercet)$$).setIndex(this.terManager.addTercet((Tercet)$$));}
									|
									term _DIV factor		{$$ = new Division($1,$3,"");
																((Tercet)$$).setIndex(this.terManager.addTercet((Tercet)$$));}
									|
									factor					{$$ = $1;}
									;

factor:                    _ID 						{$$ = $1;
																((SymbolItem)$$).setSymbolUse(SymbolItem.Use.VAR);
																((SymbolItem)$$).setScope(this.currentScope.getScope());
																//getSymnol busca en nuestra Scope y en todas las scopes por encima nuestro
																SymbolItem variable = this.symTable.getSymbol((SymbolItem)$$);
																if(variable == null){ 
																	//puede ser la variable que representa una funcion
																	((SymbolItem)$$).setSymbolUse(SymbolItem.Use.FUNC);
																	SymbolItem function = this.symTable.getSymbol((SymbolItem)$$);
																	if(function == null){
																		//si estamos en una funcion puede ser que estemos buscando un parametro
																		if(!this.terManager.areWeInFunction())
																			yyerror("Variable not declared!" , this.currentLine);
																		else{
																		//como estamos adentro de una funcion, buscamos si es un parametro
																		((SymbolItem)$$).setSymbolUse(SymbolItem.Use.PARAM);
																		SymbolItem parameter = this.symTable.getSymbol((SymbolItem)$$);
																		if(parameter == null)
																			yyerror("Variable not declared!" , this.currentLine);
																		else
																			$$ = parameter;
																		}
																	}
																	else{
																		$$ = function;
																	}
																}
																else {
																	$$ = variable;
																}}
									|
									constant					{$$ = $1; ((SymbolItem)$$).setSymbolUse(SymbolItem.Use.CONST); 
																((SymbolItem)$$).setArithmeticType(this.currentType);
																if(!this.symTable.contains((SymbolItem)$$))
																	this.symTable.addSymbol((SymbolItem)$$);
																else
																	$$ = this.symTable.getSymbol((SymbolItem)$$);}
									|
									decrement 				{$$ = $1;}
									|
									functionCall			{yyerror("It is not allow to call a function in an expression");}
									;

constant:                  _INT 						{this.currentType = SymbolItem.ArithmeticType.INT;}
									|
									_LONGINT 				{this.currentType = SymbolItem.ArithmeticType.LONG;}
									;

decrement:						_ID _MINUS_ONE			{$$ = $1;
																((SymbolItem)$$).setSymbolUse(SymbolItem.Use.VAR);
																((SymbolItem)$$).setScope(this.currentScope.getScope());
																SymbolItem variable = this.symTable.getSymbol((SymbolItem)$$);
																if(variable == null){ 
																	this.currentLine = ((SymbolItem)$1).getToken().getLine();
																	//si estamos en una funcion puede ser que estemos buscando un parametro
																	if(!this.terManager.areWeInFunction())
																		yyerror("Variable not declared!" , this.currentLine);
																	else{
																		//como estamos adentro de una funcion, buscamos si es un parametro
																		((SymbolItem)$$).setSymbolUse(SymbolItem.Use.PARAM);
																		SymbolItem parameter = this.symTable.getSymbol((SymbolItem)$$);
																		if(parameter == null)
																			yyerror("Variable not declared!" , this.currentLine);
																		else
																			$$ = parameter;
																	}
																}
																else {
																	$$ = variable;
																	Tercet toAdd = new Decrement($$,null,this.currentScope.getScope());
																	toAdd.setIndex(this.terManager.addTercet(toAdd));
																}}
									;

functionCall:					_ID _LPAREN _RPAREN 			// SIN PARAMETRO
									{SymbolItem func = this.checkFunctionDeclaration((SymbolItem)$1);
									Tercet toAdd = new FunctionCall(func,null,null,this.currentScope.getScope());
									toAdd.setIndex(this.terManager.addTercet(toAdd));
									$$ = toAdd;}
									/*
									|
									_ID _LPAREN expression _RPAREN 			// CON EXPRESION COMO PARAMETRO
									{SymbolItem func = this.checkFunctionDeclaration((SymbolItem)$1);
									Tercet toAdd = new FunctionCall(func,$3,this.currentScope.getScope());
									this.terManager.addTercet(toAdd);
									$$ = toAdd;}
									*/
									|
									_ID _LPAREN _ID _RPAREN			// CON VARIABLE COMO PARAMETRO
									{$$ = $1; 
									SymbolItem func = this.checkFunctionDeclaration((SymbolItem)$1);
									SymbolItem var = this.checkVarDeclaration((SymbolItem)$3);
									//buscamos el nombre que tiene el parametro en la declaracion de la funcion
									String param = this.symTable.getFunctionParam(this.currentScope.getScope());
									Tercet toAdd = new FunctionCall(func,var,param,this.currentScope.getScope());
									toAdd.setIndex(this.terManager.addTercet(toAdd));}
									|
									_ID _LPAREN _ID _LPAREN _RPAREN _RPAREN	// CON FUNCION COMO PARAMETRO
									{$$ = $1; 
									SymbolItem func = this.checkFunctionDeclaration((SymbolItem)$1);
									SymbolItem param = this.checkFunctionDeclaration((SymbolItem)$3);
									Tercet toAdd = new FunctionCall(func,param,param.getScopedName(),this.currentScope.getScope());
									toAdd.setIndex(this.terManager.addTercet(toAdd));}
									;

selection:                 if_section _ENDIF						{this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																				this.currentLine = ((SymbolItem)$1).getToken().getLine();}
									|
									if_section else_section _ENDIF 	{this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																				this.currentLine = ((SymbolItem)$1).getToken().getLine();}
									|
									if_section else_section error 	{yyerror("Expected endif;");}
									|
									if_section error 						{yyerror("Expected endif;");}											
									;

if_section:						_IF _LPAREN condition _RPAREN sentence_block
									|
									_IF _LPAREN condition _RPAREN executable_sentences sentence		
									{this.currentLine = ((SymbolItem)$4).getToken().getLine() +2;
									yyerror("Wrong selection statement, more than one sentence must be between braces",this.currentLine);}			
									|
									_IF _LPAREN condition _RPAREN error 			{yyerror("Wrong selection statement, if section error");}
									;

else_section:              _ELSE 
																	{Tercet newBU = new BranchUnconditional(null,null,this.currentScope.getScope());
																	newBU.setIndex(this.terManager.addTercet(newBU));
																	this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																	this.conditionSTK.push((JumpTercet)newBU);}
											sentence_block
									|
									_ELSE error 				{yyerror("Wrong selection statement, else section error");}
									;

condition:                	expression comparator expression		{Tercet newComp = new Comparator(((SymbolItem)$2).getLex(),$1,$3,this.currentScope.getScope());
																					newComp.setIndex(this.terManager.addTercet(newComp));
																					$$ = newComp;
																					Tercet newBF = new BranchFalse(newComp,null,this.currentScope.getScope());
																					newBF.setIndex(this.terManager.addTercet(newBF));
																					this.conditionSTK.push((JumpTercet)newBF);}
                          	;

comparator:             	_LESSER 						{$$ = $1;}
									|
									_GREATER 					{$$ = $1;}
									|
									_LESSER_OR_EQUAL 			{$$ = $1;}
									|
									_GREATER_OR_EQUAL 		{$$ = $1;}
									|
									_EQUAL 						{$$ = $1;}
									|
									_UNEQUAL 					{$$ = $1;}
									|
									error 						{yyerror("Invalid operator, comparator expected");}
									;

sentence_block:     			_LCBRACE executable_sentences _RCBRACE
									|
									sentence
									|
									_LCBRACE executable_sentences error {yyerror("Expected '}'");}
									;

iteration:                 _FOR _LPAREN assignment _SEMICOLON 										{//salvamos la direccion de salto antes de evaluar la condicion
																														this.iterationJumpDir = this.terManager.getNextIndex();}
																					condition _SEMICOLON increment _RPAREN sentence_block	
									{checkControlVars((SymbolItem)$3,(SymbolItem)$8);
									//ponemos los tercetos del incremento al final de las sentencias del for
									Tercet incTer = this.incrementTercets.pollFirst();
									incTer.setIndex(this.terManager.addTercet(incTer));
										while(!this.incrementTercets.isEmpty()){
											incTer = this.incrementTercets.pollFirst();
											incTer.setIndex(this.terManager.addTercet(incTer));
										}
									//salto incondicional al final del for hacia la condicion 
									Tercet newBU = new BranchUnconditional(null,null,this.currentScope.getScope());
									newBU.setIndex(this.terManager.addTercet(newBU));
									((JumpTercet)newBU).setJumpDir(this.iterationJumpDir);
									//completamos con la direccion del salto hacia lo siguiente del for para continuar la ejecucion
									this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
									this.currentLine = ((SymbolItem)$1).getToken().getLine();}
									|
									_FOR _LPAREN assignment _SEMICOLON error {yyerror("Missing condition.");}
									|
									_FOR _LPAREN error {yyerror("Missing assignment. What were you planning on iterating on?");}
									;

increment:						_ID _ASSIGN for_expression	{$$ = this.checkVarDeclaration((SymbolItem)$1);
																		this.incrementTercets.addLast(new Assignment($1,$3,""));}
									|
									_ID _MINUS_ONE					{$$ = this.checkVarDeclaration((SymbolItem)$1);
																		this.incrementTercets.addLast(new Decrement($1,null,this.currentScope.getScope()));}
									;

for_expression: 				for_expression _PLUS for_expression		{this.incrementTercets.addLast(new Addition($1,$3,""));}
									|
									for_expression _MINUS for_expression	{this.incrementTercets.addLast(new Subtraction($1,$3,""));}
									|
									_ID 											{$$ = this.checkVarDeclaration((SymbolItem)$1);}
									|
									constant										{$$ = $1; ((SymbolItem)$$).setSymbolUse(SymbolItem.Use.CONST); 
																					((SymbolItem)$$).setArithmeticType(this.currentType);
																					this.symTable.addSymbol((SymbolItem)$$);}
									;

print:                     _PRINT _LPAREN _STRING _RPAREN 			
									{$$ = $3;
									((SymbolItem)$$).setSymbolUse(SymbolItem.Use.STR); 
									this.symTable.addSymbol((SymbolItem)$$);
									this.currentLine = ((SymbolItem)$1).getToken().getLine();
									$$ = new Print($3,null,this.currentScope.getScope());
									((Tercet)$$).setIndex(this.terManager.addTercet((Tercet)$$));
									$$ = $3;}
									;


%%

LexicalAnalyzer lexAnalyzer;
SymbolsTable symTable;
TercetManager terManager;
Logger syntaxLog;
Logger syntaxErrLog;
Logger tokensLog;
Token currentToken;
int currentLine;
SymbolItem.ArithmeticType currentType;
Scope currentScope;
Stack<JumpTercet> conditionSTK;
LinkedList<Tercet> incrementTercets = new LinkedList<>();
int iterationJumpDir;

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
public SymbolItem checkFunctionDeclaration(SymbolItem s){
	s.setSymbolUse(SymbolItem.Use.FUNC); 
	s.setScope(this.currentScope.getScope());
	if(!this.symTable.contains(s)){ 
		yyerror("Funcion not declared!");
		return null;
	}
	return this.symTable.getSymbol(s);
}
public SymbolItem checkVarDeclaration(SymbolItem s){
	s.setSymbolUse(SymbolItem.Use.VAR); 
	s.setScope(this.currentScope.getScope());
	if(!this.symTable.contains(s)){ 
		yyerror("Variable not declared!");
		return null;
	}
	return this.symTable.getSymbol(s);
}
public void checkControlVars(SymbolItem s1, SymbolItem s2){
	if(!s1.getLex().equals(s2.getLex()))
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
	this.conditionSTK = new Stack<JumpTercet>();
	this.syntaxLog = new Logger("SYNTAX");
	this.syntaxErrLog = new Logger("SYNTAX ERROR");
	this.tokensLog = new Logger("TOKENS");
}
public void Run() throws IOException{
  yyparse();
}