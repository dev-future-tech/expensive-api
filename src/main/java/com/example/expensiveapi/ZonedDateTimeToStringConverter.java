package com.example.expensiveapi;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeToStringConverter extends StdConverter<ZonedDateTime, String> {

    private final DateTimeFormatter format = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @Override
    public String convert(ZonedDateTime value) {
        return value.format(format);
    }

}
