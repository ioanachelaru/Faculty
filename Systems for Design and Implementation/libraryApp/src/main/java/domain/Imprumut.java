package domain;

import javax.persistence.*;

@Entity
@Table(name = "imprumuturi")
public class Imprumut {
    @Id
    @Column(name = "idImprumut")
    private int id;
    @Column(name = "cuiAbonat")
    private String cuiAbonat;
    @Column(name = "idExemplar")
    private int idExemplar;
    @Column(name = "usernameBibliotecar")
    private String usernameBibliotecar;
    @Column(name = "date")
    private String data;
    @Column(name = "activ")
    private String activ;

    public Imprumut(){}

    public Imprumut(int id, String cuiAbonat, int idExemplar, String usernameBibliotecar, String data) {
        this.id = id;
        this.cuiAbonat = cuiAbonat;
        this.idExemplar = idExemplar;
        this.usernameBibliotecar = usernameBibliotecar;
        this.data = data;
        this.activ = "da";
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getCuiAbonat() { return cuiAbonat; }

    public void setCuiAbonat(String cuiAbonat) { this.cuiAbonat = cuiAbonat; }

    public int getIdExemplar() { return idExemplar; }

    public void setIdExemplar(int idExemplar) { this.idExemplar = idExemplar; }

    public String getUsernameBibliotecar() { return usernameBibliotecar; }

    public void setUsernameBibliotecar(String usernameBibliotecar) {
        this.usernameBibliotecar = usernameBibliotecar; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public String getActiv() { return activ; }

    public void setActiv(String activ) { this.activ = activ; }
}