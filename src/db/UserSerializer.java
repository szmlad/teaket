package db;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Admin;
import model.Customer;
import model.Salesperson;
import model.User;

import java.lang.reflect.Type;

public class UserSerializer implements JsonSerializer<User> {
    public JsonElement serialize(User src, Type type,
                                 JsonSerializationContext ctx) {
        if (src == null) {
            return null;
        }

        switch (src.type()) {
            case ADMIN:       return ctx.serialize(src, Admin.class);
            case CUSTOMER:    return ctx.serialize(src, Customer.class);
            case SALESPERSON: return ctx.serialize(src, Salesperson.class);
        }

        return null;
    }
}
