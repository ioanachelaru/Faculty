package Requests;

import java.io.Serializable;

public class GetAllSeatsForEventRequest implements Serializable {
    private int id_event;

    public GetAllSeatsForEventRequest(int id_event) {
        this.id_event = id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public int getId_event() {
        return id_event;
    }
}
