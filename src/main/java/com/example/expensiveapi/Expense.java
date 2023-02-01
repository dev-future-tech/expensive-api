package com.example.expensiveapi;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;

@Entity
@EntityListeners(value = {ExpensiveDataHandler.class})
@Table(name = "expensive_things")
public class Expense {


    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.UUID,generator = "uuid2")
    @Column(name = "expense_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String expenseId;

    @Column(name = "expense_name")
    private String expenseName;

    @Column(name = "expense_description")
    private String expenseDescription;

    @Column( name = "expense_date")
    private OffsetDateTime expenseTime;


    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
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
