package db;

import com.google.gson.GsonBuilder;
import model.User;

import java.util.HashMap;

public class UserDB extends DB<User> {
    public UserDB(String path) {
        super(path);
        g = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserSerializer())
                .registerTypeAdapter(User.class, new UserDeserializer())
                .registerTypeAdapter(HashMap.class, new UserMapSerializer())
                .registerTypeAdapter(HashMap.class, new UserMapDeserializer())
                .create();
    }
}
