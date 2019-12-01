package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "abonati")
public class Abonat {
    @Column(name = "cuiAbonat")
    private String cui;
    @Column(name = "cnpAbonat")
    private String cnp;
    @Column(name = "numeAbonat")
    private String nume;
    @Column(name = "adresaAbonat")
    private String adresa;
    @Column(name = "telefonAbonat")
    private String telefon;
    @Id
    @Column(name = "usernameAbonat")
    private String username;
    @Column(name = "parolaAbonat")
    private String password;

    public Abonat(){}

    public Abonat(String cui, String cnp, String nume, String adresa, String telefon,
                  String username, String password) {
        this.cui = cui;
        this.cnp = cnp;
        this.nume = nume;
        this.adresa = adresa;
        this.telefon = telefon;
        this.username = username;
        this.password = password;
    }

    public String getCui() { return cui; }

    public void setCui(String cui) { this.cui = cui; }

    public String getCnp() { return cnp; }

    public void setCnp(String cnp) { this.cnp = cnp; }

    public String getNume() { return nume; }

    public void setNume(String nume) { this.nume = nume; }

    public String getAdresa() { return adresa; }

    public void setAdresa(String adresa) { this.adresa = adresa; }

    public String getTelefon() { return telefon; }

    public void setTelefon(String telefon) { this.telefon = telefon; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }
}