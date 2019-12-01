package Inmultire;

import Adunare.CalculatorParalelOptimizat;
import Scadere.ScadereParalel;
import java.util.ArrayList;
import java.util.List;

public class InmultireKaratsuba {
    private Integer nrThreads;
    private List<Integer> numarMare1;
    private List<Integer> numarMare2;

    private Integer n;

    private List<Integer> p;
    private List<Integer> q;
    private List<Integer> r;
    private List<Integer> s;

    private List<Integer> pXr;
    private List<Integer> qXs;

    public InmultireKaratsuba(Integer nrThreads, List<Integer> n1, List<Integer> n2){
        this.nrThreads = nrThreads;
        this.numarMare1 = n1;
        this.numarMare2 = n2;

        if(this.numarMare1.size() < this.numarMare2.size())
            this.n = this.numarMare1.size();
        else this.n = this.numarMare2.size();

        this.p = new ArrayList<>();
        this.q = new ArrayList<>();
        this.r = new ArrayList<>();
        this.s = new ArrayList<>();

        this.initLists();

        this.pXr = new ArrayList<>();
        this.qXs = new ArrayList<>();
    }

    private void initLists(){
        for(int i = 0; i < this.numarMare1.size()/2; i++)
            this.p.add(this.numarMare1.get(i));
        for(int i = this.numarMare1.size()/2; i < this.numarMare1.size(); i++)
            this.q.add(this.numarMare1.get(i));

        for(int i = 0; i < this.numarMare2.size()/2; i++)
            this.r.add(this.numarMare2.get(i));
        for(int i = this.numarMare2.size()/2; i < this.numarMare2.size(); i++)
            this.s.add(this.numarMare2.get(i));
    }

    private List<Integer> pXr(){
        InmultireSecventiala inmultireSecventiala = new InmultireSecventiala(new ArrayList<>(this.p), new ArrayList<>(this.r));
        return inmultireSecventiala.inmulteste();
    }

    private List<Integer> qXs(){
        InmultireSecventiala inmultireSecventiala = new InmultireSecventiala(new ArrayList<>(this.q), new ArrayList<>(this.s));
        return inmultireSecventiala.inmulteste();
    }

    private List<Integer> gauss() throws InterruptedException {
        this.pXr = this.pXr();
        this.qXs = this.qXs();

        List<Integer> p = new ArrayList<>(this.p);
        List<Integer> q = new ArrayList<>(this.q);
        CalculatorParalelOptimizat calculatorPS = new CalculatorParalelOptimizat(this.nrThreads, p, q);

        List<Integer> s = new ArrayList<>(this.s);
        List<Integer> r = new ArrayList<>(this.r);
        CalculatorParalelOptimizat calculatorQR = new CalculatorParalelOptimizat(this.nrThreads, r, s);

        List<Integer> pADs = calculatorPS.aduna();
        List<Integer> qADr = calculatorQR.aduna();

        InmultireSecventiala inmultireSecventiala = new InmultireSecventiala(pADs, qADr);
        List<Integer> rightSegment = inmultireSecventiala.inmulteste();

        ScadereParalel scadereParalel1 = new ScadereParalel(this.nrThreads,rightSegment, new ArrayList<>(this.pXr));
        List<Integer> r1 = scadereParalel1.scadere();

        ScadereParalel scadereParalel = new ScadereParalel(this.nrThreads, r1, new ArrayList<>(this.qXs));

        return scadereParalel.scadere();
    }

    private List<Integer> generatePowerOfTwo(int n){
        List<Integer> list = new ArrayList<>();
        List<Integer> doi = new ArrayList<>();
        doi.add(2);
        list.add(1);

        for(int i = 0; i < n; i++) {
            InmultireSecventiala inmultireSecventiala = new InmultireSecventiala(list, new ArrayList<>(doi));
            list = inmultireSecventiala.inmulteste();
        }
        return list;
    }

    public List<Integer> inmulteste() throws InterruptedException {
        List<Integer> gauss = this.gauss();
        List<Integer> powerOfN = this.generatePowerOfTwo(this.n);
        List<Integer> powerOfHalfN = this.generatePowerOfTwo(this.n / 2);

        InmultireSecventiala firstInmultire = new InmultireSecventiala(powerOfN, this.pXr);
        List<Integer> fisrtStep = firstInmultire.inmulteste();

        InmultireSecventiala secondInmultire = new InmultireSecventiala(powerOfHalfN, gauss);
        List<Integer> secondStep = secondInmultire.inmulteste();

        CalculatorParalelOptimizat firstAdd = new CalculatorParalelOptimizat(this.nrThreads, fisrtStep, secondStep);
        List<Integer> thirdStep = firstAdd.aduna();

        CalculatorParalelOptimizat lastAdd = new CalculatorParalelOptimizat(this.nrThreads, thirdStep, this.qXs);

        return lastAdd.aduna();
    }
}