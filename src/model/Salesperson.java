package model;

import java.time.LocalDate;
import java.util.List;

public class Salesperson extends User {
    private List<Integer> manifestations;

    public Salesperson() {
        super();
    }

    public Salesperson(boolean deleted, String username, String password, String firstName, String lastName, Gender gender, LocalDate birthDate, List<Integer> manifestations) {
        super(username, password, firstName, lastName, gender, birthDate);
        this.manifestations = manifestations;
    }

    public List<Integer> getManifestations() {
        return manifestations;
    }

    public void setManifestations(List<Integer> manifestations) {
        this.manifestations = manifestations;
    }
}
