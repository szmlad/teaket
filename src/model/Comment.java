package model;

public class Comment implements Deletable {
    private boolean deleted;
    private String id;
    private String authorUsername;
    private String manifestationId;
    private String text;
    private int rating;

    public Comment() {
        this.deleted = false;
        this.id = "";
        this.authorUsername = "";
        this.manifestationId = "";
        this.text = "";
        this.rating = 0;
    }

    public Comment(String id, String authorUsername, String manifestationId, String text, int rating) {
        this.deleted = false;
        this.id = id;
        this.authorUsername = authorUsername;
        this.manifestationId = manifestationId;
        this.text = text;
        this.rating = rating;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getManifestationId() {
        return manifestationId;
    }

    public void setManifestationId(String manifestationId) {
        this.manifestationId = manifestationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
