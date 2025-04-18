package com.example.unittesting.chapter01.listing02;

public class Customer {

    public boolean purchase(IStore store, Product product, int quantity) {
        if (!store.hasEnoughInventory(product, quantity)) {
            return false;
        }
        store.removeInventory(product, quantity);
        return true;
    }
}
