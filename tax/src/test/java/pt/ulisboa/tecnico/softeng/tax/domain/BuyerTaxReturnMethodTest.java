package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxPayerException;

public class BuyerTaxReturnMethodTest {

	private Buyer buyer;

	@Before
	public void setUp() {
		this.buyer = new Buyer("123456789", "Antonio", "Quinta das Lagrimas");
	}

	@Test(expected = TaxPayerException.class)
	public void beforeYear() {
		this.buyer.taxReturn(1969);
	}

	@Test
	public void beginYear() {
		this.buyer.taxReturn(1970);
	}

	@Test
	public void afterYear() {
		this.buyer.taxReturn(1972);
	}

	@After 
	public void tearDown() {
		TaxPayer._taxpayers.clear();
	}
}
