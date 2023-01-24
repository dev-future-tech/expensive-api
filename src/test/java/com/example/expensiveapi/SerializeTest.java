package com.example.expensiveapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class SerializeTest {

    @Test
    public void testExpenseObject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ExpenseDTO dto = new ExpenseDTO();

        dto.setExpenseId(12345L);
        dto.setExpenseName("New Party Balloons");
        dto.setExpenseDescription("Balloons for the New Party!!!!!");
        dto.setExpenseTime(ZonedDateTime.now(ZoneId.of("America/Los_Angeles")));

        StringWriter stringWriter = new StringWriter();
        mapper.writeValue(stringWriter, dto);

        System.out.println(stringWriter);
    }

}
