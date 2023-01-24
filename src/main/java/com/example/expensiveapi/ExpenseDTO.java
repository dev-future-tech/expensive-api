package com.example.expensiveapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class ExpenseDTO {

    @JsonProperty("id")
    private Long expenseId;

    @JsonProperty("name")
    private String expenseName;
    @JsonProperty("description")
    private String expenseDescription;

    @JsonProperty("time_of_expense")
    @JsonSerialize(converter = ZonedDateTimeToStringConverter.class)
    @JsonDeserialize(converter = StringToZonedDateTimeConverter.class)
    private ZonedDateTime expenseTime;

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public ZonedDateTime getExpenseTime() {
        return expenseTime;
    }

    public void setExpenseTime(ZonedDateTime expenseTime) {
        this.expenseTime = expenseTime;
    }
}
