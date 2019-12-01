package Adunare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalculatorParalel {
    private Integer nr_threads;
    private List<Integer> numarMare1;
    private List<Integer> numarMare2;
    private List<Integer> vectorRezultat;
    private List<Integer> vectorCarry;



    public CalculatorParalel(Integer nr_threads, List<Integer> numarMare1, List<Integer> numarMare2) {
        this.nr_threads = nr_threads;
        this.vectorRezultat = new ArrayList<>();
        this.vectorCarry = new ArrayList<>();
        this.numarMare1 = numarMare1;
        this.numarMare2 = numarMare2;

        Collections.reverse(numarMare1);
        Collections.reverse(numarMare2);

        this.checkVectors();
        this.initVectors();
        this.vectorCarry.set(0, 0);
    }

    private void checkVectors(){
        while (numarMare1.size() > numarMare2.size())
            numarMare2.add(0);

        while (numarMare2.size() > numarMare1.size())
            numarMare1.add(0);
    }

    private void initVectors(){
        for(int i = 0; i < this.numarMare1.size(); i++){
            this.vectorRezultat.add(null);
            this.vectorCarry.add(null);
        }
        this.vectorCarry.add(null);
    }

    private void pas1() throws InterruptedException {
        Integer size = this.numarMare1.size();
        int start = 0, stop;
        int chunck_size = size / this.nr_threads, reminder = size % this.nr_threads;

        Thread[] threads = new Thread[this.nr_threads];

        for(int i = 0; i < this.nr_threads; i++){
            if(reminder > 0) {
                stop = start + chunck_size + 1;
                reminder--;
            }
            else stop = start + chunck_size;

            final int end = stop, begin = start ;

            threads[i] = new Thread(()->{
                for (int j = begin; j < end; j++) {
                        this.vectorRezultat.set(j,(this.numarMare1.get(j) + this.numarMare2.get(j)) % 10);
                    if(this.numarMare1.get(j) + this.numarMare2.get(j) > 9)
                            this.vectorCarry.set(j+1,1);
                    else
                        this.vectorCarry.set(j + 1, 0);
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

    public List<Integer> aduna() throws InterruptedException {
        this.pas1();

        List<Integer> rezultat = new ArrayList<>();

        Integer size = this.vectorRezultat.size();
        int start = 0, stop;
        int chunck_size = size/this.nr_threads, reminder = size%this.nr_threads;

        Thread[] threads = new Thread[this.nr_threads];

        for(int i = 0; i < this.nr_threads; i++){
            if(reminder > 0) {
                stop = start + chunck_size + 1;
                reminder--;
            }
            else stop = start + chunck_size;

            final int end = stop, begin = start, y = i ;

            threads[i] = new Thread(()->{
                for (int j = begin;j < end; j++) {
                        rezultat.set(j,((this.vectorRezultat.get(j) + this.vectorCarry.get(j)) % 10));
                    if(this.vectorRezultat.get(j)+this.vectorCarry.get(j) > 9)
                        this.vectorCarry.set(j+1,this.vectorCarry.get(j+1)+1);
                }

                if(y == this.nr_threads - 1)
                    if(this.vectorCarry.get(this.vectorCarry.size()-1) == 1)
                            rezultat.add(1);
            });
            threads[i].start();
            threads[i].join();
            start = stop;
        }
        Collections.reverse(rezultat);
        return rezultat;
    }
}
