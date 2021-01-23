package model;

public class Ticket implements Deletable {
    private transient boolean deleted;
    private String id;
    private String manifestationId;
    private String buyer;
    private TicketType type;
    private TicketStatus status;

    public Ticket() { }

    public Ticket(String id, String manifestationId, String buyer, TicketType type, TicketStatus status) {
        this.deleted = false;
        this.id = id;
        this.manifestationId = manifestationId;
        this.buyer = buyer;
        this.type = type;
        this.status = status;
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

    public String getManifestationId() {
        return manifestationId;
    }

    public void setManifestationId(String manifestationId) {
        this.manifestationId = manifestationId;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
