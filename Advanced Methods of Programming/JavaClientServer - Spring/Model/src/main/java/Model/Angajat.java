package Model;

import java.io.Serializable;

public class Angajat implements HasId<String>, Serializable {
    private  String userAngajat;
    private  String passwordAngajat;


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
