package com.mycompany.app.transferObjects;

import java.io.Serializable;

/**
 * Created by DotinSchool2 on 9/13/2016.
 */
public class Transaction implements Serializable{
    private String id;
    private String transactionType;
    private Integer amount;
    private String deposit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
