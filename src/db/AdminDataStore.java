package db;

import com.google.gson.GsonBuilder;
import model.Admin;

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
