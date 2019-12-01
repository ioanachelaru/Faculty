package Scadere;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScadereParalel {
    private Integer nr_threads;
    private List<Integer> numarMare1;
    private List<Integer> numarMare2;
    private List<Integer> vectorRezultat;

    public ScadereParalel(Integer nr_threads, List<Integer> numarMare1, List<Integer> numarMare2) {
        this.nr_threads = nr_threads;
        this.numarMare1 = numarMare1;
        this.numarMare2 = numarMare2;
        this.vectorRezultat = new ArrayList<>();

        Collections.reverse(numarMare1);
        Collections.reverse(numarMare2);

        this.checkVectors();
        this.initVectors();

    }

    private void checkVectors(){
        while (numarMare1.size() > numarMare2.size())
            numarMare2.add(0);

        while (numarMare2.size() > numarMare1.size())
            numarMare1.add(0);
    }

    private void initVectors(){
        for(int i = 0; i < this.numarMare1.size(); i++)
            this.vectorRezultat.add(null);
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
                    if(this.numarMare1.get(j) < this.numarMare2.get(j)){

                        int k = j + 1;
                        while(this.numarMare1.get(k) == 0) {
                            this.numarMare1.set(k, 9);
                            k++;
                        }
                        this.numarMare1.set(k, this.numarMare1.get(k) - 1);
                        this.vectorRezultat.set(j,( (10 + this.numarMare1.get(j)) - this.numarMare2.get(j)));
                    }
                    else
                        this.vectorRezultat.set(j,(this.numarMare1.get(j) - this.numarMare2.get(j)));
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

    public List<Integer> scadere() throws InterruptedException {
        this.pas1();

        int k = this.vectorRezultat.size() - 1;
        while(this.vectorRezultat.get(k) == 0){
            this.vectorRezultat.remove(k);
            k--;
        }

        Collections.reverse(this.vectorRezultat);
        return this.vectorRezultat;
    }
}
