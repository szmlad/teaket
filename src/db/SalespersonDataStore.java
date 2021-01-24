package db;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Gender;
import model.Salesperson;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class SalespersonDataStore extends DataStore<Salesperson> {
    public SalespersonDataStore(String filepath) {
        super(filepath);
        g = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
    }

    private static class NewSalespersonData {
        public String username;
        public String password;
        public String firstName;
        public String lastName;
        public LocalDate birthDate;
        public Gender gender;
    }

    public Salesperson newFromJson(String json) {
        NewSalespersonData data = g.fromJson(json, new TypeToken<NewSalespersonData>() {}.getType());

        return new Salesperson(
                data.username,
                data.password,
                data.firstName,
                data.lastName,
                data.gender,
                data.birthDate,
                new ArrayList<>()
        );
    }

    @Override
    public void fromJson() throws IOException {
        Path p = Paths.get(filepath);
        if (!Files.exists(p)) Files.createFile(p);
        try (FileReader fr = new FileReader(filepath)) {
            data = g.fromJson(fr, new TypeToken<HashMap<String, Salesperson>>() {}.getType());
            if (data == null) data = new HashMap<>();
        }
    }

    @Override
    public void put(Salesperson s) {
        data.put(s.getUsername(), s);
    }
}
