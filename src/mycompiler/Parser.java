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
    7,    7,    7,    7,    7,   21,   21,   16,   16,   16,
   16,   22,   22,   22,   23,   23,   23,   23,   24,   24,
   25,   17,   17,   18,   18,   18,   18,   29,   28,   28,
   26,   30,   30,   30,   30,   30,   30,   30,   27,   27,
   27,   31,   19,   19,   19,   32,   32,   33,   33,   33,
   33,   20,
};
final static short yylen[] = {                            2,
    0,    6,    0,    5,    3,    2,    2,    1,    4,    2,
    1,    2,    2,    1,    1,    3,    1,    0,    8,    0,
    7,    7,    6,    6,    5,    5,    2,    2,    1,    4,
    3,    2,    3,    4,    2,    3,    3,    2,    1,    2,
    2,    1,    2,    2,    3,    3,    2,    3,    3,    1,
    4,    3,    3,    1,    1,    1,    2,    1,    1,    1,
    0,    8,    6,    6,    7,    7,    5,    0,    3,    2,
    3,    1,    1,    1,    1,    1,    1,    1,    3,    1,
    3,    0,   10,    5,    3,    3,    2,    3,    3,    1,
    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,   14,   15,    0,    0,    0,
    0,    0,    0,    0,    8,   11,    0,    0,    0,   42,
    0,    0,    0,    0,    0,    0,    0,   10,    0,    0,
   47,    0,    0,    0,    6,    7,   13,    0,   17,    0,
   40,   41,   43,    0,    0,   39,    0,    0,   59,   60,
    0,   58,    0,   54,   56,    0,    0,    0,   85,    0,
    0,    0,    0,    0,   61,    0,    5,    0,    0,   45,
    4,   38,    0,   57,   78,    0,    0,   76,   72,   74,
   73,   75,   77,    0,    0,    0,    0,   92,    0,    0,
    0,    0,    9,    0,    0,    2,    0,   16,    0,    0,
    0,    0,   52,   53,   67,    0,   80,    0,    0,    0,
   25,   29,    0,   27,    0,   84,    0,    0,    0,    0,
    0,   51,    0,    0,   64,    0,   32,    0,    0,    0,
   28,    0,   24,    0,   63,    0,   23,    0,    0,   81,
   79,   70,    0,   66,   65,    0,   33,    0,   31,    0,
    0,    0,   21,   22,    0,   69,   37,   36,   34,   30,
   26,    0,    0,   62,   19,    0,   87,    0,   90,   91,
    0,   83,    0,    0,    0,    0,
};
final static short yydgoto[] = {                          2,
  110,    3,   45,    4,   14,   15,   16,   17,   40,   91,
  111,  155,  138,  112,  130,   51,   18,   19,   20,   21,
   22,   53,   54,   55,   95,   56,  108,  126,  143,   84,
  117,  163,  171,
};
final static short yysindex[] = {                      -261,
    0,    0,   42, -272, -268,    0,    0, -263, -243, -256,
 -118,  -85, -201, -231,    0,    0, -240, -222, -213,    0,
 -210, -180,   51,  -35, -184, -182, -188,    0, -161,  -35,
    0, -249,   51, -162,    0,    0,    0, -126,    0, -145,
    0,    0,    0, -131, -172,    0, -136, -195,    0,    0,
    1,    0,  -15,    0,    0, -135, -105, -111,    0,  -83,
 -112,  -76,   16,  -94,    0,  -81,    0,  -66,  -42,    0,
    0,    0,  -35,    0,    0, -107, -107,    0,    0,    0,
    0,    0,    0,  -35, -107, -107, -185,    0,  -71,  -47,
  -59,  -18,    0,  -44,  -36,    0, -101,    0, -225,  -15,
  -15,   16,    0,    0,    0,   51,    0,   34,    8, -187,
    0,    0,  -21,    0,  -71,    0,  -35,  -38,  -34,    3,
  -32,    0, -244,   10,    0, -203,    0,  -35,   32,  -22,
    0,  -16,    0,  -13,    0,    5,    0,  -71,   25,    0,
    0,    0,  -11,    0,    0, -223,    0, -242,    0,    7,
   29,   12,    0,    0,  -71,    0,    0,    0,    0,    0,
    0,  -72,   18,    0,    0,  -65,    0,  -11,    0,    0,
   47,    0,  -65,  -65,   47,   47,
};
final static short yyrindex[] = {                         0,
  -50,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   20,
    0,    0,    0,  -90,    0,    0,    0, -160,    0,    0,
    0,    0, -247,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   23,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   48,    0,    0,    0,    0,    0,    0,    0, -148,
 -127, -110,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -37,
    0,    0,    0,   -2,    0,    0,    0,    0,    0, -150,
    0,    0,    0,    0,    0,    0,    0,    0,  -20,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   22,    0,    0,    0, -105,   41,
};
final static short yygindex[] = {                         0,
  327,    0,  -31,    0,   -9,   -7,  -23,  -55,    0,  234,
 -108,    0,    0,  222,  204,  -29,  -19,    0,    0,    0,
  307,  247,  240,  -79,    0,  218, -128,    0,    0,    0,
    0,    0,  154,
};
final static int YYTABLESIZE=335;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         46,
   63,   66,   90,   34,   52,   35,  133,    1,   50,   46,
   52,  140,    5,  159,  156,   37,   23,    8,   24,   64,
   10,   72,   38,   25,   12,   26,   50,   50,   39,  153,
   27,   50,  157,   50,   50,   50,   50,   50,   65,  172,
   50,   90,   72,   99,   50,  141,  165,  160,   76,   77,
   76,   77,  144,   52,  102,  145,   52,   52,    6,    7,
   36,    9,  122,  107,   52,   52,   52,   59,  158,   41,
  105,    5,    6,    7,  123,    9,    8,  129,   42,   10,
   60,   43,   46,   12,    5,   46,  170,   33,   57,    8,
   74,   32,   10,  170,  170,   55,   12,   52,  146,   72,
   34,  109,   35,  106,   58,   72,   62,   48,   52,   35,
   35,   44,   35,   55,   55,   55,   55,   71,   55,  107,
   55,   55,   55,   55,   55,   48,   48,   55,   49,   67,
   48,   55,   48,   48,   48,   48,   48,   28,   35,   48,
   70,   29,   68,   48,  107,   69,   49,   49,    6,    7,
   73,   49,   87,   49,   49,   49,   49,   49,    6,    7,
   49,   48,   49,   50,   49,   44,   44,   44,   44,   44,
   44,   44,   44,   44,   44,    5,   89,   71,   44,   92,
    8,   71,   88,   10,   93,    5,  120,   12,    6,    7,
    8,    9,   94,   10,   30,   11,   30,   12,   44,   44,
   31,   32,   31,  169,   49,   50,    1,  166,   96,    1,
    1,    1,    1,  167,    1,  113,    1,  109,    1,   20,
   97,  114,   20,   20,   20,   20,   98,   20,  115,   20,
   47,   20,  119,   48,   49,   50,   18,  116,    3,   18,
   18,   18,   18,  118,   18,    5,   18,  132,   18,  135,
    8,   20,  136,   10,   68,  139,   75,   12,  137,   68,
   85,   86,   68,  127,    5,  142,   68,  149,   18,    8,
  150,  128,   10,  152,   76,   77,   12,  106,  151,   78,
  154,   79,   80,   81,   82,   83,   68,  147,    5,   76,
   77,  124,  125,    8,  161,  128,   10,  162,    5,  164,
   12,    6,    7,    8,    9,  168,   10,    5,   11,   86,
   12,   12,    8,   82,   46,   10,   82,   82,   82,   12,
  173,  174,  100,  101,  103,  104,  175,  176,   89,   13,
  121,  131,  148,   61,  134,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         23,
   30,   33,   58,   13,   24,   13,  115,  269,  256,   33,
   30,  256,  257,  256,  143,  256,  289,  262,  287,  269,
  265,   45,  263,  287,  269,  269,  274,  275,  269,  138,
  287,  279,  256,  281,  282,  283,  284,  285,  288,  168,
  288,   97,   66,   73,  292,  290,  155,  290,  274,  275,
  274,  275,  256,   73,   84,  259,   76,   77,  260,  261,
  292,  263,  288,   87,   84,   85,   86,  256,  292,  292,
  256,  257,  260,  261,  106,  263,  262,  109,  292,  265,
  269,  292,  106,  269,  257,  109,  166,  289,  273,  262,
  286,  287,  265,  173,  174,  256,  269,  117,  128,  123,
  110,  289,  110,  289,  287,  129,  268,  256,  128,  260,
  261,  292,  263,  274,  275,  276,  277,  290,  279,  143,
  281,  282,  283,  284,  285,  274,  275,  288,  256,  292,
  279,  292,  281,  282,  283,  284,  285,  256,  289,  288,
  272,  260,  269,  292,  168,  291,  274,  275,  260,  261,
  287,  279,  288,  281,  282,  283,  284,  285,  260,  261,
  288,  269,  270,  271,  292,  256,  257,  258,  259,  260,
  261,  262,  263,  264,  265,  257,  288,  288,  269,  292,
  262,  292,  288,  265,  261,  257,  288,  269,  260,  261,
  262,  263,  287,  265,  280,  267,  280,  269,  289,  290,
  286,  287,  286,  269,  270,  271,  257,  280,  290,  260,
  261,  262,  263,  286,  265,  263,  267,  289,  269,  257,
  287,  269,  260,  261,  262,  263,  269,  265,  288,  267,
  266,  269,  269,  269,  270,  271,  257,  256,  289,  260,
  261,  262,  263,  288,  265,  257,  267,  269,  269,  288,
  262,  289,  287,  265,  257,  288,  256,  269,  256,  262,
  276,  277,  265,  256,  257,  256,  269,  290,  289,  262,
  287,  264,  265,  269,  274,  275,  269,  289,  292,  279,
  256,  281,  282,  283,  284,  285,  289,  256,  257,  274,
  275,  258,  259,  262,  288,  264,  265,  269,  257,  288,
  269,  260,  261,  262,  263,  288,  265,  257,  267,  288,
  269,  292,  262,  266,  292,  265,  269,  270,  271,  269,
  274,  275,   76,   77,   85,   86,  173,  174,  288,    3,
   97,  110,  129,   27,  117,
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
"sentence : functionCall _SEMICOLON",
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
"factor : functionCall",
"constant : _INT",
"constant : _LONGINT",
"$$5 :",
"functionCall : _ID _LPAREN _RPAREN $$5 _ID _LPAREN _ID _RPAREN",
"functionCall : _ID _LPAREN _ID _LPAREN _RPAREN _RPAREN",
"selection : _IF _LPAREN condition _RPAREN sentence_block _ENDIF",
"selection : _IF _LPAREN condition _RPAREN sentence_block else_section _ENDIF",
"selection : _IF _LPAREN condition _RPAREN sentence_block else_section error",
"selection : _IF _LPAREN condition _RPAREN error",
"$$6 :",
"else_section : _ELSE $$6 sentence_block",
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
"$$7 :",
"iteration : _FOR _LPAREN assignment _SEMICOLON $$7 condition _SEMICOLON increment _RPAREN sentence_block",
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

//#line 456 "MyGrammar.y"

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
{yyval = val_peek(1); notify("Found Function Invocation");}
break;
case 41:
//#line 224 "MyGrammar.y"
{notify("Found Selection sentence ", this.currentLine);}
break;
case 42:
//#line 226 "MyGrammar.y"
{notify("Found Iteration sentence ", this.currentLine);}
break;
case 43:
//#line 228 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Print sentence: " + yyval.toString(), this.currentLine);}
break;
case 44:
//#line 230 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Assignment of: " + yyval.toString(), this.currentLine);}
break;
case 45:
//#line 232 "MyGrammar.y"
{yyval = val_peek(2); notify("Found Assignment with annotation of: " + yyval.toString(), this.currentLine);}
break;
case 46:
//#line 236 "MyGrammar.y"
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
//#line 249 "MyGrammar.y"
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
//#line 263 "MyGrammar.y"
{yyval = new Addition(val_peek(2),val_peek(0),"");
																	((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 49:
//#line 266 "MyGrammar.y"
{yyval = new Subtraction(val_peek(2),val_peek(0),"");
																	((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 50:
//#line 269 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 51:
//#line 273 "MyGrammar.y"
{yyval = val_peek(1); 
																	if(terManager.conversionAllowed())
																		yyval.setArithmeticType(SymbolItem.ArithmeticType.LONG);
																	else terManager.getLog().addLog("Conversion not allowed", ((SymbolItem)val_peek(3)).getToken().getLine());}
break;
case 52:
//#line 279 "MyGrammar.y"
{yyval = new Multiplication(val_peek(2),val_peek(0),"");
																((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 53:
//#line 282 "MyGrammar.y"
{yyval = new Division(val_peek(2),val_peek(0),"");
																((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 54:
//#line 285 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 55:
//#line 288 "MyGrammar.y"
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
//#line 299 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.CONST); 
																((SymbolItem)yyval).setArithmeticType(this.currentType);
																this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 57:
//#line 303 "MyGrammar.y"
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
//#line 316 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 59:
//#line 319 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.INT;}
break;
case 60:
//#line 321 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.LONG;}
break;
case 61:
//#line 325 "MyGrammar.y"
{SymbolItem func = this.checkFunctionDeclaration((SymbolItem)val_peek(2));
									Tercet toAdd = new FunctionCall(func,null,this.currentScope.getScope());
									toAdd.setIndex(this.terManager.addTercet(toAdd));
									yyval = toAdd;}
break;
case 62:
//#line 338 "MyGrammar.y"
{yyval = val_peek(7); 
									SymbolItem func = this.checkFunctionDeclaration((SymbolItem)val_peek(7));
									SymbolItem var = this.checkVarDeclaration((SymbolItem)val_peek(5));
									Tercet toAdd = new FunctionCall(func,var,this.currentScope.getScope());
									toAdd.setIndex(this.terManager.addTercet(toAdd));}
break;
case 63:
//#line 345 "MyGrammar.y"
{yyval = val_peek(5); 
									SymbolItem func = this.checkFunctionDeclaration((SymbolItem)val_peek(5));
									SymbolItem param = this.checkFunctionDeclaration((SymbolItem)val_peek(3));
									Tercet toAdd = new FunctionCall(func,param,this.currentScope.getScope());
									toAdd.setIndex(this.terManager.addTercet(toAdd));}
break;
case 64:
//#line 352 "MyGrammar.y"
{this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																															this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();}
break;
case 65:
//#line 355 "MyGrammar.y"
{this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																															this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine();}
break;
case 66:
//#line 358 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine(); 
																															yyerror("Expected endif;");}
break;
case 67:
//#line 361 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(4)).getToken().getLine();
																															yyerror("Wrong if statement, sentence(s) expected");}
break;
case 68:
//#line 367 "MyGrammar.y"
{Tercet newBU = new BranchUnconditional(null,null,this.currentScope.getScope());
																	newBU.setIndex(this.terManager.addTercet(newBU));
																	this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																	this.conditionSTK.push((JumpTercet)newBU);}
break;
case 70:
//#line 373 "MyGrammar.y"
{yyerror("Wrong else statement, sentence(s) expected");}
break;
case 71:
//#line 376 "MyGrammar.y"
{Tercet newComp = new Comparator(((SymbolItem)val_peek(1)).getLex(),val_peek(2),val_peek(0),this.currentScope.getScope());
																					newComp.setIndex(this.terManager.addTercet(newComp));
																					yyval = newComp;
																					Tercet newBF = new BranchFalse(newComp,null,this.currentScope.getScope());
																					newBF.setIndex(this.terManager.addTercet(newBF));
																					this.conditionSTK.push((JumpTercet)newBF);}
break;
case 72:
//#line 384 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 73:
//#line 386 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 74:
//#line 388 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 75:
//#line 390 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 76:
//#line 392 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 77:
//#line 394 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 78:
//#line 396 "MyGrammar.y"
{yyerror("Invalid operator, comparator expected");}
break;
case 81:
//#line 403 "MyGrammar.y"
{yyerror("Expected '}'");}
break;
case 82:
//#line 406 "MyGrammar.y"
{this.iterationJumpDir = this.terManager.getNextIndex();}
break;
case 83:
//#line 408 "MyGrammar.y"
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
//#line 421 "MyGrammar.y"
{yyerror("Missing condition.");}
break;
case 85:
//#line 423 "MyGrammar.y"
{yyerror("Missing assignment. What were you planning on iterating on?");}
break;
case 86:
//#line 426 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(2));
																		this.incrementTercets.addLast(new Assignment(val_peek(2),val_peek(0),""));}
break;
case 87:
//#line 429 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(1));
																		this.incrementTercets.addLast(new Decrement(val_peek(1),null,this.currentScope.getScope()));}
break;
case 88:
//#line 433 "MyGrammar.y"
{this.incrementTercets.addLast(new Addition(val_peek(2),val_peek(0),""));}
break;
case 89:
//#line 435 "MyGrammar.y"
{this.incrementTercets.addLast(new Subtraction(val_peek(2),val_peek(0),""));}
break;
case 90:
//#line 437 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(0));}
break;
case 91:
//#line 439 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.CONST); 
																					((SymbolItem)yyval).setArithmeticType(this.currentType);
																					this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 92:
//#line 445 "MyGrammar.y"
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
