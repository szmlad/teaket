package model;

public class CustomerStatus {
    private String name     = "";
    private double discount = 1.0;
    private int minPoints   = 0;

    public static final CustomerStatus defaultStatus =
            new CustomerStatus("Običan korisnik", 1.0, 0);

    public CustomerStatus() { }

    public CustomerStatus(String name, double discount, int minPoints) {
        this.name      = name;
        this.discount  = discount;
        this.minPoints = minPoints;
    }

    @Override
    public String toString() {
        return String.format(
                "CustomerStatus { name: %s, discount: %.2f, minPoints: %s }",
                name, discount, minPoints);
    }
}
