package com.laboratorio.myapplication.model;

import java.util.Map;

public class Cart {

    private Map<Product, Integer> products;

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }
}
