package com.example.unittesting.chapter06.listing02;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products.stream().toList();
    }
}
