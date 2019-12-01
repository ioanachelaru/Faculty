import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Analizator {

    /** Tabela de simboluri reprezentata sub forma unui hashmap **/
    private HashMap<String, Integer> tabelaSimboluri;

    /** Tabela de variabile reprezentata sub forma unui hashmap **/
    public HashMap<String, Integer> tabelaVariabile;

    /** Tabela de numere reale reprezentata sub forma unui hashmap **/
    public HashMap<String, Integer> tabelaNumere;

    /** Variabila responsabila de atribuirea unui cod fiecarui element rezervat al limbajului **/
    private Integer nrVariabile;

    /** Variabila responsabila de atribuirea unui cod fiecarei constante **/
    private Integer nrConstante;

    /** Numele fisierului in care se afla codul ce va fi analizat **/
    private String file_name;

    /** Automat finit folosit pentru validarea constantelor*/
    private AutomatFinit  automatFinitConstante;

    /** Automat finit folosit pentru validarea variabilelor*/
    private AutomatFinit  automatFinitVariabile;

    /** Automat finit folosit pentru validarea variabilelor numerice din bazele 2, 8, 16 */
    private AutomatFinit automatFinitConstanteBaze2816;

    public Analizator(String file_name) {
        this.tabelaSimboluri = new HashMap<>();
        this.tabelaVariabile = new HashMap<>();
        this.tabelaNumere = new HashMap<>();
        this.file_name = file_name;
        this.nrVariabile = 0;
        this.nrConstante = 0;
        this.addSimboluri();
        this.automatFinitConstante = new AutomatFinit("./src/main/resources/constante.txt");
        this.automatFinitVariabile = new AutomatFinit("./src/main/resources/variabile.txt");
        this.automatFinitConstanteBaze2816 = new AutomatFinit("./src/main/resources/constante_baze2816.txt");
    }

    /** Functia populeaza tabela de simboluri cu toate cuvintele
     * rezevate limbajului si codul aferent fiecaruia**/

    private void addSimboluri() {
        this.tabelaSimboluri.put("CONSTANTA",0);
        this.tabelaSimboluri.put("VAR",1);
        this.tabelaSimboluri.put("BEGIN",2);
        this.tabelaSimboluri.put("END",3);
        this.tabelaSimboluri.put("START",4);
        this.tabelaSimboluri.put("STOP",5);
        this.tabelaSimboluri.put("IF",6);
        this.tabelaSimboluri.put("WHILE",7);
        this.tabelaSimboluri.put("FOR",8);
        this.tabelaSimboluri.put("WRITE",9);
        this.tabelaSimboluri.put("READ",10);
        this.tabelaSimboluri.put("ELSE",11);
        this.tabelaSimboluri.put("INT",12);
        this.tabelaSimboluri.put("REAL",13);
        this.tabelaSimboluri.put("CHAR",14);
        this.tabelaSimboluri.put(";",15);
        this.tabelaSimboluri.put("(",16);
        this.tabelaSimboluri.put(")",17);
        this.tabelaSimboluri.put(",",18);
        this.tabelaSimboluri.put("+",19);
        this.tabelaSimboluri.put("-",20);
        this.tabelaSimboluri.put("*",21);
        this.tabelaSimboluri.put("/",22);
        this.tabelaSimboluri.put("%",23);
        this.tabelaSimboluri.put(">",24);
        this.tabelaSimboluri.put("<",25);
        this.tabelaSimboluri.put(">=",26);
        this.tabelaSimboluri.put("<=",27);
        this.tabelaSimboluri.put("=",28);
        this.tabelaSimboluri.put("==",29);
        this.tabelaSimboluri.put("AND",30);
        this.tabelaSimboluri.put("OR",31);
        this.tabelaSimboluri.put(".",32);
        this.tabelaSimboluri.put("!=",33);
    }

    /** Functia valideaza daca un string dat este o
     *  variabila valida in limbajul definit **/

    private boolean valideazaVariabila(String var){
        return var.matches("[a-zA-Z$][a-zA-Z0-9$]*") && var.length() <= 8;
    }

    /** Functia verifica daca un string dat este
     * un numar real valid **/

    private boolean isNumeric(String strNum) {
        try {
            Double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Adauga , dupa fiecare caracter din @string
     * @param string
     * @return String
     */

    private String buildString(String string){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            stringBuilder.append(string.charAt(i));
            if(i < string.length() - 1)
                stringBuilder.append(",");
        }
        return String.valueOf(stringBuilder);
    }

    /** Functia citeste linie cu linie codul programului
     * din fisierul sursa si codifica programul conform tabelei
     * de simboluri, realizand validarile aferente si detectand erorile de sintaxa**/

    public void createFIP(String file) throws Exception {
        try(BufferedReader bf = new BufferedReader(new FileReader(this.file_name))){
            String line;
            PrintWriter p = new PrintWriter(file);
            while ((line = bf.readLine()) != null){
                String[] linie = line.split(" ");

                for (String atom: linie) {

                    // nu conteaza daca sunt majuscule sau litere mici
                    Integer cod = this.tabelaSimboluri.get(atom.toUpperCase());

                    // am gasit codul aferent
                    if(cod != null)
                        p.println(cod+" -");
                    else

                    // daca este variabila adaugam codul ei din tabela de simboluri,
                    // plus un cod unic aferent acestei variabile
                    if(this.automatFinitVariabile.checkSequence(this.buildString(atom))){
                        Integer var = this.tabelaVariabile.get(atom.toUpperCase());

                        // daca exista in tabela, il afisam codul aferent
                        if(var != null)
                            p.println("1 " + var);

                        // altfel adaugam variabila in tabela si ii generam un cod
                        else{
                            p.println("1 "+this.nrVariabile);
                            this.tabelaVariabile.put(atom.toUpperCase(), this.nrVariabile);
                            this.nrVariabile++;
                        }
                    }
                    else

                    // daca este numar in baza 10 adaugam codul lui din tabela de simboluri,
                    // plus un cod unic aferent acestui numar
                    if(this.automatFinitConstante.checkSequence(this.buildString(atom))){

                        Integer nr = this.tabelaNumere.get(atom);

                        // daca exista, ii afisam codul aferent
                        if(nr != null)
                            p.println("0 " + nr);

                        // altfel adaugam numarul in tabela alaturi de codul sau
                        else{
                            p.println("0 "+this.nrConstante);
                            this.tabelaNumere.put(atom, this.nrConstante);
                            this.nrConstante++;
                        }
                    }

                    else
                        // daca este numar in baza 2 | 8 | 16 adaugam codul lui din tabela de simboluri,
                        // plus un cod unic aferent acestui numar
                        if(this.automatFinitConstanteBaze2816.checkSequence(this.buildString(atom))){

                            Integer nr = this.tabelaNumere.get(atom);

                            // daca exista, ii afisam codul aferent
                            if(nr != null)
                                p.println("0 " + nr);

                                // altfel adaugam numarul in tabela alaturi de codul sau
                            else{
                                p.println("0 "+this.nrConstante);
                                this.tabelaNumere.put(atom, this.nrConstante);
                                this.nrConstante++;
                            }
                        }

                    // daca nu s-a gasit tipul string-ului in tabela de simboluri,
                    // aceasta este o eroare de sintaxa
                    else {
                            System.out.println(atom);
                            throw new Exception("Eroare de sintaxa!\n");
                        }

                }
            }
            p.close();
        }
        catch (IOException ex) {
            System.out.println("Eroare de fisier\n");
        }
    }

    public void afisareHashMap(HashMap<String, Integer> map){
        for (String key: map.keySet()) {
            System.out.println(key+ " " +map.get(key));
        }
    }
}