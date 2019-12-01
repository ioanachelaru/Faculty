package validatori;

import domain.Participant;

public class ValidatorParticipant implements Validator<Participant> {
    String err="";

    @Override
    public void validate(Participant entity) throws ValidationException {
        if(entity.getNumeParticipant()=="")
            err+="Numele participantului nu poate fi vid!\n";
        if(entity.getVarstaParticipant()<1||entity.getVarstaParticipant()>120)
            err+="Varsta incorecta!\n";

        if(err.length()>0)
            throw new ValidationException(err);
    }
}
