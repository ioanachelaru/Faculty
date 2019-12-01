package utils;

import domain.Proba;

public class ProbaExtins extends Proba {
    private Integer nrInscrisi;

    public ProbaExtins(Integer idProba, Integer distanta, String stil, Integer nrInscrisi) {
        super(idProba, distanta, stil);
        this.nrInscrisi=nrInscrisi;
    }

    public Integer getNrInscrisi() {
        return nrInscrisi;
    }

    public void setNrInscrisi(Integer nrInscrisi) {
        this.nrInscrisi = nrInscrisi;
    }
}
