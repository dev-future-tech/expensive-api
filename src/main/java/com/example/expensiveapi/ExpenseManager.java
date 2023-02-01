package com.example.expensiveapi;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseManager {

    private final Logger log = LoggerFactory.getLogger(ExpenseManager.class);

    private final ExpenseRepository expenseRepository;


    @Autowired
    public ExpenseManager(ExpenseRepository _repository) {
        this.expenseRepository = _repository;
    }

    public ExpenseDTO addExpense(String name, String description) throws InsertExpenseException {
        var expense = new Expense();
        expense.setExpenseName(name);
        expense.setExpenseDescription(description);
        expense.setExpenseTime(OffsetDateTime.now());

        var saved = this.expenseRepository.save(expense);

        return toDTO(saved);
    }

    public ExpenseDTO getExpenseById(String expenseId) throws ExpenseNotFoundException {
        var expense = this.expenseRepository.findById(expenseId);

        return expense.map(this::toDTO)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
    }

    private ExpenseDTO toDTO(Expense expense) {
        var toReturn = new ExpenseDTO();
        toReturn.setExpenseId(expense.getExpenseId());
        toReturn.setExpenseName(expense.getExpenseName());
        toReturn.setExpenseDescription(expense.getExpenseDescription());
        toReturn.setExpenseTime(ZonedDateTime.of(expense.getExpenseTime().toLocalDateTime(), expense.getExpenseTime().getOffset()));
        return toReturn;
    }

    public List<ExpenseDTO> listExpenses() {

        List<Expense> found = this.expenseRepository.findAll();

        return found.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
