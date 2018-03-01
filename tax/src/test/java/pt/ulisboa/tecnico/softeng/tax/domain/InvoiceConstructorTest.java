package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.InvoiceException;

public class InvoiceConstructorTest {
    
	private static final String VALUE = "2";
	private static final String DATE = "13/3/2000";
	private static final String ITEM_TYPE = "TOKEN";
	private static final String SELLER = "tu";
	private static final String BUYER = "eu";


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
		new Invoice(null, DATE, ITEM_TYPE, SELLER, BUYER);
	}

	@Test
	public void ivaTaxValue(){
		ItemType it = new ItemType (ITEM_TYPE, 20);
		Invoice invoice = new Invoice(VALUE, DATE, ITEM_TYPE, SELLER, BUYER);

		int tax = ItemType.itemtypes.get(ITEM_TYPE);

		Assert.assertEquals(tax/100, Invoice.getIva())
	}
	
	@Test(expected = InvoiceException.class)
	public void notEmptyVALUE() {
		new Invoice("", DATE, ITEM_TYPE, SELLER, BUYER);
	}
	
	@Test(expected = InvoiceException.class)
	public void notStringDATA() {
		new Invoice(VALUE, null, ITEM_TYPE , SELLER, BUYER);
	}
	
	@Test(expected = InvoiceException.class)
	public void notEmptyDATA() {
		new Invoice(VALUE, "", ITEM_TYPE , SELLER, BUYER);
	}
	
	@Test(expected = InvoiceException.class)
	public void notValidDATA() {
		new Invoice(VALUE, "13/3/1969", ITEM_TYPE , SELLER, BUYER);
	}
	
	@Test(expected = InvoiceException.class)
	public void notStringITEM_TYPE() {
		new Invoice(VALUE, DATE, null, SELLER, BUYER);
	}
	
	@Test(expected = InvoiceException.class)
	public void notEmptyITEM_TYPE() {
		new Invoice(VALUE, DATE, "", SELLER, BUYER);
	}
	
	@Test(expected = InvoiceException.class)
	public void notStringSELLER() {
		new Invoice(VALUE, DATE, ITEM_TYPE, null, BUYER);
	}
	
	@Test(expected = InvoiceException.class)
	public void notEmptySELLER() {
		new Invoice(VALUE, DATE, ITEM_TYPE, "", BUYER);
	}
	
	@Test(expected = InvoiceException.class)
	public void notStringBUYER() {
		new Invoice(VALUE, DATE, ITEM_TYPE, SELLER, null);
	}
	
	@Test(expected = InvoiceException.class)
	public void notEmptyBUYER() {
		new Invoice(VALUE, DATE, ITEM_TYPE, SELLER, "");
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
