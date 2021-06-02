package db;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import model.*;

import java.lang.reflect.Type;

public class UserDeserializer implements JsonDeserializer<User> {
    public User deserialize(JsonElement json, Type type,
                            JsonDeserializationContext ctx)
            throws JsonParseException {
        UserType ut = UserType.valueOf(
                json.getAsJsonObject().get("type").getAsString());
        switch (ut) {
            case ADMIN:       return ctx.deserialize(json, Admin.class);
            case CUSTOMER:    return ctx.deserialize(json, Customer.class);
            case SALESPERSON: return ctx.deserialize(json, Salesperson.class);
        }

        return null;
    }
}
