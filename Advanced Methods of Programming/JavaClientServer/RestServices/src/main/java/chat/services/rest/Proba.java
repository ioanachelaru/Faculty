package chat.services.rest;

import Model.HasId;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

public class Proba implements HasId<Integer>, Serializable {
    private int id;
    private Integer distanta;
    private String stil;

    public Proba(Integer idProba, Integer distanta, String stil){
        this.id=idProba;
        this.stil = stil;
        this.distanta = distanta;
    }
    public Proba(){}

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) { this.id=integer; }

    public Integer getDistanta() {
        return distanta;
    }

    public void setDistanta(Integer distanta) {
        this.distanta = distanta;
    }

    public String getStil() {
        return stil;
    }

    public void setStil(String stil) {
        this.stil = stil;
    }
}
