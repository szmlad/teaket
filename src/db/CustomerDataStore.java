package db;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import model.Admin;
import model.Customer;
import model.CustomerStatus;
import model.Gender;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomerDataStore extends DataStore<Customer> {
    public CustomerDataStore(String filepath) {
        super(filepath);
        g = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
    }

    private static class NewCustomerData {
        public String username;
        public String password;
        public String firstName;
        public String lastName;
        public LocalDate birthDate;
        public Gender gender;
    }

    public Customer newFromJson(String json) {
        NewCustomerData data = g.fromJson(json, new TypeToken<NewCustomerData>() {}.getType());

        return new Customer(
                data.username,
                data.password,
                data.firstName,
                data.lastName,
                data.gender,
                data.birthDate,
                new ArrayList<>(),
                CustomerStatus.getDefault(),
                0
        );
    }

    @Override
    public void fromJson() throws IOException {
        Path p = Paths.get(filepath);
        if (!Files.exists(p)) Files.createFile(p);
        try (FileReader fr = new FileReader(filepath)) {
            data = g.fromJson(fr, new TypeToken<HashMap<String, Customer>>() {}.getType());
            if (data == null) data = new HashMap<>();
        }
    }

    public String withType(Customer c) {
        JsonObject o = g.toJsonTree(c).getAsJsonObject();
        o.addProperty("type", "customer");
        return g.toJson(o);
    }

    @Override
    public Customer singleFromJson(String json) {
        return g.fromJson(json, new TypeToken<Customer>() {}.getType());
    }

    @Override
    public void put(Customer c) {
        data.put(c.getUsername(), c);
    }
}
