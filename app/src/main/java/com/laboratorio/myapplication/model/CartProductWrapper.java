package com.laboratorio.myapplication.model;

public class CartProductWrapper {
    private CartProduct product;
    private Integer quantity;

    public CartProduct getProduct() {
        return product;
    }

    public void setProduct(CartProduct product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
