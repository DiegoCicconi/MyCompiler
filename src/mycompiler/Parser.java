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
    1,    1,    1,    8,    6,    6,   10,   10,   11,   11,
   14,    7,   15,    7,    7,    7,    7,    7,   12,   12,
   13,   13,   16,   16,   16,   16,   16,   16,   17,   17,
    5,    5,    9,    9,    9,    9,    9,    9,   22,   22,
   18,   18,   18,   18,   25,   25,   25,   26,   26,   26,
   26,   27,   27,   24,   23,   23,   23,   19,   19,   19,
   19,   28,   28,   28,   32,   29,   29,   30,   33,   33,
   33,   33,   33,   33,   33,   31,   31,   31,   34,   20,
   20,   20,   35,   35,   36,   36,   36,   36,   21,
};
final static short yylen[] = {                            2,
    0,    4,    0,    3,    3,    2,    3,    2,    2,    1,
    2,    1,    1,    5,    2,    2,    1,    1,    3,    1,
    0,    8,    0,    7,    7,    6,    6,    5,    5,    2,
    2,    1,    4,    3,    2,    3,    4,    2,    3,    3,
    2,    1,    2,    1,    2,    2,    3,    2,    3,    1,
    3,    3,    1,    4,    3,    3,    1,    1,    1,    1,
    1,    1,    1,    2,    3,    4,    6,    2,    3,    3,
    2,    5,    6,    5,    0,    3,    2,    3,    1,    1,
    1,    1,    1,    1,    1,    3,    1,    3,    0,   10,
    5,    3,    3,    2,    3,    3,    1,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,   17,   18,    0,    0,    0,
    0,    0,    0,    0,   10,   12,   13,    0,    0,   44,
    0,    0,    0,   50,    0,    0,    4,    0,    0,    0,
    0,    0,    0,   64,    0,    2,    0,    8,   11,    9,
   16,    0,   20,    0,   43,   45,    0,   48,   71,    0,
   68,    0,    6,    0,   42,    0,    0,   62,   63,    0,
   61,   60,    0,   57,   59,    0,    0,    0,   92,    0,
    0,    0,    0,    0,   65,    7,    0,    0,   47,   77,
    0,   70,   69,    5,   41,    0,   85,    0,    0,   83,
   79,   81,   80,   82,   84,    0,    0,    0,    0,   99,
    0,    0,    0,    0,    0,    0,   66,    0,   19,    0,
   87,   76,    0,    0,    0,    0,   55,   56,   74,    0,
    0,   72,    0,    0,   28,   32,    0,   30,    0,   91,
    0,   14,    0,    0,    0,    0,   54,    0,   35,    0,
    0,    0,   31,    0,   27,    0,   67,   26,    0,    0,
   88,   86,    0,   36,    0,   34,    0,    0,   24,   25,
    0,   40,   39,   37,   33,   29,    0,    0,   22,    0,
   94,    0,   97,   98,    0,   90,    0,    0,    0,    0,
};
final static short yydgoto[] = {                          2,
  124,    3,   27,    4,   54,   14,   15,   16,   17,   18,
   44,  103,  125,  161,  149,  126,  142,   60,   19,   20,
   21,   22,   23,   24,   63,   64,   65,   25,   52,   66,
  112,   81,   96,  131,  168,  175,
};
final static short yysindex[] = {                      -254,
    0,    0,  102, -225, -204,    0,    0, -179, -195, -175,
 -143,  -49, -209, -171,    0,    0,    0, -160, -153,    0,
 -147, -145, -130,    0,  -36, -197,    0,  -96, -124, -127,
 -190,  -97,  -96,    0, -253,    0, -105,    0,    0,    0,
    0,  -80,    0,  -88,    0,    0,  -56,    0,    0,  -38,
    0,   -4,    0,  -64,    0,  -54, -263,    0,    0,   26,
    0,    0, -176,    0,    0,  -53,  -42, -212,    0, -261,
  -52,  -13, -148, -132,    0,    0,  -37,  -16,    0,    0,
    8,    0,    0,    0,    0,  -96,    0,   33,   33,    0,
    0,    0,    0,    0,    0,  -96,   33,   33, -167,    0,
  -48, -213,  -25,    5,  -14,  -12,    0, -169,    0,  -63,
    0,    0, -262, -176, -176, -148,    0,    0,    0,  -63,
    0,    0,   82, -125,    0,    0,   14,    0,  -48,    0,
  -96,    0,    2,   18,    7,  -65,    0,    0,    0,  -96,
   92,   -2,    0,    9,    0,    6,    0,    0,  -48,   50,
    0,    0, -229,    0, -246,    0,   30,   43,    0,    0,
  -48,    0,    0,    0,    0,    0, -198,   31,    0,   55,
    0,    8,    0,    0,  -67,    0,   55,   55,  -67,  -67,
};
final static short yyrindex[] = {                         0,
  -33,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   29,    0,    0,  -79,    0,    0,   10,
    0,    0,    0,    0,    0,    0, -245,    0,    0,    0,
    0,    0, -151,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   40,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   23,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -131, -116, -274,    0,    0,    0,    0,
   58,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -18,    0,    0,    0,   72,    0,    0,
    0,   24,    0,    0,    0,    0,    0,    0,    0,   -3,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   34,    0,    0,    0,   45,   47,
};
final static short yygindex[] = {                         0,
  333,    0,  327,    0,  -90,  -10,   -8,   -6,  -26,  -66,
    0,  234, -108,    0,    0,  219,  204,  -29,    0,    0,
    0,  319,  -27,  -11,  107,  171,  -71,    0,    0,  221,
  -91,    0,    0,    0,    0,  103,
};
final static int YYTABLESIZE=371;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         55,
   61,  102,   37,   73,   38,   61,   39,  122,  120,  164,
   58,   88,   89,   78,    1,   74,   62,   78,   33,  136,
  145,   62,   34,   35,   34,  137,  162,   85,   58,   58,
   58,   58,  141,   58,   75,   58,   58,   58,   58,   58,
  159,  102,   58,  165,   88,   89,   58,    6,    7,  127,
    6,    7,  169,    9,  111,  128,  113,   11,   61,    5,
   61,   61,  163,   26,    8,   69,  116,   10,   61,   61,
   61,   12,  121,   30,   62,  101,   62,   62,   70,   26,
  176,  170,   28,   55,   62,   62,   62,  171,  119,    5,
    6,    7,   53,  138,    8,   41,   55,   10,  174,   97,
   98,   12,   42,   61,   53,  174,  174,   29,   43,   85,
  153,   31,   61,   37,   85,   38,   32,   39,  134,   62,
   40,  110,   53,   53,   51,   88,   89,   53,   62,   53,
   53,   53,   53,   53,    6,    7,   53,    9,   45,   52,
   53,   11,   51,   51,   46,  111,   47,   51,   67,   51,
   51,   51,   51,   51,  106,  107,   51,   52,   52,   68,
   51,   48,   52,  123,   52,   52,   52,   52,   52,   56,
   72,   52,   57,   58,   59,   52,   46,   46,   46,   46,
   46,   46,   46,   46,   46,   46,   76,   46,   77,   46,
  151,    5,    5,    5,  114,  115,    8,    8,    8,   10,
   10,   10,   78,   12,   12,   12,  177,  178,    5,   46,
   46,    6,    7,    8,    9,   79,   10,   80,   11,   49,
   12,   50,   51,    1,  152,   84,    1,    1,    1,    1,
   33,    1,   86,    1,   99,    1,   34,   35,   23,  104,
  123,   23,   23,   23,   23,  100,   23,  105,   23,  108,
   23,   82,  109,   21,   83,    3,   21,   21,   21,   21,
  130,   21,  129,   21,    5,   21,   75,  117,  118,    8,
   23,   75,   10,  148,   75,  133,   12,  132,   75,  179,
  180,   87,  144,   38,   38,   21,   38,  156,   89,  147,
   38,   89,   89,   89,  150,  157,  110,  158,   75,   88,
   89,   57,   58,   59,   90,  160,   91,   92,   93,   94,
   95,  167,   38,   87,   42,   87,   87,  166,  172,   42,
   15,   93,   42,  173,   58,   59,   42,   73,   41,   73,
   73,   49,   95,   41,   96,   13,   41,  139,    5,   36,
   41,  135,  143,    8,  155,  140,   10,  154,    5,   71,
   12,  146,    0,    8,    0,  140,   10,    0,    5,    0,
   12,    6,    7,    8,    9,    0,   10,    0,   11,    0,
   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         26,
   28,   68,   13,   33,   13,   33,   13,   99,   99,  256,
  256,  274,  275,  288,  269,  269,   28,  292,  280,  110,
  129,   33,  286,  287,  286,  288,  256,   54,  274,  275,
  276,  277,  123,  279,  288,  281,  282,  283,  284,  285,
  149,  108,  288,  290,  274,  275,  292,  260,  261,  263,
  260,  261,  161,  263,   81,  269,   86,  267,   86,  257,
   88,   89,  292,  289,  262,  256,   96,  265,   96,   97,
   98,  269,   99,  269,   86,  288,   88,   89,  269,  289,
  172,  280,  287,  110,   96,   97,   98,  286,  256,  257,
  260,  261,  290,  120,  262,  256,  123,  265,  170,  276,
  277,  269,  263,  131,  256,  177,  178,  287,  269,  136,
  140,  287,  140,  124,  141,  124,  260,  124,  288,  131,
  292,  289,  274,  275,  256,  274,  275,  279,  140,  281,
  282,  283,  284,  285,  260,  261,  288,  263,  292,  256,
  292,  267,  274,  275,  292,  172,  292,  279,  273,  281,
  282,  283,  284,  285,  287,  288,  288,  274,  275,  287,
  292,  292,  279,  289,  281,  282,  283,  284,  285,  266,
  268,  288,  269,  270,  271,  292,  256,  257,  258,  259,
  260,  261,  262,  263,  264,  265,  292,  267,  269,  269,
  256,  257,  257,  257,   88,   89,  262,  262,  262,  265,
  265,  265,  291,  269,  269,  269,  274,  275,  257,  289,
  290,  260,  261,  262,  263,  272,  265,  256,  267,  256,
  269,  258,  259,  257,  290,  290,  260,  261,  262,  263,
  280,  265,  287,  267,  288,  269,  286,  287,  257,  292,
  289,  260,  261,  262,  263,  288,  265,  261,  267,  287,
  269,  256,  269,  257,  259,  289,  260,  261,  262,  263,
  256,  265,  288,  267,  257,  269,  257,   97,   98,  262,
  289,  262,  265,  256,  265,  288,  269,  292,  269,  177,
  178,  256,  269,  260,  261,  289,  263,  290,  266,  288,
  267,  269,  270,  271,  288,  287,  289,  292,  289,  274,
  275,  269,  270,  271,  279,  256,  281,  282,  283,  284,
  285,  269,  289,  256,  257,  258,  259,  288,  288,  262,
  292,  288,  265,  269,  270,  271,  269,  256,  257,  258,
  259,  292,  288,  262,  288,    3,  265,  256,  257,   13,
  269,  108,  124,  262,  141,  264,  265,  256,  257,   31,
  269,  131,   -1,  262,   -1,  264,  265,   -1,  257,   -1,
  269,  260,  261,  262,  263,   -1,  265,   -1,  267,   -1,
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
"declaration_sentences : declaration_sentences allow_conversion",
"declaration_sentences : allow_conversion",
"declaration_sentences : sentence",
"allow_conversion : _ALLOW _INTEGER _TO _LONGINTEGER _SEMICOLON",
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
"assignment : decrement",
"expression : expression _PLUS term",
"expression : expression _MINUS term",
"expression : term",
"expression : _INTTOLONG _LPAREN expression _RPAREN",
"term : term _MULT factor",
"term : term _DIV factor",
"term : factor",
"factor : _ID",
"factor : constant",
"factor : decrement",
"factor : functionCall",
"constant : _INT",
"constant : _LONGINT",
"decrement : _ID _MINUS_ONE",
"functionCall : _ID _LPAREN _RPAREN",
"functionCall : _ID _LPAREN _ID _RPAREN",
"functionCall : _ID _LPAREN _ID _LPAREN _RPAREN _RPAREN",
"selection : if_section _ENDIF",
"selection : if_section else_section _ENDIF",
"selection : if_section else_section error",
"selection : if_section error",
"if_section : _IF _LPAREN condition _RPAREN sentence_block",
"if_section : _IF _LPAREN condition _RPAREN executable_sentences sentence",
"if_section : _IF _LPAREN condition _RPAREN error",
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

//#line 511 "MyGrammar.y"

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
//#line 536 "Parser.java"
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
{yyval = val_peek(3); notify(yyval.toString() + " End Of File Reached!", ((SymbolItem)val_peek(0)).getToken().getLine());}
break;
case 3:
//#line 64 "MyGrammar.y"
{this.currentScope = new Scope(val_peek(0).getLex());}
break;
case 4:
//#line 66 "MyGrammar.y"
{yyval = val_peek(2); notify(yyval.toString() + " End Of File Reached!", ((SymbolItem)val_peek(0)).getToken().getLine());}
break;
case 5:
//#line 69 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 6:
//#line 71 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 13:
//#line 86 "MyGrammar.y"
{yyerror("Sentences can't be used in the declarative portion of the code!");}
break;
case 14:
//#line 89 "MyGrammar.y"
{notify("Conversion int to long allowed"); this.terManager.enableConversion();}
break;
case 16:
//#line 94 "MyGrammar.y"
{yyerror("Identifier(s) expected at declaration");}
break;
case 17:
//#line 97 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.INT;}
break;
case 18:
//#line 99 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.LONG;}
break;
case 19:
//#line 102 "MyGrammar.y"
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
//#line 114 "MyGrammar.y"
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
case 21:
//#line 127 "MyGrammar.y"
{/*pusheamos la scope para el contenido de adetro de la funcion*/
																													this.currentScope.pushScope(((SymbolItem)val_peek(3)).getLex());
																													/*le avisamos al manager que estamos adentro de una funcion*/
																													this.terManager.inFunction();
																													((SymbolItem)val_peek(1)).setScope(this.currentScope.getScope());
																													Tercet function = new Function(val_peek(3),val_peek(1),this.currentScope.getScope());
																													function.setIndex(this.terManager.addTercet(function));}
break;
case 22:
//#line 134 "MyGrammar.y"
{yyval = val_peek(5);
																													/*el $8 es el function_body, que recursivamente nos devuelve la sentencia del return*/
																													Tercet ret = new Return(val_peek(5),val_peek(0),this.currentScope.getScope());
																													ret.setIndex(this.terManager.addTercet(ret));
																													/*avisamos que salimos de la funcion y sacamos la Scope del stack*/
																													this.terManager.outOfFunction();
																													this.currentScope.popScope();
																													/*chequeamos que el nombre de la funcion no este usado ya*/
																													((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)yyval).setArithmeticType(this.currentType);
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
case 23:
//#line 154 "MyGrammar.y"
{this.currentScope.pushScope(((SymbolItem)val_peek(2)).getLex());
																													this.terManager.inFunction();
																													Tercet function = new Function(val_peek(2),null,this.currentScope.getScope());
																													function.setIndex(this.terManager.addTercet(function));}
break;
case 24:
//#line 158 "MyGrammar.y"
{yyval = val_peek(4);
															 														Tercet ret = new Return(val_peek(4),val_peek(0),this.currentScope.getScope());
																													ret.setIndex(this.terManager.addTercet(ret));
															 														this.terManager.outOfFunction();
																													this.currentScope.popScope();
																													((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)yyval).setArithmeticType(this.currentType);
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
case 25:
//#line 175 "MyGrammar.y"
{yyerror("Missing function body.");}
break;
case 26:
//#line 177 "MyGrammar.y"
{yyerror("Missing function body.");}
break;
case 27:
//#line 179 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
break;
case 28:
//#line 182 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(4)).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
break;
case 29:
//#line 186 "MyGrammar.y"
{yyval = val_peek(2); 
																													/* comprobamos que exista la funcion del parametro..*/
																													((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.FUNC);
																													((SymbolItem)yyval).setArithmeticType(this.currentType);
																													((SymbolItem)yyval).setScope(this.currentScope.getScope());
																													/* ..y que sea del mismo tipo aritmetico*/
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
case 30:
//#line 202 "MyGrammar.y"
{yyval = val_peek(0);
																													((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.PARAM); 
																													((SymbolItem)yyval).setArithmeticType(this.currentType);
																													((SymbolItem)yyval).setScope(this.currentScope.getScope());
																													this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 31:
//#line 209 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 32:
//#line 211 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 33:
//#line 214 "MyGrammar.y"
{yyval = val_peek(1);}
break;
case 34:
//#line 216 "MyGrammar.y"
{yyval = val_peek(1);}
break;
case 35:
//#line 218 "MyGrammar.y"
{yyerror("Expected sentence or return sentence.");}
break;
case 36:
//#line 220 "MyGrammar.y"
{yyerror("Expected return sentence.");}
break;
case 37:
//#line 222 "MyGrammar.y"
{yyerror("Expected '}'.");}
break;
case 38:
//#line 224 "MyGrammar.y"
{yyerror("Expected '}'.");}
break;
case 39:
//#line 227 "MyGrammar.y"
{yyval = val_peek(1);}
break;
case 40:
//#line 229 "MyGrammar.y"
{yyerror("Expected ';' after return statement");}
break;
case 43:
//#line 237 "MyGrammar.y"
{notify("Found Selection sentence ", this.currentLine);}
break;
case 44:
//#line 239 "MyGrammar.y"
{notify("Found Iteration sentence ", this.currentLine);}
break;
case 45:
//#line 241 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Print sentence: " + yyval.toString(), this.currentLine);}
break;
case 46:
//#line 243 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Assignment of: " + yyval.toString(), this.currentLine);}
break;
case 47:
//#line 245 "MyGrammar.y"
{yyval = val_peek(2); notify("Found Assignment with annotation of: " + yyval.toString(), this.currentLine);}
break;
case 48:
//#line 247 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Function Invocation");}
break;
case 49:
//#line 250 "MyGrammar.y"
{yyval = val_peek(2); 
																	((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR);
																	((SymbolItem)yyval).setScope(this.currentScope.getScope());
																	SymbolItem variable = this.symTable.getSymbol((SymbolItem)yyval);
																	if(variable == null){ 
																		this.currentLine = ((SymbolItem)val_peek(2)).getToken().getLine();
																		yyerror("Variable not declared!" , this.currentLine);
																	}
																	else {
																		yyval = variable;
																		Tercet toAdd = new Assignment(val_peek(2),val_peek(0),"");
																		toAdd.setIndex(this.terManager.addTercet(toAdd));
																	}}
break;
case 51:
//#line 267 "MyGrammar.y"
{yyval = new Addition(val_peek(2),val_peek(0),"");
																	((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 52:
//#line 270 "MyGrammar.y"
{yyval = new Subtraction(val_peek(2),val_peek(0),"");
																	((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 53:
//#line 273 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 54:
//#line 277 "MyGrammar.y"
{yyval = val_peek(1); 
									/*preguntamos al manager si fueron habilitadas las conversiones*/
									if(terManager.conversionAllowed()){yyval.setArithmeticType(SymbolItem.ArithmeticType.LONG);}
									else {yyerror("Conversion not allowed", ((SymbolItem)val_peek(3)).getToken().getLine());}}
break;
case 55:
//#line 283 "MyGrammar.y"
{yyval = new Multiplication(val_peek(2),val_peek(0),"");
																((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 56:
//#line 286 "MyGrammar.y"
{yyval = new Division(val_peek(2),val_peek(0),"");
																((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 57:
//#line 289 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 58:
//#line 292 "MyGrammar.y"
{yyval = val_peek(0);
																((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR);
																((SymbolItem)yyval).setScope(this.currentScope.getScope());
																/*getSymnol busca en nuestra Scope y en todas las scopes por encima nuestro*/
																SymbolItem variable = this.symTable.getSymbol((SymbolItem)yyval);
																if(variable == null){ 
																	/*puede ser la variable que representa una funcion*/
																	((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.FUNC);
																	SymbolItem function = this.symTable.getSymbol((SymbolItem)yyval);
																	if(function == null){
																		/*si estamos en una funcion puede ser que estemos buscando un parametro*/
																		if(!this.terManager.areWeInFunction())
																			yyerror("Variable not declared!" , this.currentLine);
																		else{
																		/*como estamos adentro de una funcion, buscamos si es un parametro*/
																		((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.PARAM);
																		SymbolItem parameter = this.symTable.getSymbol((SymbolItem)yyval);
																		if(parameter == null)
																			yyerror("Variable not declared!" , this.currentLine);
																		else
																			yyval = parameter;
																		}
																	}
																	else{
																		yyval = function;
																	}
																}
																else {
																	yyval = variable;
																}}
break;
case 59:
//#line 323 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.CONST); 
																((SymbolItem)yyval).setArithmeticType(this.currentType);
																if(!this.symTable.contains((SymbolItem)yyval))
																	this.symTable.addSymbol((SymbolItem)yyval);
																else
																	yyval = this.symTable.getSymbol((SymbolItem)yyval);}
break;
case 60:
//#line 330 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 61:
//#line 332 "MyGrammar.y"
{yyerror("It is not allow to call a function in an expression");}
break;
case 62:
//#line 335 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.INT;}
break;
case 63:
//#line 337 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.LONG;}
break;
case 64:
//#line 340 "MyGrammar.y"
{yyval = val_peek(1);
																((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR);
																((SymbolItem)yyval).setScope(this.currentScope.getScope());
																SymbolItem variable = this.symTable.getSymbol((SymbolItem)yyval);
																if(variable == null){ 
																	this.currentLine = ((SymbolItem)val_peek(1)).getToken().getLine();
																	/*si estamos en una funcion puede ser que estemos buscando un parametro*/
																	if(!this.terManager.areWeInFunction())
																		yyerror("Variable not declared!" , this.currentLine);
																	else{
																		/*como estamos adentro de una funcion, buscamos si es un parametro*/
																		((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.PARAM);
																		SymbolItem parameter = this.symTable.getSymbol((SymbolItem)yyval);
																		if(parameter == null)
																			yyerror("Variable not declared!" , this.currentLine);
																		else
																			yyval = parameter;
																	}
																}
																else {
																	yyval = variable;
																	Tercet toAdd = new Decrement(yyval,null,this.currentScope.getScope());
																	toAdd.setIndex(this.terManager.addTercet(toAdd));
																}}
break;
case 65:
//#line 367 "MyGrammar.y"
{SymbolItem func = this.checkFunctionDeclaration((SymbolItem)val_peek(2));
									Tercet toAdd = new FunctionCall(func,null,null,this.currentScope.getScope());
									toAdd.setIndex(this.terManager.addTercet(toAdd));
									yyval = toAdd;}
break;
case 66:
//#line 381 "MyGrammar.y"
{yyval = val_peek(3); 
									SymbolItem func = this.checkFunctionDeclaration((SymbolItem)val_peek(3));
									SymbolItem var = this.checkVarDeclaration((SymbolItem)val_peek(1));
									/*buscamos el nombre que tiene el parametro en la declaracion de la funcion*/
									String param = this.symTable.getFunctionParam(this.currentScope.getScope());
									Tercet toAdd = new FunctionCall(func,var,param,this.currentScope.getScope());
									toAdd.setIndex(this.terManager.addTercet(toAdd));}
break;
case 67:
//#line 390 "MyGrammar.y"
{yyval = val_peek(5); 
									SymbolItem func = this.checkFunctionDeclaration((SymbolItem)val_peek(5));
									SymbolItem param = this.checkFunctionDeclaration((SymbolItem)val_peek(3));
									Tercet toAdd = new FunctionCall(func,param,param.getScopedName(),this.currentScope.getScope());
									toAdd.setIndex(this.terManager.addTercet(toAdd));}
break;
case 68:
//#line 397 "MyGrammar.y"
{this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																				this.currentLine = ((SymbolItem)val_peek(1)).getToken().getLine();}
break;
case 69:
//#line 400 "MyGrammar.y"
{this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																				this.currentLine = ((SymbolItem)val_peek(2)).getToken().getLine();}
break;
case 70:
//#line 403 "MyGrammar.y"
{yyerror("Expected endif;");}
break;
case 71:
//#line 405 "MyGrammar.y"
{yyerror("Expected endif;");}
break;
case 73:
//#line 411 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(2)).getToken().getLine() +2;
									yyerror("Wrong selection statement, more than one sentence must be between braces",this.currentLine);}
break;
case 74:
//#line 414 "MyGrammar.y"
{yyerror("Wrong selection statement, if section error");}
break;
case 75:
//#line 418 "MyGrammar.y"
{Tercet newBU = new BranchUnconditional(null,null,this.currentScope.getScope());
																	newBU.setIndex(this.terManager.addTercet(newBU));
																	this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																	this.conditionSTK.push((JumpTercet)newBU);}
break;
case 77:
//#line 424 "MyGrammar.y"
{yyerror("Wrong selection statement, else section error");}
break;
case 78:
//#line 427 "MyGrammar.y"
{Tercet newComp = new Comparator(((SymbolItem)val_peek(1)).getLex(),val_peek(2),val_peek(0),this.currentScope.getScope());
																					newComp.setIndex(this.terManager.addTercet(newComp));
																					yyval = newComp;
																					Tercet newBF = new BranchFalse(newComp,null,this.currentScope.getScope());
																					newBF.setIndex(this.terManager.addTercet(newBF));
																					this.conditionSTK.push((JumpTercet)newBF);}
break;
case 79:
//#line 435 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 80:
//#line 437 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 81:
//#line 439 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 82:
//#line 441 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 83:
//#line 443 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 84:
//#line 445 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 85:
//#line 447 "MyGrammar.y"
{yyerror("Invalid operator, comparator expected");}
break;
case 88:
//#line 454 "MyGrammar.y"
{yyerror("Expected '}'");}
break;
case 89:
//#line 457 "MyGrammar.y"
{/*salvamos la direccion de salto antes de evaluar la condicion*/
																														this.iterationJumpDir = this.terManager.getNextIndex();}
break;
case 90:
//#line 460 "MyGrammar.y"
{checkControlVars((SymbolItem)val_peek(7),(SymbolItem)val_peek(2));
									/*ponemos los tercetos del incremento al final de las sentencias del for*/
									Tercet incTer = this.incrementTercets.pollFirst();
									incTer.setIndex(this.terManager.addTercet(incTer));
										while(!this.incrementTercets.isEmpty()){
											incTer = this.incrementTercets.pollFirst();
											incTer.setIndex(this.terManager.addTercet(incTer));
										}
									/*salto incondicional al final del for hacia la condicion */
									Tercet newBU = new BranchUnconditional(null,null,this.currentScope.getScope());
									newBU.setIndex(this.terManager.addTercet(newBU));
									((JumpTercet)newBU).setJumpDir(this.iterationJumpDir);
									/*completamos con la direccion del salto hacia lo siguiente del for para continuar la ejecucion*/
									this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
									this.currentLine = ((SymbolItem)val_peek(9)).getToken().getLine();}
break;
case 91:
//#line 476 "MyGrammar.y"
{yyerror("Missing condition.");}
break;
case 92:
//#line 478 "MyGrammar.y"
{yyerror("Missing assignment. What were you planning on iterating on?");}
break;
case 93:
//#line 481 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(2));
																		this.incrementTercets.addLast(new Assignment(val_peek(2),val_peek(0),""));}
break;
case 94:
//#line 484 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(1));
																		this.incrementTercets.addLast(new Decrement(val_peek(1),null,this.currentScope.getScope()));}
break;
case 95:
//#line 488 "MyGrammar.y"
{this.incrementTercets.addLast(new Addition(val_peek(2),val_peek(0),""));}
break;
case 96:
//#line 490 "MyGrammar.y"
{this.incrementTercets.addLast(new Subtraction(val_peek(2),val_peek(0),""));}
break;
case 97:
//#line 492 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(0));}
break;
case 98:
//#line 494 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.CONST); 
																					((SymbolItem)yyval).setArithmeticType(this.currentType);
																					this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 99:
//#line 500 "MyGrammar.y"
{yyval = val_peek(1);
									((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.STR); 
									this.symTable.addSymbol((SymbolItem)yyval);
									this.currentLine = ((SymbolItem)val_peek(3)).getToken().getLine();
									yyval = new Print(val_peek(1),null,this.currentScope.getScope());
									((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));
									yyval = val_peek(1);}
break;
//#line 1232 "Parser.java"
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
