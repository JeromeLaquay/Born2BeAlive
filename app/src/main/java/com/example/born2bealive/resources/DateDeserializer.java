package com.example.born2bealive.resources;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateDeserializer implements JsonDeserializer {

    public static final SimpleDateFormat sServerDateDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat format = DateFormat.getInstance();
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!(json instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }

        try {
            return sServerDateDateFormat.parse(json.getAsString());
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }

}
