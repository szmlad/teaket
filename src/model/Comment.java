package model;

public class Comment implements DBObject {
    private String id       = "";
    private boolean deleted = false;

    private String authorId = "";
    private String eventId  = "";
    private String text     = "";
    private int rating      = 0;

    public Comment() { }

    public Comment(String id, String authorId,
                   String eventId, String text, int rating) {
        this.deleted  = false;
        this.id       = id;
        this.authorId = authorId;
        this.eventId  = eventId;
        this.text     = text;
        this.rating   = rating;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof Comment)) return false;
        Comment c = (Comment) that;
        return id.equals(c.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "Comment { id: %s, authorId: %s, eventId: %s, " +
                "text: %s, rating: %d, deleted: %s }",
                id, authorId, eventId, text, rating, deleted);
    }

    public String id() { return id; }
    public void setId(String id) { this.id = id; }

    public boolean deleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public String authorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }

    public String eventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String text() { return text; }
    public void setText(String text) { this.text = text; }

    public int rating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}
