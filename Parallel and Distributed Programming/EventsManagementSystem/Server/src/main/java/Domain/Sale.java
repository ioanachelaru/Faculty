package Domain;

import java.util.Date;
import java.util.List;

public class Sale {
    private Date date_of_sale;
    private int id_sale;
    private List<Seat> seats_sold;

    public Sale(Date date_of_sale, int id_sale, List<Seat> seats_sold) {
        this.date_of_sale = date_of_sale;
        this.id_sale = id_sale;
        this.seats_sold = seats_sold;
    }

    public Date getDate_of_sale() { return date_of_sale; }

    public void setDate_of_sale(Date date_of_sale) { this.date_of_sale = date_of_sale; }

    public int getId_sale() { return id_sale; }

    public void setId_sale(int id_sale) { this.id_sale = id_sale; }

    public List<Seat> getSeats_sold() { return seats_sold; }

    public void setSeats_sold(List<Seat> seats_sold) { this.seats_sold = seats_sold; }
}
