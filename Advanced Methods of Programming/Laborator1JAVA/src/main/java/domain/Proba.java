package domain;

public class Proba implements HasId<Integer> {
    private int idProba;
    private Distanta distanta;
    private Stil stil;

    public Proba(Integer idProba, Integer distanta, String stil){
        this.idProba=idProba;
        this.stil=Stil.valueOf(stil);
        this.distanta=Distanta.valueOf(Distanta.getValue(distanta));
    }

    @Override
    public Integer getId() {
        return idProba;
    }

    @Override
    public void setId(Integer integer) { this.idProba=integer; }

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

    public Integer getDistantaInt(){
        return Distanta.getValueInt(this.distanta.toString());
    }
}
