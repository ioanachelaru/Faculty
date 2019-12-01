package Adunare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalculatorSerial {

    private List<Integer> numarMare1;
    private List<Integer> numarMare2;

    private List<Integer> vectorRezultat = new ArrayList<>();
    private List<Integer> vectorCarry = new ArrayList<>();

    public CalculatorSerial(List<Integer> numarMare1, List<Integer> numarMare2) {

        this.vectorCarry.add(0);
        this.numarMare1 = numarMare1;
        this.numarMare2 = numarMare2;

        Collections.reverse(numarMare1);
        Collections.reverse(numarMare2);

        this.checkVectors();
    }

    private void checkVectors(){
        while (numarMare1.size() > numarMare2.size())
            numarMare2.add(0);

        while (numarMare2.size() > numarMare1.size())
            numarMare1.add(0);
    }

    public List<Integer> aduna(){
        for (int i = 0; i < this.numarMare1.size(); i++) {
            this.vectorRezultat.add((this.numarMare1.get(i) + this.numarMare2.get(i))%10);
            if(this.numarMare1.get(i) + this.numarMare2.get(i) > 9)
                this.vectorCarry.add(1);
            else this.vectorCarry.add(0);
        }
        List<Integer> rezultat = new ArrayList<>();
        for(int i = 0; i < this.vectorRezultat.size(); i++){
            rezultat.add((this.vectorRezultat.get(i)+this.vectorCarry.get(i))%10);
            if(this.vectorRezultat.get(i)+this.vectorCarry.get(i) > 9)
                this.vectorCarry.set(i+1,this.vectorCarry.get(i+1)+1);
        }

        if(this.vectorCarry.get(this.vectorCarry.size()-1) == 1)
            rezultat.add(1);

        Collections.reverse(rezultat);
        return rezultat;
    }
}
