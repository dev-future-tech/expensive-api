package com.example.expensiveapi;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class StringToZonedDateTimeConverter extends StdConverter<String, ZonedDateTime> {
    private final DateTimeFormatter format = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    @Override
    public ZonedDateTime convert(String s) {
        return ZonedDateTime.parse(s, format);
    }
}
