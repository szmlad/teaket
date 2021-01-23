package db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Deletable;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public void fromJson() throws IOException {
        Path p = Paths.get(filepath);
        if (!Files.exists(p)) Files.createFile(p);
        try (FileReader fr = new FileReader(filepath)) {
            data = g.fromJson(fr, new TypeToken<HashMap<String, T>>() {}.getType());
            if (data == null) data = new HashMap<>();
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

    public String toJson(Map<String, T> d) {
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
