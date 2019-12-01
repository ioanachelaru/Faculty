namespace SharedDLL.Utils
{
    public interface IObservable
    {
        void AddObserver(IObserver e);
        void RemoveObserver(IObserver e);
        void NotifyObservers(int id, int newEnrollCount);
    }
}
