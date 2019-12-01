using System;

namespace SharedDLL.Domain
{
    [Serializable]
    public class Participant : IHasID<int>
    {
        private int IdParticipant;
        private string NumeParticipant;
        private int VarstaParticipant;

        public Participant(int Id,string Nume,int Varsta)
        {
            this.IdParticipant = Id;
            this.NumeParticipant = Nume;
            this.VarstaParticipant = Varsta;
        }
        
        public int GetId() { return this.IdParticipant; }

        public string GetNumeParticipant() { return this.NumeParticipant; }

        public int GetVarstaParticipant() { return this.VarstaParticipant; }
    }
}