package com.example.ol_shop.model;

public class Bill {
    private String _id;
    private String accountId;
    private String productId;
    private Double totalPrice;
    private int quantity;
    private int statusBill;
    public Bill(String accountId, String productId, Double totalPrice, int quantity, int status) {
        this.accountId = accountId;
        this.productId = productId;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.statusBill = status;
    }

    public Bill() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatusBill() {
        return statusBill;
    }

    public void setStatusBill(int statusBill) {
        this.statusBill = statusBill;
    }
}
