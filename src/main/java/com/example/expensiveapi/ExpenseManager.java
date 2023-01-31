package com.example.expensiveapi;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExpenseManager {

    private final Logger log = LoggerFactory.getLogger(ExpenseManager.class);

    private final AmazonDynamoDB amazonDynamoDB;

    private final String TABLE_NAME = "expensive-things";


    @Autowired
    public ExpenseManager(AmazonDynamoDB _amazonDynamoDB) {
        this.amazonDynamoDB = _amazonDynamoDB;
    }

    public void addExpense(String name, String description) throws InsertExpenseException {
        HashMap<String,AttributeValue> item_values =
                new HashMap<>();
        item_values.put("expense_id", new AttributeValue(UUID.randomUUID().toString()));
        item_values.put("expense_name", new AttributeValue(name));
        item_values.put("expense_description", new AttributeValue(description));

        try {
            this.amazonDynamoDB.putItem(TABLE_NAME, item_values);
        } catch (Exception e) {
            log.error("Error inserting into the expensive-things table", e);
            throw new InsertExpenseException("Error inserting into the expensive-things table", e);
        }
    }

    public List<ExpenseDTO> listExpenses() {
        List<ExpenseDTO> results = new ArrayList<>();

        ScanRequest scanRequest = new ScanRequest()
                .withTableName("expensive-things");
        ScanResult result = amazonDynamoDB.scan(scanRequest);
        for (Map<String, AttributeValue> item : result.getItems()){
            Set<String> it = item.keySet();

            String name = item.get("expense_name").getS();
            String description = item.get("expense_description").getS();
            String expenseId = item.get("expense_id").getS();

            ExpenseDTO dto = new ExpenseDTO();
            dto.setExpenseId(expenseId);
            dto.setExpenseName(name);
            dto.setExpenseDescription(description);
            results.add(dto);

        }
        return results;
    }
}
