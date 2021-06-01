package model;

public class Event implements DBObject {
    private String id          = "";
    private boolean deleted    = false;

    private String name        = "";
    private String type        = "";
    private int seatCount      = 0;
    private long time          = 0;
    private double ticketPrice = 0;
    private Location location  = new Location();
    private String image       = "";

    public Event() {}

    public Event(String id, String name, String type, int seatCount,
                 long time, double ticketPrice, Location location,
                 String image) {
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
        if (!(that instanceof Event)) return false;
        Event m = (Event) that;
        return id.equals(m.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "Event { id: %s, deleted: %s, name: %s, " +
                "type: %s, seatCount: %d, time: %d, ticketPrice: %.2f, " +
                "location: %s }",
                id, deleted, name, type, seatCount,
                time, ticketPrice, location);
    }

    public String id() { return id; }
    public void setId(String id) { this.id = id; }

    public boolean deleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public String name() { return name; }
    public void setName(String name) { this.name = name; }

    public String type() { return type; }
    public void setType(String type) { this.type = type; }

    public int seatCount() { return seatCount; }
    public void setSeatCount(int seatCount) { this.seatCount = seatCount; }

    public long time() { return time; }
    public void setTime(long time) { this.time = time; }

    public double ticketPrice() { return ticketPrice; }
    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Location location() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public String image() { return image; }
    public void setImage(String image) { this.image = image; }
}
