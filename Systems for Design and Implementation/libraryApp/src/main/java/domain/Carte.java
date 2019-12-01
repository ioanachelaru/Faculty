package domain;

import javax.persistence.*;

@Entity
@Table(name = "carti")
public class Carte {
    @Id
    @Column(name = "idCarte")
    private int id;
    @Column(name = "titluCarte")
    private String titlu;
    @Column(name = "autorCarte")
    private String autor;
    @Column(name = "nrExemplare")
    private int nrExemplare;

    public Carte(){}

    public Carte(int idCarte, String titlu, String autor) {
        this.id = idCarte;
        this.titlu = titlu;
        this.autor = autor;
        this.nrExemplare = 0;
    }

    public int getId() { return id; }

    public void setIdCarte(int idCarte) { this.id = idCarte; }

    public String getTitlu() { return titlu; }

    public void setTitlu(String titlu) { this.titlu = titlu; }

    public String getAutor() { return autor; }

    public void setAutor(String autor) { this.autor = autor; }

    public int getNrExemplare() { return nrExemplare; }

    public void setNrExemplare(int nrExemplare) { this.nrExemplare = nrExemplare; }

}