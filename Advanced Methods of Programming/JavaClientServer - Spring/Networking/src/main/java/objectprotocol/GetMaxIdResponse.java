package objectprotocol;

public class GetMaxIdResponse implements Response {
    private Integer id;

    public GetMaxIdResponse(Integer id) { this.id = id; }
    public Integer getId() { return this.id; }
}
