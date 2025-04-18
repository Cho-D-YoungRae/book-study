package com.example.unittesting.chapter01.listing01;

import java.util.HashMap;
import java.util.Map;

public class Store {

    private final Map<Product, Integer> inventory = new HashMap<>();

    public boolean hasEnoughInventory(Product product, int quantity) {
        return getInventory(product) >= quantity;
    }

    public void removeInventory(Product product, int quantity) {
        if (!hasEnoughInventory(product, quantity)) {
            throw new IllegalArgumentException("Not enough inventory");
        }
        inventory.put(product, getInventory(product) - quantity);
    }

    public void addInventory(Product product, int quantity) {
        inventory.put(product, getInventory(product) + quantity);
    }

    public int getInventory(Product product) {
        return inventory.getOrDefault(product, 0);
    }
}
