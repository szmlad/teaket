package db;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Location;
import model.Manifestation;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ManifestationDataStore extends DataStore<Manifestation> {
    public ManifestationDataStore(String filepath) {
        super(filepath);
        g = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .create();
    }

    private static class NewManifestationData {
        String id;
        String name;
        String type;
        int seatCount;
        LocalDateTime time;
        double ticketPrice;
        Location location;
        String image;
    }

    public Manifestation newFromJson(String json) {
        NewManifestationData data = g.fromJson(json, new TypeToken<NewManifestationData>() {}.getType());

        return new Manifestation(
                data.id,
                data.name,
                data.type,
                data.seatCount,
                data.time,
                data.ticketPrice,
                data.location,
                data.image
        );
    }

    @Override
    public void fromJson() throws IOException {
        Path p = Paths.get(filepath);
        if (!Files.exists(p)) Files.createFile(p);
        try (FileReader fr = new FileReader(filepath)) {
            data = g.fromJson(fr, new TypeToken<HashMap<String, Manifestation>>() {}.getType());
            if (data == null) data = new HashMap<>();
        }
    }

    @Override
    public void put(Manifestation m) {
        data.put(m.getId(), m);
    }
}
