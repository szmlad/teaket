package db;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Admin;
import model.Customer;
import model.Salesperson;
import model.User;

import java.lang.reflect.Type;
import java.util.Map;

public class UserMapSerializer implements JsonSerializer<Map<String, User>> {
    public JsonElement serialize(Map<String, User> src, Type type,
                                 JsonSerializationContext ctx) {
        if (src == null) {
            return null;
        }

        JsonObject obj = new JsonObject();
        src.forEach((id, user) -> {
            switch (user.type()) {
                case ADMIN:
                    obj.add(id, ctx.serialize(user, Admin.class));
                    break;
                case CUSTOMER:
                    obj.add(id, ctx.serialize(user, Customer.class));
                    break;
                case SALESPERSON:
                    obj.add(id, ctx.serialize(user, Salesperson.class));
                    break;
            }
        });

        return obj;
    }
}
