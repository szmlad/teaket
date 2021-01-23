package model;

import java.time.LocalDate;

public abstract class User {
    protected boolean deleted;
    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected Gender gender;
    protected LocalDate birthDate;

    public User() { }

    public User(boolean deleted, String username, String password, String firstName, String lastName, Gender gender, LocalDate birthDate) {
        this.deleted = deleted;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
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

    public boolean isAdmin() {
        return this instanceof Admin;
    }

    public boolean isCustomer() {
        return this instanceof Customer;
    }

    public boolean isSalesperson() {
        return this instanceof Salesperson;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
