package com.laboratorio.myapplication.model;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String encryptedPassword;
    private String phone;
    private String image;
    private String age;
    private Address address;

    public User(String name, String lastName, String email, String phone, String age, String password) {
        this.firstName = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.image = null;
        this.age = age;
        this.address = new Address();
        this.encryptedPassword = password;
    }

    public User(){

    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
