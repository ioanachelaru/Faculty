using SharedDLL.Domain;
using SharedDLL.Utils;
using System.Collections.Generic;

namespace Server.Server
{
    public interface IServer
    {
        void AddInscriere(Inscriere i);
        //void EncreaseWith(int id, int encreaseWith);
        void Logout(IObserver observer);
        int GetMaxId();
        int FindUser(Angajat user, IObserver observer);
        List<ProbaDTO> GetAllProbeDTO();
        List<Participant> GetParticipantsByProba(int idProba);

        Proba FindProba(int idProba);

        void AddParticipant(Participant p);
    }
}
