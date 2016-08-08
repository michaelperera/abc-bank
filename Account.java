package com.abc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public  abstract class Account {

	private final AccountType accountType;
	public List<Transaction> transactions;

	public Account(AccountType accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount));
		}
	}

	public abstract double interestEarned();

	public void depositWithDate(long amount, LocalDate date) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			double interest = getInterestAmount(date, amount);
			//System.out.println("interest "+interest);
			if(interest >0 && interest !=1)
				transactions.add(new Transaction(amount*interest-amount, date));
			else
				transactions.add(new Transaction(amount, date));
		}
	}

	private double getInterestAmount(LocalDate despositedDay, long amount) {
		LocalDate now =DateProvider.getInstance().now();
		long days = ChronoUnit.DAYS.between(despositedDay, now);
		if(days!=0)
			return (Math.pow((1+(interestEarned()/365)),days));
		else
			return 0;
	}
	

	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t: transactions)
			amount += t.amount;
		return amount;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void printTransactions(){
		for (Transaction t: transactions)
			System.out.println(t.toString());
	}
	public void printTransactionDate(){
		for (Transaction t: transactions)
			System.out.println(t.getTransactionDate());
	}
	public void getLastTransactionDate(){
		int size = transactions.size();
		System.out.println(transactions.get(size-1).getTransactionDate());
	}
}
