package com.abc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MaxiSavingsAccount extends Account {

	public MaxiSavingsAccount() {
		super(AccountType.MAXI_SAVINGS);
	}

	@Override
	public double interestEarned() {
		double add;
		double amount = sumTransactions(); 
		if(isTransactionInLast10days())
			return  .01; 
		else
			return  .05;
		
		
		/*if (amount <= 1000)
			return amount * 0.02;
		if (amount <= 2000)
			return 20 + (amount-1000) * 0.05;
		return 70 + (amount-2000) * 0.1;*/
		
		
		
	}
	
	private boolean isTransactionInLast10days() {
		LocalDate now =DateProvider.getInstance().now();
		LocalDate tenDaysAgo = now.plus(-10, ChronoUnit.DAYS);
		if(transactions.size()==0){
			return false;
		}
		if(tenDaysAgo.isAfter(transactions.get(transactions.size()-1).getTransactionDate()))
			return false;
		else
			return true;
	}
	
}