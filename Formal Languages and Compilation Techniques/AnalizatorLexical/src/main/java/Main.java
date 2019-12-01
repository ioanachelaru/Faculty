public class Main {
    public static void main(String[] args) {
        Analizator analizator = new Analizator("./src/main/resources/aria.txt");

        try{
        analizator.createFIP("./src/main/resources/rez.txt");
        analizator.afisareHashMap(analizator.tabelaVariabile);
        System.out.println("--------------");
        analizator.afisareHashMap(analizator.tabelaNumere);
        }catch (Exception e) {
            e.printStackTrace();
        }

//        AutomatFinit automatFinit = new AutomatFinit("./src/main/resources/constante.txt");
//        Meniu meniu = new Meniu(automatFinit);
//        meniu.run();
    }
}
