package db;

import com.google.gson.Gson;
import model.Manifestation;

public class ManifestationDataStore extends DataStore<Manifestation> {
    public ManifestationDataStore(String filepath) {
        super(filepath);
        g = new Gson();
    }

    @Override
    public void put(Manifestation m) {
        data.put(m.getId(), m);
    }

    @Override
    public void delete(String key) {
        Manifestation m = data.get(key);
        if (m == null) return;
        m.setDeleted(true);
    }
}
