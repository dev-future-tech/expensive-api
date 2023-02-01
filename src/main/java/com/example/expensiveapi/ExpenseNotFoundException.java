package com.example.expensiveapi;

public class ExpenseNotFoundException extends Exception {

    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
