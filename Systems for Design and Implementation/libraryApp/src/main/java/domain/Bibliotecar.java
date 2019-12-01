package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bibliotecari")
public class Bibliotecar {
    @Id
    @Column(name = "usernameBibliotecar")
    private String username;
    @Column(name = "parolaBibliotecar")
    private String password;
    @Column(name = "numeBibliotecar")
    private String name;

    public Bibliotecar(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public Bibliotecar(){}

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}