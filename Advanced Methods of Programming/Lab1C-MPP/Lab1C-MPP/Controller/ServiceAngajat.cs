using Lab1C_MPP.Domain;
using Lab1C_MPP.Repo;
using System.Collections.Generic;

namespace Lab1C_MPP.Controller
{
    class ServiceAngajat
    {
        private RepoAngajat repoAngajat;

        public ServiceAngajat(RepoAngajat repo)
        {
            this.repoAngajat = repo;
        }

        public void Save(Angajat angajat)
        {
            this.repoAngajat.Save(angajat);
        }

        public int Size()
        {
            return this.repoAngajat.Size();
        }

        public Angajat FindOne(string id)
        {
            Angajat angajat = this.repoAngajat.FindOne(id);
            if (angajat == null) throw new RepositoryException("Angajat inexistent!");
            else return angajat;
        }

        public IEnumerable<Angajat> FindAll()
        {
            return this.repoAngajat.FindAll();
        }
    }
}
