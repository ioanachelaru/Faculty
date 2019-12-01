package objectprotocol;

import Model.Participant;

public class AddParticipantRequest implements Request {
    private Participant participant;

    public AddParticipantRequest(Participant participant) {
        this.participant = participant;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
}
