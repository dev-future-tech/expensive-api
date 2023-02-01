package com.example.expensiveapi;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExpensiveDataHandler {

    protected RestTemplate template;

    private final Logger log = LoggerFactory.getLogger(ExpensiveDataHandler.class);

    public ExpensiveDataHandler(RestTemplate template) {
        this.template = template;
    }

    public ExpensiveDataHandler() {

    }

    @PrePersist
    protected void onBeforeCreate(Object entity) {
        Expense expense = (Expense) entity;
        log.debug("Saving expense {}", expense.getExpenseName());
        assert template != null;
    }

    @PostPersist
    protected void onAfterSave(Object entity) {
        Expense expense = (Expense) entity;
        log.debug("Created expense with id {}", expense.getExpenseId());
    }

}
