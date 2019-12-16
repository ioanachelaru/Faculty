package Domain;

import java.util.List;

public class Sale {
    private int id_sale;
    private int id_event;
    private String date_of_sale;
    private List<Seat> seats_sold;

    public Sale(int id_sale, int id_event,String date_of_sale, List<Seat> seats_sold) {
        this.id_sale = id_sale;
        this.id_event = id_event;
        this.date_of_sale = date_of_sale;
        this.seats_sold = seats_sold;
    }

    public String getDate_of_sale() { return date_of_sale; }

    public void setDate_of_sale(String date_of_sale) { this.date_of_sale = date_of_sale; }

    public int getId_sale() { return id_sale; }

    public void setId_sale(int id_sale) { this.id_sale = id_sale; }

    public List<Seat> getSeats_sold() { return seats_sold; }

    public void setSeats_sold(List<Seat> seats_sold) { this.seats_sold = seats_sold; }

    public int getId_event() { return id_event; }

    public void setId_event(int id_event) { this.id_event = id_event; }
}
