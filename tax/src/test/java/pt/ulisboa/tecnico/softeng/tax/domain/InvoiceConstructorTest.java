package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.bank.exception.InvoiceException;

public class InvoiceConstructorTest {
    
	private static final String VALUE = "2";
	private static final String DATE = "13/3/2000";
	private static final String ITEM_TYPE = "TOKEN";
	private static final String SELLER = "tu";
	private static final String BUYER = "yo";


	@Test
	public void success() {

		Invoice Invoice = new Invoice(VALUE, DATE, ITEM_TYPE, SELLER, BUYER);

		Assert.assertEquals(VALUE, Invoice.getVALUE());
		Assert.assertEquals(DATE, Invoice.getDATE());
		Assert.assertEquals(ITEM_TYPE, Invoice.getITEM_TYPE());
		Assert.assertEquals(SELLER, Invoice.getSELLER());
		Assert.assertEquals(BUYER, Invoice.getBUYER());
	}

	@Test(expected = InvoiceException.class)
	public void notStringVALUE() {
		new Invoice(123, DATE, ITEM_TYPE, SELLER, BUYER);
	}
	
	@Test(expected = InvoiceException.class)
	public void notStringDATA() {
		new Invoice(VALUE, 123, ITEM_TYPE , SELLER, BUYER);
	}
	
	@Test(expected = InvoiceException.class)
	public void notValidDATA() {
		new Invoice(VALUE, "13/3/1969", ITEM_TYPE , SELLER, BUYER);
	}
	
	@Test(expected = InvoiceException.class)
	public void notStringITEM_TYPE() {
		new Invoice(VALUE, DATE, 123, SELLER, BUYER);
	}
	
	@Test(expected = InvoiceException.class)
	public void notStringSELLER() {
		new Invoice(VALUE, DATE, ITEM_TYPE, 123, BUYER);
	}
	
	@Test(expected = InvoiceException.class)
	public void notStringBUYER() {
		new Invoice(VALUE, DATE, ITEM_TYPE, SELLER, 123);
	}
	
	
	
	
	@Test
	public void notUnique() {
		new Invoice(VALUE, DATE, ITEM_TYPE, SELLER, BUYER);
		try {
			new Invoice(VALUE, DATE, ITEM_TYPE, SELLER, BUYER);
			Assert.fail();
		} catch (InvoiceException tpe) {
			Assert.assertEquals(1, Invoice.Invoices.size());
		}
	}

	
}
