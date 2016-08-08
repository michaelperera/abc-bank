package com.abc;


public class CheckingAccount extends Account {
	public CheckingAccount() {
		super(AccountType.CHECKING);
	}
	
	@Override
	public double interestEarned() {
		 return 0.1;
	}

}