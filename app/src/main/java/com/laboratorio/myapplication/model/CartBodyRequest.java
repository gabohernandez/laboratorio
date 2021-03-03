package com.laboratorio.myapplication.model;

import java.util.List;

public class CartBodyRequest {

    private List<CartProductWrapper> cartProducts;
    private CartNodeDate nodeDate;
    private String observation;
    private CartGeneral general;

    public List<CartProductWrapper> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProductWrapper> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public CartNodeDate getNodeDate() {
        return nodeDate;
    }

    public void setNodeDate(CartNodeDate nodeDate) {
        this.nodeDate = nodeDate;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public CartGeneral getGeneral() {
        return general;
    }

    public void setGeneral(CartGeneral general) {
        this.general = general;
    }
}
