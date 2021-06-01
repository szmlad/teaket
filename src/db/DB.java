package db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.DBObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class DB<T extends DBObject> {
    protected String path;
    protected Map<String, T> data = new HashMap<>();
    protected Gson g = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public DB(String path) {
        this.path = path;
    }

    public String serialize(T value) {
        return g.toJson(value);
    }

    public T deserialize(String json, Class<T> cls) {
        return g.fromJson(json, TypeToken.get(cls).getType());
    }

    public void serializeAll(Class<T> cls) {
        try (FileWriter w = new FileWriter(path)) {
            g.toJson(data, TypeToken.getParameterized(
                    HashMap.class, String.class, cls).getType(), w);
        } catch (IOException e) {
            System.err.printf("Failed to serialize %s\n", cls.getName());
        }
    }

    public void deserializeAll(Class<T> cls) {
        Path p = Paths.get(path);
        try {
            if (!Files.exists(p)) {
                Files.createFile(p);
            }
            try (FileReader r = new FileReader(path)) {
                data = g.fromJson(r, TypeToken.getParameterized(
                                HashMap.class, String.class, cls).getType());
                if (data == null) {
                    data = new HashMap<>();
                }
            }
        } catch (IOException e) {
            System.err.printf("Failed to deserialize %s\n", cls.getName());
            data = new HashMap<>();
        }
    }

    public Stream<T> all() {
        return data.values().stream();
    }

    public Stream<T> allActive() {
        return data.values().stream()
                .filter(v -> !v.deleted());
    }

    public T get(String k) {
        return data.get(k);
    }

    public T getActive(String k) {
        T v = data.get(k);
        if (v == null || v.deleted()) return null;
        return v;
    }
    public void put(T v) {
        data.put(v.id(), v);
    }

    public void delete(String k) {
        T v = data.get(k);
        if (v == null) return;
        v.setDeleted(true);
    }
}
