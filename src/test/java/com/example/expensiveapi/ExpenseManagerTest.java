package com.example.expensiveapi;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
@SpringBootTest
@ExtendWith(DynamoDBExtension.class)
public class ExpenseManagerTest {

    @Container
    static final GenericContainer dynamoDb = new GenericContainer("amazon/dynamodb-local:1.13.2")
            .withCommand("-jar DynamoDBLocal.jar -inMemory -sharedDb")
            .withExposedPorts(8000);

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("amazon.aws.endpoint", () -> String.format("http://%s:%s", dynamoDb.getContainerIpAddress(), dynamoDb.getFirstMappedPort()));
        registry.add("amazon.aws.region", () -> "us-east-1");
        registry.add("amazon.aws.accessKey", () -> "hello");
        registry.add("amazon.aws.secretKey", () -> "superman");
    }
    private final Logger log = LoggerFactory.getLogger(ExpenseManagerTest.class);

    @Autowired
    ExpenseManager manager;

    @Autowired
    AmazonDynamoDB amazonDynamoDB;

    @Test
    public void testInsertExpense() {

        try {
            this.manager.addExpense("Balloons", "Birthday balloons for the anniversary");
        } catch (InsertExpenseException iee) {
            log.error(iee.getMessage(), iee);
        }
    }

    @Test
    public void testGetItems() {
        try {
            this.manager.addExpense("Balloons", "Birthday balloons for the anniversary");
        } catch (InsertExpenseException iee) {
            log.error(iee.getMessage(), iee);
        }
        List<ExpenseDTO> expenses = this.manager.listExpenses();

        Assertions.assertThat(expenses).hasSize(1);

        expenses.forEach(expense -> {
            log.info("ExpenseId: {}, ExpenseName: {}, ExpenseDescription: {}", expense.getExpenseId(),
                    expense.getExpenseName(), expense.getExpenseDescription());
        });
    }
}
