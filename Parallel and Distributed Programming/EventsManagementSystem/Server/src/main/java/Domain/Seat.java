package Domain;

public class Seat {
    private String type;
    private Double price;
    private Boolean availability;

    public Seat(String type, Double price, Boolean availability) {
        this.type = type;
        this.price = price;
        this.availability = availability;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }

    public Boolean getAvailability() { return availability; }

    public void setAvailability(Boolean availability) { this.availability = availability; }
}
