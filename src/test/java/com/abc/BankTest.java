package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void customerSummary() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new Account(Account.CHECKING));
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}

	@Test
	public void checkingAccount() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.CHECKING);
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);
		checkingAccount.deposit(100.0);


		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savings_account() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(1500.0);

		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Ignore
	public void maxi_savings_account() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(3000.0);

		assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}
	@Ignore
	@Test
	public void maxi_savings_account_higher_interest(){
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.CHECKING);
		Account savingAccount = new Account(Account.SAVINGS);
		Account maxiAccount = new Account(Account.MAXI_SAVINGS);

		Customer bill = new Customer("Bill").openAccount(checkingAccount);

		bill.openAccount(savingAccount);
		bill.openAccount(maxiAccount);

		bank.addCustomer(bill);

		maxiAccount.depositInPast(1000, new Date("Fri JUL 03 00:46:09 EDT 2016"));
		bank.totalInterestPaid();

		assertEquals(50.0, bank.totalInterestPaid(), DOUBLE_DELTA);

	}
	@Test
	public void maxi_savings_account_lower_interest(){
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.CHECKING);
		Account savingAccount = new Account(Account.SAVINGS);
		Account maxiAccount = new Account(Account.MAXI_SAVINGS);

		Customer bill = new Customer("Bill").openAccount(checkingAccount);

		bill.openAccount(savingAccount);
		bill.openAccount(maxiAccount);

		bank.addCustomer(bill);

		maxiAccount.deposit(1000);
		bank.totalInterestPaid();


		assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);

	}
	@Test
	public void maxi_savings_account_higher_interest_compounding_daily(){
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.CHECKING);
		Account savingAccount = new Account(Account.SAVINGS);
		Account maxiAccount = new Account(Account.MAXI_SAVINGS);

		Customer bill = new Customer("Bill").openAccount(checkingAccount);

		bill.openAccount(savingAccount);
		bill.openAccount(maxiAccount);

		bank.addCustomer(bill);

		maxiAccount.depositInPast(1000, new Date("Fri JUL 03 00:46:09 EDT 2016"));
		//bank.totalInterestPaid();

		assertEquals(1004.5304700350099, bank.totalInterestPaid(), DOUBLE_DELTA);

	}
	@Test
	public void withdraw(){
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.CHECKING);
		Account savingAccount = new Account(Account.SAVINGS);
		Account maxiAccount = new Account(Account.MAXI_SAVINGS);

		Customer bill = new Customer("Bill").openAccount(checkingAccount);

		bill.openAccount(savingAccount);
		bill.openAccount(maxiAccount);

		bank.addCustomer(bill);

		checkingAccount.deposit(500);
		savingAccount.deposit(1000);


		bank.transferToAccount(bill, checkingAccount, savingAccount, 15);

		String str = "Statement for Bill\n"
					+"\n"
					+"Checking Account\n"
					+"  deposit $500.00\n"
					+"  withdrawal $15.00\n"
					+"Total $485.00\n"
					+"\n"
					+"Savings Account\n"
					+"  deposit $1,000.00\n"
					+"  deposit $15.00\n"
					+"Total $1,015.00\n"
					+"\n"
					+"Maxi Savings Account\n"
					+"Total $0.00\n"
					+"\n"
					+"Total In All Accounts $1,500.00";


		assertEquals(str, bill.getStatement());
	}


}
