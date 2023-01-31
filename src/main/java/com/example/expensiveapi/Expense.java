package com.example.expensiveapi;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "expensive_things")
public class Expense {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "expense_id")
    private UUID expenseId;

    @Column(name = "expense_name")
    private String expenseName;

    @Column(name = "expense_description")
    private String expenseDescription;

    @Column( name = "expense_date")
    private OffsetDateTime expenseTime;


    public UUID getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(UUID expenseId) {
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

    public OffsetDateTime getExpenseTime() {
        return expenseTime;
    }

    public void setExpenseTime(OffsetDateTime expenseTime) {
        this.expenseTime = expenseTime;
    }
}
