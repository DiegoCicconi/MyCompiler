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
    2,    0,    4,    0,    3,    3,    1,    1,    1,    1,
    1,    1,    1,    6,    6,    9,    9,   10,   10,   13,
    7,   14,    7,    7,    7,    7,    7,   11,   11,   12,
   12,   15,   15,   15,   15,   15,   15,   16,   16,    5,
    5,    8,    8,    8,    8,    8,    8,   21,   21,   17,
   17,   17,   17,   23,   23,   23,   24,   24,   24,   24,
   25,   25,   22,   22,   18,   18,   18,   18,   29,   28,
   28,   26,   30,   30,   30,   30,   30,   30,   30,   27,
   27,   27,   31,   19,   19,   19,   32,   32,   33,   33,
   33,   33,   20,
};
final static short yylen[] = {                            2,
    0,    4,    0,    3,    3,    2,    3,    2,    2,    1,
    4,    2,    1,    2,    2,    1,    1,    3,    1,    0,
    8,    0,    7,    7,    6,    6,    5,    5,    2,    2,
    1,    4,    3,    2,    3,    4,    2,    3,    3,    2,
    1,    2,    1,    2,    2,    3,    2,    3,    2,    3,
    3,    1,    4,    3,    3,    1,    1,    1,    2,    1,
    1,    1,    3,    4,    6,    7,    7,    5,    0,    3,
    2,    3,    1,    1,    1,    1,    1,    1,    1,    3,
    1,    3,    0,   10,    5,    3,    3,    2,    3,    3,
    1,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,   16,   17,    0,    0,    0,
    0,    0,    0,    0,   10,   13,    0,    0,   43,    0,
    0,    0,    0,    4,    0,    0,    0,    0,   12,    0,
    0,   49,    0,    2,    0,    8,    9,   15,    0,   19,
    0,   42,   44,    0,   47,    6,    0,   41,    0,    0,
   61,   62,    0,   60,    0,   56,   58,    0,    0,    0,
   86,    0,    0,    0,    0,   63,    0,    7,    0,    0,
   46,    5,   40,    0,   59,   79,    0,    0,   77,   73,
   75,   74,   76,   78,    0,    0,    0,    0,   93,    0,
    0,    0,    0,   11,   64,    0,   18,    0,    0,    0,
    0,   54,   55,   68,    0,   81,    0,    0,    0,   27,
   31,    0,   29,    0,   85,    0,    0,    0,   53,    0,
    0,   65,    0,   34,    0,    0,    0,   30,    0,   26,
    0,   25,    0,    0,   82,   80,   71,    0,   67,   66,
    0,   35,    0,   33,    0,    0,   23,   24,    0,   70,
   39,   38,   36,   32,   28,    0,    0,   21,    0,   88,
    0,   91,   92,    0,   84,    0,    0,    0,    0,
};
final static short yydgoto[] = {                          2,
  109,    3,   24,    4,   47,   14,   15,   16,   17,   41,
   92,  110,  149,  133,  111,  127,   53,   18,   19,   20,
   21,   22,   55,   56,   57,   58,  107,  123,  138,   85,
  116,  157,  164,
};
final static short yysindex[] = {                      -254,
    0,    0,   36, -264, -253,    0,    0, -230, -201, -197,
 -173, -121, -167, -186,    0,    0, -242, -181,    0, -171,
 -163, -161, -101,    0,  -85, -136, -192, -200,    0, -110,
  -85,    0,   -4,    0, -130,    0,    0,    0, -100,    0,
 -117,    0,    0,  -92,    0,    0,  -75,    0,  -96, -182,
    0,    0,  -28,    0, -135,    0,    0,  -95,  -89, -244,
    0, -222,  -79,  -54,  -42,    0, -262,    0,  -68,  -43,
    0,    0,    0,  -85,    0,    0,   42,   42,    0,    0,
    0,    0,    0,    0,  -85,   42,   42,  -86,    0,  -65,
 -191,  -58,  -14,    0,    0, -148,    0, -256, -135, -135,
  -42,    0,    0,    0,   45,    0,   27,   12,  -88,    0,
    0,  -19,    0,  -65,    0,  -85,  -12,    1,    0, -102,
    4,    0,   14,    0,  -85,   26,    2,    0,  -16,    0,
    8,    0,  -65,   38,    0,    0,    0, -210,    0,    0,
 -252,    0, -245,    0,   16,   -5,    0,    0,  -65,    0,
    0,    0,    0,    0,    0, -178,   18,    0,   46,    0,
 -210,    0,    0,   34,    0,   46,   46,   34,   34,
};
final static short yyrindex[] = {                         0,
  -51,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   32,    0,    0, -112,    0,    0,    0,    0,    0, -246,
    0,    0,    0,    0, -208,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   33,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    9,    0,    0,    0,    0,    0, -165, -149,
 -164,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -40,    0,    0,    0,
  -17,    0,    0,    0,    0,    0,   -2,    0,    0,    0,
    0,    0,    0,  -26,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   39,    0,    0,    0,  -89,   40,
};
final static short yygindex[] = {                         0,
  323,    0,  316,    0,  100,  -11,  -10,  -23,  -55,    0,
  235,  -63,    0,    0,  221,  206,  -25,    0,    0,    0,
  305,  -24,  241,  234,  -78,  218, -118,    0,    0,    0,
    0,    0,  156,
};
final static int YYTABLESIZE=334;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         48,
   54,   35,   36,  151,   91,   65,   54,   67,   54,   57,
  153,   77,   78,   38,    1,    6,    7,   77,   78,  150,
   39,   77,   78,   73,   23,   95,   40,   57,   57,   57,
   57,  119,   57,   25,   57,   57,   57,   57,   57,  152,
   91,   57,  165,   90,  154,   57,    5,   52,   98,   54,
  130,    8,   54,   54,   10,   61,   26,   31,   12,  101,
   54,   54,   54,   32,  106,   52,   52,   27,   62,  147,
   52,  112,   52,   52,   52,   52,   52,  113,  105,   52,
  163,   48,   29,   52,   48,  158,   30,  163,  163,   28,
   50,   54,    6,    7,   60,    9,   73,   35,   36,  141,
   54,  159,   73,   75,   33,   37,   51,  160,   50,   50,
   42,    6,    7,   50,  106,   50,   50,   50,   50,   50,
   43,   23,   50,   72,   51,   51,   50,   72,   44,   51,
   45,   51,   51,   51,   51,   51,   59,  106,   51,  117,
   86,   87,   51,   45,   45,   45,   45,   45,   45,   45,
   45,   45,   45,  135,    5,    5,   45,   64,   31,    8,
    8,   68,   10,   10,   32,   33,   12,   12,   69,  104,
    5,    6,    7,   70,    9,    8,   45,   45,   10,   71,
   49,    5,   12,   50,   51,   52,    8,  136,   46,   10,
   74,    5,   88,   12,    6,    7,    8,    9,   89,   10,
  108,   11,  105,   12,  120,    1,   94,  126,    1,    1,
    1,    1,   93,    1,   72,    1,   22,    1,   96,   22,
   22,   22,   22,  108,   22,   97,   22,   76,   22,  114,
   20,   77,   78,   20,   20,   20,   20,    3,   20,   69,
   20,  115,   20,  132,   69,   77,   78,   69,   22,  129,
   79,   69,   80,   81,   82,   83,   84,   37,   37,  137,
   37,   49,   20,  156,   50,   51,   52,  124,    5,  139,
  145,   69,  140,    8,   83,  125,   10,   83,   83,   83,
   12,  142,    5,   66,  121,  122,   37,    8,  134,  125,
   10,  144,    5,  148,   12,    6,    7,    8,    9,  146,
   10,    5,   11,  155,   12,  161,    8,  166,  167,   10,
   50,   51,   52,   12,  162,   51,   52,   99,  100,  102,
  103,  168,  169,   14,   48,   13,   87,   90,   34,  128,
  118,  143,   63,  131,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         23,
   25,   13,   13,  256,   60,   31,   31,   33,   33,  256,
  256,  274,  275,  256,  269,  260,  261,  274,  275,  138,
  263,  274,  275,   47,  289,  288,  269,  274,  275,  276,
  277,  288,  279,  287,  281,  282,  283,  284,  285,  292,
   96,  288,  161,  288,  290,  292,  257,  256,   74,   74,
  114,  262,   77,   78,  265,  256,  287,  280,  269,   85,
   85,   86,   87,  286,   88,  274,  275,  269,  269,  133,
  279,  263,  281,  282,  283,  284,  285,  269,  289,  288,
  159,  105,  256,  292,  108,  149,  260,  166,  167,  287,
  256,  116,  260,  261,  287,  263,  120,  109,  109,  125,
  125,  280,  126,  286,  287,  292,  256,  286,  274,  275,
  292,  260,  261,  279,  138,  281,  282,  283,  284,  285,
  292,  289,  288,  288,  274,  275,  292,  292,  292,  279,
  292,  281,  282,  283,  284,  285,  273,  161,  288,  288,
  276,  277,  292,  256,  257,  258,  259,  260,  261,  262,
  263,  264,  265,  256,  257,  257,  269,  268,  280,  262,
  262,  292,  265,  265,  286,  287,  269,  269,  269,  256,
  257,  260,  261,  291,  263,  262,  289,  290,  265,  272,
  266,  257,  269,  269,  270,  271,  262,  290,  290,  265,
  287,  257,  288,  269,  260,  261,  262,  263,  288,  265,
  289,  267,  289,  269,  105,  257,  261,  108,  260,  261,
  262,  263,  292,  265,  290,  267,  257,  269,  287,  260,
  261,  262,  263,  289,  265,  269,  267,  256,  269,  288,
  257,  274,  275,  260,  261,  262,  263,  289,  265,  257,
  267,  256,  269,  256,  262,  274,  275,  265,  289,  269,
  279,  269,  281,  282,  283,  284,  285,  260,  261,  256,
  263,  266,  289,  269,  269,  270,  271,  256,  257,  256,
  287,  289,  259,  262,  266,  264,  265,  269,  270,  271,
  269,  256,  257,  288,  258,  259,  289,  262,  288,  264,
  265,  290,  257,  256,  269,  260,  261,  262,  263,  292,
  265,  257,  267,  288,  269,  288,  262,  274,  275,  265,
  269,  270,  271,  269,  269,  270,  271,   77,   78,   86,
   87,  166,  167,  292,  292,    3,  288,  288,   13,  109,
   96,  126,   28,  116,
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
"program : _ID $$1 declaration_sentences executabe_part",
"$$2 :",
"program : _ID $$2 executabe_part",
"executabe_part : _LCBRACE executable_sentences _RCBRACE",
"executabe_part : _LCBRACE _RCBRACE",
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
"functionCall : _ID _LPAREN expression _RPAREN",
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

//#line 459 "MyGrammar.y"

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
//#line 519 "Parser.java"
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
{yyval = val_peek(3); notify(yyval.toString() + " Compilated Successfully!", ((SymbolItem)val_peek(0)).getToken().getLine());}
break;
case 3:
//#line 64 "MyGrammar.y"
{this.currentScope = new Scope(val_peek(0).getLex());}
break;
case 4:
//#line 66 "MyGrammar.y"
{yyval = val_peek(2); notify(yyval.toString() + " Compilated Successfully!", ((SymbolItem)val_peek(0)).getToken().getLine());}
break;
case 5:
//#line 69 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 6:
//#line 71 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 11:
//#line 82 "MyGrammar.y"
{this.terManager.enableConversion();}
break;
case 12:
//#line 84 "MyGrammar.y"
{yyerror("Conversion invalid");}
break;
case 13:
//#line 86 "MyGrammar.y"
{yyerror("Sentences can't be used in the declarative portion of the code!");}
break;
case 15:
//#line 91 "MyGrammar.y"
{yyerror("Identifier(s) expected at declaration");}
break;
case 16:
//#line 94 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.INT;}
break;
case 17:
//#line 96 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.LONG;}
break;
case 18:
//#line 99 "MyGrammar.y"
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
case 19:
//#line 111 "MyGrammar.y"
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
//#line 124 "MyGrammar.y"
{this.currentScope.pushScope(((SymbolItem)val_peek(3)).getLex());
																													this.terManager.inFunction();
																													((SymbolItem)val_peek(1)).setScope(this.currentScope.getScope());
																													Tercet function = new Function(val_peek(3),val_peek(1),this.currentScope.getScope());
																													function.setIndex(this.terManager.addTercet(function));}
break;
case 21:
//#line 129 "MyGrammar.y"
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
case 22:
//#line 146 "MyGrammar.y"
{this.currentScope.pushScope(((SymbolItem)val_peek(2)).getLex());
																													this.terManager.inFunction();
																													Tercet function = new Function(val_peek(2),null,this.currentScope.getScope());
																													function.setIndex(this.terManager.addTercet(function));}
break;
case 23:
//#line 150 "MyGrammar.y"
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
case 24:
//#line 167 "MyGrammar.y"
{yyerror("Missing function body.");}
break;
case 25:
//#line 169 "MyGrammar.y"
{yyerror("Missing function body.");}
break;
case 26:
//#line 171 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
break;
case 27:
//#line 174 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(4)).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
break;
case 28:
//#line 178 "MyGrammar.y"
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
case 29:
//#line 192 "MyGrammar.y"
{yyval = val_peek(0);
																													((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.PARAM); 
																													((SymbolItem)yyval).setArithmeticType(this.currentType);
																													((SymbolItem)yyval).setScope(this.currentScope.getScope());
																													this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 30:
//#line 199 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 31:
//#line 201 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 32:
//#line 204 "MyGrammar.y"
{yyval = val_peek(1);}
break;
case 33:
//#line 206 "MyGrammar.y"
{yyval = val_peek(1);}
break;
case 34:
//#line 208 "MyGrammar.y"
{yyerror("Expected sentence or return sentence.");}
break;
case 35:
//#line 210 "MyGrammar.y"
{yyerror("Expected return sentence.");}
break;
case 36:
//#line 212 "MyGrammar.y"
{yyerror("Expected '}'.");}
break;
case 37:
//#line 214 "MyGrammar.y"
{yyerror("Expected '}'.");}
break;
case 38:
//#line 217 "MyGrammar.y"
{yyval = val_peek(1);}
break;
case 39:
//#line 219 "MyGrammar.y"
{yyerror("Expected ';' after return statement");}
break;
case 42:
//#line 227 "MyGrammar.y"
{notify("Found Selection sentence ", this.currentLine);}
break;
case 43:
//#line 229 "MyGrammar.y"
{notify("Found Iteration sentence ", this.currentLine);}
break;
case 44:
//#line 231 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Print sentence: " + yyval.toString(), this.currentLine);}
break;
case 45:
//#line 233 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Assignment of: " + yyval.toString(), this.currentLine);}
break;
case 46:
//#line 235 "MyGrammar.y"
{yyval = val_peek(2); notify("Found Assignment with annotation of: " + yyval.toString(), this.currentLine);}
break;
case 47:
//#line 237 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Function Invocation");}
break;
case 48:
//#line 240 "MyGrammar.y"
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
case 49:
//#line 253 "MyGrammar.y"
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
case 50:
//#line 267 "MyGrammar.y"
{yyval = new Addition(val_peek(2),val_peek(0),"");
																	((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 51:
//#line 270 "MyGrammar.y"
{yyval = new Subtraction(val_peek(2),val_peek(0),"");
																	((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 52:
//#line 273 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 53:
//#line 277 "MyGrammar.y"
{yyval = val_peek(1); 
																	if(terManager.conversionAllowed())
																		yyval.setArithmeticType(SymbolItem.ArithmeticType.LONG);
																	else terManager.getLog().addLog("Conversion not allowed", ((SymbolItem)val_peek(3)).getToken().getLine());}
break;
case 54:
//#line 283 "MyGrammar.y"
{yyval = new Multiplication(val_peek(2),val_peek(0),"");
																((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 55:
//#line 286 "MyGrammar.y"
{yyval = new Division(val_peek(2),val_peek(0),"");
																((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 56:
//#line 289 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 57:
//#line 292 "MyGrammar.y"
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
case 58:
//#line 303 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.CONST); 
																((SymbolItem)yyval).setArithmeticType(this.currentType);
																this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 59:
//#line 307 "MyGrammar.y"
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
case 60:
//#line 320 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 61:
//#line 323 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.INT;}
break;
case 62:
//#line 325 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.LONG;}
break;
case 63:
//#line 329 "MyGrammar.y"
{SymbolItem func = this.checkFunctionDeclaration((SymbolItem)val_peek(2));
									Tercet toAdd = new FunctionCall(func,null,this.currentScope.getScope());
									toAdd.setIndex(this.terManager.addTercet(toAdd));
									yyval = toAdd;}
break;
case 64:
//#line 335 "MyGrammar.y"
{yyval = val_peek(3);
									SymbolItem func = this.checkFunctionDeclaration((SymbolItem)val_peek(3));
									this.terManager.addTercet(new FunctionCall(func,val_peek(1),this.currentScope.getScope()));}
break;
case 65:
//#line 356 "MyGrammar.y"
{this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																															this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();}
break;
case 66:
//#line 359 "MyGrammar.y"
{this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																															this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine();}
break;
case 67:
//#line 362 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine(); 
																															yyerror("Expected endif;");}
break;
case 68:
//#line 365 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(4)).getToken().getLine();
																															yyerror("Wrong if statement, sentence(s) section error");}
break;
case 69:
//#line 370 "MyGrammar.y"
{Tercet newBU = new BranchUnconditional(null,null,this.currentScope.getScope());
																	newBU.setIndex(this.terManager.addTercet(newBU));
																	this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																	this.conditionSTK.push((JumpTercet)newBU);}
break;
case 71:
//#line 376 "MyGrammar.y"
{yyerror("Wrong else statement, sentence(s) expected");}
break;
case 72:
//#line 379 "MyGrammar.y"
{Tercet newComp = new Comparator(((SymbolItem)val_peek(1)).getLex(),val_peek(2),val_peek(0),this.currentScope.getScope());
																					newComp.setIndex(this.terManager.addTercet(newComp));
																					yyval = newComp;
																					Tercet newBF = new BranchFalse(newComp,null,this.currentScope.getScope());
																					newBF.setIndex(this.terManager.addTercet(newBF));
																					this.conditionSTK.push((JumpTercet)newBF);}
break;
case 73:
//#line 387 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 74:
//#line 389 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 75:
//#line 391 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 76:
//#line 393 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 77:
//#line 395 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 78:
//#line 397 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 79:
//#line 399 "MyGrammar.y"
{yyerror("Invalid operator, comparator expected");}
break;
case 82:
//#line 406 "MyGrammar.y"
{yyerror("Expected '}'");}
break;
case 83:
//#line 409 "MyGrammar.y"
{this.iterationJumpDir = this.terManager.getNextIndex();}
break;
case 84:
//#line 411 "MyGrammar.y"
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
case 85:
//#line 424 "MyGrammar.y"
{yyerror("Missing condition.");}
break;
case 86:
//#line 426 "MyGrammar.y"
{yyerror("Missing assignment. What were you planning on iterating on?");}
break;
case 87:
//#line 429 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(2));
																		this.incrementTercets.addLast(new Assignment(val_peek(2),val_peek(0),""));}
break;
case 88:
//#line 432 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(1));
																		this.incrementTercets.addLast(new Decrement(val_peek(1),null,this.currentScope.getScope()));}
break;
case 89:
//#line 436 "MyGrammar.y"
{this.incrementTercets.addLast(new Addition(val_peek(2),val_peek(0),""));}
break;
case 90:
//#line 438 "MyGrammar.y"
{this.incrementTercets.addLast(new Subtraction(val_peek(2),val_peek(0),""));}
break;
case 91:
//#line 440 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(0));}
break;
case 92:
//#line 442 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.CONST); 
																					((SymbolItem)yyval).setArithmeticType(this.currentType);
																					this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 93:
//#line 448 "MyGrammar.y"
{yyval = val_peek(1);
									((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.STR); 
									this.symTable.addSymbol((SymbolItem)yyval);
									this.currentLine = ((SymbolItem)val_peek(3)).getToken().getLine();
									yyval = new Print(val_peek(1),null,this.currentScope.getScope());
									((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));
									yyval = val_peek(1);}
break;
//#line 1164 "Parser.java"
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
