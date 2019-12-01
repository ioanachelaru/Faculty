package Adunare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Calculator
{
    private List<Integer> numarMare1;
    private List<Integer> numarMare2;

    public Calculator(List<Integer> numarMare1, List<Integer> numarMare2) {
        this.numarMare1 = numarMare1;
        this.numarMare2 = numarMare2;

        this.checkVectors();

        Collections.reverse(numarMare1);
        Collections.reverse(numarMare2);

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

    private List<String> pas1(){
        List<String> c = new ArrayList<>();
        for (int i = 0; i < this.numarMare1.size(); i++){
            if(numarMare1.get(i) + numarMare2.get(i) >= 10)
                c.add("C");

            if(numarMare1.get(i) + numarMare2.get(i) == 9)
                c.add("M");

            if(numarMare1.get(i) + numarMare2.get(i) <= 8)
                c.add("N");
        }
        return c;
    }

    private List<String> pas2(){
        List<String> c = this.pas1();
        List<String> e = new ArrayList<>();
        for(int i = 0; i < c.size(); i++){
            if(i == 0){
                e.add(c.get(i));
            } else
            if(c.get(i).equals("M"))
            {
                int j = i-1;
                while(c.get(j).equals("M") && j > 0){
                    j--;
                }
                if(c.get(j).equals("C"))
                    e.add("C");
                else e.add(this.operatorX(c.get(i - 1), c.get(i)));
            }
            else e.add(this.operatorX(c.get(i - 1), c.get(i)));
        }
        return e;
    }

    private List<Integer> pas3(){
        List<Integer> m = new ArrayList<>();
        List<String> e = pas2();
        for(int i = 0; i < e.size(); i++){
            if(i == 0){
                m.add(0);
            }else
                if(e.get(i - 1).equals("C"))
                    m.add(1);
                else
                    m.add(0);
        }
        return m;
    }

    private List<Integer> pas4(){
        List<Integer> r = new ArrayList<>();
        List<Integer> m =pas3();
        for(int i = 0; i < m.size(); i++){
            r.add((numarMare1.get(i) + numarMare2.get(i) + m.get(i))%10);
            if(i == m.size() - 1){
                int b = (numarMare1.get(i) + numarMare2.get(i) + m.get(i))%100;
                if(b > 9){
                    r.add(b / 10);
                }
            }
        }

        return r;
    }

    public List<Integer> aduna(){
        List<Integer> r = pas4();
        Collections.reverse(r);
        return r;
    }
}