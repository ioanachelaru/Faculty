using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab1C_MPP.Domain
{
    class Proba : IHasID<int>
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
