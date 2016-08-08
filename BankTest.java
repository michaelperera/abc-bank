package com.abc.test;

import org.junit.Test;

import com.abc.Account;
import com.abc.AccountType;
import com.abc.Bank;
import com.abc.CheckingAccount;
import com.abc.Customer;
import com.abc.MaxiSavingsAccount;
import com.abc.SavingsAccount;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.Ignore;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;
	@Test //Test customer statement generation
	public void Withdraw(){
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Account savingsAccount = new SavingsAccount();

		Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

		checkingAccount.deposit(100.0);
		savingsAccount.deposit(4000.0);
		savingsAccount.withdraw(200.0);

		bank.transferToAccount(henry, checkingAccount, savingsAccount, 15);

		String str = "Statement for Henry"+

					"\n\nChecking Account"+
					"\n  deposit $100.00"+
					"\n  withdrawal $15.00"+
					"\nTotal $85.00"+
					
					"\n\nSavings Account"+
					"\n  deposit $4,000.00"+
					"\n  withdrawal $200.00"+
					"\n  deposit $15.00"+
					"\nTotal $3,815.00"+
					
					"\n\nTotal In All Accounts $3,900.00";
		assertEquals(str, henry.getStatement());


	}
	@Test
	public void customerSummary2() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new CheckingAccount());
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}

	@Test
	public void checkingAccount2() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);

		checkingAccount.deposit(100.0);
		System.out.println("bank.totalInterestPaid()"+bank.totalInterestPaid());
		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savings_account2() {
		Bank bank = new Bank();
		Account checkingAccount = new SavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(1500.0);

		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void maxi_savings_account2() {
		Bank bank = new Bank();
		Account maxiAccount = new MaxiSavingsAccount();
		bank.addCustomer(new Customer("Bob").openAccount(maxiAccount));

		maxiAccount.deposit(3000.0);

		assertEquals(.01, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void customerSummary() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new CheckingAccount());
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}

	@Test
	public void checkingAccount() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);
		checkingAccount.deposit(100.0);


		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savingsAccount() {
		Bank bank = new Bank();
		Account checkingAccount = new SavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(1500.0);

		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Ignore
	public void maxi_savings_account() {
		Bank bank = new Bank();
		Account checkingAccount = new MaxiSavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(3000.0);

		assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savings_account_higher_interest(){
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Account savingAccount = new SavingsAccount();
		Account maxiAccount = new MaxiSavingsAccount();

		Customer bill = new Customer("Bill").openAccount(checkingAccount);

		bill.openAccount(savingAccount);
		bill.openAccount(maxiAccount);

		bank.addCustomer(bill);

		savingAccount.deposit(20000);
		bank.totalInterestPaid();

		assertEquals(39.15, bank.totalInterestPaid(), DOUBLE_DELTA);

	}

	@Test
	public void savings_account_lower_interest(){
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Account savingAccount = new SavingsAccount();
		Account maxiAccount = new MaxiSavingsAccount();

		Customer bill = new Customer("Bill").openAccount(checkingAccount);

		bill.openAccount(savingAccount);
		bill.openAccount(maxiAccount);

		bank.addCustomer(bill);

		savingAccount.deposit(200);
		bank.totalInterestPaid();

		assertEquals(.35, bank.totalInterestPaid(), DOUBLE_DELTA);

	}

	@Test
	public void maxi_savings_account_lower_interest(){
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Account savingAccount = new SavingsAccount();
		Account maxiAccount = new MaxiSavingsAccount();

		Customer bill = new Customer("Bill").openAccount(checkingAccount);

		bill.openAccount(savingAccount);
		bill.openAccount(maxiAccount);

		bank.addCustomer(bill);

		maxiAccount.deposit(1000);
		bank.totalInterestPaid();


		assertEquals(.11, bank.totalInterestPaid(), DOUBLE_DELTA);

	}
	@Test
	public void maxi_savings_account_higher_interest_compounding_daily(){
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Account savingAccount = new SavingsAccount();
		Account maxiAccount = new MaxiSavingsAccount();

		Customer bill = new Customer("Bill").openAccount(checkingAccount);

		bill.openAccount(savingAccount);
		bill.openAccount(maxiAccount);

		bank.addCustomer(bill);

		LocalDate today = LocalDate.now();
		LocalDate thirtyDaysAgo = today.plus(-300, ChronoUnit.DAYS);

		maxiAccount.depositWithDate(12300, thirtyDaysAgo);

		assertEquals(0.15, bank.totalInterestPaid(), DOUBLE_DELTA);

	}
	@Test
	public void withdraw(){
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Account savingAccount = new SavingsAccount();
		Account maxiAccount = new MaxiSavingsAccount();

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
