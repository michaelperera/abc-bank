package com.abc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	private final int accountType;
	public List<Transaction> transactions;

	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	public void depositInPast(double amount, Date date) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount, date));
		}
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

	public double interestEarned() {
		double amount = sumTransactions();
		switch(accountType){
		case CHECKING:
			return amount*getCompoundingDailyRate(.001);
		case SAVINGS:
			if (amount <= 1000)
				return amount * getCompoundingDailyRate(0.001);
			else
				return 1 + (amount-1000) * getCompoundingDailyRate(0.002);
		case MAXI_SAVINGS:
			if(isTransactionInLast10days())
				return amount *getCompoundingDailyRate(.001); 
			else
				return amount *getCompoundingDailyRate(.05);
		default:
			return amount * getCompoundingDailyRate(0.001);
		}
	}

	public double getCompoundingDailyRate(double rate){
		
		Date despositedDay; 
		
		if(transactions.size()>0)
			despositedDay=transactions.get(transactions.size()-1).getTransactionDate();
		else
			despositedDay = DateProvider.getInstance().now();
		Date now =DateProvider.getInstance().now();
		
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		
		//System.out.println("Days= "+daysBetween(despositedDay, now));
		
		int days = daysBetween(despositedDay, now);
		
		if(days!=0)
			return Math.pow(1+rate/365,days);
		else
			return rate;
		
		
	}
	
	public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
}
	private boolean isTransactionInLast10days() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.add( Calendar.DAY_OF_YEAR, -10);
		Date tenDaysAgo = cal.getTime();
		
		if(transactions.size()==0){
				return false;
		}
			
		
		if(tenDaysAgo.after(transactions.get(transactions.size()-1).getTransactionDate()))
			return false;
		else
			return true;
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

	public int getAccountType() {
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
