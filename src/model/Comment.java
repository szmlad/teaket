package model;

public class Comment {
    private boolean deleted;
    private String authorUsername;
    private String manifestationId;
    private String text;
    private int rating;

    public Comment() { }

    public Comment(String authorUsername, String manifestationId, String text, int rating) {
        this.authorUsername = authorUsername;
        this.manifestationId = manifestationId;
        this.text = text;
        this.rating = rating;
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
