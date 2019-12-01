package objectprotocol;

public class GetAllParticipantsByIdRequest implements Request{
    private String id;

    public  GetAllParticipantsByIdRequest(String id) { this.id = id;}

    public String getId() {return this.id;}
}
