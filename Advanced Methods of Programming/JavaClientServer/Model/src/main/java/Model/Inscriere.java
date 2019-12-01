package Model;

import java.io.Serializable;

public class Inscriere implements HasId<Integer>, Serializable {
    private int idInscriere;
    private int idProba;
    private int idParticipant;


    public Inscriere(int idProba, int idParticipant) {
        this.idInscriere = Integer.parseInt(String.valueOf(idProba)+String.valueOf(idParticipant));
        this.idProba = idProba;
        this.idParticipant = idParticipant;
    }

    public Integer getId() { return idInscriere; }

    public void setId(Integer idInscriere) { this.idInscriere = idInscriere; }

    public int getIdProba() { return idProba; }

    public void setIdProba(int idProba) { this.idProba = idProba; }

    public int getIdParticipant() { return idParticipant; }

    public void setIdParticipant(int idParticipant) { this.idParticipant = idParticipant; }
}
