package objectprotocol;

import Model.ProbaExtins;

import java.util.List;

public class GetAllProbaExtinsResponse implements Response {
    private List<ProbaExtins> probaExtins;

    public GetAllProbaExtinsResponse(List<ProbaExtins> probaExtins) {
        this.probaExtins = probaExtins;
    }
    public List<ProbaExtins> getProbaExtins() { return probaExtins; }
}
