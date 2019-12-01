using Server.Repo;
using SharedDLL.Domain;
using SharedDLL.Utils;
using System.Collections.Generic;
using System.Runtime.CompilerServices;
using System.Threading;

namespace Server.Server
{
    public class ServerImpl : IServer
    {
        private RepoAngajat repoAngajat;
        private RepoParticipant repoParticipant;
        private RepoProba repoProba;
        private RepoInscriere repoInscriere;

        private List<IObserver> loggedUsers;

        public ServerImpl(RepoAngajat ra, RepoParticipant rpa, RepoProba rpr, RepoInscriere ri)
        {
            this.repoAngajat = ra;
            this.repoParticipant = rpa;
            this.repoProba = rpr;
            this.repoInscriere = ri;
            loggedUsers = new List<IObserver>();
        }

        [MethodImpl(MethodImplOptions.Synchronized)]
        public void AddInscriere(Inscriere i)
        {
            repoInscriere.Save(i);
        }

        [MethodImpl(MethodImplOptions.Synchronized)]
        public void AddParticipant(Participant p)
        {
            repoParticipant.Save(p);
        }

        [MethodImpl(MethodImplOptions.Synchronized)]
        public Proba FindProba(int idProba)
        {
            return repoProba.FindOne(idProba);
        }

        /*
        [MethodImpl(MethodImplOptions.Synchronized)]
        public void EncreaseWith(int id, int encreaseWith)
        {
            mRep.DecreaseTicketCount(id, decreaseWith);
            foreach (var obs in loggedUsers)
            {
                //obs.Update(id, decreaseWith);
                ThreadPool.QueueUserWorkItem((state) =>
                {
                    obs.Update(id, decreaseWith);
                });
            }
        }
        */
        [MethodImpl(MethodImplOptions.Synchronized)]
        public int FindUser(Angajat user, IObserver observer)
        {
            var angajat = repoAngajat.FindOne(user.GetId());
            if (angajat != null)
                if (angajat.GetPasswordAngajat().Equals(user.GetPasswordAngajat()))
                {
                    loggedUsers.Add(observer);
                    return 0;
                }
                else return 2;
            return 1;
        }

        [MethodImpl(MethodImplOptions.Synchronized)]
        public List<ProbaDTO> GetAllProbeDTO()
        {
            IEnumerable<Proba> probe = repoProba.FindAll();
            List<ProbaDTO> probaDTOs = new List<ProbaDTO>();
            foreach(var p in probe)
            {
                int temp = repoProba.NumarInscrieri(p.GetId());
                probaDTOs.Add(new ProbaDTO(p, temp));
            }
            return probaDTOs;
        }
        
        [MethodImpl(MethodImplOptions.Synchronized)]
        public List<Participant> GetParticipantsByProba(int idProba)
        {
            List<Participant> participants = new List<Participant>();
            IEnumerable<Participant> participanti = repoParticipant.FindAll();
            foreach(Participant p in participanti)
            {
                int temp = int.Parse(idProba.ToString() + p.GetId().ToString());
                if(repoInscriere.FindOne(temp) != null)
                {
                    participants.Add(p);
                }
            }
            return participants;
        }
        
        [MethodImpl(MethodImplOptions.Synchronized)]
        public void Logout(IObserver observer)
        {
            loggedUsers.Remove(observer);
        }

        [MethodImpl(MethodImplOptions.Synchronized)]
        public int GetMaxId()
        {
            return repoParticipant.MaxId() + 1;
        }
    }
}
