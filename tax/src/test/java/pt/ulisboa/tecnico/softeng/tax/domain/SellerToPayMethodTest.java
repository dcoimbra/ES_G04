package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxPayerException;

public class SellerToPayMethodTest {

	private Seller seller;
    private Invoice i;
    private ItemType it;

	@Before
	public void setUp() {
		IRS.getIRS()._taxpayers.clear();
		IRS.getIRS()._itemtypes.clear();
		Invoice._invoices.clear();
		
		this.seller = new Seller("123456789", "Antonio", "Quinta das Lagrimas");
		ItemType it = new ItemType("token1", 29);
		this.i = new Invoice(1.5f, new LocalDate().now(), "token1" ,this.seller , new Buyer("123456780", "Jose Toni", "Quinta das Lagrimas"));
	}

	@Test(expected = TaxPayerException.class)
	public void beforeYear() {
		this.seller.toPay(1969);
	}

	@Test
	public void beginYear() {
		this.seller.toPay(1970);
	}

	@Test
	public void thisYear() {
		this.seller.toPay(2018);
	}
	
	@Test
	public void afterYear() {
		this.seller.toPay(1972);
	}

	@After 
	public void tearDown() {
		IRS.getIRS()._taxpayers.clear();
		IRS.getIRS()._itemtypes.clear();
		Invoice._invoices.clear();
	}
}
