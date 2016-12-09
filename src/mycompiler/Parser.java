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
import java.util.Stack;
import java.util.LinkedList;

//#line 24 "Parser.java"




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
    1,    5,    5,    8,    8,    9,    9,   12,    6,   13,
    6,    6,    6,    6,    6,   10,   10,   11,   11,   14,
   14,   14,   14,   14,   14,   15,   15,    3,    3,    7,
    7,    7,    7,    7,    7,   20,   20,   16,   16,   16,
   16,   22,   22,   22,   23,   23,   23,   23,   24,   24,
   21,   21,   21,   17,   17,   17,   17,   28,   27,   27,
   25,   29,   29,   29,   29,   29,   29,   29,   26,   26,
   26,   30,   18,   18,   18,   31,   31,   32,   32,   32,
   32,   19,
};
final static short yylen[] = {                            2,
    0,    6,    0,    5,    3,    2,    2,    1,    4,    2,
    1,    2,    2,    1,    1,    3,    1,    0,    8,    0,
    7,    7,    6,    6,    5,    5,    2,    2,    1,    4,
    3,    2,    3,    4,    2,    3,    3,    2,    1,    2,
    1,    2,    2,    3,    2,    3,    2,    3,    3,    1,
    4,    3,    3,    1,    1,    1,    2,    1,    1,    1,
    3,    4,    6,    6,    7,    7,    5,    0,    3,    2,
    3,    1,    1,    1,    1,    1,    1,    1,    3,    1,
    3,    0,   10,    5,    3,    3,    2,    3,    3,    1,
    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,   14,   15,    0,    0,    0,
    0,    0,    0,    0,    8,   11,    0,    0,   41,    0,
    0,    0,    0,    0,    0,    0,    0,   10,    0,    0,
   47,    0,    0,    0,    6,    7,   13,    0,   17,    0,
   40,   42,    0,   45,    0,   39,    0,    0,   59,   60,
    0,   58,    0,   54,   56,    0,    0,    0,   85,    0,
    0,    0,    0,    0,   61,    0,    5,    0,    0,   44,
    4,   38,    0,   57,   78,    0,    0,   76,   72,   74,
   73,   75,   77,    0,    0,    0,    0,   92,    0,    0,
    0,    0,    9,    0,   62,    2,    0,   16,    0,    0,
    0,    0,   52,   53,   67,    0,   80,    0,    0,    0,
   25,   29,    0,   27,    0,   84,    0,    0,    0,    0,
   51,    0,    0,   64,    0,   32,    0,    0,    0,   28,
    0,   24,    0,   63,   23,    0,    0,   81,   79,   70,
    0,   66,   65,    0,   33,    0,   31,    0,    0,   21,
   22,    0,   69,   37,   36,   34,   30,   26,    0,    0,
   19,    0,   87,    0,   90,   91,    0,   83,    0,    0,
    0,    0,
};
final static short yydgoto[] = {                          2,
  110,    3,   45,    4,   14,   15,   16,   17,   40,   91,
  111,  152,  136,  112,  129,   51,   18,   19,   20,   21,
   22,   53,   54,   55,   56,  108,  125,  141,   84,  117,
  160,  167,
};
final static short yysindex[] = {                      -253,
    0,    0,   35, -256, -237,    0,    0, -183, -174, -159,
 -201, -235, -226, -156,    0,    0, -172, -152,    0, -134,
 -111, -107, -118, -109,  -81,  -91, -229,    0,  -68, -109,
    0, -249, -118,  -79,    0,    0,    0,  -45,    0,  -76,
    0,    0,  -44,    0,  -83,    0,  -57, -261,    0,    0,
  -80,    0, -184,    0,    0,  -55,  -48, -242,    0, -143,
  -41,  -17,  -86, -139,    0,  -82,    0,  -27,   -7,    0,
    0,    0, -109,    0,    0,  -14,  -14,    0,    0,    0,
    0,    0,    0, -109,  -14,  -14, -233,    0,  -51,  -85,
  -22,   12,    0,  -12,    0,    0, -192,    0, -213, -184,
 -184,  -86,    0,    0,    0, -118,    0,   14,   13, -173,
    0,    0,   10,    0,  -51,    0, -109,   -1,   34,    6,
    0, -248,   43,    0, -186,    0, -109,   24,   11,    0,
   18,    0,   26,    0,    0,  -51,   63,    0,    0,    0,
   -4,    0,    0, -244,    0, -243,    0,   32,   52,    0,
    0,  -51,    0,    0,    0,    0,    0,    0,  -54,   36,
    0,   40,    0,   -4,    0,    0,    9,    0,   40,   40,
    9,    9,
};
final static short yyrindex[] = {                         0,
  -40,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   30,
    0,    0,  -92,    0,    0,    0,    0, -162,    0,    0,
    0,    0, -203,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   31,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   37,    0,    0,    0,    0,    0,    0,    0, -150,
 -129, -181,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -26,    0,
    0,    0,    2,    0,    0,    0,    0,    0,  -70,    0,
    0,    0,    0,    0,    0,    0,  -15,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   38,    0,    0,    0,
  -48,   39,
};
final static short yygindex[] = {                         0,
  322,    0,  -32,    0,  -10,   -9,  -23,  -56,    0,  231,
 -108,    0,    0,  219,  202,  -24,    0,    0,    0,  304,
  -19,  236,  229,  -60,  215, -126,    0,    0,    0,    0,
    0,  147,
};
final static int YYTABLESIZE=332;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         46,
   66,   90,   34,   35,   52,   63,  132,  138,    5,   46,
   52,  154,  156,    8,  153,    1,   10,    6,    7,   64,
   12,   72,  105,    5,   74,   32,   59,  150,    8,   76,
   77,   10,   23,    6,    7,   12,    9,  168,   65,   60,
   90,  139,   72,  161,   30,   89,  157,  155,   99,   24,
   31,   32,   50,   52,   28,  106,   52,   52,   29,  102,
   76,   77,   33,  107,   52,   52,   52,    6,    7,  142,
   50,   50,  143,  122,  121,   50,  128,   50,   50,   50,
   50,   50,   46,   37,   50,   46,    6,    7,   50,    9,
   38,   85,   86,   55,   26,  119,   39,   52,   72,   34,
   35,  166,  144,   25,   72,   48,   71,   52,  166,  166,
   71,   55,   55,   55,   55,  109,   55,  107,   55,   55,
   55,   55,   55,   48,   48,   55,   49,   27,   48,   55,
   48,   48,   48,   48,   48,   36,   30,   48,    5,   41,
  107,   48,   31,    8,   49,   49,   10,   94,   95,   49,
   12,   49,   49,   49,   49,   49,   47,   42,   49,   48,
   49,   50,   49,   43,   43,   43,   43,   43,   43,   43,
   43,   43,   43,    5,    5,   75,   43,  113,    8,    8,
   43,   10,   10,  114,   44,   12,   12,   76,   77,   35,
   35,   57,   35,   76,   77,   58,   43,   43,   78,   62,
   79,   80,   81,   82,   83,    5,   71,   96,    6,    7,
    8,    9,   67,   10,   69,   11,    1,   12,   35,    1,
    1,    1,    1,   68,    1,  162,    1,   70,    1,   73,
   20,  163,   87,   20,   20,   20,   20,  109,   20,   88,
   20,   18,   20,   93,   18,   18,   18,   18,    3,   18,
   92,   18,    5,   18,   48,   49,   50,    8,   68,   97,
   10,   98,   20,   68,   12,  115,   68,  116,  126,    5,
   68,  123,  124,   18,    8,  118,  127,   10,  131,  145,
    5,   12,  169,  170,  106,    8,  134,  127,   10,  135,
   68,    5,   12,  137,    6,    7,    8,    9,  140,   10,
  147,   11,   82,   12,  148,   82,   82,   82,  165,   49,
   50,  100,  101,  103,  104,  171,  172,  149,  151,  158,
  159,   12,   46,  164,   13,   86,   89,  120,  130,  146,
   61,  133,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         23,
   33,   58,   13,   13,   24,   30,  115,  256,  257,   33,
   30,  256,  256,  262,  141,  269,  265,  260,  261,  269,
  269,   45,  256,  257,  286,  287,  256,  136,  262,  274,
  275,  265,  289,  260,  261,  269,  263,  164,  288,  269,
   97,  290,   66,  152,  280,  288,  290,  292,   73,  287,
  286,  287,  256,   73,  256,  289,   76,   77,  260,   84,
  274,  275,  289,   87,   84,   85,   86,  260,  261,  256,
  274,  275,  259,  106,  288,  279,  109,  281,  282,  283,
  284,  285,  106,  256,  288,  109,  260,  261,  292,  263,
  263,  276,  277,  256,  269,  288,  269,  117,  122,  110,
  110,  162,  127,  287,  128,  256,  288,  127,  169,  170,
  292,  274,  275,  276,  277,  289,  279,  141,  281,  282,
  283,  284,  285,  274,  275,  288,  256,  287,  279,  292,
  281,  282,  283,  284,  285,  292,  280,  288,  257,  292,
  164,  292,  286,  262,  274,  275,  265,  287,  288,  279,
  269,  281,  282,  283,  284,  285,  266,  292,  288,  269,
  270,  271,  292,  256,  257,  258,  259,  260,  261,  262,
  263,  264,  265,  257,  257,  256,  269,  263,  262,  262,
  292,  265,  265,  269,  292,  269,  269,  274,  275,  260,
  261,  273,  263,  274,  275,  287,  289,  290,  279,  268,
  281,  282,  283,  284,  285,  257,  290,  290,  260,  261,
  262,  263,  292,  265,  291,  267,  257,  269,  289,  260,
  261,  262,  263,  269,  265,  280,  267,  272,  269,  287,
  257,  286,  288,  260,  261,  262,  263,  289,  265,  288,
  267,  257,  269,  261,  260,  261,  262,  263,  289,  265,
  292,  267,  257,  269,  269,  270,  271,  262,  257,  287,
  265,  269,  289,  262,  269,  288,  265,  256,  256,  257,
  269,  258,  259,  289,  262,  288,  264,  265,  269,  256,
  257,  269,  274,  275,  289,  262,  288,  264,  265,  256,
  289,  257,  269,  288,  260,  261,  262,  263,  256,  265,
  290,  267,  266,  269,  287,  269,  270,  271,  269,  270,
  271,   76,   77,   85,   86,  169,  170,  292,  256,  288,
  269,  292,  292,  288,    3,  288,  288,   97,  110,  128,
   27,  117,
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
"$$3 :",
"function : type _FUNCTION _ID _LPAREN parameter _RPAREN $$3 function_body",
"$$4 :",
"function : type _FUNCTION _ID _LPAREN _RPAREN $$4 function_body",
"function : type _FUNCTION _ID _LPAREN parameter _RPAREN error",
"function : type _FUNCTION _ID _LPAREN _RPAREN error",
"function : _FUNCTION _ID _LPAREN parameter _RPAREN function_body",
"function : _FUNCTION _ID _LPAREN _RPAREN function_body",
"parameter : type _FUNCTION _ID _LPAREN _RPAREN",
"parameter : type _ID",
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
"executable_sentences : executable_sentences sentence",
"executable_sentences : sentence",
"sentence : selection _SEMICOLON",
"sentence : iteration",
"sentence : print _SEMICOLON",
"sentence : assignment _SEMICOLON",
"sentence : assignment _SEMICOLON _ANNOT",
"sentence : functionCall _SEMICOLON",
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
"factor : functionCall",
"constant : _INT",
"constant : _LONGINT",
"functionCall : _ID _LPAREN _RPAREN",
"functionCall : _ID _LPAREN _ID _RPAREN",
"functionCall : _ID _LPAREN _ID _LPAREN _RPAREN _RPAREN",
"selection : _IF _LPAREN condition _RPAREN sentence_block _ENDIF",
"selection : _IF _LPAREN condition _RPAREN sentence_block else_section _ENDIF",
"selection : _IF _LPAREN condition _RPAREN sentence_block else_section error",
"selection : _IF _LPAREN condition _RPAREN error",
"$$5 :",
"else_section : _ELSE $$5 sentence_block",
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
"$$6 :",
"iteration : _FOR _LPAREN assignment _SEMICOLON $$6 condition _SEMICOLON increment _RPAREN sentence_block",
"iteration : _FOR _LPAREN assignment _SEMICOLON error",
"iteration : _FOR _LPAREN error",
"increment : _ID _ASSIGN for_expression",
"increment : _ID _MINUS_ONE",
"for_expression : for_expression _PLUS for_expression",
"for_expression : for_expression _MINUS for_expression",
"for_expression : _ID",
"for_expression : constant",
"print : _PRINT _LPAREN _STRING _RPAREN",
};

//#line 455 "MyGrammar.y"

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
//#line 521 "Parser.java"
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
//#line 60 "MyGrammar.y"
{this.currentScope = new Scope(val_peek(0).getLex());}
break;
case 2:
//#line 62 "MyGrammar.y"
{yyval = val_peek(5); notify(yyval.toString() + " Compilated Successfully!", ((SymbolItem)val_peek(1)).getToken().getLine() + 1);}
break;
case 3:
//#line 64 "MyGrammar.y"
{this.currentScope = new Scope(val_peek(0).getLex());}
break;
case 4:
//#line 66 "MyGrammar.y"
{yyval = val_peek(4); notify(yyval.toString() + " Compilated Successfully!", ((SymbolItem)val_peek(1)).getToken().getLine() + 1);}
break;
case 9:
//#line 77 "MyGrammar.y"
{this.terManager.enableConversion();}
break;
case 10:
//#line 79 "MyGrammar.y"
{yyerror("Conversion invalid");}
break;
case 11:
//#line 81 "MyGrammar.y"
{yyerror("Sentences can't be used in the declarative portion of the code!");}
break;
case 13:
//#line 86 "MyGrammar.y"
{yyerror("Identifier(s) expected at declaration");}
break;
case 14:
//#line 89 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.INT;}
break;
case 15:
//#line 91 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.LONG;}
break;
case 16:
//#line 94 "MyGrammar.y"
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
//#line 106 "MyGrammar.y"
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
case 18:
//#line 119 "MyGrammar.y"
{this.currentScope.pushScope(((SymbolItem)val_peek(3)).getLex());
																													this.terManager.inFunction();
																													((SymbolItem)val_peek(1)).setScope(this.currentScope.getScope());
																													Tercet function = new Function(val_peek(3),val_peek(1),this.currentScope.getScope());
																													function.setIndex(this.terManager.addTercet(function));}
break;
case 19:
//#line 124 "MyGrammar.y"
{yyval = val_peek(5);
																													Tercet ret = new Return(val_peek(5),val_peek(0),this.currentScope.getScope());
																													ret.setIndex(this.terManager.addTercet(ret));
																													this.terManager.outOfFunction();
																													((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)yyval).setArithmeticType(this.currentType);
																													this.currentScope.popScope();
																													((SymbolItem)yyval).setScope(this.currentScope.getScope());
																													if(!this.symTable.contains((SymbolItem)yyval)){
																														this.symTable.addSymbol((SymbolItem)yyval);
																													}
																													else{
																														this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine();
																														yyerror("Function name already used!", this.currentLine);
																													}
																													notify("Found function declaration: " + yyval.toString(), currentLine);}
break;
case 20:
//#line 141 "MyGrammar.y"
{this.currentScope.pushScope(((SymbolItem)val_peek(2)).getLex());
																													this.terManager.inFunction();
																													Tercet function = new Function(val_peek(2),null,this.currentScope.getScope());
																													function.setIndex(this.terManager.addTercet(function));}
break;
case 21:
//#line 145 "MyGrammar.y"
{yyval = val_peek(4);
															 														Tercet ret = new Return(val_peek(4),val_peek(0),this.currentScope.getScope());
																													ret.setIndex(this.terManager.addTercet(ret));
															 														this.terManager.outOfFunction();
																													((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)yyval).setArithmeticType(this.currentType);
																													this.currentScope.popScope();
																													((SymbolItem)yyval).setScope(this.currentScope.getScope());
																													if(!this.symTable.contains((SymbolItem)yyval)){
																														this.symTable.addSymbol((SymbolItem)yyval);
																													}
																													else{
																														this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();
																														yyerror("Function name already used!", this.currentLine);
																													}
																													notify("Found function declaration: " + yyval.toString(), currentLine);}
break;
case 22:
//#line 162 "MyGrammar.y"
{yyerror("Missing function body.");}
break;
case 23:
//#line 164 "MyGrammar.y"
{yyerror("Missing function body.");}
break;
case 24:
//#line 166 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
break;
case 25:
//#line 169 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(4)).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
break;
case 26:
//#line 173 "MyGrammar.y"
{yyval = val_peek(2); 
																													((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)yyval).setArithmeticType(this.currentType);
																													((SymbolItem)yyval).setScope(this.currentScope.getScope());
																													if(!this.symTable.containsArithmetic((SymbolItem)yyval)){ 
																														this.currentLine = ((SymbolItem)val_peek(2)).getToken().getLine();
																														yyerror("Function not declared!" , this.currentLine);
																													}
																													else {
																														((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.PARAM);
																														((SymbolItem)yyval).setArithmeticType(this.currentType);
																														this.symTable.addSymbol((SymbolItem)yyval);
																													}}
break;
case 27:
//#line 187 "MyGrammar.y"
{yyval = val_peek(0);
																													((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.PARAM); 
																													((SymbolItem)yyval).setArithmeticType(this.currentType);
																													((SymbolItem)yyval).setScope(this.currentScope.getScope());
																													this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 28:
//#line 194 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 29:
//#line 196 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 30:
//#line 199 "MyGrammar.y"
{yyval = val_peek(1);}
break;
case 31:
//#line 201 "MyGrammar.y"
{yyval = val_peek(1);}
break;
case 32:
//#line 203 "MyGrammar.y"
{yyerror("Expected sentence or return sentence.");}
break;
case 33:
//#line 205 "MyGrammar.y"
{yyerror("Expected return sentence.");}
break;
case 34:
//#line 207 "MyGrammar.y"
{yyerror("Expected '}'.");}
break;
case 35:
//#line 209 "MyGrammar.y"
{yyerror("Expected '}'.");}
break;
case 36:
//#line 212 "MyGrammar.y"
{yyval = val_peek(1);}
break;
case 37:
//#line 214 "MyGrammar.y"
{yyerror("Expected ';' after return statement");}
break;
case 40:
//#line 222 "MyGrammar.y"
{notify("Found Selection sentence ", this.currentLine);}
break;
case 41:
//#line 224 "MyGrammar.y"
{notify("Found Iteration sentence ", this.currentLine);}
break;
case 42:
//#line 226 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Print sentence: " + yyval.toString(), this.currentLine);}
break;
case 43:
//#line 228 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Assignment of: " + yyval.toString(), this.currentLine);}
break;
case 44:
//#line 230 "MyGrammar.y"
{yyval = val_peek(2); notify("Found Assignment with annotation of: " + yyval.toString(), this.currentLine);}
break;
case 45:
//#line 232 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Function Invocation");}
break;
case 46:
//#line 235 "MyGrammar.y"
{yyval = val_peek(2); this.currentLine = ((SymbolItem)val_peek(2)).getToken().getLine();
																	((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR);
																	((SymbolItem)yyval).setScope(this.currentScope.getScope());
																	SymbolItem variable = this.symTable.getSymbol((SymbolItem)yyval);
																	if(variable == null){ 
																		yyerror("Variable not declared!" , this.currentLine);
																	}
																	else {
																		yyval = variable;
																		Tercet toAdd = new Assignment(val_peek(2),val_peek(0),"");
																		toAdd.setIndex(this.terManager.addTercet(toAdd));
																	}}
break;
case 47:
//#line 248 "MyGrammar.y"
{yyval = val_peek(1);
																	((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR);
																	((SymbolItem)yyval).setScope(this.currentScope.getScope());
																	SymbolItem variable = this.symTable.getSymbol((SymbolItem)yyval);
																	if(variable == null){ 
																		yyerror("Variable not declared!" , this.currentLine);
																	}
																	else {
																		yyval = variable;
																		Tercet toAdd = new Decrement(yyval,null,this.currentScope.getScope());
																		toAdd.setIndex(this.terManager.addTercet(toAdd));
																	}}
break;
case 48:
//#line 262 "MyGrammar.y"
{yyval = new Addition(val_peek(2),val_peek(0),"");
																	((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 49:
//#line 265 "MyGrammar.y"
{yyval = new Subtraction(val_peek(2),val_peek(0),"");
																	((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 50:
//#line 268 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 51:
//#line 272 "MyGrammar.y"
{yyval = val_peek(1); 
																	if(terManager.conversionAllowed())
																		yyval.setArithmeticType(SymbolItem.ArithmeticType.LONG);
																	else terManager.getLog().addLog("Conversion not allowed", ((SymbolItem)val_peek(3)).getToken().getLine());}
break;
case 52:
//#line 278 "MyGrammar.y"
{yyval = new Multiplication(val_peek(2),val_peek(0),"");
																((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 53:
//#line 281 "MyGrammar.y"
{yyval = new Division(val_peek(2),val_peek(0),"");
																((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 54:
//#line 284 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 55:
//#line 287 "MyGrammar.y"
{yyval = val_peek(0);
																((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR);
																((SymbolItem)yyval).setScope(this.currentScope.getScope());
																SymbolItem variable = this.symTable.getSymbol((SymbolItem)yyval);
																if(variable == null){ 
																	yyerror("Variable not declared!" , this.currentLine);
																}
																else {
																	yyval = variable;
																}}
break;
case 56:
//#line 298 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.CONST); 
																((SymbolItem)yyval).setArithmeticType(this.currentType);
																this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 57:
//#line 302 "MyGrammar.y"
{yyval = val_peek(1);
																((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR);
																((SymbolItem)yyval).setScope(this.currentScope.getScope());
																SymbolItem variable = this.symTable.getSymbol((SymbolItem)yyval);
																if(variable == null){ 
																	yyerror("Variable not declared!" , this.currentLine);
																}
																else {
																	yyval = variable;
																	Tercet toAdd = new Decrement(yyval,null,this.currentScope.getScope());
																	toAdd.setIndex(this.terManager.addTercet(toAdd));
																}}
break;
case 58:
//#line 315 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 59:
//#line 318 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.INT;}
break;
case 60:
//#line 320 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.LONG;}
break;
case 61:
//#line 324 "MyGrammar.y"
{SymbolItem func = this.checkFunctionDeclaration((SymbolItem)val_peek(2));
									Tercet toAdd = new FunctionCall(func,null,this.currentScope.getScope());
									toAdd.setIndex(this.terManager.addTercet(toAdd));
									yyval = toAdd;}
break;
case 62:
//#line 337 "MyGrammar.y"
{yyval = val_peek(3); 
									SymbolItem func = this.checkFunctionDeclaration((SymbolItem)val_peek(3));
									SymbolItem var = this.checkVarDeclaration((SymbolItem)val_peek(1));
									Tercet toAdd = new FunctionCall(func,var,this.currentScope.getScope());
									toAdd.setIndex(this.terManager.addTercet(toAdd));}
break;
case 63:
//#line 344 "MyGrammar.y"
{yyval = val_peek(5); 
									SymbolItem func = this.checkFunctionDeclaration((SymbolItem)val_peek(5));
									SymbolItem param = this.checkFunctionDeclaration((SymbolItem)val_peek(3));
									Tercet toAdd = new FunctionCall(func,param,this.currentScope.getScope());
									toAdd.setIndex(this.terManager.addTercet(toAdd));}
break;
case 64:
//#line 351 "MyGrammar.y"
{this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																															this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();}
break;
case 65:
//#line 354 "MyGrammar.y"
{this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																															this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine();}
break;
case 66:
//#line 357 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine(); 
																															yyerror("Expected endif;");}
break;
case 67:
//#line 360 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(4)).getToken().getLine();
																															yyerror("Wrong if statement, sentence(s) expected");}
break;
case 68:
//#line 366 "MyGrammar.y"
{Tercet newBU = new BranchUnconditional(null,null,this.currentScope.getScope());
																	newBU.setIndex(this.terManager.addTercet(newBU));
																	this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																	this.conditionSTK.push((JumpTercet)newBU);}
break;
case 70:
//#line 372 "MyGrammar.y"
{yyerror("Wrong else statement, sentence(s) expected");}
break;
case 71:
//#line 375 "MyGrammar.y"
{Tercet newComp = new Comparator(((SymbolItem)val_peek(1)).getLex(),val_peek(2),val_peek(0),this.currentScope.getScope());
																					newComp.setIndex(this.terManager.addTercet(newComp));
																					yyval = newComp;
																					Tercet newBF = new BranchFalse(newComp,null,this.currentScope.getScope());
																					newBF.setIndex(this.terManager.addTercet(newBF));
																					this.conditionSTK.push((JumpTercet)newBF);}
break;
case 72:
//#line 383 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 73:
//#line 385 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 74:
//#line 387 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 75:
//#line 389 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 76:
//#line 391 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 77:
//#line 393 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 78:
//#line 395 "MyGrammar.y"
{yyerror("Invalid operator, comparator expected");}
break;
case 81:
//#line 402 "MyGrammar.y"
{yyerror("Expected '}'");}
break;
case 82:
//#line 405 "MyGrammar.y"
{this.iterationJumpDir = this.terManager.getNextIndex();}
break;
case 83:
//#line 407 "MyGrammar.y"
{checkControlVars((SymbolItem)val_peek(7),(SymbolItem)val_peek(2));
									Tercet incTer = this.incrementTercets.pollFirst();
									incTer.setIndex(this.terManager.addTercet(incTer));
										while(!this.incrementTercets.isEmpty()){
											incTer = this.incrementTercets.pollFirst();
											incTer.setIndex(this.terManager.addTercet(incTer));
										}
									Tercet newBU = new BranchUnconditional(null,null,this.currentScope.getScope());
									newBU.setIndex(this.terManager.addTercet(newBU));
									((JumpTercet)newBU).setJumpDir(this.iterationJumpDir);
									this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
									this.currentLine = ((SymbolItem)val_peek(9)).getToken().getLine();}
break;
case 84:
//#line 420 "MyGrammar.y"
{yyerror("Missing condition.");}
break;
case 85:
//#line 422 "MyGrammar.y"
{yyerror("Missing assignment. What were you planning on iterating on?");}
break;
case 86:
//#line 425 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(2));
																		this.incrementTercets.addLast(new Assignment(val_peek(2),val_peek(0),""));}
break;
case 87:
//#line 428 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(1));
																		this.incrementTercets.addLast(new Decrement(val_peek(1),null,this.currentScope.getScope()));}
break;
case 88:
//#line 432 "MyGrammar.y"
{this.incrementTercets.addLast(new Addition(val_peek(2),val_peek(0),""));}
break;
case 89:
//#line 434 "MyGrammar.y"
{this.incrementTercets.addLast(new Subtraction(val_peek(2),val_peek(0),""));}
break;
case 90:
//#line 436 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(0));}
break;
case 91:
//#line 438 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.CONST); 
																					((SymbolItem)yyval).setArithmeticType(this.currentType);
																					this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 92:
//#line 444 "MyGrammar.y"
{yyval = val_peek(1);
									((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.STR); 
									this.symTable.addSymbol((SymbolItem)yyval);
									this.currentLine = ((SymbolItem)val_peek(3)).getToken().getLine();
									yyval = new Print(val_peek(1),null,this.currentScope.getScope());
									((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));
									yyval = val_peek(1);}
break;
//#line 1168 "Parser.java"
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
