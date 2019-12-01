import Adunare.CalculatorParalelOptimizat;
import Inmultire.InmultireKaratsuba;
import Inmultire.InmultireParalel;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Worker worker = new Worker();

        //worker.writeToFileEasy("./src/main/resources/moreNumbers.txt",7,1,9);
        //worker.writeToFileEasy("./src/main/resources/numbers.txt",7,1,9);

        List<Integer> numar1 = worker.getDataFromFile("./src/main/resources/moreNumbers.txt");
        List<Integer> numar2 = worker.getDataFromFile("./src/main/resources/numbers.txt");

        //InmultireSecventiala inmultireSecventiala = new InmultireSecventiala(numar1,numar2);

        //InmultireParalel inmultireParalel= new InmultireParalel(16, numar1, numar2);

        //ScadereParalel scadereParalel = new ScadereParalel(4, numar1, numar2);
        CalculatorParalelOptimizat calculator = new CalculatorParalelOptimizat(6, numar1, numar2);

        long timeStart = System.nanoTime();

        List<Integer> rezultat = calculator.aduna();
        //List<Integer> rezultat = scadereParalel.scadere();

        long timeStop = System.nanoTime();

        System.out.println(rezultat);
        System.out.println(timeStop-timeStart);
    }
}