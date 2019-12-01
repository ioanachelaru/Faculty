using System.Collections.Generic;

namespace Server.Repo
{
    interface IRepository<ID, E>
    {
        int Size();
        void Save(E entity);
        E FindOne(ID Id);
        IEnumerable<E> FindAll();
    }
}
