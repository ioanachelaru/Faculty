package objectprotocol;

import Model.Angajat;

public class LogoutRequest implements Request {
    private Angajat user;

    public LogoutRequest(Angajat user) {
        this.user = user;
    }

    public Angajat getUser() { return user; }
}
