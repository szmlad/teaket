package db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public abstract class DataStore<T> {
    protected String filepath;
    protected HashMap<String, T> data = new HashMap<>();
    protected Gson g;

    public DataStore(String filepath) {
        this.filepath = filepath;
    }

    public void fromJson() throws IOException {
        try (FileReader fr = new FileReader(filepath)) {
            data = g.fromJson(fr, new TypeToken<HashMap<String, T>>() {}.getType());
        }
    }

    public void toJson() throws IOException {
        try (FileWriter fw = new FileWriter(filepath)) {
            g.toJson(data, fw);
        }
    }

    public T get(String key) {
        return data.get(key);
    }

    public abstract void put(T value);
    public abstract void delete(String key);
}
