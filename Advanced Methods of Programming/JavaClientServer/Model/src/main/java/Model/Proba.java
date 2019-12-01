package Model;

import java.io.Serializable;

public class Proba implements HasId<Integer>, Serializable {
    private int id;
    private Distanta distanta;
    private Stil stil;

    public Proba(Integer idProba, Integer distanta, String stil){
        this.id=idProba;
        this.stil=Stil.valueOf(stil);
        this.distanta=Distanta.valueOf(Distanta.getValue(distanta));
    }
    public Proba(){}

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) { this.id=integer; }

    public Distanta getDistanta() {
        return distanta;
    }

    public void setDistanta(Distanta distanta) {
        this.distanta = distanta;
    }

    public Stil getStil() {
        return stil;
    }

    public void setStil(Stil stil) {
        this.stil = stil;
    }

    public Integer distantaInt(){
        return Distanta.getValueInt(this.distanta.toString());
    }
}
