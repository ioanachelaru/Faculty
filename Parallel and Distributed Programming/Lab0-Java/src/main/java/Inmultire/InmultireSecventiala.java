package Inmultire;

import Adunare.CalculatorSerial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InmultireSecventiala {
    private List<Integer> numarMare1;
    private List<Integer> numarMare2;

    private List<List<Integer>> resultOfMultiplication;

    public InmultireSecventiala(List<Integer> numarMare1, List<Integer> numarMare2) {
        this.numarMare1 = numarMare1;
        this.numarMare2 = numarMare2;

        Collections.reverse(numarMare1);
        Collections.reverse(numarMare2);

        this.resultOfMultiplication = new ArrayList<>();

        this.getResultsOfMultiplication();
    }

    private void getResultsOfMultiplication(){

        for(int i = 0; i < this.numarMare1.size(); i++){
            List<Integer> currentResult = new ArrayList<>();
            List<Integer> currentCarry = new ArrayList<>();
            currentCarry.add(0);

            for (int j = 0; j < this.numarMare2.size(); j++){
                int result = this.numarMare1.get(i) * this.numarMare2.get(j);
                currentResult.add(result % 10);

                if(result > 9){
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
            this.resultOfMultiplication.add(rezultat);
        }

        this.addBackZeros();
        this.addFrontZeros();

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

    public List<Integer> inmulteste(){
        List<Integer> currentResult = this.resultOfMultiplication.get(0);

        for(int i = 1; i < this.resultOfMultiplication.size(); i++){

            CalculatorSerial calculatorSerial = new CalculatorSerial(currentResult, this.resultOfMultiplication.get(i));
            currentResult = calculatorSerial.aduna();
        }

        return currentResult;
    }
}

