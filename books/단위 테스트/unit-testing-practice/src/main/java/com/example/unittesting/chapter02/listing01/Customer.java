package com.example.unittesting.chapter02.listing01;

public class Customer {

    public boolean purchase(Store store, Product product, int quantity) {
        if (!store.hasEnoughInventory(product, quantity)) {
            return false;
        }
        store.removeInventory(product, quantity);
        return true;
    }
}
