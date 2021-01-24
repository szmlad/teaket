package db;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Gender;
import model.Salesperson;

import java.time.LocalDate;
import java.util.ArrayList;

public class SalespersonDataStore extends DataStore<Salesperson> {
    public SalespersonDataStore(String filepath) {
        super(filepath);
        g = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
    }

    private class SalespersonData {
        public String username;
        public String password;
        public String firstName;
        public String lastName;
        public LocalDate birthDate;
        public Gender gender;
    }

    public Salesperson newFromJson(String json) {
        SalespersonData data = g.fromJson(json, new TypeToken<SalespersonData>() {}.getType());

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
    public void put(Salesperson s) {
        data.put(s.getUsername(), s);
    }
}
