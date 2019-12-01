package domain;

public enum Distanta {
    cincizeci(50),
    doua_sute(200),
    opt_sute(800),
    o_mie_cinci_sute(1500);

    private final int value;

    Distanta(int x){ this.value=x; }

    public static String getValue(int x){
        if(x==50) return "cincizeci";
        if(x==200) return "doua_sute";
        if(x==800) return "opt_sute";
        if(x==1500) return "o_mie_cinci_sute";

        return null;
    }

    public static Integer getValueInt(String x){
        if(x.equals("cincizeci")) return 50;
        if(x.equals("doua_sute")) return 200;
        if(x.equals("opt_sute")) return 800;
        if(x.equals("o_mie_cinci_sute")) return 1500;

        return 0;
    }
}