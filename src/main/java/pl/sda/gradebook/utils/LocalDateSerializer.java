package pl.sda.gradebook.utils;

import com.google.gson.*;
import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

public class LocalDateSerializer implements JsonSerializer<LocalDate>,
        JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT,
                                 JsonDeserializationContext context) throws JsonParseException {
        return ISODateTimeFormat.basicDate().parseLocalDate(json.getAsString());
    }

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        return new JsonPrimitive(ISODateTimeFormat.basicDate().print(src));
    }
}
