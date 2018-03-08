package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxPayerException;

public class BuyerTaxReturnMethodTest {

	private Buyer buyer;

	private static ItemType IT1 = new ItemType("Desporto", 20);
	private static ItemType IT2 = new ItemType("Panela", 20);

	private static Invoice INVOICE1 = new Invoice(0.05f, 
										  new LocalDate(1972, 5, 2), 
										  "Desporto", 
										  new Seller("987654321", "Rodas", "Luso"),
										  new Buyer("123456789", "Antonio", "Q"));

	private static Invoice INVOICE2 = new Invoice(0.05f, 
										  new LocalDate(1972, 5, 2), 
										  "Panela", 
										  new Seller("135797531", "Valter", "Vendas Novas"),
										  new Buyer("246808642", "Lucio", "Luso"));

	@Before
	public void setUp() {
		IRS.getIRS()._taxpayers.clear();
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
		IRS.getIRS()._taxpayers.clear();
	}
}
