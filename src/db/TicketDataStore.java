package db;

import com.google.gson.Gson;
import model.Ticket;

public class TicketDataStore extends DataStore<Ticket> {
    public TicketDataStore(String filepath) {
        super(filepath);
        g = new Gson();
    }

    @Override
    public void put(Ticket t) {
        data.put(t.getId(), t);
    }

    @Override
    public void delete(String key) {
        Ticket t = data.get(key);
        if (t == null) return;
        t.setDeleted(true);
    }
}
