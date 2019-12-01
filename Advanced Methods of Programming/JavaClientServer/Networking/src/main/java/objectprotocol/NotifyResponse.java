package objectprotocol;

import Model.ProbaExtins;
import java.util.List;

public class NotifyResponse implements UpdateResponse {
    Iterable<ProbaExtins> probe;

    public NotifyResponse(Iterable<ProbaExtins> probe) {
        this.probe = probe;
    }

    public Iterable<ProbaExtins> getProbe() { return probe; }
}
