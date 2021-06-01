package model;

public class Admin implements DBObject {
    private UserCredentials credentials = new UserCredentials();
    boolean deleted                     = false;

    public Admin() { }

    public Admin(UserCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof Admin)) return false;
        Admin ad = (Admin) that;
        return credentials.equals(ad.credentials);
    }

    @Override
    public int hashCode() {
        return credentials.hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "Admin { cred: %s, deleted: %s }",
                credentials, deleted);
    }

    public String id() { return credentials.username(); }
    public void setId(String id) { credentials.setUsername(id); }

    public boolean deleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public UserCredentials credentials() { return credentials; }
    public void setCredentials(UserCredentials cred) {
        this.credentials = cred;
    }
}
