package Domain;

import java.util.Date;
import java.util.List;

public class Event {
    private int id;
    private Date date;
    private String title;
    private String description;
    private List<Seat> seats;


    public Event(Integer id, Date date, String title, String description, List<Seat> seats){
        this.id = id;
        this.date = date;
        this.title = title;
        this.description = description;
        this.seats = seats;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public List<Seat> getSeats() { return seats; }

    public void setSeats(List<Seat> seats) { this.seats = seats; }
}
