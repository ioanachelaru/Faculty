package Services;

import Model.ProbaExtins;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
    void update(Iterable<ProbaExtins> probe) throws RemoteException;
}
