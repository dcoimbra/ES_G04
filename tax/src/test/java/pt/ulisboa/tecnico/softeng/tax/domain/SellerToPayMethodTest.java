package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.BuyerException;

public class SellerToPayMethodTest {

	private Seller seller ;

	@Before
	public void setUp() {
		this.seller = new Seller("123456789", "Antonio", "Quinta das Lagrimas");
	}

	@Test(expected = BuyerException.class)
	public void beforeYear() {
		this.seller.toPay(1969);
	}

	@Test
	public void beginYear() {
		this.seller.toPay(1970);
	}

	@Test
	public void afterYear() {
		this.seller.toPay(1972);
	}

	@After 
	public void tearDown() {
		TaxPayer.taxpayers.clear();
	}
}
