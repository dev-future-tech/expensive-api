package com.example.expensiveapi;

public class InsertExpenseException extends Throwable {
    public InsertExpenseException(String s, Exception e) {
        super(s, e);
    }
}
