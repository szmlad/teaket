package model;

public class Address {
    private String street = "";
    private String city   = "";
    private int zipCode   = 0;

    public Address() { }

    public Address(String street, String city, int zipCode) {
        this.street  = street;
        this.city    = city;
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return String.format(
                "Address { street: %s, city: %s, zipCode: %d }",
                street, city, zipCode);
    }

    public String street() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String city() { return city; }
    public void setCity(String city) { this.city = city; }

    public int zipCode() { return zipCode; }
    public void setZipCode(int zipCode) { this.zipCode = zipCode; }
}
