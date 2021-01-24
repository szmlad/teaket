package db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Ticket;
import model.TicketStatus;
import model.TicketType;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class TicketDataStore extends DataStore<Ticket> {
    public TicketDataStore(String filepath) {
        super(filepath);
        g = new Gson();
    }

    private static class NewTicketData {
        String id;
        String manifestationId;
        String buyer;
        TicketType type;
        TicketStatus status;
    }

    public Ticket newFromJson(String json) {
        NewTicketData data = g.fromJson(json, new TypeToken<NewTicketData>() {}.getType());

        return new Ticket(
                data.id,
                data.manifestationId,
                data.buyer,
                data.type,
                data.status
        );
    }

    @Override
    public void fromJson() throws IOException {
        Path p = Paths.get(filepath);
        if (!Files.exists(p)) Files.createFile(p);
        try (FileReader fr = new FileReader(filepath)) {
            data = g.fromJson(fr, new TypeToken<HashMap<String, Ticket>>() {}.getType());
            if (data == null) data = new HashMap<>();
        }
    }

    @Override
    public void put(Ticket t) {
        data.put(t.getId(), t);
    }
}
