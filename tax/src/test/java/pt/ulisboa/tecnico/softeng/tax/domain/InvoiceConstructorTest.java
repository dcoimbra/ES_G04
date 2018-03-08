package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.InvoiceException;

public class InvoiceConstructorTest {
    

	private static final float _value =1.5f;
	private static final LocalDate _date = new LocalDate().now();
	private static final String _itemType = "token";
	private static final Seller _seller = new Seller("123456789","Jose Toni","Quinta das Lagrimas");
	private static final Buyer _buyer = new Buyer("123456780", "Jose Toni", "Quinta das Lagrimas");
	

	@Test
	public void success() {

		ItemType it = new ItemType(_itemType, 20);

		Invoice Invoice = new Invoice(_value, _date, _itemType, _seller, _buyer);

		Assert.assertEquals(_value, Invoice.getVALUE(), 0.0f);
		Assert.assertEquals(_date, Invoice.getDATE());
		Assert.assertEquals(_itemType, Invoice.getITEM_TYPE());
		Assert.assertEquals(_seller, Invoice.getSELLER());
		Assert.assertEquals(_buyer, Invoice.getBUYER());
	}

	/*Test border values for invoice constructor*/

	@Test
	public void ivaTax_value(){
		ItemType it = new ItemType (_itemType, 20);
		Invoice invoice = new Invoice(_value, _date, _itemType, _seller, _buyer);

		int tax = ItemType.getItemTypeByName(_itemType).getTAX();

		Assert.assertEquals(it.getTAX()/100, invoice.getIVA(),0.0f);
	}
	
	@Test(expected = InvoiceException.class)
	public void nullDATE() {
		new Invoice(_value, null, _itemType , _seller, _buyer);
	}
	
	@Test
	public void beginDATE() {
		ItemType it = new ItemType(_itemType, 20);
		new Invoice(_value, new LocalDate(1970,1,1) , _itemType , _seller, _buyer);
	}
	
	@Test(expected = InvoiceException.class)
	public void beforeDATE() {
		new Invoice(_value, new LocalDate(1969,10,1), _itemType , _seller, _buyer);
	}
	
	@Test(expected = InvoiceException.class)
	public void notString_itemType() {
		new Invoice(_value, _date, null, _seller, _buyer);
	}
	
	@Test(expected = InvoiceException.class)
	public void notEmpty_itemType() {
		new Invoice(_value, _date, "", _seller, _buyer);
	}
	
	@Test(expected = InvoiceException.class)
	public void notString_seller() {
		new Invoice(_value, _date, _itemType, null, _buyer);
	}
	
	
	@Test(expected = InvoiceException.class)
	public void notString_buyer() {
		new Invoice(_value, _date, _itemType, _seller, null);
	}

	@After 
	public void tearDown() {
		ItemType._itemtypes.clear();
		Invoice._invoices.clear();
	}
}
