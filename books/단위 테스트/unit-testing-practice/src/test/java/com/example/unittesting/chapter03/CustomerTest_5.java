package com.example.unittesting.chapter03;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerTest_5 extends IntegrationTest {

    @Test
    void purchase_succeeds_when_enough_inventory() {
        // Database 사용
    }
}

abstract class IntegrationTest {
    protected Database database;

    @BeforeEach
    void setUp() {
        database = new Database();
    }

    @AfterEach
    void tearDown() {
        database.dispose();
    }
}

class Database {
    public void dispose() {

    }
}
