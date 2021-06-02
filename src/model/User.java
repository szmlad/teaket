package model;

public abstract class User implements DBObject {
    protected String username  = "";
    protected boolean deleted  = false;

    protected String password  = "";
    protected String firstName = "";
    protected String lastName  = "";
    protected Gender gender    = Gender.NA;
    protected long birthdate   = 0;
    protected UserType type    = UserType.ADMIN;

    public User() { }

    public User(String username, String password, String firstName,
                String lastName, Gender gender, long birthdate, UserType type) {
        this.username  = username;
        this.password  = password;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.gender    = gender;
        this.birthdate = birthdate;
        this.type      = type;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof User)) return false;
        User u = (User) that;
        return username.equals(u.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    public String id() { return username; }
    public void setId(String id) { this.username = username; }

    public boolean deleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

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

    public UserType type() { return type; }
    public void setType(UserType type) { this.type = type; }
}
