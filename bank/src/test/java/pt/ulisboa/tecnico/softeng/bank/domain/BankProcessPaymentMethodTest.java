package pt.ulisboa.tecnico.softeng.bank.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class BankProcessPaymentMethodTest {

	private Bank bank;
	private Account account;
	private String reference;

	@Before
	public void setUp() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "António");
		this.account = new Account(this.bank, client);
	}

	@Test(expected = BankException.class)
	public void wrongIBAN(){
		this.bank.processPayment("XPTO100",100);
	}

	@Test
	public void success() {
		this.account.deposit(100);
		this.bank.processPayment(this.account.getIBAN(),100);

		Assert.assertEquals(0, this.account.getBalance());
	}

	@After
	public void tearDown() {
		Bank.banks.clear();
	}
}
