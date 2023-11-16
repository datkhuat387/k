package com.example.ol_shop.model;

public class Statistical {
    private Double statisticalMoney;
    private Integer numberOfBillsConfirm;
    private Integer numberOfBillsCancelled;

    private Integer numberOfBills;
    public Double getStatisticalMoney() {
        return statisticalMoney;
    }

    public void setStatisticalMoney(Double statisticalMoney) {
        this.statisticalMoney = statisticalMoney;
    }

    public Integer getNumberOfBillsConfirm() {
        return numberOfBillsConfirm;
    }

    public void setNumberOfBillsConfirm(Integer numberOfBillsConfirm) {
        this.numberOfBillsConfirm = numberOfBillsConfirm;
    }

    public Integer getNumberOfBillsCancelled() {
        return numberOfBillsCancelled;
    }

    public void setNumberOfBillsCancelled(Integer numberOfBillsCancelled) {
        this.numberOfBillsCancelled = numberOfBillsCancelled;
    }

    public Integer getNumberOfBills() {
        return numberOfBills;
    }

    public void setNumberOfBills(Integer numberOfBills) {
        this.numberOfBills = numberOfBills;
    }
}
