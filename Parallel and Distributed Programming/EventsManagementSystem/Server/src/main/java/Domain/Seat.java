package Domain;

import java.io.Serializable;

public class Seat implements Serializable {
    private String type;
    private String name;
    private Double price;
    private Boolean availability;

    public Seat(String type, String name, Double price, Boolean availability) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.availability = availability;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }

    public Boolean getAvailability() { return availability; }

    public void setAvailability(Boolean availability) { this.availability = availability; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
