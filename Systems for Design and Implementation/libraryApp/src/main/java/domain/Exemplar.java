package domain;

import javax.persistence.*;

@Entity
@Table(name = "exemplare")
public class Exemplar {
    @Id
    @Column(name = "idExemplar")
    private int id;
    @Column(name = "idCarte")
    private int idCarte;
    @Column(name = "anAparitie")
    private int anAparitie;
    @Column(name = "disponibil")
    private String disponibil;

    public Exemplar(){}

    public Exemplar(int id, int idCarte, int anAparitie) {
        this.id = id;
        this.idCarte = idCarte;
        this.anAparitie = anAparitie;
        this.disponibil = "disponibil";
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getIdCarte() { return idCarte; }

    public void setIdCarte(int idCarte) { this.idCarte = idCarte; }

    public int getAnAparitie() { return anAparitie; }

    public void setAnAparitie(int anAparitie) { this.anAparitie = anAparitie; }

    public String getDisponibil() { return disponibil; }

    public void setDisponibil(String disponibil) { this.disponibil = disponibil; }
}