package model;

import java.util.UUID;

public class CustomerStatus {
    private String id;
    private String name;
    private double discount;
    private int pointsRequired;
    private static CustomerStatus defaultStatus = new CustomerStatus("Običan korisnik", 1.0, 0);

    public CustomerStatus() { }

    public CustomerStatus(String name, double discount, int pointsRequired) {
        this.id = UUID.randomUUID().toString();

        this.name = name;
        this.discount = discount;
        this.pointsRequired = pointsRequired;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getPointsRequired() {
        return pointsRequired;
    }

    public void setPointsRequired(int pointsRequired) {
        this.pointsRequired = pointsRequired;
    }

    public static CustomerStatus getDefault() {
        return defaultStatus;
    }
}
