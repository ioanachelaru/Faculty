package Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "angajat")
public class Angajat implements HasId<String>, Serializable {
    @Id
    @Column(name = "userAngajat")
    private  String userAngajat;

    @Column(name = "passwordAngajat")
    private  String passwordAngajat;

    public Angajat() {}

    public Angajat(String userAngajat, String passwordAngajat) {
        this.userAngajat = userAngajat;
        this.passwordAngajat = passwordAngajat;
    }

    public String getId() {
        return userAngajat;
    }

    public void setId(String userAngajat) {
        this.userAngajat = userAngajat;
    }

    public String getPasswordAngajat() {
        return passwordAngajat;
    }

    public void setPasswordAngajat(String passwordAngajat) {
        this.passwordAngajat = passwordAngajat;
    }

    @Override
    public String toString() {
        return userAngajat+" "+passwordAngajat;
    }
}
