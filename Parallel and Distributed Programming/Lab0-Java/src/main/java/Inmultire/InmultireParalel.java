package Inmultire;

import Adunare.CalculatorParalelOptimizat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InmultireParalel {
    private Integer nr_threads;
    private List<Integer> numarMare1;
    private List<Integer> numarMare2;

    private List<List<Integer>> resultOfMultiplication;

    public InmultireParalel(Integer nr, List<Integer> numarMare1, List<Integer> numarMare2) throws InterruptedException {
        this.nr_threads = nr;
        this.numarMare1 = numarMare1;
        this.numarMare2 = numarMare2;

        Collections.reverse(numarMare1);
        Collections.reverse(numarMare2);

        this.resultOfMultiplication = new ArrayList<>();
        this.initResultsofMultiplication();

        this.getResultsOfMultiplication();
    }

    private void initResultsofMultiplication(){
        for(int i = 0; i < this.numarMare2.size(); i++){
            this.resultOfMultiplication.add(new ArrayList<>());
        }
    }

    private void addBackZeros() {
        for (int i = 0; i < this.resultOfMultiplication.size(); i++){
            for(int j = 0; j < i; j++){
                this.resultOfMultiplication.get(i).add(0);
            }
        }
    }

    private void addFrontZeros() {
        for (int i = 0; i < this.resultOfMultiplication.size(); i++){
            Collections.reverse(this.resultOfMultiplication.get(i));
            for(int j = 0; j < this.numarMare1.size() - 1 - i; j++){
                this.resultOfMultiplication.get(i).add(0);
            }
            Collections.reverse(this.resultOfMultiplication.get(i));
        }
    }

    private void getResultsOfMultiplication() throws InterruptedException {
        Integer size = this.numarMare2.size();
        int start = 0, stop;
        int chunck_size = size / this.nr_threads, reminder = size % this.nr_threads;

        Thread[] threads = new Thread[this.nr_threads];

        for(int i = 0; i < this.nr_threads; i++){
            if(reminder > 0) {
                stop = start + chunck_size + 1;
                reminder--;
            }
            else stop = start + chunck_size;

            final int end = stop, begin = start;

            threads[i] = new Thread(()->{
                for (int j = begin; j < end; j++) {

                    List<Integer> currentResult = new ArrayList<>();
                    List<Integer> currentCarry = new ArrayList<>();
                    currentCarry.add(0);

                    for(int ii = 0; ii < this.numarMare1.size(); ii++) {

                        int result = this.numarMare1.get(ii) * this.numarMare2.get(j);
                        currentResult.add(result % 10);

                        if (result > 9) {
                            Integer carry = result / 10;
                            currentCarry.add(carry);
                        } else currentCarry.add(0);
                    }

                        List<Integer> rezultat = new ArrayList<>();
                        for(int k = 0; k < currentResult.size(); k++)
                        {
                            rezultat.add((currentResult.get(k)+currentCarry.get(k)) % 10);

                            if(currentResult.get(k)+currentCarry.get(k) > 9){
                                currentCarry.set(k + 1, currentCarry.get(k + 1) + 1);
                            }

                    }
                    if(currentCarry.get(currentCarry.size()-1) != 0)
                        rezultat.add(currentCarry.get(currentCarry.size()-1));
                    Collections.reverse(rezultat);
                    this.resultOfMultiplication.set(j, rezultat);

                }
            });

            start = stop;
        }

        for (int i = 0; i < this.nr_threads; i++) {
            threads[i].start();
        }

        for(int i = 0; i < this.nr_threads; i++){
            threads[i].join();
        }
    }

    public List<Integer> inmulteste() throws InterruptedException {

        this.addBackZeros();
        this.addFrontZeros();

        List<Integer> currentResult = this.resultOfMultiplication.get(0);

        for(int i = 1; i < this.resultOfMultiplication.size(); i++){

            CalculatorParalelOptimizat calculator = new CalculatorParalelOptimizat(this.nr_threads, currentResult,
                    this.resultOfMultiplication.get(i));
            currentResult = calculator.aduna();
        }

        int i = 0;
        while(currentResult.get(i) == 0) {
            currentResult.remove(i);
        }

        return currentResult;
    }
}