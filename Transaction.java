package com.abc;

import java.time.LocalDate;

public class Transaction {

	public final double amount;
    private LocalDate transactionDate;

    public Transaction(double amount2) {
        this.amount = amount2;
        this.transactionDate = LocalDate.now();
    }
    
    public Transaction(double amount, LocalDate date) {
        this.amount = amount;
        this.transactionDate = date;
    }
    
    public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public double getAmount() {
		return amount;
	}

	@Override
   	public String toString() {
   		return "Transaction [amount=" + amount + ", transactionDate=" + transactionDate + "]";
   	}
}
