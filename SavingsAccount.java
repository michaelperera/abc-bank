package com.abc;


public class SavingsAccount extends Account {

	public SavingsAccount() {
		super(AccountType.SAVINGS);
	}
	
	@Override
	public double interestEarned() {
		double total = sumTransactions();
		if (total <= 1000)
			return total * 0.001;
		else
			return 1 + (total - 1000) * 0.002;
	}
	

}