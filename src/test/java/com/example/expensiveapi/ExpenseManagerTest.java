package com.example.expensiveapi;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@SpringBootTest
public class ExpenseManagerTest {

    private final Logger log = LoggerFactory.getLogger(ExpenseManagerTest.class);

    @Autowired
    ExpenseManager manager;

    @Test
    @Transactional
    public void testInsertExpense() {

        try {
            var expenseSaved = this.manager.addExpense("Balloons", "Birthday balloons for the anniversary");

            var found = this.manager.getExpenseById(expenseSaved.getExpenseId());

            assertThat(expenseSaved).isNotNull();
            assertThat(expenseSaved.getExpenseName()).isEqualTo(found.getExpenseName());
            assertThat(expenseSaved.getExpenseId()).isEqualTo(found.getExpenseId());

        } catch (InsertExpenseException | ExpenseNotFoundException iee) {
            log.error(iee.getMessage(), iee);
        }
    }

    @Test
    @Transactional
    public void testGetItems() {
        try {
            log.debug("Adding test expense...");
            this.manager.addExpense("Balloons", "Birthday balloons for the anniversary");
        } catch (InsertExpenseException iee) {
            log.error(iee.getMessage(), iee);
        }

        log.debug("Listing out the expenses that are in the table...");
        List<ExpenseDTO> expenses = this.manager.listExpenses();

        Assertions.assertThat(expenses).hasSize(1);

        expenses.forEach(expense -> {
            log.info("ExpenseId: {}, ExpenseName: {}, ExpenseDescription: {}", expense.getExpenseId(),
                    expense.getExpenseName(), expense.getExpenseDescription());
        });
    }
}
