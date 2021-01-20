package db;

import com.google.gson.GsonBuilder;
import model.Customer;

import java.time.LocalDate;

public class CustomerDataStore extends DataStore<Customer> {
    public CustomerDataStore(String filepath) {
        super(filepath);
        g = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
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
