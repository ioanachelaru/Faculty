package Responses;

import Domain.Seat;
import java.io.Serializable;
import java.util.List;

public class GetAllSeatsAvailableResponse implements Serializable {
    private List<Seat> response;

    public GetAllSeatsAvailableResponse(List<Seat> response) {
        this.response = response;
    }

    public List<Seat> getResponse() {
        return response;
    }

    public void setResponse(List<Seat> response) {
        this.response = response;
    }
}
