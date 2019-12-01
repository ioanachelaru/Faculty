using Lab1C_MPP.Domain;
using Lab1C_MPP.Repo;
using Lab1C_MPP.Validators;
using System.Collections.Generic;

namespace Lab1C_MPP.Controller
{
    class ServiceParticipant
    {
        private RepoParticipant repoParticipant;
        private Validators.ValidatorParticipant validatorParticipant;

        public ServiceParticipant(RepoParticipant repo, Validators.ValidatorParticipant validator)
        {
            this.repoParticipant = repo;
            this.validatorParticipant = validator;
        }

        public void Save(Participant participant)
        {
            Participant id = this.repoParticipant.FindOne(participant.GetId());
            if (id == null)
                try
                {
                    this.validatorParticipant.Validate(participant);
                    this.repoParticipant.Save(participant);
                }
                catch (ValidationException e)
                {
                    throw new ValidationException(e.Message);
                }
            else throw new RepositoryException("Participantul exista deja");
        }

        public IEnumerable<Participant> FindAll()
        {
            return this.repoParticipant.FindAll();
        }

        public Participant FindOne(int integer)
        {
            Participant participant = this.FindOne(integer);
            if (participant == null)
                throw new RepositoryException("Participant inexistent!");
            else return participant;
        }

        public int Size()
        {
            return this.repoParticipant.Size();
        }

        public int MaxId()
        {
            return this.repoParticipant.MaxId();
        }
    }
}
