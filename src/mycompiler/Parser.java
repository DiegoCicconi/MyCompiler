//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 3 "MyGrammar.y"
package mycompiler;

import java.io.IOException;

//#line 22 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Referenceable
String   yytext;//user variable to return contextual strings
Referenceable yyval; //used to return semantic vals from action routines
Referenceable yylval;//the 'lval' (result) I got from yylex()
Referenceable valstk[] = new Referenceable[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Referenceable();
  yylval=new Referenceable();
  valptr=-1;
}
final void val_push(Referenceable val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Referenceable[] newstack = new Referenceable[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Referenceable val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Referenceable val_peek(int relative)
{
  return valstk[valptr-relative];
}
final Referenceable dup_yyval(Referenceable val)
{
  return val;
}
//#### end semantic value section ####
public final static short _IF=257;
public final static short _ELSE=258;
public final static short _ENDIF=259;
public final static short _INTEGER=260;
public final static short _LONGINTEGER=261;
public final static short _PRINT=262;
public final static short _FUNCTION=263;
public final static short _RETURN=264;
public final static short _FOR=265;
public final static short _INTTOLONG=266;
public final static short _ALLOW=267;
public final static short _TO=268;
public final static short _ID=269;
public final static short _INT=270;
public final static short _LONGINT=271;
public final static short _ANNOT=272;
public final static short _STRING=273;
public final static short _PLUS=274;
public final static short _MINUS=275;
public final static short _MULT=276;
public final static short _DIV=277;
public final static short _COLON=278;
public final static short _EQUAL=279;
public final static short _ASSIGN=280;
public final static short _LESSER=281;
public final static short _LESSER_OR_EQUAL=282;
public final static short _GREATER=283;
public final static short _GREATER_OR_EQUAL=284;
public final static short _UNEQUAL=285;
public final static short _MINUS_ONE=286;
public final static short _LPAREN=287;
public final static short _RPAREN=288;
public final static short _LCBRACE=289;
public final static short _RCBRACE=290;
public final static short _COMMA=291;
public final static short _SEMICOLON=292;
public final static short _END=293;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    1,    1,    3,    3,    6,
    6,    7,    7,    2,    2,    5,    5,    5,    5,    5,
   11,   11,   12,   12,   12,   13,   13,   13,   14,   14,
   14,   14,   15,   15,    8,    8,    8,    8,    8,   18,
   18,   16,   19,   19,   19,   19,   19,   19,   19,   17,
   17,   17,    9,    9,    9,    9,    9,    9,   10,   20,
   20,   20,   21,   21,   21,   22,   22,   22,   22,   22,
   22,   23,   23,    4,    4,    4,    4,    4,    4,
};
final static short yylen[] = {                            2,
    5,    4,    3,    2,    2,    1,    1,    2,    2,    1,
    1,    3,    1,    2,    1,    2,    1,    2,    2,    3,
    3,    2,    3,    3,    1,    3,    3,    1,    1,    1,
    4,    2,    1,    1,    6,    7,    7,    4,    5,    2,
    2,    3,    1,    1,    1,    1,    1,    1,    1,    3,
    1,    3,   13,   12,   13,   12,    7,    3,    4,    2,
    2,    2,    3,    3,    2,    4,    3,    2,    3,    4,
    2,    2,    1,    7,    6,    7,    6,    6,    5,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   10,   11,    0,    0,    0,    0,    0,
    0,    0,    6,    7,    0,    0,   17,    0,    0,    0,
    0,    0,    0,    0,   22,    0,   15,    0,    0,    4,
    5,    9,    0,   13,    0,   16,   18,    0,    0,    0,
   33,   34,    0,    0,   28,   30,    0,    0,    0,   58,
    0,    0,    2,   14,    0,    3,    0,    0,   20,    0,
   32,   49,    0,    0,   47,   43,   45,   44,   46,   48,
    0,    0,    0,   38,    0,   59,    0,    0,    0,    0,
    0,    0,    1,    0,   12,    0,    0,    0,    0,   26,
   27,   39,    0,   51,    0,   61,   60,   62,    0,    0,
   73,   79,    0,    0,    0,    0,   31,    0,    0,   35,
    0,   68,    0,    0,    0,   72,   78,    0,   77,   75,
    0,   52,   50,   41,   40,   37,   36,   65,    0,   69,
    0,   67,   57,    0,   76,   74,   64,   63,   70,   66,
    0,    0,    0,    0,    0,    0,    0,   56,   54,   55,
   53,
};
final static short yydgoto[] = {                          2,
  100,   26,   12,   13,   14,   15,   35,   16,   17,   18,
   19,   43,   44,   45,   46,   47,   95,  111,   71,   81,
  115,  101,  102,
};
final static short yysindex[] = {                      -218,
  -28,    0, -265,    0,    0, -215, -181, -191, -255,   42,
 -240, -165,    0,    0,  -44, -142,    0, -140, -119,   -4,
 -171, -110, -239,   -4,    0, -233,    0,   42, -111,    0,
    0,    0, -123,    0, -108,    0,    0,  -80,  -86,  -88,
    0,    0,    1, -190,    0,    0, -247,  -84,  -89,    0,
  -78, -241,    0,    0, -184,    0,  -79,  -60,    0,   -4,
    0,    0,   -4,   -4,    0,    0,    0,    0,    0,    0,
   -4,   -4,   -4,    0,  -62,    0,  -59,  -56,  -49,  -11,
  -66,   -4,    0,  -18,    0, -236, -190, -190, -241,    0,
    0,    0,   42,    0, -146,    0,    0,    0,   31, -166,
    0,    0,  -11, -264,  -93,  -65,    0, -250,  -51,    0,
 -182,    0,  -91,   41,  -64,    0,    0,    3,    0,    0,
  -72,    0,    0,    0,    0,    0,    0,    0, -248,    0,
 -243,    0,    0,  -52,    0,    0,    0,    0,    0,    0,
  -25, -210,   -4,  -33, -195,  -41,  -26,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -45,    0,    0, -103,    0, -221,
    0,    0,    0, -174,    0,    0,    0,    0,    0,    0,
    0,  -36,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -153, -141, -242,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -144,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  259,  -24,   -9,   -8,  -10,    0,    0,    0,    0,    0,
    0,  -23,   61,   76,    0,  146, -104,    0,    0,  184,
  157,  177,  -37,
};
final static int YYTABLESIZE=311;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         27,
   52,   29,   30,   55,  125,  122,    3,  137,   74,   63,
   64,    6,  139,   42,    8,   54,   50,   27,    9,    4,
    5,   20,    7,    3,   24,   63,   64,  118,    6,   51,
   25,    8,   63,   64,   29,    9,   86,   63,   64,  123,
   75,  149,  151,  138,   54,   42,  140,   89,   28,   42,
    1,  107,   29,   29,   29,   29,   53,   29,  104,   29,
   29,   29,   29,   29,   94,  117,   29,  120,  108,  143,
   29,   21,    3,  126,  114,  144,  127,    6,   63,   64,
    8,   25,   27,  136,    9,   72,   73,   22,   27,  129,
   29,   30,  147,    4,    5,   23,    7,   54,   94,   25,
   25,   48,   23,   54,   25,   83,   25,   25,   25,   25,
   25,  109,  110,   25,   24,   71,   71,   25,   71,  145,
   23,   23,   99,   87,   88,   23,   31,   23,   23,   23,
   23,   23,   24,   24,   23,   94,   94,   24,   23,   24,
   24,   24,   24,   24,   71,   57,   24,   90,   91,   36,
   24,   37,   19,   19,   19,   19,   19,   19,   19,   19,
   19,   19,  119,    3,  128,   19,    4,    5,    6,    7,
   77,    8,   38,   78,   39,    9,   49,   40,   41,   42,
   56,   79,   58,  135,    3,   19,   19,    4,    5,    6,
    7,   59,    8,   92,    3,   99,    9,   61,   80,    6,
   60,   82,    8,   76,  124,    3,    9,   84,   85,   96,
    6,   32,   97,    8,  148,    3,   99,    9,   33,   98,
    6,  103,  121,    8,   34,  132,   93,    9,    3,  150,
    3,    4,    5,    6,    7,    6,    8,   93,    8,  141,
    9,   77,    9,  142,   78,    3,    8,   93,    4,    5,
    6,    7,   79,    8,  146,   21,   62,    9,  133,   11,
   10,   39,   93,  134,   40,   41,   42,  106,   39,  105,
  131,   40,   41,   42,   63,   64,  116,   99,    0,   65,
    0,   66,   67,   68,   69,   70,  112,    3,    0,    0,
    0,    0,    6,    0,  113,    8,  130,    3,    3,    9,
    0,    0,    6,    6,  113,    8,    8,    0,    0,    9,
    9,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         10,
   24,   11,   11,   28,  109,  256,  257,  256,  256,  274,
  275,  262,  256,  256,  265,   26,  256,   28,  269,  260,
  261,  287,  263,  257,  280,  274,  275,  292,  262,  269,
  286,  265,  274,  275,  256,  269,   60,  274,  275,  290,
  288,  146,  147,  292,   55,  288,  290,   71,  289,  292,
  269,  288,  274,  275,  276,  277,  290,  279,   82,  281,
  282,  283,  284,  285,   75,  103,  288,  105,   93,  280,
  292,  287,  257,  256,   99,  286,  259,  262,  274,  275,
  265,  256,   93,  121,  269,  276,  277,  269,   99,  113,
  100,  100,  288,  260,  261,  287,  263,  108,  109,  274,
  275,  273,  256,  114,  279,  290,  281,  282,  283,  284,
  285,  258,  259,  288,  256,  260,  261,  292,  263,  143,
  274,  275,  289,   63,   64,  279,  292,  281,  282,  283,
  284,  285,  274,  275,  288,  146,  147,  279,  292,  281,
  282,  283,  284,  285,  289,  269,  288,   72,   73,  292,
  292,  292,  256,  257,  258,  259,  260,  261,  262,  263,
  264,  265,  256,  257,  256,  269,  260,  261,  262,  263,
  260,  265,  292,  263,  266,  269,  287,  269,  270,  271,
  292,  271,  291,  256,  257,  289,  290,  260,  261,  262,
  263,  272,  265,  256,  257,  289,  269,  286,  288,  262,
  287,  280,  265,  288,  256,  257,  269,  287,  269,  269,
  262,  256,  269,  265,  256,  257,  289,  269,  263,  269,
  262,  288,  288,  265,  269,  290,  289,  269,  257,  256,
  257,  260,  261,  262,  263,  262,  265,  289,  265,  292,
  269,  260,  269,  269,  263,  257,  292,  289,  260,  261,
  262,  263,  271,  265,  288,  292,  256,  269,  256,    1,
  289,  266,  289,  118,  269,  270,  271,   84,  266,  288,
  114,  269,  270,  271,  274,  275,  100,  289,   -1,  279,
   -1,  281,  282,  283,  284,  285,  256,  257,   -1,   -1,
   -1,   -1,  262,   -1,  264,  265,  256,  257,  257,  269,
   -1,   -1,  262,  262,  264,  265,  265,   -1,   -1,  269,
  269,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=293;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"_IF","_ELSE","_ENDIF","_INTEGER","_LONGINTEGER","_PRINT",
"_FUNCTION","_RETURN","_FOR","_INTTOLONG","_ALLOW","_TO","_ID","_INT",
"_LONGINT","_ANNOT","_STRING","_PLUS","_MINUS","_MULT","_DIV","_COLON","_EQUAL",
"_ASSIGN","_LESSER","_LESSER_OR_EQUAL","_GREATER","_GREATER_OR_EQUAL",
"_UNEQUAL","_MINUS_ONE","_LPAREN","_RPAREN","_LCBRACE","_RCBRACE","_COMMA",
"_SEMICOLON","_END",
};
final static String yyrule[] = {
"$accept : program",
"program : _ID declaration_sentences _LCBRACE executable_sentences _RCBRACE",
"program : _ID _LCBRACE executable_sentences _RCBRACE",
"declaration_sentences : declaration_sentences type_var_list _SEMICOLON",
"declaration_sentences : declaration_sentences function",
"declaration_sentences : type_var_list _SEMICOLON",
"declaration_sentences : function",
"declaration_sentences : sentence",
"type_var_list : type var_list",
"type_var_list : type error",
"type : _INTEGER",
"type : _LONGINTEGER",
"var_list : var_list _COMMA _ID",
"var_list : _ID",
"executable_sentences : executable_sentences sentence",
"executable_sentences : sentence",
"sentence : selection _SEMICOLON",
"sentence : iteration",
"sentence : print _SEMICOLON",
"sentence : assignment _SEMICOLON",
"sentence : assignment _SEMICOLON _ANNOT",
"assignment : _ID _ASSIGN expression",
"assignment : _ID _MINUS_ONE",
"expression : expression _PLUS term",
"expression : expression _MINUS term",
"expression : term",
"term : term _MULT factor",
"term : term _DIV factor",
"term : factor",
"factor : _ID",
"factor : constant",
"factor : _INTTOLONG _LPAREN expression _RPAREN",
"factor : _ID _MINUS_ONE",
"constant : _INT",
"constant : _LONGINT",
"selection : _IF _LPAREN condition _RPAREN sentence_block _ENDIF",
"selection : _IF _LPAREN condition _RPAREN sentence_block else_section _ENDIF",
"selection : _IF _LPAREN condition _RPAREN sentence_block else_section error",
"selection : _IF _LPAREN condition error",
"selection : _IF _LPAREN condition _RPAREN error",
"else_section : _ELSE sentence_block",
"else_section : _ELSE error",
"condition : expression comparator expression",
"comparator : _LESSER",
"comparator : _GREATER",
"comparator : _LESSER_OR_EQUAL",
"comparator : _GREATER_OR_EQUAL",
"comparator : _EQUAL",
"comparator : _UNEQUAL",
"comparator : error",
"sentence_block : _LCBRACE executable_sentences _RCBRACE",
"sentence_block : sentence",
"sentence_block : _LCBRACE executable_sentences error",
"iteration : _FOR _LPAREN _ID _ASSIGN expression _SEMICOLON condition _SEMICOLON _ID _ASSIGN expression _RPAREN sentence_block",
"iteration : _FOR _LPAREN _ID _ASSIGN expression _SEMICOLON condition _SEMICOLON _ID _MINUS_ONE _RPAREN sentence_block",
"iteration : _FOR _LPAREN _ID _ASSIGN expression _SEMICOLON condition _SEMICOLON _ID _ASSIGN expression _RPAREN error",
"iteration : _FOR _LPAREN _ID _ASSIGN expression _SEMICOLON condition _SEMICOLON _ID _MINUS_ONE _RPAREN error",
"iteration : _FOR _LPAREN _ID _ASSIGN expression _SEMICOLON error",
"iteration : _FOR _LPAREN error",
"print : _PRINT _LPAREN _STRING _RPAREN",
"parameter : _FUNCTION _ID",
"parameter : _INTEGER _ID",
"parameter : _LONGINT _ID",
"return_sentence : _RETURN expression _SEMICOLON",
"return_sentence : _RETURN expression error",
"return_sentence : _RETURN error",
"function_sentence_block : _LCBRACE executable_sentences return_sentence _RCBRACE",
"function_sentence_block : _LCBRACE return_sentence _RCBRACE",
"function_sentence_block : _LCBRACE error",
"function_sentence_block : _LCBRACE executable_sentences error",
"function_sentence_block : _LCBRACE executable_sentences return_sentence error",
"function_sentence_block : _LCBRACE return_sentence",
"function_body : declaration_sentences function_sentence_block",
"function_body : function_sentence_block",
"function : type _FUNCTION _ID _LPAREN parameter _RPAREN function_body",
"function : type _FUNCTION _ID _LPAREN _RPAREN function_body",
"function : type _FUNCTION _ID _LPAREN parameter _RPAREN error",
"function : type _FUNCTION _ID _LPAREN _RPAREN error",
"function : _FUNCTION _ID _LPAREN parameter _RPAREN function_body",
"function : _FUNCTION _ID _LPAREN _RPAREN function_body",
};

//#line 320 "MyGrammar.y"

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
//#line 468 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
throws IOException
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 59 "MyGrammar.y"
{yyval = val_peek(4); notify(yyval.toString() + " Compilated Successfully!", ((SymbolItem)val_peek(0)).getToken().getLine());}
break;
case 2:
//#line 62 "MyGrammar.y"
{yyval = val_peek(3); notify(yyval.toString() + " Compilated Successfully!", ((SymbolItem)val_peek(0)).getToken().getLine());}
break;
case 7:
//#line 73 "MyGrammar.y"
{yyerror("Sentences can't be used in the declarative portion of the code!");}
break;
case 9:
//#line 78 "MyGrammar.y"
{yyerror("Identifier(s) expected at declaration");}
break;
case 10:
//#line 81 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.INT;}
break;
case 11:
//#line 83 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.LONG;}
break;
case 12:
//#line 85 "MyGrammar.y"
{yyval = val_peek(0);
																((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																((SymbolItem)yyval).setArithmeticType(this.currentType);
																if(!this.symTable.contains((SymbolItem)yyval)){
																	this.symTable.addSymbol((SymbolItem)yyval);	
																}
																else {
																	yyerror("Variable already declared!");
																}
																notify("Found variable declaration: " + yyval.toString());}
break;
case 13:
//#line 96 "MyGrammar.y"
{yyval = val_peek(0);
																((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																((SymbolItem)yyval).setArithmeticType(this.currentType);
																if(!this.symTable.contains((SymbolItem)yyval)){
																	this.symTable.addSymbol((SymbolItem)yyval);	
																}
																else {
																	yyerror("Variable already declared!");
																}
																notify("Found variable declaration: " + yyval.toString());}
break;
case 16:
//#line 113 "MyGrammar.y"
{notify("Found Selection sentence ", this.currentLine);}
break;
case 17:
//#line 115 "MyGrammar.y"
{notify("Found Iteration sentence ", this.currentLine);}
break;
case 18:
//#line 117 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Print sentence: " + yyval.toString(), this.currentLine); this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 19:
//#line 119 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Assignment of: " + yyval.toString());}
break;
case 20:
//#line 121 "MyGrammar.y"
{yyval = val_peek(2); notify("Found Assignment with annotation of: " + yyval.toString());}
break;
case 21:
//#line 124 "MyGrammar.y"
{yyval = val_peek(2);
																	((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR);
																	if(!this.symTable.contains((SymbolItem)yyval)){ 
																		yyerror("Variable not declared!");
																	}
																	else {
																		yyval = this.symTable.getSymbol((SymbolItem)yyval);
																		Tercet toAdd = new Assignment(val_peek(2),val_peek(0),"");
																		int index = this.terManager.addTercet(toAdd);
																		toAdd.setIndex(index);
																	}}
break;
case 22:
//#line 136 "MyGrammar.y"
{yyval = val_peek(1);
																	((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																	if(!this.symTable.contains((SymbolItem)yyval)){ 
																		yyerror("Variable not declared!");
																	}
																	else {
																		yyval = this.symTable.getSymbol((SymbolItem)yyval);
																	}}
break;
case 23:
//#line 146 "MyGrammar.y"
{yyval = new Addition(val_peek(2),val_peek(0),"");
																	int index = this.terManager.addTercet((Tercet)yyval);
																	((Tercet)yyval).setIndex(index);}
break;
case 24:
//#line 150 "MyGrammar.y"
{yyval = new Subtraction(val_peek(2),val_peek(0),""); 
																	int index = this.terManager.addTercet((Tercet)yyval);
																	((Tercet)yyval).setIndex(index);}
break;
case 25:
//#line 154 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 26:
//#line 157 "MyGrammar.y"
{yyval = new Multiplication(val_peek(2),val_peek(0),""); 
																int index = this.terManager.addTercet((Tercet)yyval);
																((Tercet)yyval).setIndex(index);}
break;
case 27:
//#line 161 "MyGrammar.y"
{yyval = new Division(val_peek(2),val_peek(0),"");
																int index = this.terManager.addTercet((Tercet)yyval);
																((Tercet)yyval).setIndex(index);}
break;
case 28:
//#line 165 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 29:
//#line 168 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																if(!this.symTable.contains((SymbolItem)yyval)){ yyerror("Variable not declared!");}}
break;
case 30:
//#line 171 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.CONST); 
																((SymbolItem)yyval).setArithmeticType(this.currentType);
																this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 32:
//#line 177 "MyGrammar.y"
{yyval = val_peek(1); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																if(!this.symTable.contains((SymbolItem)yyval)){ yyerror("Variable not declared!");}}
break;
case 33:
//#line 181 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.INT;}
break;
case 34:
//#line 183 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.LONG;}
break;
case 35:
//#line 186 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();}
break;
case 36:
//#line 188 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine();}
break;
case 37:
//#line 190 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine(); 
																															yyerror("Expected endif;");}
break;
case 38:
//#line 193 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(3)).getToken().getLine();
																															yyerror("Expected ')'", this.currentLine);}
break;
case 39:
//#line 196 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(4)).getToken().getLine();
																															yyerror("Wrong if statement, sentence(s) expected");}
break;
case 41:
//#line 202 "MyGrammar.y"
{yyerror("Wrong else statement, sentence(s) expected");}
break;
case 49:
//#line 220 "MyGrammar.y"
{yyerror("Invalid operator, comparator expected");}
break;
case 52:
//#line 227 "MyGrammar.y"
{yyerror("Expected '}'");}
break;
case 53:
//#line 232 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(12)).getToken().getLine(); checkControlVars((SymbolItem)val_peek(10),(SymbolItem)val_peek(4));}
break;
case 54:
//#line 235 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(11)).getToken().getLine(); checkControlVars((SymbolItem)val_peek(9),(SymbolItem)val_peek(3));}
break;
case 55:
//#line 237 "MyGrammar.y"
{yyerror("Missing 'for' body.");}
break;
case 56:
//#line 239 "MyGrammar.y"
{yyerror("Missing 'for' body.");}
break;
case 57:
//#line 241 "MyGrammar.y"
{yyerror("Missing condition.");}
break;
case 58:
//#line 243 "MyGrammar.y"
{yyerror("Missing assignment. What were you planning on iterating on?");}
break;
case 59:
//#line 247 "MyGrammar.y"
{yyval = val_peek(1); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.STR); this.currentLine = ((SymbolItem)val_peek(3)).getToken().getLine();}
break;
case 64:
//#line 259 "MyGrammar.y"
{yyerror("Expected ';' after return statement");}
break;
case 65:
//#line 261 "MyGrammar.y"
{yyerror("Expected something to return");}
break;
case 68:
//#line 268 "MyGrammar.y"
{yyerror("Expected sentence or return sentence.");}
break;
case 69:
//#line 270 "MyGrammar.y"
{yyerror("Expected return sentence.");}
break;
case 70:
//#line 272 "MyGrammar.y"
{yyerror("Expected '}'.");}
break;
case 71:
//#line 274 "MyGrammar.y"
{yyerror("Expected '}'.");}
break;
case 74:
//#line 282 "MyGrammar.y"
{yyval = val_peek(4);
																													((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)yyval).setArithmeticType(this.currentType);
																													this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();
																													if(!this.symTable.contains((SymbolItem)yyval)){
																														this.symTable.addSymbol((SymbolItem)yyval);
																													}
																													else{
																														yyerror("Function name already used!", this.currentLine);
																													}
																													notify("Found function declaration: " + yyval.toString(), currentLine);}
break;
case 75:
//#line 294 "MyGrammar.y"
{yyval = val_peek(3);
																													((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)yyval).setArithmeticType(this.currentType);
																													this.currentLine = ((SymbolItem)val_peek(4)).getToken().getLine();
																													if(!this.symTable.contains((SymbolItem)yyval)){
																														this.symTable.addSymbol((SymbolItem)yyval);
																													}
																													else{
																														yyerror("Function name already used!", this.currentLine);
																													}
																													notify("Found function declaration: " + yyval.toString(), currentLine);}
break;
case 76:
//#line 306 "MyGrammar.y"
{yyerror("Missing function body.");}
break;
case 77:
//#line 308 "MyGrammar.y"
{yyerror("Missing function body.");}
break;
case 78:
//#line 310 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
break;
case 79:
//#line 313 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(4)).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
break;
//#line 901 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
