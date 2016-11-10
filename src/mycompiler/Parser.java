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
    2,    0,    4,    0,    1,    1,    1,    1,    1,    1,
    1,    5,    5,    8,    8,    9,    9,    3,    3,    7,
    7,    7,    7,    7,   13,   13,   14,   14,   14,   14,
   15,   15,   15,   16,   16,   16,   17,   17,   10,   10,
   10,   10,   10,   20,   20,   18,   21,   21,   21,   21,
   21,   21,   21,   19,   19,   19,   11,   11,   11,   11,
   11,   11,   12,   24,    6,   25,    6,    6,    6,    6,
    6,   22,   22,   22,   23,   23,   26,   26,   26,   26,
   26,   26,   27,   27,   27,
};
final static short yylen[] = {                            2,
    0,    6,    0,    5,    3,    2,    2,    1,    4,    2,
    1,    2,    2,    1,    1,    3,    1,    2,    1,    2,
    1,    2,    2,    3,    3,    2,    3,    3,    1,    4,
    3,    3,    1,    1,    1,    2,    1,    1,    6,    7,
    7,    4,    5,    2,    2,    3,    1,    1,    1,    1,
    1,    1,    1,    3,    1,    3,   13,   12,   13,   12,
    7,    3,    4,    0,    8,    0,    7,    7,    6,    6,
    5,    2,    2,    2,    2,    1,    4,    3,    2,    3,
    4,    2,    3,    3,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,   14,   15,    0,    0,    0,
    0,    0,    0,    0,    8,   11,    0,    0,   21,    0,
    0,    0,    0,    0,    0,    0,   10,    0,    0,   26,
    0,    0,    6,    7,   13,    0,   17,    0,   20,   22,
    0,    0,   19,    0,    0,   37,   38,    0,    0,   33,
   35,    0,    0,    0,   62,    0,    0,    0,    0,    5,
    0,    0,   24,    4,   18,    0,   36,   53,    0,    0,
   51,   47,   49,   48,   50,   52,    0,    0,    0,   42,
    0,   63,    0,    0,    0,    0,    0,    0,    9,    2,
    0,   16,    0,    0,    0,    0,   31,   32,   43,    0,
   55,    0,   73,   74,   72,    0,    0,   71,   76,    0,
    0,    0,    0,   30,    0,    0,   39,    0,   79,    0,
    0,    0,   75,   70,    0,   69,    0,    0,   56,   54,
   45,   44,   41,   40,   85,    0,   80,    0,   78,   61,
    0,   67,   68,    0,   84,   83,   81,   77,    0,   65,
    0,    0,    0,    0,    0,    0,   60,   58,   59,   57,
};
final static short yydgoto[] = {                          2,
  107,    3,   42,    4,   14,   15,   16,   17,   38,   18,
   19,   20,   21,   48,   49,   50,   51,   52,  102,  118,
   77,   87,  108,  144,  127,  109,  122,
};
final static short yysindex[] = {                      -250,
    0,    0,   56, -258, -213,    0,    0, -200, -186, -183,
 -175, -272, -248, -179,    0,    0, -245, -165,    0, -140,
 -132,   65, -154, -148, -181, -189,    0,  -84, -154,    0,
   65, -128,    0,    0,    0,  -91,    0,  -99,    0,    0,
  -77, -100,    0,  -86,  -89,    0,    0,   -2, -157,    0,
    0, -235,  -75, -197,    0,  -63,  -50,  -94,  -71,    0,
  -62,  -42,    0,    0,    0, -154,    0,    0, -133, -133,
    0,    0,    0,    0,    0,    0, -154, -133, -133,    0,
 -106,    0,  -38,  -36,  -30,  -55,  -47, -154,    0,    0,
    6,    0, -120, -157, -157,  -94,    0,    0,    0,   65,
    0,  -88,    0,    0,    0,   36,   -4,    0,    0,  -55,
 -204,   12,  -43,    0, -167,  -90,    0,  -98,    0, -194,
   46,  -32,    0,    0,   18,    0,  -55,   14,    0,    0,
    0,    0,    0,    0,    0, -206,    0, -239,    0,    0,
  -40,    0,    0,  -55,    0,    0,    0,    0,    7,    0,
 -264, -154,  -17,  -59,  -80,  -69,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
  -39,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   -6,    0,    0,
 -116,    0,    0,    0, -249,    0,    0,    0, -227,    0,
    0,    0,    0,    0,    0,    0,    0,    3,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -174, -153, -246,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -25,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    2,    0,    0,    0,    0,    0,  -14,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  275,    0,  -27,    0,  -11,  -10,  -22,    0,    0,    0,
    0,    0,    0,  -28,  134,  182,    0,  165, -111,    0,
    0,  205, -104,    0,    0,  190,  178,
};
final static int YYTABLESIZE=334;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         43,
   58,   32,   33,   59,  132,  124,   34,   29,   43,   46,
   35,    6,    7,   30,    9,  152,  147,   36,    1,   65,
   80,  153,  142,   37,   34,   34,   34,   34,   29,   34,
   22,   34,   34,   34,   34,   34,   65,   93,   34,  150,
   31,   46,   34,  158,  160,   46,   29,   29,   96,  145,
  148,   29,   81,   29,   29,   29,   29,   29,  101,  111,
   29,  135,   83,   84,   29,   85,   55,   69,   70,   69,
   70,   44,  115,   23,   45,   46,   47,   43,  121,   56,
   27,   27,   25,   43,   28,  146,   24,  125,  129,    5,
   86,  136,   65,  101,    8,   32,   33,   10,   65,   27,
   27,   12,   28,   26,   27,   54,   27,   27,   27,   27,
   27,   44,   34,   27,   45,   46,   47,   27,   78,   79,
   28,   28,  130,  154,   53,   28,   39,   28,   28,   28,
   28,   28,  101,  101,   28,   45,   46,   47,   28,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   99,
    5,   40,   23,   69,   70,    8,    5,  133,   10,   41,
  134,    8,   12,   60,   10,  131,    5,  114,   12,  116,
  117,    8,   23,   23,   10,  157,    5,   61,   12,   69,
   70,    8,  100,   57,   10,    5,  159,    5,   12,   64,
    8,   62,    8,   10,   63,   10,   67,   12,  100,   12,
   66,    5,   94,   95,    6,    7,    8,    9,  100,   10,
   89,   11,   82,   12,   69,   70,   88,    1,   90,  100,
    1,    1,    1,    1,   91,    1,   92,    1,  156,    1,
  103,   66,  104,  106,   66,   66,   66,   66,  105,   66,
  110,   66,   64,   66,  128,   64,   64,   64,   64,    3,
   64,  149,   64,   68,   64,    6,    7,  139,    9,   97,
   98,   82,   82,   66,   82,   83,   84,  126,   85,  143,
  155,   69,   70,  140,   64,  151,   71,   13,   72,   73,
   74,   75,   76,   44,  106,   12,   45,   46,   47,  141,
   82,  119,    5,  112,   25,  113,  123,    8,  138,  120,
   10,  137,    5,    0,   12,    0,    0,    8,    0,  120,
   10,    0,    5,    0,   12,    6,    7,    8,    9,    0,
   10,    5,   11,    0,   12,    0,    8,    0,    0,   10,
    0,    0,    0,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         22,
   29,   13,   13,   31,  116,  110,  256,  280,   31,  256,
  256,  260,  261,  286,  263,  280,  256,  263,  269,   42,
  256,  286,  127,  269,  274,  275,  276,  277,  256,  279,
  289,  281,  282,  283,  284,  285,   59,   66,  288,  144,
  289,  288,  292,  155,  156,  292,  274,  275,   77,  256,
  290,  279,  288,  281,  282,  283,  284,  285,   81,   88,
  288,  256,  260,  261,  292,  263,  256,  274,  275,  274,
  275,  266,  100,  287,  269,  270,  271,  100,  106,  269,
  256,  256,  269,  106,  260,  292,  287,  292,  256,  257,
  288,  120,  115,  116,  262,  107,  107,  265,  121,  274,
  275,  269,  256,  287,  279,  287,  281,  282,  283,  284,
  285,  266,  292,  288,  269,  270,  271,  292,  276,  277,
  274,  275,  290,  152,  273,  279,  292,  281,  282,  283,
  284,  285,  155,  156,  288,  269,  270,  271,  292,  256,
  257,  258,  259,  260,  261,  262,  263,  264,  265,  256,
  257,  292,  269,  274,  275,  262,  257,  256,  265,  292,
  259,  262,  269,  292,  265,  256,  257,  288,  269,  258,
  259,  262,  289,  290,  265,  256,  257,  269,  269,  274,
  275,  262,  289,  268,  265,  257,  256,  257,  269,  290,
  262,  291,  262,  265,  272,  265,  286,  269,  289,  269,
  287,  257,   69,   70,  260,  261,  262,  263,  289,  265,
  261,  267,  288,  269,  274,  275,  280,  257,  290,  289,
  260,  261,  262,  263,  287,  265,  269,  267,  288,  269,
  269,  257,  269,  289,  260,  261,  262,  263,  269,  265,
  288,  267,  257,  269,  288,  260,  261,  262,  263,  289,
  265,  292,  267,  256,  269,  260,  261,  290,  263,   78,
   79,  260,  261,  289,  263,  260,  261,  256,  263,  256,
  288,  274,  275,  256,  289,  269,  279,    3,  281,  282,
  283,  284,  285,  266,  289,  292,  269,  270,  271,  125,
  289,  256,  257,  288,  292,   91,  107,  262,  121,  264,
  265,  256,  257,   -1,  269,   -1,   -1,  262,   -1,  264,
  265,   -1,  257,   -1,  269,  260,  261,  262,  263,   -1,
  265,  257,  267,   -1,  269,   -1,  262,   -1,   -1,  265,
   -1,   -1,   -1,  269,
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
"$$1 :",
"program : _ID $$1 declaration_sentences _LCBRACE executable_sentences _RCBRACE",
"$$2 :",
"program : _ID $$2 _LCBRACE executable_sentences _RCBRACE",
"declaration_sentences : declaration_sentences type_var_list _SEMICOLON",
"declaration_sentences : declaration_sentences function",
"declaration_sentences : type_var_list _SEMICOLON",
"declaration_sentences : function",
"declaration_sentences : _ALLOW _INTEGER _TO _LONGINTEGER",
"declaration_sentences : _ALLOW error",
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
"expression : _INTTOLONG _LPAREN expression _RPAREN",
"term : term _MULT factor",
"term : term _DIV factor",
"term : factor",
"factor : _ID",
"factor : constant",
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
"$$3 :",
"function : type _FUNCTION _ID _LPAREN parameter _RPAREN $$3 function_body",
"$$4 :",
"function : type _FUNCTION _ID _LPAREN _RPAREN $$4 function_body",
"function : type _FUNCTION _ID _LPAREN parameter _RPAREN error",
"function : type _FUNCTION _ID _LPAREN _RPAREN error",
"function : _FUNCTION _ID _LPAREN parameter _RPAREN function_body",
"function : _FUNCTION _ID _LPAREN _RPAREN function_body",
"parameter : _FUNCTION _ID",
"parameter : _INTEGER _ID",
"parameter : _LONGINTEGER _ID",
"function_body : declaration_sentences function_sentence_block",
"function_body : function_sentence_block",
"function_sentence_block : _LCBRACE executable_sentences return_sentence _RCBRACE",
"function_sentence_block : _LCBRACE return_sentence _RCBRACE",
"function_sentence_block : _LCBRACE error",
"function_sentence_block : _LCBRACE executable_sentences error",
"function_sentence_block : _LCBRACE executable_sentences return_sentence error",
"function_sentence_block : _LCBRACE return_sentence",
"return_sentence : _RETURN expression _SEMICOLON",
"return_sentence : _RETURN expression error",
"return_sentence : _RETURN error",
};

//#line 343 "MyGrammar.y"

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
//#line 481 "Parser.java"
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
//#line 58 "MyGrammar.y"
{this.currentScope = new Scope(val_peek(0).getLex());}
break;
case 2:
//#line 60 "MyGrammar.y"
{yyval = val_peek(5); notify(yyval.toString() + " Compilated Successfully!", ((SymbolItem)val_peek(1)).getToken().getLine() + 1);}
break;
case 3:
//#line 62 "MyGrammar.y"
{this.currentScope = new Scope(val_peek(0).getLex());}
break;
case 4:
//#line 64 "MyGrammar.y"
{yyval = val_peek(4); notify(yyval.toString() + " Compilated Successfully!", ((SymbolItem)val_peek(1)).getToken().getLine() + 1);}
break;
case 9:
//#line 75 "MyGrammar.y"
{this.terManager.enableConversion();}
break;
case 10:
//#line 77 "MyGrammar.y"
{yyerror("Conversion invalid");}
break;
case 11:
//#line 79 "MyGrammar.y"
{yyerror("Sentences can't be used in the declarative portion of the code!");}
break;
case 13:
//#line 84 "MyGrammar.y"
{yyerror("Identifier(s) expected at declaration");}
break;
case 14:
//#line 87 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.INT;}
break;
case 15:
//#line 89 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.LONG;}
break;
case 16:
//#line 91 "MyGrammar.y"
{yyval = val_peek(0);
																((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																((SymbolItem)yyval).setArithmeticType(this.currentType);
																((SymbolItem)yyval).setScope(this.currentScope.getScope());
																if(!this.symTable.contains((SymbolItem)yyval)){
																	this.symTable.addSymbol((SymbolItem)yyval);	
																}
																else {
																	yyerror("Variable already declared!");
																}
																notify("Found variable declaration: " + yyval.toString());}
break;
case 17:
//#line 103 "MyGrammar.y"
{yyval = val_peek(0);
																((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																((SymbolItem)yyval).setArithmeticType(this.currentType);
																((SymbolItem)yyval).setScope(this.currentScope.getScope());
																if(!this.symTable.contains((SymbolItem)yyval)){
																	this.symTable.addSymbol((SymbolItem)yyval);	
																}
																else {
																	yyerror("Variable already declared!");
																}
																notify("Found variable declaration: " + yyval.toString());}
break;
case 20:
//#line 121 "MyGrammar.y"
{notify("Found Selection sentence ", this.currentLine);}
break;
case 21:
//#line 123 "MyGrammar.y"
{notify("Found Iteration sentence ", this.currentLine);}
break;
case 22:
//#line 125 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Print sentence: " + yyval.toString(), this.currentLine); this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 23:
//#line 127 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Assignment of: " + yyval.toString(), this.currentLine);}
break;
case 24:
//#line 129 "MyGrammar.y"
{yyval = val_peek(2); notify("Found Assignment with annotation of: " + yyval.toString(), this.currentLine);}
break;
case 25:
//#line 132 "MyGrammar.y"
{yyval = val_peek(2); this.currentLine = ((SymbolItem)val_peek(2)).getToken().getLine();
																	((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR);
																	((SymbolItem)yyval).setScope(this.currentScope.getScope());
																	SymbolItem variable = this.symTable.getSymbol((SymbolItem)yyval);
																	if(variable == null){ 
																		yyerror("Variable not declared!");
																	}
																	else {
																		yyval = variable;
																		Tercet toAdd = new Assignment(val_peek(2),val_peek(0),"");
																		int index = this.terManager.addTercet(toAdd);
																		toAdd.setIndex(index);
																	}}
break;
case 26:
//#line 146 "MyGrammar.y"
{yyval = val_peek(1);
																	((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																	((SymbolItem)yyval).setScope(this.currentScope.getScope());
																	SymbolItem variable = this.symTable.getSymbol((SymbolItem)yyval);
																	if(variable == null){ 
																		yyerror("Variable not declared!");
																	}
																	else {
																		yyval = variable;
																	}}
break;
case 27:
//#line 158 "MyGrammar.y"
{yyval = new Addition(val_peek(2),val_peek(0),"");
																	int index = this.terManager.addTercet((Tercet)yyval);
																	((Tercet)yyval).setIndex(index);}
break;
case 28:
//#line 162 "MyGrammar.y"
{yyval = new Subtraction(val_peek(2),val_peek(0),""); 
																	int index = this.terManager.addTercet((Tercet)yyval);
																	((Tercet)yyval).setIndex(index);}
break;
case 29:
//#line 166 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 30:
//#line 170 "MyGrammar.y"
{yyval = val_peek(1); 
																	if(terManager.conversionAllowed()){
																		yyval.setArithmeticType(SymbolItem.ArithmeticType.LONG);
																	}
																	else{
																		terManager.getLog().addLog("Conversion not allowed", ((SymbolItem)val_peek(3)).getToken().getLine());
																	}}
break;
case 31:
//#line 179 "MyGrammar.y"
{yyval = new Multiplication(val_peek(2),val_peek(0),""); 
																int index = this.terManager.addTercet((Tercet)yyval);
																((Tercet)yyval).setIndex(index);}
break;
case 32:
//#line 183 "MyGrammar.y"
{yyval = new Division(val_peek(2),val_peek(0),"");
																int index = this.terManager.addTercet((Tercet)yyval);
																((Tercet)yyval).setIndex(index);}
break;
case 33:
//#line 187 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 34:
//#line 190 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																if(!this.symTable.contains((SymbolItem)yyval)){ yyerror("Variable not declared!");}}
break;
case 35:
//#line 193 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.CONST); 
																((SymbolItem)yyval).setArithmeticType(this.currentType);
																this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 36:
//#line 197 "MyGrammar.y"
{yyval = val_peek(1); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																if(!this.symTable.contains((SymbolItem)yyval)){ yyerror("Variable not declared!");}}
break;
case 37:
//#line 201 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.INT;}
break;
case 38:
//#line 203 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.LONG;}
break;
case 39:
//#line 206 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();}
break;
case 40:
//#line 208 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine();}
break;
case 41:
//#line 210 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine(); 
																															yyerror("Expected endif;");}
break;
case 42:
//#line 213 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(3)).getToken().getLine();
																															yyerror("Expected ')'", this.currentLine);}
break;
case 43:
//#line 216 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(4)).getToken().getLine();
																															yyerror("Wrong if statement, sentence(s) expected");}
break;
case 45:
//#line 222 "MyGrammar.y"
{yyerror("Wrong else statement, sentence(s) expected");}
break;
case 53:
//#line 240 "MyGrammar.y"
{yyerror("Invalid operator, comparator expected");}
break;
case 56:
//#line 247 "MyGrammar.y"
{yyerror("Expected '}'");}
break;
case 57:
//#line 251 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(12)).getToken().getLine(); checkControlVars((SymbolItem)val_peek(10),(SymbolItem)val_peek(4));}
break;
case 58:
//#line 254 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(11)).getToken().getLine(); checkControlVars((SymbolItem)val_peek(9),(SymbolItem)val_peek(3));}
break;
case 59:
//#line 256 "MyGrammar.y"
{yyerror("Missing 'for' body.");}
break;
case 60:
//#line 258 "MyGrammar.y"
{yyerror("Missing 'for' body.");}
break;
case 61:
//#line 260 "MyGrammar.y"
{yyerror("Missing condition.");}
break;
case 62:
//#line 262 "MyGrammar.y"
{yyerror("Missing assignment. What were you planning on iterating on?");}
break;
case 63:
//#line 266 "MyGrammar.y"
{yyval = val_peek(1); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.STR); this.currentLine = ((SymbolItem)val_peek(3)).getToken().getLine();}
break;
case 64:
//#line 269 "MyGrammar.y"
{this.currentScope.pushScope(((SymbolItem)val_peek(3)).getLex());}
break;
case 65:
//#line 270 "MyGrammar.y"
{yyval = val_peek(5);
																													((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)yyval).setArithmeticType(this.currentType);
																													this.currentScope.popScope();
																													((SymbolItem)yyval).setScope(this.currentScope.getScope());
																													this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine();
																													if(!this.symTable.contains((SymbolItem)yyval)){
																														this.symTable.addSymbol((SymbolItem)yyval);
																													}
																													else{
																														yyerror("Function name already used!", this.currentLine);
																													}
																													notify("Found function declaration: " + yyval.toString(), currentLine);}
break;
case 66:
//#line 284 "MyGrammar.y"
{this.currentScope.pushScope(((SymbolItem)val_peek(2)).getLex());}
break;
case 67:
//#line 285 "MyGrammar.y"
{yyval = val_peek(4);
																													((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)yyval).setArithmeticType(this.currentType);
																													this.currentScope.popScope();
																													((SymbolItem)yyval).setScope(this.currentScope.getScope());
																													this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();
																													if(!this.symTable.contains((SymbolItem)yyval)){
																														this.symTable.addSymbol((SymbolItem)yyval);
																													}
																													else{
																														yyerror("Function name already used!", this.currentLine);
																													}
																													notify("Found function declaration: " + yyval.toString(), currentLine);}
break;
case 68:
//#line 299 "MyGrammar.y"
{yyerror("Missing function body.");}
break;
case 69:
//#line 301 "MyGrammar.y"
{yyerror("Missing function body.");}
break;
case 70:
//#line 303 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
break;
case 71:
//#line 306 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(4)).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
break;
case 79:
//#line 326 "MyGrammar.y"
{yyerror("Expected sentence or return sentence.");}
break;
case 80:
//#line 328 "MyGrammar.y"
{yyerror("Expected return sentence.");}
break;
case 81:
//#line 330 "MyGrammar.y"
{yyerror("Expected '}'.");}
break;
case 82:
//#line 332 "MyGrammar.y"
{yyerror("Expected '}'.");}
break;
case 84:
//#line 337 "MyGrammar.y"
{yyerror("Expected ';' after return statement");}
break;
case 85:
//#line 339 "MyGrammar.y"
{yyerror("Expected something to return");}
break;
//#line 958 "Parser.java"
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
