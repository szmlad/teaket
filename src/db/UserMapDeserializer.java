package db;

import com.google.gson.*;
import model.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UserMapDeserializer
        implements JsonDeserializer<Map<String, User>> {
    public Map<String, User> deserialize(JsonElement json, Type type,
                                         JsonDeserializationContext ctx)
            throws JsonParseException {
        Map<String, User> users = new HashMap<>();
        JsonObject obj = json.getAsJsonObject();

        for (Map.Entry<String, JsonElement> e : obj.entrySet()) {
            UserType ut = UserType.valueOf(
                    e.getValue().getAsJsonObject().get("type").getAsString());
            switch (ut) {
                case ADMIN:
                    users.put(e.getKey(),
                            ctx.deserialize(e.getValue(), Admin.class));
                    break;
                case CUSTOMER:
                    users.put(e.getKey(),
                            ctx.deserialize(e.getValue(), Customer.class));
                    break;
                case SALESPERSON:
                    users.put(e.getKey(),
                            ctx.deserialize(e.getValue(), Salesperson.class));
                    break;
            }
        }

        return users;
    }
}
