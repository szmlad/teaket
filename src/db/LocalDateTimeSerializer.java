package db;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
    public JsonElement serialize(LocalDateTime datetime, Type typeOfSrc, JsonSerializationContext ctx) {
        return new JsonPrimitive(datetime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
