%{
	#include <string.h>
	#include <stdio.h>
	#include <stdlib.h>

	extern int yylex();
	extern int yyparse();
	extern FILE* yyin;
    extern char* yytext;
	extern int errorLine;
	void yyerror(const char* s);
    
    //the variable containing the DataSegment for the assembly program
    char DS[1000];
    
    //the variable containing the CodeSegment for the assembly program
    char CS[1000];
    
    //add variables to DataSegment
    void addTempToDS(char *s);
    
    //add assembly code to CodeSegment
    void addTempToCS(char *s);
    
    char* moveVarToPrintBuffer(char *s);
    
    //write the assembly code to file
    void writeAssemblyToFile();
    
    //counter for the temp variables
    int tempnr = 1;
    //create a new temp variable and add it to DS
    void newTempName(char *s);
%}

%token IDENTIFICATOR
%token CONSTANTA
%token BEGIN_PROG
%token END_PROG
%token START
%token STOP
%token IF
%token ELSE
%token FOR
%token WHILE
%token WRITE
%token READ
%token INT
%token REAL
%token CHAR
%token GT
%token LT
%token GE
%token LE
%token ASSIGN
%token NEQ
%token EQ
%token OR
%token AND

%%
program: intrare_program
          lista_decl
          intrare_bloc
          lista_instr
          iesire_bloc
         iesire_program

intrare_program: BEGIN_PROG
iesire_program: END_PROG

intrare_bloc: START
iesire_bloc: STOP

lista_decl: decl ';' lista_decl | decl ';'
decl: tip lista_var | tip lista_attr
tip: INT | REAL | CHAR
lista_var: IDENTIFICATOR ',' lista_var | IDENTIFICATOR
lista_attr: instr_attr | instr_attr ',' lista_attr

lista_instr: instr  lista_instr | instr
instr: instr_attr';' | instr_io ';'| instr_cicl | instr_sel

instr_attr: IDENTIFICATOR ASSIGN expr
expr: termen op expr | termen
termen: CONSTANTA | IDENTIFICATOR
op: '+' | '-' | '*' | '/' | '%'

instr_cicl: while | for
while: WHILE '(' lista_cond ')'
        intrare_bloc
            lista_instr
        iesire_bloc

lista_cond: cond op_logic lista_cond | cond
cond: expr op_rel expr
op_logic: AND | OR
op_rel: LE | LT | GE | GT | EQ | NEQ

for: FOR '(' decl ';' cond ';' instr_attr ')'
        intrare_bloc
            lista_instr
        iesire_bloc

instr_sel: IF '(' cond ')'
            intrare_bloc
                lista_instr
            iesire_bloc
           ELSE
            intrare_bloc
                lista_instr
            iesire_bloc
            | IF '(' cond ')'
                intrare_bloc
                    lista_instr
                iesire_bloc

instr_io: READ '(' IDENTIFICATOR ')' |
          WRITE '(' IDENTIFICATOR ')' |
            WRITE'(' CONSTANTA ')'{
                char *tmp = (char *)malloc(sizeof(char)*100);
                sprintf(tmp, "push dword %s\ncall [printf] \nadd esp, 4 \n", $0);
                addTempToCS(tmp);
            }
;
%%

int main(int argc, char* argv[]){
	++argv, --argc; /* skip over program name */ 
	// sets the input for flex file
    	if (argc > 0) 
        	yyin = fopen(argv[0], "r"); 
    	else 
        	yyin = stdin;
	//read each line from the input file and process it
   	while (!feof(yyin)) {
        	yyparse();
    	}

	printf("The file is syntactically correct!\n");

    memset(DS, 0, 1000);
    memset(CS, 0, 1000);
    writeAssemblyToFile();
    	return 0;
}

void yyerror(const char *s){
	printf("Error: Symbol %s at line -> %d ! \n", yytext, errorLine);
    	exit(1);
}

void addTempToDS(char *s) {
    strcat(DS, s);
}


void addTempToCS(char *s) {
    strcat(CS, s);
    printf("%s", CS);
}

void newTempName(char *s) {
    sprintf(s, "temp%d dw 1\n", tempnr);
    addTempToDS(s);
    sprintf(s, "temp%d", tempnr);
    tempnr++;
}

void writeAssemblyToFile() {
    char *beginProgram = (char *) malloc(sizeof(char)*300);
    char *beginDS = (char *) malloc(sizeof(char)*100);
    char *beginCS = (char *) malloc(sizeof(char)*100);
    char *endProgram = (char *) malloc(sizeof(char)*100);
    
    
    sprintf(beginProgram, "bits32\nglobal start\nextern exit, printf, scanf\nimport printf, scanf msvcrt.dll\n");
    sprintf(beginDS, "segment data use32 class=data\n");
    sprintf(beginCS, "segment code use32 class=code\nstart:\n");
    sprintf(endProgram,"\npush dword 0\ncall [exit]");
    
    FILE *f = fopen("out.out", "w");
    if(f == NULL) {
        perror("The file out.out has failed.");
        exit(1);
    }
    
    fwrite(beginProgram, strlen(beginProgram), 1, f);
    fwrite(beginDS, strlen(beginDS), 1, f);
    fwrite(DS, strlen(DS), 1, f);
    fwrite(beginCS, strlen(beginCS), 1, f);
    fwrite(CS, strlen(CS), 1, f);
    fwrite(endProgram, strlen(endProgram), 1, f);
    fclose(f);
}