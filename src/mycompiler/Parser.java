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
   26,   27,   27,   24,   23,   23,   19,   19,   19,   19,
   28,   28,   28,   32,   29,   29,   30,   33,   33,   33,
   33,   33,   33,   33,   31,   31,   31,   34,   20,   20,
   20,   35,   35,   36,   36,   36,   36,   21,
};
final static short yylen[] = {                            2,
    0,    4,    0,    3,    3,    2,    3,    2,    2,    1,
    2,    1,    1,    5,    2,    2,    1,    1,    3,    1,
    0,    8,    0,    7,    7,    6,    6,    5,    5,    2,
    2,    1,    4,    3,    2,    3,    4,    2,    3,    3,
    2,    1,    2,    1,    2,    2,    3,    2,    3,    1,
    3,    3,    1,    4,    3,    3,    1,    1,    1,    1,
    1,    1,    1,    2,    3,    4,    2,    3,    3,    2,
    5,    6,    5,    0,    3,    2,    3,    1,    1,    1,
    1,    1,    1,    1,    3,    1,    3,    0,   10,    5,
    3,    3,    2,    3,    3,    1,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,   17,   18,    0,    0,    0,
    0,    0,    0,    0,   10,   12,   13,    0,    0,   44,
    0,    0,    0,   50,    0,    0,    4,    0,    0,    0,
    0,    0,    0,   64,    0,    2,    0,    8,   11,    9,
   16,    0,   20,    0,   43,   45,    0,   48,   70,    0,
   67,    0,    6,    0,   42,    0,    0,   62,   63,    0,
   61,   60,    0,   57,   59,    0,    0,    0,   91,    0,
    0,    0,    0,   65,    0,    7,    0,    0,   47,   76,
    0,   69,   68,    5,   41,    0,   84,    0,    0,   82,
   78,   80,   79,   81,   83,    0,    0,    0,    0,   98,
    0,    0,    0,    0,    0,   66,    0,   19,    0,   86,
   75,    0,    0,    0,    0,   55,   56,   73,    0,    0,
   71,    0,    0,   28,   32,    0,   30,    0,   90,    0,
   14,    0,    0,    0,   54,    0,   35,    0,    0,    0,
   31,    0,   27,    0,   26,    0,    0,   87,   85,    0,
   36,    0,   34,    0,    0,   24,   25,    0,   40,   39,
   37,   33,   29,    0,    0,   22,    0,   93,    0,   96,
   97,    0,   89,    0,    0,    0,    0,
};
final static short yydgoto[] = {                          2,
  123,    3,   27,    4,   54,   14,   15,   16,   17,   18,
   44,  103,  124,  158,  146,  125,  140,   60,   19,   20,
   21,   22,   23,   24,   63,   64,   65,   25,   52,   66,
  111,   81,   96,  130,  165,  172,
};
final static short yysindex[] = {                      -217,
    0,    0,   88, -229, -266,    0,    0, -215, -189, -202,
 -173, -182,    1, -186,    0,    0,    0,  -59, -170,    0,
 -168, -161, -147,    0,   15,  -83,    0,   41, -163, -119,
 -246,  -91,   41,    0, -136,    0, -108,    0,    0,    0,
    0,  -68,    0,  -75,    0,    0,  -69,    0,    0,  -45,
    0, -224,    0,  -82,    0,  -58, -145,    0,    0,    4,
    0,    0,  -88,    0,    0,  -70,  -57, -230,    0, -110,
  -43,   -7,  -39,    0, -248,    0,  -40,  -13,    0,    0,
  -14,    0,    0,    0,    0,   41,    0, -115, -115,    0,
    0,    0,    0,    0,    0,   41, -115, -115, -240,    0,
  -67,  -64,  -25,   28,   -1,    0, -204,    0,   97,    0,
    0, -137,  -88,  -88,  -39,    0,    0,    0,   97,    0,
    0,   68,    9,    0,    0,   -4,    0,  -67,    0,   41,
    0,   38,   20,  -84,    0,    0,    0,   41,   78,   36,
    0,  -20,    0,   30,    0,  -67,   72,    0,    0, -241,
    0, -237,    0,   43,   60,    0,    0,  -67,    0,    0,
    0,    0,    0,  -53,   48,    0,   11,    0,  -14,    0,
    0,  -16,    0,   11,   11,  -16,  -16,
};
final static short yyrindex[] = {                         0,
  -48,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   46,    0,    0,  -98,    0,    0,  -12,
    0,    0,    0,    0,    0,    0, -238,    0,    0,    0,
    0,    0, -193,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   47,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   94,    0,    0,    0,    0,    0,    0,
    0,    0, -156, -135, -268,    0,    0,    0,    0,   44,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -37,    0,    0,    0,   58,    0,    0,    0,   32,
    0,    0,    0,    0,    0,    0,  -23,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   53,    0,    0,    0,   56,   64,
};
final static short yygindex[] = {                         0,
  343,    0,  345,    0,   -2,  -11,   -9,   -8,  -26,  -65,
    0,  249,  -80,    0,    0,  238,  228,  -22,    0,    0,
    0,  337,  -27,  -21,  208,  207,  -73,    0,    0,  239,
  -90,    0,    0,    0,    0,  144,
};
final static int YYTABLESIZE=369;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         55,
   61,   37,  102,   38,   39,   61,   62,   61,  121,   69,
   73,   62,   75,   62,  159,  118,    5,   58,  161,   77,
   28,    8,   70,   77,   10,   88,   89,   85,   12,    6,
    7,   82,   88,   89,   83,   58,   58,   58,   58,  106,
   58,  102,   58,   58,   58,   58,   58,  143,  109,   58,
  160,    1,  162,   58,  110,    6,    7,  101,   61,   26,
   61,   61,   53,  112,   62,  156,   62,   62,   61,   61,
   61,   29,  120,  115,   62,   62,   62,  166,  173,   30,
   53,   53,   55,  132,   31,   53,   32,   53,   53,   53,
   53,   53,  136,  171,   53,   55,  119,   33,   53,   51,
  171,  171,   61,   34,   35,   40,  134,   85,   62,   67,
   61,   37,   85,   38,   39,  150,   62,   51,   51,  139,
   52,   45,   51,   46,   51,   51,   51,   51,   51,   56,
   47,   51,   57,   58,   59,   51,   88,   89,   52,   52,
   34,   35,  110,   52,   48,   52,   52,   52,   52,   52,
  135,   74,   52,   57,   58,   59,   52,   46,   46,   46,
   46,   46,   46,   46,   46,   46,   46,   68,   46,   33,
   46,  148,    5,    5,    5,   34,   72,    8,    8,    8,
   10,   10,   10,   76,   12,   12,   12,   97,   98,    5,
   46,   46,    6,    7,    8,    9,   41,   10,  126,   11,
   77,   12,   79,   42,  127,  149,   53,   84,    1,   43,
   80,    1,    1,    1,    1,   78,    1,   99,    1,   23,
    1,  122,   23,   23,   23,   23,  167,   23,   86,   23,
  100,   23,  168,   21,   88,   89,   21,   21,   21,   21,
    3,   21,    5,   21,   74,   21,  107,    8,  104,   74,
   10,   23,   74,  105,   12,  108,   74,  174,  175,   87,
    6,    7,  128,    9,  142,   21,  154,   11,    6,    7,
   49,    9,   50,   51,  109,   11,   74,   88,   89,  170,
   58,   59,   90,  129,   91,   92,   93,   94,   95,   26,
  131,   38,   38,  145,   38,  113,  114,  122,   38,   86,
   42,   86,   86,  116,  117,   42,   56,  147,   42,   57,
   58,   59,   42,   72,   41,   72,   72,  176,  177,   41,
   38,  155,   41,  137,    5,  153,   41,  157,  164,    8,
  163,  138,   10,  151,    5,  169,   12,   15,   49,    8,
   92,  138,   10,   94,    5,   13,   12,    6,    7,    8,
    9,   95,   10,    5,   11,  133,   12,   36,    8,   88,
  141,   10,   88,   88,   88,   12,  152,   71,  144,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         26,
   28,   13,   68,   13,   13,   33,   28,   35,   99,  256,
   33,   33,   35,   35,  256,  256,  257,  256,  256,  288,
  287,  262,  269,  292,  265,  274,  275,   54,  269,  260,
  261,  256,  274,  275,  259,  274,  275,  276,  277,  288,
  279,  107,  281,  282,  283,  284,  285,  128,  289,  288,
  292,  269,  290,  292,   81,  260,  261,  288,   86,  289,
   88,   89,  256,   86,   86,  146,   88,   89,   96,   97,
   98,  287,   99,   96,   96,   97,   98,  158,  169,  269,
  274,  275,  109,  288,  287,  279,  260,  281,  282,  283,
  284,  285,  119,  167,  288,  122,   99,  280,  292,  256,
  174,  175,  130,  286,  287,  292,  109,  134,  130,  273,
  138,  123,  139,  123,  123,  138,  138,  274,  275,  122,
  256,  292,  279,  292,  281,  282,  283,  284,  285,  266,
  292,  288,  269,  270,  271,  292,  274,  275,  274,  275,
  286,  287,  169,  279,  292,  281,  282,  283,  284,  285,
  288,  288,  288,  269,  270,  271,  292,  256,  257,  258,
  259,  260,  261,  262,  263,  264,  265,  287,  267,  280,
  269,  256,  257,  257,  257,  286,  268,  262,  262,  262,
  265,  265,  265,  292,  269,  269,  269,  276,  277,  257,
  289,  290,  260,  261,  262,  263,  256,  265,  263,  267,
  269,  269,  272,  263,  269,  290,  290,  290,  257,  269,
  256,  260,  261,  262,  263,  291,  265,  288,  267,  257,
  269,  289,  260,  261,  262,  263,  280,  265,  287,  267,
  288,  269,  286,  257,  274,  275,  260,  261,  262,  263,
  289,  265,  257,  267,  257,  269,  287,  262,  292,  262,
  265,  289,  265,  261,  269,  269,  269,  274,  275,  256,
  260,  261,  288,  263,  269,  289,  287,  267,  260,  261,
  256,  263,  258,  259,  289,  267,  289,  274,  275,  269,
  270,  271,  279,  256,  281,  282,  283,  284,  285,  289,
  292,  260,  261,  256,  263,   88,   89,  289,  267,  256,
  257,  258,  259,   97,   98,  262,  266,  288,  265,  269,
  270,  271,  269,  256,  257,  258,  259,  174,  175,  262,
  289,  292,  265,  256,  257,  290,  269,  256,  269,  262,
  288,  264,  265,  256,  257,  288,  269,  292,  292,  262,
  288,  264,  265,  288,  257,    3,  269,  260,  261,  262,
  263,  288,  265,  257,  267,  107,  269,   13,  262,  266,
  123,  265,  269,  270,  271,  269,  139,   31,  130,
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
"functionCall : _ID _LPAREN expression _RPAREN",
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

//#line 463 "MyGrammar.y"

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
//#line 533 "Parser.java"
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
{this.currentScope.pushScope(((SymbolItem)val_peek(3)).getLex());
																													this.terManager.inFunction();
																													((SymbolItem)val_peek(1)).setScope(this.currentScope.getScope());
																													Tercet function = new Function(val_peek(3),val_peek(1),this.currentScope.getScope());
																													function.setIndex(this.terManager.addTercet(function));}
break;
case 22:
//#line 132 "MyGrammar.y"
{yyval = val_peek(5);
																													Tercet ret = new Return(val_peek(5),val_peek(0),this.currentScope.getScope());
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
																														this.currentLine = ((SymbolItem)val_peek(6)).getToken().getLine();
																														yyerror("Function name already used!", this.currentLine);
																													}
																													notify("Found function declaration: " + yyval.toString(), currentLine);}
break;
case 23:
//#line 149 "MyGrammar.y"
{this.currentScope.pushScope(((SymbolItem)val_peek(2)).getLex());
																													this.terManager.inFunction();
																													Tercet function = new Function(val_peek(2),null,this.currentScope.getScope());
																													function.setIndex(this.terManager.addTercet(function));}
break;
case 24:
//#line 153 "MyGrammar.y"
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
//#line 170 "MyGrammar.y"
{yyerror("Missing function body.");}
break;
case 26:
//#line 172 "MyGrammar.y"
{yyerror("Missing function body.");}
break;
case 27:
//#line 174 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(5)).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
break;
case 28:
//#line 177 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(4)).getToken().getLine();
																													yyerror("Missing function type", this.currentLine);}
break;
case 29:
//#line 181 "MyGrammar.y"
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
case 30:
//#line 195 "MyGrammar.y"
{yyval = val_peek(0);
																													((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.PARAM); 
																													((SymbolItem)yyval).setArithmeticType(this.currentType);
																													((SymbolItem)yyval).setScope(this.currentScope.getScope());
																													this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 31:
//#line 202 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 32:
//#line 204 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 33:
//#line 207 "MyGrammar.y"
{yyval = val_peek(1);}
break;
case 34:
//#line 209 "MyGrammar.y"
{yyval = val_peek(1);}
break;
case 35:
//#line 211 "MyGrammar.y"
{yyerror("Expected sentence or return sentence.");}
break;
case 36:
//#line 213 "MyGrammar.y"
{yyerror("Expected return sentence.");}
break;
case 37:
//#line 215 "MyGrammar.y"
{yyerror("Expected '}'.");}
break;
case 38:
//#line 217 "MyGrammar.y"
{yyerror("Expected '}'.");}
break;
case 39:
//#line 220 "MyGrammar.y"
{yyval = val_peek(1);}
break;
case 40:
//#line 222 "MyGrammar.y"
{yyerror("Expected ';' after return statement");}
break;
case 43:
//#line 230 "MyGrammar.y"
{notify("Found Selection sentence ", this.currentLine);}
break;
case 44:
//#line 232 "MyGrammar.y"
{notify("Found Iteration sentence ", this.currentLine);}
break;
case 45:
//#line 234 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Print sentence: " + yyval.toString(), this.currentLine);}
break;
case 46:
//#line 236 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Assignment of: " + yyval.toString(), this.currentLine);}
break;
case 47:
//#line 238 "MyGrammar.y"
{yyval = val_peek(2); notify("Found Assignment with annotation of: " + yyval.toString(), this.currentLine);}
break;
case 48:
//#line 240 "MyGrammar.y"
{yyval = val_peek(1); notify("Found Function Invocation");}
break;
case 49:
//#line 243 "MyGrammar.y"
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
//#line 260 "MyGrammar.y"
{yyval = new Addition(val_peek(2),val_peek(0),"");
																	((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 52:
//#line 263 "MyGrammar.y"
{yyval = new Subtraction(val_peek(2),val_peek(0),"");
																	((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 53:
//#line 266 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 54:
//#line 270 "MyGrammar.y"
{yyval = val_peek(1); 
									if(terManager.conversionAllowed()){yyval.setArithmeticType(SymbolItem.ArithmeticType.LONG);}
									else {yyerror("Conversion not allowed", ((SymbolItem)val_peek(3)).getToken().getLine());}}
break;
case 55:
//#line 275 "MyGrammar.y"
{yyval = new Multiplication(val_peek(2),val_peek(0),"");
																((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 56:
//#line 278 "MyGrammar.y"
{yyval = new Division(val_peek(2),val_peek(0),"");
																((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));}
break;
case 57:
//#line 281 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 58:
//#line 284 "MyGrammar.y"
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
case 59:
//#line 295 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.CONST); 
																((SymbolItem)yyval).setArithmeticType(this.currentType);
																this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 60:
//#line 299 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 61:
//#line 301 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 62:
//#line 304 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.INT;}
break;
case 63:
//#line 306 "MyGrammar.y"
{this.currentType = SymbolItem.ArithmeticType.LONG;}
break;
case 64:
//#line 309 "MyGrammar.y"
{yyval = val_peek(1);
																((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.VAR);
																((SymbolItem)yyval).setScope(this.currentScope.getScope());
																SymbolItem variable = this.symTable.getSymbol((SymbolItem)yyval);
																if(variable == null){ 
																	this.currentLine = ((SymbolItem)val_peek(1)).getToken().getLine();
																	yyerror("Variable not declared!" , this.currentLine);
																}
																else {
																	yyval = variable;
																	Tercet toAdd = new Decrement(yyval,null,this.currentScope.getScope());
																	toAdd.setIndex(this.terManager.addTercet(toAdd));
																}}
break;
case 65:
//#line 325 "MyGrammar.y"
{SymbolItem func = this.checkFunctionDeclaration((SymbolItem)val_peek(2));
									Tercet toAdd = new FunctionCall(func,null,this.currentScope.getScope());
									toAdd.setIndex(this.terManager.addTercet(toAdd));
									yyval = toAdd;}
break;
case 66:
//#line 331 "MyGrammar.y"
{SymbolItem func = this.checkFunctionDeclaration((SymbolItem)val_peek(3));
									Tercet toAdd = new FunctionCall(func,val_peek(1),this.currentScope.getScope());
									this.terManager.addTercet(toAdd);
									yyval = toAdd;}
break;
case 67:
//#line 353 "MyGrammar.y"
{this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																				this.currentLine = ((SymbolItem)val_peek(1)).getToken().getLine();}
break;
case 68:
//#line 356 "MyGrammar.y"
{this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																				this.currentLine = ((SymbolItem)val_peek(2)).getToken().getLine();}
break;
case 69:
//#line 359 "MyGrammar.y"
{yyerror("Expected endif;");}
break;
case 70:
//#line 361 "MyGrammar.y"
{yyerror("Expected endif;");}
break;
case 72:
//#line 367 "MyGrammar.y"
{this.currentLine = ((SymbolItem)val_peek(2)).getToken().getLine() +2;
									yyerror("Wrong selection statement, more than one sentence must be between braces",this.currentLine);}
break;
case 73:
//#line 370 "MyGrammar.y"
{yyerror("Wrong selection statement, if section error");}
break;
case 74:
//#line 374 "MyGrammar.y"
{Tercet newBU = new BranchUnconditional(null,null,this.currentScope.getScope());
																	newBU.setIndex(this.terManager.addTercet(newBU));
																	this.conditionSTK.pop().setJumpDir(this.terManager.getNextIndex());
																	this.conditionSTK.push((JumpTercet)newBU);}
break;
case 76:
//#line 380 "MyGrammar.y"
{yyerror("Wrong selection statement, else section error");}
break;
case 77:
//#line 383 "MyGrammar.y"
{Tercet newComp = new Comparator(((SymbolItem)val_peek(1)).getLex(),val_peek(2),val_peek(0),this.currentScope.getScope());
																					newComp.setIndex(this.terManager.addTercet(newComp));
																					yyval = newComp;
																					Tercet newBF = new BranchFalse(newComp,null,this.currentScope.getScope());
																					newBF.setIndex(this.terManager.addTercet(newBF));
																					this.conditionSTK.push((JumpTercet)newBF);}
break;
case 78:
//#line 391 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 79:
//#line 393 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 80:
//#line 395 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 81:
//#line 397 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 82:
//#line 399 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 83:
//#line 401 "MyGrammar.y"
{yyval = val_peek(0);}
break;
case 84:
//#line 403 "MyGrammar.y"
{yyerror("Invalid operator, comparator expected");}
break;
case 87:
//#line 410 "MyGrammar.y"
{yyerror("Expected '}'");}
break;
case 88:
//#line 413 "MyGrammar.y"
{this.iterationJumpDir = this.terManager.getNextIndex();}
break;
case 89:
//#line 415 "MyGrammar.y"
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
case 90:
//#line 428 "MyGrammar.y"
{yyerror("Missing condition.");}
break;
case 91:
//#line 430 "MyGrammar.y"
{yyerror("Missing assignment. What were you planning on iterating on?");}
break;
case 92:
//#line 433 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(2));
																		this.incrementTercets.addLast(new Assignment(val_peek(2),val_peek(0),""));}
break;
case 93:
//#line 436 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(1));
																		this.incrementTercets.addLast(new Decrement(val_peek(1),null,this.currentScope.getScope()));}
break;
case 94:
//#line 440 "MyGrammar.y"
{this.incrementTercets.addLast(new Addition(val_peek(2),val_peek(0),""));}
break;
case 95:
//#line 442 "MyGrammar.y"
{this.incrementTercets.addLast(new Subtraction(val_peek(2),val_peek(0),""));}
break;
case 96:
//#line 444 "MyGrammar.y"
{yyval = this.checkVarDeclaration((SymbolItem)val_peek(0));}
break;
case 97:
//#line 446 "MyGrammar.y"
{yyval = val_peek(0); ((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.CONST); 
																					((SymbolItem)yyval).setArithmeticType(this.currentType);
																					this.symTable.addSymbol((SymbolItem)yyval);}
break;
case 98:
//#line 452 "MyGrammar.y"
{yyval = val_peek(1);
									((SymbolItem)yyval).setSymbolUse(SymbolItem.Use.STR); 
									this.symTable.addSymbol((SymbolItem)yyval);
									this.currentLine = ((SymbolItem)val_peek(3)).getToken().getLine();
									yyval = new Print(val_peek(1),null,this.currentScope.getScope());
									((Tercet)yyval).setIndex(this.terManager.addTercet((Tercet)yyval));
									yyval = val_peek(1);}
break;
//#line 1172 "Parser.java"
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
