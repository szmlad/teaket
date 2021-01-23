package db;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Customer;
import model.CustomerStatus;
import model.Gender;

import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerDataStore extends DataStore<Customer> {
    public CustomerDataStore(String filepath) {
        super(filepath);
        g = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
    }

    private class CustomerData {
        public String username;
        public String password;
        public String firstName;
        public String lastName;
        public LocalDate birthDate;
        public Gender gender;
    }

    public Customer newFromJson(String json) {
        CustomerData data = g.fromJson(json, new TypeToken<CustomerData>() {}.getType());

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
    public void put(Customer c) {
        data.put(c.getUsername(), c);
    }

    @Override
    public void delete(String key) {
        Customer c = data.get(key);
        if (c == null) return;
        c.setDeleted(true);
    }
}
