%{
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "lab4.tab.h"

typedef struct {
    int id1;
    int id2;
}FIP;

typedef struct{
    char nume[10];
}TSiden;

typedef struct{
	int valoare;
    int index;
}Cint;

typedef struct{
	float valoare;
    int index;
}Creal;

typedef struct{
	char valoare[50];
    int index;
}Cstring;

int fipLength = 0;
int constLength = 0;
int constCode = 0;
int identifCode = 1;
int identifLength = 0;
FIP program[300];

int constRealLength = 0;
int constIntregiLength = 0;
int constStringLength = 0;
int errorLine = 1;

TSiden TSidentif[30];
Creal TSconstante[30];
Cint TSconstanteIntregi[30];
Cstring TSconstanteString[30];

void addFip(int id1, int id2){
	program[fipLength].id1 = id1;
	program[fipLength].id2 = id2;
	fipLength++;
}

void addRealConst(float atom){
	int gasit = 0;
	int i;
	for(i = 0; i < constRealLength; i++){
		if(TSconstante[i].valoare == atom){
		  gasit = 1;
		  addFip(constCode,TSconstante[i].index);
		}
	}
	if(gasit == 0){
	  TSconstante[constRealLength].valoare = atom;
	  TSconstante[constRealLength].index = constLength;
	  addFip(constCode,constLength);
	  constRealLength++;
	  constLength++;
	}
}

void addConstIntregi(int atom){
	int gasit = 0;
	int i;
	for(i = 0; i < constIntregiLength; i++){
		if(TSconstanteIntregi[i].valoare == atom){
		  gasit = 1;
		  addFip(constCode,TSconstanteIntregi[i].index);
		}
	}
	if(gasit == 0){
	  TSconstanteIntregi[constIntregiLength].valoare = atom;
	  TSconstanteIntregi[constIntregiLength].index = constLength;
	  addFip(constCode,constLength);
	  constIntregiLength++;
	  constLength++;
	}
}

void addConstString(char* atom){
	int gasit = 0;
	int i;
	for(i = 0; i < constStringLength; i++){
		
		if(strcmp(TSconstanteString[i].valoare, atom) == 0){
		  gasit = 1;
		  addFip(constCode,TSconstanteString[i].index);
		}
	}
	if(gasit == 0){
	  strcpy(TSconstanteString[constStringLength].valoare,atom);
	  TSconstanteString[constStringLength].index = constLength;
	  addFip(constCode,constLength);
	  constStringLength++;
	  constLength++;
	}
}

void addIdentif(char* atom){
	int gasit = 0;
	int i = 0;
	while(i < identifLength){
	  int rez = strcmp(TSidentif[i].nume,atom);
	  if(strcmp(TSidentif[i].nume, atom) == 0){
	   gasit = 1;
	   addFip(identifCode, i);
	  }
	  i++;
	}

	if(gasit == 0){
	 strcpy(TSidentif[identifLength].nume,atom);
         addFip(identifCode,identifLength);
         identifLength++;
	}
}

void showFIP(){
    printf("FIP\n");
    int t;
    for(t=0;t<fipLength;t++){
        if(program[t].id1 != identifCode && program[t].id1 != constCode){
          printf(" %d  - ",program[t].id1);printf("\n");
        }
        else{
          printf(" %d  %d ",program[t].id1,program[t].id2);printf("\n");
        }
    }
    printf("\n");
}

void showConst(){
    printf("Constante\n");
    int i;
    for(i = 0 ;i<constRealLength;i++){
        printf(" %f  %d", TSconstante[i].valoare , TSconstante[i].index);
        printf("\n");
    }

    for(i = 0 ;i<constIntregiLength;i++){
        printf(" %d  %d", TSconstanteIntregi[i].valoare , TSconstanteIntregi[i].index);
        printf("\n");
    }

    for(i = 0 ;i<constStringLength;i++){
        printf(" %s  %d", TSconstanteString[i].valoare , TSconstanteString[i].index);
        printf("\n");
    }
    printf("\n");
}

void showId(){
    printf("Identificatori\n");
    int i;
    for(i = 0 ;i<identifLength;i++){
        printf(" %s  %d", TSidentif[i].nume , i);
        printf("\n");
    }
    printf("\n");
}

void show(){
    showConst();
    showId();
    showFIP();
}

%}

%option noyywrap
DIGIT [0-9]
ID    [a-z][a-z0-9_]*
ST    \".*\"

%%
{DIGIT}+                                {addConstIntregi(atoi( yytext )); return CONSTANTA;}
{DIGIT}+"."{DIGIT}*                     {addRealConst(atof( yytext )); return CONSTANTA;}
"-"+{DIGIT}+"."{DIGIT}*                 {addRealConst(atof( yytext )); return CONSTANTA;}

begin                                   {addFip(2,0); return BEGIN_PROG;}
end					{addFip(3,0); return END_PROG;}
start					{addFip(4,0); return START;}
stop					{addFip(5,0); return STOP;}
if                                    	{addFip(6,0); return IF;}
while                                   {addFip(7,0); return WHILE;}
for                                    	{addFip(8,0); return FOR;}
write                                   {addFip(9,0); return WRITE;}
read                                    {addFip(10,0); return READ;}
else                                    {addFip(11,0); return ELSE;}
int                                    	{addFip(12,0); return INT;}
real                                    {addFip(13,0); return REAL;}
char                                    {addFip(14,0); return CHAR;}
\;                                    	{addFip(15,0); return ';';}
\(                                    	{addFip(16,0); return '(';}
\)                                    	{addFip(17,0); return ')';}
\,                                    	{addFip(18,0); return ',';}
\+                                    	{addFip(19,0); return '+';}
\-                                    	{addFip(20,0); return '-';}
\*                                    	{addFip(21,0); return '*';}
\/                                    	{addFip(22,0); return '/';}
\%                                    	{addFip(23,0); return '%';}
\>                                    	{addFip(24,0); return GT;}
\<                                    	{addFip(25,0); return LT;}
\>=                                    	{addFip(26,0); return GE;}
\<=                                    	{addFip(27,0); return LE;}
\=                                    	{addFip(28,0); return ASSIGN;}
\==                                    	{addFip(29,0); return EQ;}
\and                                    {addFip(30,0); return AND;}
\or                                    	{addFip(31,0); return OR;}
\.                                    	{addFip(32,0);return '.';}
\!=                                    	{addFip(33,0); return NEQ;}
{ID}					{addIdentif(yytext); return IDENTIFICATOR;}
{ST}					{addConstString(yytext); return CONSTANTA;}
[\n]					++errorLine;
[ \t\n]+             ;/* eat up whitespace */
. {
    printf("illegal token %s si %d !",yytext,yytext[0]);
}
%%