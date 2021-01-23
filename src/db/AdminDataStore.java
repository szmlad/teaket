package db;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Admin;
import model.Gender;

import java.time.LocalDate;

public class AdminDataStore extends DataStore<Admin> {
    public AdminDataStore(String filepath) {
        super(filepath);
        g = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
    }

    private class AdminData {
        public String username;
        public String password;
        public String firstName;
        public String lastName;
        public LocalDate birthDate;
        public Gender gender;
    }

    public Admin newFromJson(String json) {
        AdminData data = g.fromJson(json, new TypeToken<AdminData>() {}.getType());

        return new Admin(
                data.username,
                data.password,
                data.firstName,
                data.lastName,
                data.gender,
                data.birthDate
        );
    }

    @Override
    public void put(Admin a) {
        data.put(a.getUsername(), a);
    }

    @Override
    public void delete(String key) {
        Admin a = data.get(key);
        if (a == null) return;
        a.setDeleted(true);
    }
}
