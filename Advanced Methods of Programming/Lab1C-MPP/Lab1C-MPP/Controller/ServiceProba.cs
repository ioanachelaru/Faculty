using Lab1C_MPP.Domain;
using Lab1C_MPP.Repo;
using System.Collections.Generic;

namespace Lab1C_MPP.Controller
{
    class ServiceProba
    {
        private RepoProba repoProba;

        public ServiceProba(RepoProba repoProba)
        {
            this.repoProba = repoProba;
        }

        public void Save(Proba entity)
        {
            Proba proba = this.repoProba.FindOne(entity.GetId());
            if (proba == null)
                this.repoProba.Save(entity);
            else throw new RepositoryException("Proba exista deja!");
        }

        public int Size() { return this.repoProba.Size(); }

        public Proba FindOne(int integer)
        {
            Proba proba = this.repoProba.FindOne(integer);
            if (proba == null)
                throw new RepositoryException("Proba nu exista!");
            else return proba;
        }

        public IEnumerable<Proba> FindAll() { return this.repoProba.FindAll(); }

        public int numarInscrieri(int id) { return this.repoProba.NumarInscrieri(id); }
    }
}
