package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxPayerException;

public class BuyerTaxReturnMethodTest {

	private Buyer buyer;
    private Invoice i;
    private ItemType it;

	@Before
	public void setUp() {
		IRS.getIRS()._taxpayers.clear();
		IRS.getIRS()._itemtypes.clear();
		Invoice._invoices.clear();
		
		this.buyer = new Buyer("123456789", "Antonio", "Quinta das Lagrimas");
		ItemType it = new ItemType("token1", 29);
		this.i = new Invoice(1.5f, new LocalDate().now(), "token1" ,new Seller("123456780", "Jose Toni", "Quinta das Lagrimas") , this.buyer);
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
	public void thisYear() {
		this.buyer.taxReturn(1971);
	}
	
	@Test
	public void afterYear() {
		this.buyer.taxReturn(2018);
	}

	@After 
	public void tearDown() {
		IRS.getIRS()._taxpayers.clear();
		IRS.getIRS()._itemtypes.clear();
		Invoice._invoices.clear();
	}
}

