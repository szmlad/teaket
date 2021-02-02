package db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Customer;
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
        g = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    private static class NewTicketData {
        String manifestationId;
        String buyer;
        TicketType type;
        TicketStatus status;
    }

    public Ticket newFromJson(String json) {
        NewTicketData data = g.fromJson(json, new TypeToken<NewTicketData>() {}.getType());

        String id = String.format("%010d", this.data.size());
        return new Ticket(
                id,
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
    public Ticket singleFromJson(String json) {
        return g.fromJson(json, new TypeToken<Ticket>() {}.getType());
    }

    @Override
    public void put(Ticket t) {
        data.put(t.getId(), t);
    }
}
