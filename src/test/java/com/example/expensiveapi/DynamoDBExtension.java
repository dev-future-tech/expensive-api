package com.example.expensiveapi;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.model.*;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DynamoDBExtension implements BeforeAllCallback, AfterEachCallback {

    private final Logger log = LoggerFactory.getLogger(DynamoDBExtension.class);

    private DynamoDB dynamoDB;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        log.info("Running DynamoDB creation");
        ApplicationContext context = SpringExtension.getApplicationContext(extensionContext);
        AmazonDynamoDB amazonDynamoDb = context.getBean(AmazonDynamoDB.class);
        this.dynamoDB = new DynamoDB(amazonDynamoDb);

        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("expense_id").withAttributeType("S"));

        List<KeySchemaElement> keySchema = new ArrayList<>();
        keySchema.add(new KeySchemaElement().withAttributeName("expense_id").withKeyType(KeyType.HASH));

        CreateTableRequest request = new CreateTableRequest().withTableName("expensive-things").withKeySchema(keySchema)
                .withAttributeDefinitions(attributeDefinitions).withProvisionedThroughput(
                        new ProvisionedThroughput().withReadCapacityUnits(5L).withWriteCapacityUnits(6L));

        try {
            Table table = dynamoDB.createTable(request);
            table.waitForActive();
            log.info("Table expensive-things created!");
        } catch(Exception e) {
            System.out.println("Error creating table: expensive-things");
            e.printStackTrace(System.out);
        }

    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {

        log.info("Truncating the expensive-things table...");
        Table table = this.dynamoDB.getTable("expensive-things");
        ScanSpec spec = new ScanSpec();
        ItemCollection<ScanOutcome> items = table.scan(spec);
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            String hashKey = item.getString("expense_id");
            PrimaryKey key = new PrimaryKey("expense_id", hashKey);
            table.deleteItem(key);
            System.out.printf("Deleted item with key: %s\n", hashKey);
        }
    }
}
