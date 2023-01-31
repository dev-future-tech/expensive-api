package com.example.expensiveapi;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@RepositoryEventHandler()
public class ExpensiveDataHandler {

    @HandleBeforeCreate
    public void handleBeforeCreateExpense(Expense expense) {

    }
}
