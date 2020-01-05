package Requests;

import Domain.Seat;
import java.io.Serializable;
import java.util.List;

public class BuyRequest implements Serializable{
    private String clientName;
    private int id_event;
    private String date;
    private List<Seat> seats;

    public BuyRequest(String clientName,int id_event, String date, List<Seat> seats) {
        this.clientName = clientName;
        this.id_event = id_event;
        this.date = date;
        this.seats = seats;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
