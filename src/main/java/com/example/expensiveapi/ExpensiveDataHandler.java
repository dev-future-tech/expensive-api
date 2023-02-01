package com.example.expensiveapi;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpensiveDataHandler {

    private final Logger log = LoggerFactory.getLogger(ExpensiveDataHandler.class);

    @PrePersist
    protected void onBeforeCreate(Object entity) {
        Expense expense = (Expense) entity;
        log.debug("Saving expense {}", expense.getExpenseName());
    }

    @PostPersist
    protected void onAfterSave(Object entity) {
        Expense expense = (Expense) entity;
        log.debug("Created expense with id {}", expense.getExpenseId());
    }

}
