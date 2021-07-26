package com.example.meimall;

import java.util.ArrayList;

public class Order {
    private int id;
    private ArrayList<GroceryItem>items;
    private String address;
    private String zipcode;
    private String phone;
    private String email;
    private double totalPrice;
    private String paymentMethod;
    private boolean success;

    public Order(ArrayList<GroceryItem> items, String address, String zipcode, String phone, String email, double totalPrice, String paymentMethod, boolean success) {
        this.id=Utils.getOrderId();
        this.items = items;
        this.address = address;
        this.zipcode = zipcode;
        this.phone = phone;
        this.email = email;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.success = success;
    }

    public Order() {
        this.id=Utils.getOrderId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<GroceryItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", items=" + items +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", totalPrice=" + totalPrice +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", success=" + success +
                '}';
    }
    public int getSizeOrderItems(){
        return items.size();
    }
}
