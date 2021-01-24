package db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Ticket;
import model.TicketStatus;
import model.TicketType;

public class TicketDataStore extends DataStore<Ticket> {
    public TicketDataStore(String filepath) {
        super(filepath);
        g = new Gson();
    }

    private class TicketData {
        String id;
        String manifestationId;
        String buyer;
        TicketType type;
        TicketStatus status;
    }

    public Ticket newFromJson(String json) {
        TicketData data = g.fromJson(json, new TypeToken<TicketData>() {}.getType());

        return new Ticket(
                data.id,
                data.manifestationId,
                data.buyer,
                data.type,
                data.status
        );
    }

    @Override
    public void put(Ticket t) {
        data.put(t.getId(), t);
    }
}
