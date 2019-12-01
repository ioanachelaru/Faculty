package objectprotocol;

import Model.Angajat;
import java.util.List;

public class GetAllAngajatsResponse implements Response {
    private List<Angajat> angajats;

    public GetAllAngajatsResponse(List<Angajat> angajats) {
        this.angajats = angajats;
    }

    public List<Angajat> getAngajats() {
        return angajats;
    }
}
