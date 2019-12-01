package Adunare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalculatorParalelOptimizat {
    private Integer nr_threads;

    private List<Integer> numarMare1;
    private List<Integer> numarMare2;

    private Integer size;
    private List<String> vectorC;
    private List<String> vectorE;
    private List<Integer> vectorM;
    private List<Integer> vectorR;

    public CalculatorParalelOptimizat(Integer nr_threads, List<Integer> numarMare1, List<Integer> numarMare2) {
        this.nr_threads = nr_threads;

        this.numarMare1 = numarMare1;
        this.numarMare2 = numarMare2;

        this.vectorC = new ArrayList<>();
        this.vectorM = new ArrayList<>();
        this.vectorE = new ArrayList<>();
        this.vectorR = new ArrayList<>();

        Collections.reverse(numarMare1);
        Collections.reverse(numarMare2);

        this.checkVectors();

        this.size = this.numarMare1.size();

        this.initVectors();
    }

    private void initVectors(){
        for(int i = 0; i < this.size; i++){
            this.vectorC.add(null);
            this.vectorM.add(null);
            this.vectorE.add(null);
            this.vectorR.add(null);
        }
        this.vectorR.add(null);
    }

    private void checkVectors(){
        while (numarMare1.size() > numarMare2.size())
            numarMare2.add(0);

        while (numarMare2.size() > numarMare1.size())
            numarMare1.add(0);
    }

    private String operatorX(String x, String y){
        if(x.equals("N") && y.equals("N"))
            return "N";
        else if(x.equals("N") && y.equals("M"))
            return "N";
        else if(x.equals("N") && y.equals("C"))
            return "C";
        else if(x.equals("M") && y.equals("N"))
            return "N";
        else if(x.equals("M") && y.equals("M"))
            return "M";
        else if(x.equals("M") && y.equals("C"))
            return "C";
        else if(x.equals("C") && y.equals("N"))
            return "N";
        else if(x.equals("C") && y.equals("M"))
            return "C";
        else
            return "C";
    }

    private void pas1() throws InterruptedException {
        int start = 0, stop;
        int chunck_size = this.size / this.nr_threads;
        int reminder = this.size % this.nr_threads;

        Thread[] threads = new Thread[this.nr_threads];

        for(int i = 0; i < this.nr_threads; i++) {
            if (reminder > 0) {
                stop = start + chunck_size + 1;
                reminder--;
            } else stop = start + chunck_size;

            final int begin = start, end = stop;
            threads[i] = new Thread(() -> {
                for (int j = begin; j < end; j++){
                    if(this.numarMare1.get(j) + this.numarMare2.get(j) >= 10)
                        this.vectorC.set(j, "C");

                    if(this.numarMare1.get(j) + this.numarMare2.get(j) == 9)
                        this.vectorC.set(j, "M");

                    if(this.numarMare1.get(j) + this.numarMare2.get(j) <= 8)
                        this.vectorC.set(j, "N");
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

    private void pas2() throws InterruptedException {
        int start = 0, stop;
        int chunck_size = this.size / this.nr_threads;
        int reminder = this.size % this.nr_threads;

        Thread[] threads = new Thread[this.nr_threads];

        for(int i = 0; i < this.nr_threads; i++) {
            if (reminder > 0) {
                stop = start + chunck_size + 1;
                reminder--;
            } else stop = start + chunck_size;

            final int begin = start, end = stop;

            threads[i] = new Thread(()->{
                for(int j = begin; j < end; j++){
                    if(j == 0){
                        this.vectorE.set(j, this.vectorC.get(j));
                    } else
                    if(this.vectorC.get(j).equals("M"))
                    {
                        int y = j-1;
                        while(this.vectorC.get(j).equals("M") && y > 0){
                            y--;
                        }
                        if(this.vectorC.get(y).equals("C"))
                            this.vectorE.set(j, "C");
                        else
                            this.vectorE.set(j, this.operatorX(this.vectorC.get(j - 1), this.vectorC.get(j)));
                    }
                    else
                        this.vectorE.set(j, this.operatorX(this.vectorC.get(j - 1), this.vectorC.get(j)));
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

    private void pas3() throws InterruptedException {
        int start = 0, stop;
        int chunck_size = this.size / this.nr_threads;
        int reminder = this.size % this.nr_threads;

        Thread[] threads = new Thread[this.nr_threads];

        for(int i = 0; i < this.nr_threads; i++) {
            if (reminder > 0) {
                stop = start + chunck_size + 1;
                reminder--;
            } else stop = start + chunck_size;

            final int begin = start, end = stop;

            threads[i] = new Thread(()->{
                for(int j = begin; j < end; j++){
                    if(j == 0){
                        this.vectorM.set(j, 0);
                    } else
                        if(this.vectorE.get(j - 1).equals("C"))
                            this.vectorM.set(j, 1);
                        else
                            this.vectorM.set(j, 0);
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

    private void pas4() throws InterruptedException {
        int start = 0, stop;
        int chunck_size = this.size / this.nr_threads;
        int reminder = this.size % this.nr_threads;

        Thread[] threads = new Thread[this.nr_threads];

        for(int i = 0; i < this.nr_threads; i++) {
            if (reminder > 0) {
                stop = start + chunck_size + 1;
                reminder--;
            } else stop = start + chunck_size;

            final int begin = start, end = stop;

            threads[i] = new Thread(()->{
                for(int j = begin; j < end; j++){
                    this.vectorR.set(j, (this.numarMare1.get(j) + this.numarMare2.get(j) + this.vectorM.get(j))%10);
                    if(j == this.size - 1){
                        int b = (this.numarMare1.get(j) + this.numarMare2.get(j) + this.vectorM.get(j)) % 100;
                        if(b > 9)
                            this.vectorR.set(j + 1, b / 10);
                    }
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
        this.pas2();
        this.pas3();
        this.pas4();

        if(this.vectorR.get(this.vectorR.size() - 1) == null)
            this.vectorR.remove(this.vectorR.size() - 1);

        Collections.reverse(this.vectorR);
        return this.vectorR;
    }

}