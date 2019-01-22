package ru.mycompany.restaurantVoting.web.rest.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MyObjectMapper extends ObjectMapper {

    public MyObjectMapper() {
        // For serialization of Java 8 Dates and Times as ISO-8601 format
        registerModule(new JavaTimeModule());
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
