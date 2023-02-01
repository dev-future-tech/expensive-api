package com.example.expensiveapi;

import com.amazonaws.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@RestController
@RequestMapping(path="/api/expensive")
public class ExpensiveController {

    private final ExpenseManager expenseManager;

    @Autowired
    public ExpensiveController(ExpenseManager _manager) {
        this.expenseManager = _manager;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpenses() {
        List<ExpenseDTO> expenses = this.expenseManager.listExpenses();

        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity<Void> insertNewExpense(@RequestParam("expense_name") String name, @RequestParam("expense_description") String description) {

        try {
            var created = this.expenseManager.addExpense(name, description);
            return ResponseEntity.created(URI.create(String.format("/api/expensive/%s", created.getExpenseId().toString()))).build();
        } catch(InsertExpenseException iee) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
