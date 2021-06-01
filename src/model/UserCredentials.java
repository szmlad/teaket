package model;

public class UserCredentials {
    private String username  = "";
    private String password  = "";
    private String firstName = "";
    private String lastName  = "";
    private Gender gender    = Gender.NA;
    private long birthdate   = 0;

    public UserCredentials() { }

    public UserCredentials(String username, String password, String firstName,
                           String lastName, Gender gender, long birthdate) {
        this.username  = username;
        this.password  = password;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.gender    = gender;
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof UserCredentials)) return false;
        UserCredentials cred = (UserCredentials) that;
        return username.equals(cred.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "UserCredentials { username: %s, password: %s, " +
                "firstName: %s, lastName: %s, gender: %s, birthday: %s }",
                username, password, firstName, lastName, gender, birthdate);
    }

    public String username() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String password() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String firstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String lastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Gender gender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public long birthdate() { return birthdate; }
    public void setBirthdate(long birthdate) { this.birthdate = birthdate; }
}
