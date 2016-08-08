package com.abc;

public enum AccountType {

	CHECKING(0, "Checking"),
    SAVINGS(1, "Savings"),
    MAXI_SAVINGS(2, "Maxi Savings");
	
	private int value;
	
	private String description;
	
	private AccountType(int value, String description) {
		this.value = value;
		this.description = description;
	}
}