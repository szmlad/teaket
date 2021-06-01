package model;

public class Ticket implements DBObject {
    private String id           = "";
    private boolean deleted     = false;

    private String eventId      = "";
    private String ownerId      = "";
    private TicketType type     = TicketType.REGULAR;
    private TicketStatus status = TicketStatus.ABANDONED;

    public Ticket() { }

    public Ticket(String id, String eventId, String ownerId,
                  TicketType type, TicketStatus status) {
        this.deleted = false;
        this.id      = id;
        this.eventId = eventId;
        this.ownerId = ownerId;
        this.type    = type;
        this.status  = status;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof Ticket)) return false;
        Ticket t = (Ticket) that;
        return id.equals(t.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Ticket { id: %s, eventId: %s, ownerId: %s, " +
                        "type: %s, status: %s, deleted: %s }",
                id, eventId, ownerId, type, status, deleted);
    }

    public String id() { return id; }
    public void setId(String id) { this.id = id; }

    public boolean deleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public String eventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String ownerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public TicketType type() { return type; }
    public void setType(TicketType type) { this.type = type; }

    public TicketStatus status() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }
}
