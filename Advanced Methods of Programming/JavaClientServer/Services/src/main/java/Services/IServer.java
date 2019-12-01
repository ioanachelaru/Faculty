package Services;

import Model.Angajat;
import Model.Inscriere;
import Model.Participant;
import Model.ProbaExtins;
import java.util.List;

public interface IServer {
    void login(Angajat user, IObserver client);

    void logout(Angajat user, IObserver client);

    List<Angajat> getAllAngajati();

    List<ProbaExtins> getAllProbaExtins();

    Iterable<Participant> getAllParticipants(String idProba);

    Integer getMaxId();

    void addParticipant(Participant participant);

    void addInscriere(Inscriere inscriere);
}
