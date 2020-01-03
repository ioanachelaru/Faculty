package Responses;

import Domain.Event;
import java.io.Serializable;
import java.util.List;

public class GetAllEventsAvailableResponse implements Serializable {
    private List<Event> response;

    public GetAllEventsAvailableResponse(List<Event> response) {
        this.response = response;
    }

    public List<Event> getResponse() {
        return response;
    }

    public void setResponse(List<Event> response) {
        this.response = response;
    }
}
