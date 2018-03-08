package pt.ulisboa.tecnico.softeng.tax.domain;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import org.joda.time.LocalDate;
import pt.ulisboa.tecnico.softeng.tax.exception.InvoiceException;

public class SellerGetInvoiceByReferenceMethodTest {

	private Seller seller;

	@Before
	public void setUp() {
		this.seller = new Seller("123456789", "Antonio", "Quinta das Lagrimas");
	}

	@Test
	public void success() {

		ItemType it = new ItemType("Desporto", 20);

		Invoice inv = new Invoice(0.05f, 
								  new LocalDate(1972, 5, 2), 
								  "Desporto", 
								  new Seller("987654321", "Rodas", "Luso"),
								  new Buyer("135797531", "Antonio", "Q"));

		this.seller.getInvoiceByReference(inv.getREFERENCE());
	}

	@Test(expected = InvoiceException.class)
	public void noSuchInvoice() {

		ItemType it = new ItemType("Cozinha", 20);

		Invoice inv = new Invoice(0.05f, 
								  new LocalDate(1972, 5, 2), 
								  "Cozinha", 
								  new Seller("987654321", "Rodas", "Luso"),
								  new Buyer("135797531", "Antonio", "Q"));

		this.seller.getInvoiceByReference(new Timestamp(0).toString());
	}

	@Test(expected = InvoiceException.class)
	public void invalidReferenceNull() {

		this.seller.getInvoiceByReference(null);
	}


	@Test(expected = InvoiceException.class)
	public void invalidReferenceEmpty() {

		this.seller.getInvoiceByReference("");
	}

	@After 
	public void tearDown() {
		ItemType._itemtypes.clear();
		Invoice._invoices.clear();
		IRS.getIRS()._taxpayers.clear();
	}
}
