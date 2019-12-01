namespace Lab1C_MPP.Domain
{
    class Inscriere:IHasID<int>
    {
        private int IdInscriere;
        private int IdProba;
        private int IdParticipant;

        public Inscriere(int IdPr, int IdPa)
        {
            this.IdInscriere = int.Parse(IdPr.ToString()+IdPa.ToString());
            this.IdProba = IdPr;
            this.IdParticipant = IdPa;
        }

        public int GetId() { return this.IdInscriere; }

        public int GetIdProba() { return this.IdProba; }

        public int GetIdParticipant() { return this.IdParticipant; }
    }
}
