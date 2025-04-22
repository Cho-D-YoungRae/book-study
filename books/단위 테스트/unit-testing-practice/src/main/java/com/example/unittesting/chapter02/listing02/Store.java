package com.example.unittesting.chapter02.listing02;

import java.util.HashMap;
import java.util.Map;

public class Store implements IStore {

    private final Map<Product, Integer> inventory = new HashMap<>();

    @Override
    public boolean hasEnoughInventory(Product product, int quantity) {
        return getInventory(product) >= quantity;
    }

    @Override
    public void removeInventory(Product product, int quantity) {
        if (!hasEnoughInventory(product, quantity)) {
            throw new IllegalArgumentException("Not enough inventory");
        }
        inventory.put(product, getInventory(product) - quantity);
    }

    @Override
    public void addInventory(Product product, int quantity) {
        inventory.put(product, getInventory(product) + quantity);
    }

    @Override
    public int getInventory(Product product) {
        return inventory.getOrDefault(product, 0);
    }
}
