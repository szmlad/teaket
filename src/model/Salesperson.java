package model;

import java.util.ArrayList;
import java.util.List;

public class Salesperson implements DBObject {
    private UserCredentials credentials = new UserCredentials();
    private boolean deleted             = false;

    private List<String> events         = new ArrayList<>();

    public Salesperson() { }

    public Salesperson(UserCredentials credentials, List<String> events) {
        this.credentials = credentials;
        this.events      = events;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof Salesperson)) return false;
        Salesperson sp = (Salesperson) that;
        return credentials.equals(sp.credentials);
    }

    @Override
    public int hashCode() {
        return credentials.hashCode();
    }

    public String id() { return credentials.username(); }
    public void setId(String id) { credentials.setUsername(id); }

    public boolean deleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public UserCredentials credentials() { return this.credentials; }
    public void setCredentials(UserCredentials credentials) {
        this.credentials = credentials;
    }

    public List<String> events() { return this.events; }
    public void setEvents(List<String> events) { this.events = events; }
}
