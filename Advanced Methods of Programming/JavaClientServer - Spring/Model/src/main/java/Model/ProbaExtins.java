package Model;

import java.io.Serializable;

public class ProbaExtins extends Proba implements Serializable {
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
