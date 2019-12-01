package Services;

import Model.ProbaExtins;

public interface IObserver {
    void update(Iterable<ProbaExtins> probe);
}
