package model;

public class Admin extends User {
    public Admin() {
        super();
        this.type = UserType.ADMIN;
    }

    public Admin(String username, String password, String firstName,
                 String lastName, Gender gender, long birthdate) {
        super(username, password, firstName,
                lastName, gender, birthdate, UserType.ADMIN);
    }

    @Override
    public String toString() {
        return String.format(
                "Admin { username: %s, password: %s, firstName: %s, " +
                "lastName: %s, gender: %s, birthdate: %d }",
                username, password, firstName, lastName, gender, birthdate);
    }
}