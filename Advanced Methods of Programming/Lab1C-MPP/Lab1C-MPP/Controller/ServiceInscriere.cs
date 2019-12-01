using Lab1C_MPP.Domain;
using Lab1C_MPP.Repo;
using System.Collections.Generic;

namespace Lab1C_MPP.Controller
{
    class ServiceInscriere
    {
        private RepoInscriere repoInscriere;

        public ServiceInscriere(RepoInscriere repoInscriere)
        {
            this.repoInscriere = repoInscriere;
        }

        public void Save(Inscriere inscriere)
        {
            Inscriere i = this.repoInscriere.FindOne(inscriere.GetId());
            if (i == null)
                this.repoInscriere.Save(inscriere);
            else throw new RepositoryException("Participantul este deja inscris la acesta proba!");
        }

        public IEnumerable<Inscriere> findAll()
        {
            return this.repoInscriere.FindAll();
        }

        public Inscriere FindOne(int id) {
            return this.repoInscriere.FindOne(id);
        }

        public int size()
        {
            return this.repoInscriere.Size();
        }
    }
}
