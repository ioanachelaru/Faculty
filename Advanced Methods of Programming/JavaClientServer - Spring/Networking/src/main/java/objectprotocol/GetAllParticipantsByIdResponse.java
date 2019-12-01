package objectprotocol;

import Model.Participant;

import java.util.List;

public class GetAllParticipantsByIdResponse implements Response {
    private Iterable<Participant> participants;

    public GetAllParticipantsByIdResponse(Iterable<Participant> participants) { this.participants = participants; }
    public Iterable<Participant> getParticipants() { return this.participants; }
}
