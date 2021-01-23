package db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public abstract class DataStore<T> {
    protected String filepath;
    protected HashMap<String, T> data = new HashMap<>();
    protected Gson g;

    public DataStore(String filepath) {
        this.filepath = filepath;
    }

    public void fromJson() throws IOException {
        Path p = Paths.get(filepath);
        if (!Files.exists(p)) Files.createFile(p);
        try (FileReader fr = new FileReader(filepath)) {
            data = g.fromJson(fr, new TypeToken<HashMap<String, T>>() {}.getType());
        }
    }

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

    public String toJson(HashMap<String, T> d) {
        return g.toJson(d);
    }

    public String singleToJson(T value) {
        return g.toJson(value);
    }

    public Stream<T> values() {
        return data.values().stream();
    }

    public T get(String key) {
        return data.get(key);
    }

    public abstract void put(T value);
    public abstract void delete(String key);
}
