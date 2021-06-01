package model;

public class Location {
    private double latitude  = 0;
    private double longitude = 0;
    private Address address  = new Address();

    public Location() {}

    public Location(double latitude, double longitude, Address address) {
        this.latitude  = latitude;
        this.longitude = longitude;
        this.address   = address;
    }

    @Override
    public String toString() {
        return String.format(
                "Location { latitude: %.2f, longitude: %.2f, address: %s }",
                latitude, longitude, address);
    }

    public double latitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double longitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public Address address() { return address; }
    public void setAddress(Address address) { this.address = address; }
}
