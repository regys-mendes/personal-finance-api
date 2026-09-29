package com.regysmendes.personalfinance.dto.transaction;

import com.regysmendes.personalfinance.entities.TransactionType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionInsertDTO implements Serializable {

    private String description;
    private BigDecimal value;
    private LocalDate date;
    private TransactionType transactionType;

    public TransactionInsertDTO(){
    }

    public TransactionInsertDTO(String description, BigDecimal value, LocalDate date, TransactionType transactionType) {
        this.description = description;
        this.value = value;
        this.date = date;
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
