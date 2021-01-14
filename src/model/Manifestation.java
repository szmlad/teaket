package model;

import java.time.LocalDateTime;

public class Manifestation {
    private boolean deleted;
    private String id;
    private String name;
    private String type;
    private int seatCount;
    private LocalDateTime time;
    private double ticketPrice;
    private Location location;
    private String image;

    public Manifestation() { }

    public Manifestation(boolean deleted, String id, String name, String type, int seatCount, LocalDateTime time, double ticketPrice, Location location, String image) {
        this.deleted = deleted;
        this.id = id;
        this.name = name;
        this.type = type;
        this.seatCount = seatCount;
        this.time = time;
        this.ticketPrice = ticketPrice;
        this.location = location;
        this.image = image;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
