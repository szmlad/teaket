package model;

import java.util.UUID;

public class CustomerStatus {
    private String name;
    private double discount;
    private int pointsRequired;

    private static final CustomerStatus defaultStatus =
            new CustomerStatus("Običan korisnik", 1.0, 0);

    public CustomerStatus() {
        this.name = "";
        this.discount = 0;
        this.pointsRequired = 0;
    }

    public CustomerStatus(String name, double discount, int pointsRequired) {
        this.name = name;
        this.discount = discount;
        this.pointsRequired = pointsRequired;
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
