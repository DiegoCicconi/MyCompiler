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
    1,    5,    5,    8,    8,    9,    9,    3,    3,    7,
    7,    7,    7,    7,   13,   13,   14,   14,   14,   14,
   15,   15,   15,   16,   16,   16,   17,   17,   10,   10,
   10,   10,   10,   21,   20,   20,   18,   22,   22,   22,
   22,   22,   22,   22,   19,   19,   19,   23,   11,   11,
   11,   24,   24,   25,   25,   25,   25,   12,   28,    6,
   29,    6,    6,    6,    6,    6,   26,   26,   26,   27,
   27,   30,   30,   30,   30,   30,   30,   31,   31,   31,
};
final static short yylen[] = {                            2,
    0,    6,    0,    5,    3,    2,    2,    1,    4,    2,
    1,    2,    2,    1,    1,    3,    1,    2,    1,    2,
    1,    2,    2,    3,    3,    2,    3,    3,    1,    4,
    3,    3,    1,    1,    1,    2,    1,    1,    6,    7,
    7,    4,    5,    0,    3,    2,    3,    1,    1,    1,
    1,    1,    1,    1,    3,    1,    3,    0,   10,    5,
    3,    3,    2,    3,    3,    1,    1,    4,    0,    8,
    0,    7,    7,    6,    6,    5,    2,    2,    2,    2,
    1,    4,    3,    2,    3,    4,    2,    3,    3,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,   14,   15,    0,    0,    0,
    0,    0,    0,    0,    8,   11,    0,    0,   21,    0,
    0,    0,    0,    0,    0,    0,   10,    0,    0,   26,
    0,    0,    6,    7,   13,    0,   17,    0,   20,   22,
    0,    0,   19,    0,    0,   37,   38,    0,    0,   33,
   35,    0,    0,    0,   61,    0,    0,    0,    0,    5,
    0,    0,   24,    4,   18,    0,   36,   54,    0,    0,
   52,   48,   50,   49,   51,   53,    0,    0,    0,   42,
    0,   68,    0,    0,    0,    0,    0,    0,    9,    2,
    0,   16,    0,    0,    0,    0,   31,   32,   43,    0,
   56,    0,   78,   79,   77,    0,    0,   76,   81,    0,
   60,    0,    0,    0,   30,    0,    0,   39,    0,   84,
    0,    0,    0,   80,   75,    0,   74,    0,    0,   57,
   55,   46,    0,   41,   40,   90,    0,   85,    0,   83,
    0,   72,   73,    0,   45,   89,   88,   86,   82,    0,
    0,   70,    0,   63,    0,   66,   67,    0,   59,    0,
    0,    0,    0,
};
final static short yydgoto[] = {                          2,
  107,    3,   42,    4,   14,   15,   16,   17,   38,   18,
   19,   20,   21,   48,   49,   50,   51,   52,  102,  119,
  133,   77,  112,  151,  158,   87,  108,  144,  128,  109,
  123,
};
final static short yysindex[] = {                      -233,
    0,    0,   32, -262, -239,    0,    0, -235, -226, -215,
 -244, -211, -170, -166,    0,    0, -250, -135,    0, -128,
 -125,   41,   38, -192, -183, -209,    0,  -90,   38,    0,
   41, -112,    0,    0,    0,  -77,    0,  -93,    0,    0,
  -67, -180,    0,  -80,  -63,    0,    0,  -20, -159,    0,
    0, -242,  -58,  -17,    0,  -83,  -29, -151, -106,    0,
  -62,  -35,    0,    0,    0,   38,    0,    0, -156, -156,
    0,    0,    0,    0,    0,    0,   38, -156, -156,    0,
 -107,    0,  -34,  -30,  -28,  -86,  -43,   -7,    0,    0,
  -13,    0, -187, -159, -159, -151,    0,    0,    0,   41,
    0, -105,    0,    0,    0,   12,  -95,    0,    0,  -86,
    0,   38,   -4,  -32,    0, -207,    1,    0, -188,    0,
 -134,   22,  -18,    0,    0,  -25,    0,  -86,    2,    0,
    0,    0,  -38,    0,    0,    0, -241,    0, -245,    0,
   -9,    0,    0,  -86,    0,    0,    0,    0,    0, -194,
  -15,    0,   48,    0,  -38,    0,    0, -114,    0,   48,
   48, -114, -114,
};
final static short yyrindex[] = {                         0,
  -72,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -10,    0,    0,
 -117,    0,    0,    0, -253,    0,    0,    0, -218,    0,
    0,    0,    0,    0,    0,    0,    0,   -2,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   45,    0,    0,
    0,    0,    0, -176, -154, -248,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -61,    0,    0,    0,  -36,    0,    0,    0,
    0,    0,  -23,    0,    0,    0,    0,    0,  -47,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   -8,    0,    0,
    0,   -5,   -3,
};
final static short yygindex[] = {                         0,
  267,    0,  -27,    0,  -12,  -11,  -22,    0,    0,    0,
    0,    0,  262,  -24,  100,  108, -143,  184,  -79,    0,
    0,    0,    0,    0,   51,  209, -103,    0,    0,  195,
  183,
};
final static int YYTABLESIZE=319;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         43,
   32,   33,   34,   59,   58,   35,  125,   47,   43,  157,
  148,   27,   36,   80,  146,   28,  157,  157,   37,   65,
   34,   34,   34,   34,  142,   34,   22,   34,   34,   34,
   34,   34,   69,   70,   34,    1,   65,   29,   34,   47,
  152,   93,   25,   47,  149,   81,   55,   23,  130,    5,
  147,   24,   96,  145,    8,   29,   29,   10,  101,   12,
   29,   12,   29,   29,   29,   29,   29,  134,   29,   29,
  135,   26,  116,   29,   30,  159,    5,   43,  122,   27,
   53,    8,  131,   43,   10,  153,   69,   70,   12,    6,
    7,  154,    9,   65,   32,   33,  137,   27,   27,   65,
  115,   28,   27,   54,   27,   27,   27,   27,   27,   64,
  101,   27,   45,   46,   47,   27,   78,   79,   31,   28,
   28,  136,   69,   70,   28,   34,   28,   28,   28,   28,
   28,   44,  101,   28,   45,   46,   47,   28,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   99,    5,
    5,   23,  117,  118,    8,    8,   39,   10,   10,  160,
  161,   12,   12,   40,    6,    7,   41,    9,   94,   95,
    5,   23,   23,    6,    7,    8,    9,   57,   10,   60,
   11,  100,   12,   90,    1,   97,   98,    1,    1,    1,
    1,   61,    1,  106,    1,   71,    1,   62,   71,   71,
   71,   71,  106,   71,   63,   71,   66,   71,   88,   69,
  162,  163,   69,   69,   69,   69,    3,   69,    5,   69,
   44,   69,   67,    8,   91,   44,   10,   71,   44,   82,
   12,   89,   44,   92,  103,   68,   87,   87,  104,   87,
  105,   69,   83,   84,  110,   85,   83,   84,  111,   85,
  100,  127,   44,   69,   70,  129,  132,  143,   71,  150,
   72,   73,   74,   75,   76,   87,  141,  120,    5,   13,
   86,  140,  155,    8,  113,  121,   10,  138,    5,   62,
   12,   12,   64,    8,   65,  121,   10,   56,    5,   25,
   12,    6,    7,    8,    9,  126,   10,    5,   11,  114,
   12,  124,    8,   44,  139,   10,   45,   46,   47,   12,
   58,    0,    0,   58,   58,   58,  156,   46,   47,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         22,
   13,   13,  256,   31,   29,  256,  110,  256,   31,  153,
  256,  256,  263,  256,  256,  260,  160,  161,  269,   42,
  274,  275,  276,  277,  128,  279,  289,  281,  282,  283,
  284,  285,  274,  275,  288,  269,   59,  256,  292,  288,
  144,   66,  269,  292,  290,  288,  256,  287,  256,  257,
  292,  287,   77,  133,  262,  274,  275,  265,   81,  269,
  279,  269,  281,  282,  283,  284,  285,  256,  280,  288,
  259,  287,  100,  292,  286,  155,  257,  100,  106,  256,
  273,  262,  290,  106,  265,  280,  274,  275,  269,  260,
  261,  286,  263,  116,  107,  107,  121,  274,  275,  122,
  288,  256,  279,  287,  281,  282,  283,  284,  285,  290,
  133,  288,  269,  270,  271,  292,  276,  277,  289,  274,
  275,  256,  274,  275,  279,  292,  281,  282,  283,  284,
  285,  266,  155,  288,  269,  270,  271,  292,  256,  257,
  258,  259,  260,  261,  262,  263,  264,  265,  256,  257,
  257,  269,  258,  259,  262,  262,  292,  265,  265,  274,
  275,  269,  269,  292,  260,  261,  292,  263,   69,   70,
  257,  289,  290,  260,  261,  262,  263,  268,  265,  292,
  267,  289,  269,  290,  257,   78,   79,  260,  261,  262,
  263,  269,  265,  289,  267,  257,  269,  291,  260,  261,
  262,  263,  289,  265,  272,  267,  287,  269,  292,  257,
  160,  161,  260,  261,  262,  263,  289,  265,  257,  267,
  257,  269,  286,  262,  287,  262,  265,  289,  265,  288,
  269,  261,  269,  269,  269,  256,  260,  261,  269,  263,
  269,  289,  260,  261,  288,  263,  260,  261,  256,  263,
  289,  256,  289,  274,  275,  288,  256,  256,  279,  269,
  281,  282,  283,  284,  285,  289,  292,  256,  257,    3,
  288,  290,  288,  262,  288,  264,  265,  256,  257,  288,
  269,  292,  288,  262,  288,  264,  265,   26,  257,  292,
  269,  260,  261,  262,  263,  112,  265,  257,  267,   91,
  269,  107,  262,  266,  122,  265,  269,  270,  271,  269,
  266,   -1,   -1,  269,  270,  271,  269,  270,  271,
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
"$$3 :",
"else_section : _ELSE $$3 sentence_block",
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
"$$4 :",
"iteration : _FOR _LPAREN assignment _SEMICOLON $$4 condition _SEMICOLON increment _RPAREN sentence_block",
"iteration : _FOR _LPAREN assignment _SEMICOLON error",
"iteration : _FOR _LPAREN error",
"increment : _ID _ASSIGN for_expression",
"increment : _ID _MINUS_ONE",
"for_expression : for_expression _PLUS for_expression",
"for_expression : for_expression _MINUS for_expression",
"for_expression : _ID",
"for_expression : constant",
"print : _PRINT _LPAREN _STRING _RPAREN",
"$$5 :",
"function : type _FUNCTION _ID _LPAREN parameter _RPAREN $$5 function_body",
"$$6 :",
"function : type _FUNCTION _ID _LPAREN _RPAREN $$6 function_body",
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

//#line 407 "MyGrammar.y"

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
	this.conditionSTK = new Stack<JumpTercet>();
	this.syntaxLog = new Logger("SYNTAX");
	this.syntaxErrLog = new Logger("SYNTAX ERROR");
	this.tokensLog = new Logger("TOKENS");
}
public void Run() throws IOException{
  yyparse();
}
//#line 493 "Parser.java"
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
//#line 93 "MyGrammar.y"
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
//#line 105 "MyGrammar.y"
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
//#line 123 "MyGrammar.y"
{notify("Found Selection sentence ", this.currentLine);}
break;
case 21:
//#line 125 "MyGrammar.y"
{notify("Found Iteration sentence ", this.currentLine);}
break;
case 22:
//#line 127 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Print sentence: " + yyval.toString(), this.currentLine);}
break;
case 23:
//#line 129 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Assignment of: " + yyval.toString(), this.currentLine);}
break;
case 24:
//#line 131 "MyGrammar.y"
{yyval = val_peek(2); notify("Found Assignment with annotation of: " + yyval.toString(), this.currentLine);}
break;
case 25:
//#line 134 "MyGrammar.y"
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
case 26:
//#line 147 "MyGrammar.y"
{yyval = val_peek(1); this.currentLine = ((SymbolItem)val_peek(1)).getToken().getLine();
																	((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																	((SymbolItem)yyval).setScope(this.currentScope.getScope());
																	SymbolItem variable = this.symTable.getSymbol((SymbolItem)yyval);
																	if(variable == null){ 
																		yyerror("Variable not declared!", this.currentLine);
																	}
																	else {
																		yyval = new Decrement(val_peek(1),null,this.currentScope.getScope());
																		((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));
																	}}
break;
case 27:
//#line 160 "MyGrammar.y"
{yyval = new Addition(val_peek(2),val_peek(0),"");
																	((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 28:
//#line 163 "MyGrammar.y"
{yyval = new Subtraction(val_peek(2),val_peek(0),"");
																	((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
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
																((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 32:
//#line 182 "MyGrammar.y"
{yyval = new Division(val_peek(2),val_peek(0),"");
																((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 33:
//#line 185 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 34:
//#line 188 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																((SymbolItem)yyval).setScope(this.currentScope.getScope());
																if(!this.symTable.contains((SymbolItem)yyval)){ yyerror("Variable not declared!");}}
break;
case 35:
//#line 192 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.CONST); 
																((SymbolItem)yyval).setArithmeticType(this.currentType);
																this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 36:
//#line 196 "MyGrammar.y"
{yyval = val_peek(1); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																((SymbolItem)yyval).setScope(this.currentScope.getScope());
																if(!this.symTable.contains((SymbolItem)yyval)){ yyerror("Variable not declared!");}
																yyval = new Decrement(val_peek(1),null,this.currentScope.getScope());
																((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));
																yyval = val_peek(1);}
break;
case 37:
//#line 204 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.INT;}
break;
case 38:
//#line 206 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.LONG;}
break;
case 39:
//#line 209 "MyGrammar.y"
{this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																															this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();}
break;
case 40:
//#line 212 "MyGrammar.y"
{this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																															this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine();}
break;
case 41:
//#line 215 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine(); 
																															yyerror("Expected endif;");}
break;
case 42:
//#line 218 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(3)).getToken().getLine();
																															yyerror("Expected ')'", this.currentLine);}
break;
case 43:
//#line 221 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(4)).getToken().getLine();
																															yyerror("Wrong if statement, sentence(s) expected");}
break;
case 44:
//#line 227 "MyGrammar.y"
{Tercet newBU = new BranchUnconditional(null,null,this.currentScope.getScope());
																	newBU.setIndex(this.terManager.addTercet(newBU));
																	this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																	this.conditionSTK.push((JumpTercet)newBU);}
break;
case 46:
//#line 233 "MyGrammar.y"
{yyerror("Wrong else statement, sentence(s) expected");}
break;
case 47:
//#line 236 "MyGrammar.y"
{Tercet newComp = new Comparator(((SymbolItem)val_peek(1)).getLex(),val_peek(2),val_peek(0),this.currentScope.getScope());
																					newComp.setIndex(this.terManager.addTercet(newComp));
																					yyval = newComp;
																					Tercet newBF = new BranchFalse(newComp,null,this.currentScope.getScope());
																					newBF.setIndex(this.terManager.addTercet(newBF));
																					this.conditionSTK.push((JumpTercet)newBF);}
break;
case 48:
//#line 244 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 49:
//#line 246 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 50:
//#line 248 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 51:
//#line 250 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 52:
//#line 252 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 53:
//#line 254 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 54:
//#line 256 "MyGrammar.y"
{yyerror("Invalid operator, comparator expected");}
break;
case 57:
//#line 263 "MyGrammar.y"
{yyerror("Expected '}'");}
break;
case 58:
//#line 266 "MyGrammar.y"
{this.iterationJumpDir = this.terManager.getNextIndex();}
break;
case 59:
//#line 268 "MyGrammar.y"
{Tercet incTer = this.incrementTercets.pollFirst();
									incTer.setIndex(this.terManager.addTercet(incTer));
										while(!this.incrementTercets.isEmpty()){
											incTer = this.incrementTercets.pollFirst();
											incTer.setIndex(this.terManager.addTercet(incTer));
										}
									Tercet newBU = new BranchUnconditional(null,null,this.currentScope.getScope());
									newBU.setIndex(this.terManager.addTercet(newBU));
									((JumpTercet)newBU).setJumpDir(this.iterationJumpDir);
									this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
									this.currentLine = ((SymbolItem)val_peek(9)).getToken().getLine(); checkControlVars((SymbolItem)val_peek(7),(SymbolItem)val_peek(3));}
break;
case 60:
//#line 280 "MyGrammar.y"
{yyerror("Missing condition.");}
break;
case 61:
//#line 282 "MyGrammar.y"
{yyerror("Missing assignment. What were you planning on iterating on?");}
break;
case 62:
//#line 285 "MyGrammar.y"
{yyval = val_peek(2); this.currentLine = ((SymbolItem)val_peek(2)).getToken().getLine();
																		((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR);
																		((SymbolItem)yyval).setScope(this.currentScope.getScope());
																		SymbolItem variable = this.symTable.getSymbol((SymbolItem)yyval);
																		if(variable == null){ 
																			yyerror("For Variable not declared!");
																		}
																		else {
																			yyval = variable;
																			this.incrementTercets.addLast(new Assignment(val_peek(2),val_peek(0),""));
																		}}
break;
case 63:
//#line 297 "MyGrammar.y"
{yyval = val_peek(1);
																		((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																		((SymbolItem)yyval).setScope(this.currentScope.getScope());
																		SymbolItem variable = this.symTable.getSymbol((SymbolItem)yyval);
																		if(variable == null){ 
																			yyerror("For Variable not declared!");
																		}
																		else {
																			yyval = new Decrement(val_peek(1),null,this.currentScope.getScope());
																			this.incrementTercets.addLast(new Decrement(val_peek(1),null,this.currentScope.getScope()));
																		}}
break;
case 64:
//#line 310 "MyGrammar.y"
{this.incrementTercets.addLast(new Addition(val_peek(2),val_peek(0),""));}
break;
case 65:
//#line 312 "MyGrammar.y"
{this.incrementTercets.addLast(new Subtraction(val_peek(2),val_peek(0),""));}
break;
case 66:
//#line 314 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR); 
																					((SymbolItem)yyval).setScope(this.currentScope.getScope());
																					if(!this.symTable.contains((SymbolItem)yyval)){ yyerror("Variable not declared!");}}
break;
case 67:
//#line 318 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.CONST); 
																					((SymbolItem)yyval).setArithmeticType(this.currentType);
																					this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 68:
//#line 324 "MyGrammar.y"
{yyval = val_peek(1);
									((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.STR); 
									this.symTable.addSymbol((SymbolItem)yyval);
									this.currentLine = ((SymbolItem)val_peek(3)).getToken().getLine();
									yyval = new Print(val_peek(1),null,this.currentScope.getScope());
									((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));
									yyval = val_peek(1);}
break;
case 69:
//#line 333 "MyGrammar.y"
{this.currentScope.pushScope(((SymbolItem)val_peek(3)).getLex());}
break;
case 70:
//#line 334 "MyGrammar.y"
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
case 71:
//#line 348 "MyGrammar.y"
{this.currentScope.pushScope(((SymbolItem)val_peek(2)).getLex());}
break;
case 72:
//#line 349 "MyGrammar.y"
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
case 73:
//#line 363 "MyGrammar.y"
{yyerror("Missing function body.");}
break;
case 74:
//#line 365 "MyGrammar.y"
{yyerror("Missing function body.");}
break;
case 75:
//#line 367 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
break;
case 76:
//#line 370 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(4)).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
break;
case 84:
//#line 390 "MyGrammar.y"
{yyerror("Expected sentence or return sentence.");}
break;
case 85:
//#line 392 "MyGrammar.y"
{yyerror("Expected return sentence.");}
break;
case 86:
//#line 394 "MyGrammar.y"
{yyerror("Expected '}'.");}
break;
case 87:
//#line 396 "MyGrammar.y"
{yyerror("Expected '}'.");}
break;
case 89:
//#line 401 "MyGrammar.y"
{yyerror("Expected ';' after return statement");}
break;
case 90:
//#line 403 "MyGrammar.y"
{yyerror("Expected something to return");}
break;
//#line 1069 "Parser.java"
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
