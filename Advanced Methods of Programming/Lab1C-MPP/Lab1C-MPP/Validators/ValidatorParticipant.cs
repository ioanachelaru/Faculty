using Lab1C_MPP.Domain;

namespace Lab1C_MPP.Validators
{
    class ValidatorParticipant : IValidator<Participant>
    {
        string err = "";
        public void Validate(Participant entity)
        {
            if(entity.GetNumeParticipant()=="")
                err += "Numele participantului nu poate fi vid!\n";
            if (entity.GetVarstaParticipant() < 1 || entity.GetVarstaParticipant() > 120)
                err += "Varsta incorecta!\n";

            if (err.Length > 0)
                throw new ValidationException(err);
        }
    }
}
