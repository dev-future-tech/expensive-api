package com.example.expensiveapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@RestController
@RequestMapping(path="/api/expensive")
public class ExpensiveController {

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpenses() {

        List<ExpenseDTO> expenses = LongStream.range(1,10).mapToObj(value -> {
            ExpenseDTO dto = new ExpenseDTO();
            dto.setExpenseId(value);
            dto.setExpenseName(String.format("Expense number %s", value));
            dto.setExpenseDescription(String.format("This is the expense that is number %d", value));
            dto.setExpenseTime(ZonedDateTime.now(ZoneId.of("America/Los_Angeles")));
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(expenses);
    }
}
