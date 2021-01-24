package db;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Admin;
import model.Gender;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;

public class AdminDataStore extends DataStore<Admin> {
    public AdminDataStore(String filepath) {
        super(filepath);
        g = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
    }

    private class NewAdminData {
        public String username;
        public String password;
        public String firstName;
        public String lastName;
        public LocalDate birthDate;
        public Gender gender;
    }

    @Override
    public void fromJson() throws IOException {
        Path p = Paths.get(filepath);
        if (!Files.exists(p)) Files.createFile(p);
        try (FileReader fr = new FileReader(filepath)) {
            data = g.fromJson(fr, new TypeToken<HashMap<String, Admin>>() {}.getType());
            if (data == null) data = new HashMap<>();
        }
    }

    public Admin newFromJson(String json) {
        NewAdminData data = g.fromJson(json, new TypeToken<NewAdminData>() {}.getType());

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
}
