package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.InvoiceException;

public class SellerGetInvoiceByReferenceMethodTest {

	private Seller seller;

	@Before
	public void setUp() {
		this.seller = new Seller("123456789", "Antonio", "Quinta das Lagrimas");
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
		TaxPayer._taxpayers.clear();
	}
}
