using SharedDLL.Domain;
using System;

namespace SharedDLL.Domain
{
    [Serializable]
    public class ProbaDTO
    {
        private Proba proba;
        private int noInscrisi;

        public ProbaDTO(Proba proba, int no)
        {
            this.proba = proba;
            this.noInscrisi = no;
        }

        public Proba GetProba() { return this.proba; }

        public int GetNoInscrisi() { return this.noInscrisi; }
    }
}
