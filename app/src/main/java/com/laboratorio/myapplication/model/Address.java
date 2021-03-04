package com.laboratorio.myapplication.model;

public class Address {

    private String street;
    private String number;
    private String betweenStreets;
    private String apartment;
    private String floor;
    private String description;

    public Address(String calle, String entreCalles, String numero, String piso, String dpto, String descript) {
        this.street = calle;
        this.number = numero;
        this.betweenStreets = entreCalles;
        this.apartment = dpto;
        this.floor = piso;
        this.description = descript;
    }

    public Address() {

    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBetweenStreets() {
        return betweenStreets;
    }

    public void setBetweenStreets(String betweenStreets) {
        this.betweenStreets = betweenStreets;
    }
}
