using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab1C_MPP.Repo
{
    interface IRepository<ID, E>
    {
        int Size();
        void Save(E entity);
        E FindOne(ID Id);
        IEnumerable<E> FindAll();
    }
}
