package db;

import com.google.gson.GsonBuilder;
import model.Salesperson;

import java.time.LocalDate;

public class SalespersonDataStore extends DataStore<Salesperson> {
    public SalespersonDataStore(String filepath) {
        super(filepath);
        g = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
    }

    @Override
    public void put(Salesperson s) {
        data.put(s.getUsername(), s);
    }

    @Override
    public void delete(String key) {
        Salesperson s = data.get(key);
        if (s == null) return;
        s.setDeleted(true);
    }
}
