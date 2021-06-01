package model;

public interface DBObject {
    String id();
    void setId(String id);

    boolean deleted();
    void setDeleted(boolean deleted);
}
