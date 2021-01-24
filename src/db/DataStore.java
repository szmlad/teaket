package db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Deletable;
import util.Func;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public abstract class DataStore<T extends Deletable> {
    protected String filepath;
    protected Map<String, T> data;
    protected Gson g;

    public DataStore(String filepath) {
        this.data = new HashMap<>();
        this.filepath = filepath;
    }

    public abstract void fromJson() throws IOException;

    public HashMap<String, T> fromJson(String json) {
        return g.fromJson(json, new TypeToken<HashMap<String, T>>() {}.getType());
    }

    public T singleFromJson(String json) {
        return g.fromJson(json, new TypeToken<T>() {}.getType());
    }

    public void toJson() throws IOException {
        try (FileWriter fw = new FileWriter(filepath)) {
            g.toJson(data, fw);
        }
    }

    public String toJson(Map<String, T> d) {
        return g.toJson(d, new TypeToken<Map<String, T>>() {}.getType());
    }

    public String singleToJson(T value) {
        return g.toJson(value);
    }

    public Stream<T> values() {
        return data.values().stream();
    }

    public Stream<T> active() {
        return data.values().stream().filter(Func.not(T::getDeleted));
    }

    public T get(String key) {
        return data.get(key);
    }

    public T getActive(String key) {
        T value = data.get(key);
        if (value == null) return null;
        if (value.getDeleted()) return null;
        return value;
    }

    public void delete(String key) {
        T value = data.get(key);
        if (value == null) return;
        value.setDeleted(true);
    }

    public abstract void put(T value);
}
