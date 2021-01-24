package db;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Location;
import model.Manifestation;

import java.time.LocalDateTime;

public class ManifestationDataStore extends DataStore<Manifestation> {
    public ManifestationDataStore(String filepath) {
        super(filepath);
        g = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateDeserializer())
                .create();
    }

    private class NewManifestationData {
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
    public void put(Manifestation m) {
        data.put(m.getId(), m);
    }
}
