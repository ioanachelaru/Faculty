using System;

namespace SharedDLL.Domain
{
    [Serializable]
    public class Proba : IHasID<int>
    {
        private int IdProba;
        private string Stil;
        private int Distanta;
       public Proba(int Id, string St, int Dist)
        {
            this.IdProba = Id;
            this.Stil = St;
            this.Distanta = Dist;
        }

        public int GetId() { return this.IdProba; }

        public string GetStil() { return this.Stil; }

        public int GetDistanta() { return this.Distanta; }
    }
}
