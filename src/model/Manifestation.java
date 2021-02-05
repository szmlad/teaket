package model;

import java.time.LocalDateTime;

public class Manifestation implements Deletable {
    private boolean deleted;
    private String id;
    private String name;
    private String type;
    private int seatCount;
    private LocalDateTime time;
    private double ticketPrice;
    private Location location;
    private String image;

    public Manifestation() {
        this.deleted = false;
        this.id = "";
        this.name = "";
        this.type = "";
        this.seatCount = 0;
        this.time = LocalDateTime.now();
        this.ticketPrice = 0;
        this.location = new Location();
        this.image = "";
    }

    public Manifestation(String id, String name, String type, int seatCount, LocalDateTime time, double ticketPrice, Location location, String image) {
        this.deleted = false;
        this.id = id;
        this.name = name;
        this.type = type;
        this.seatCount = seatCount;
        this.time = time;
        this.ticketPrice = ticketPrice;
        this.location = location;
        this.image = image;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof Manifestation)) return false;
        Manifestation m = (Manifestation) that;
        return id.equals(m.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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
