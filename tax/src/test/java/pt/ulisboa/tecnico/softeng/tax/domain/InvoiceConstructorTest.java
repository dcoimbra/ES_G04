package pt.ulisboa.tecnico.softeng.tax.domain;

import java.time.LocalDate;
import java.time.Local_date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.InvoiceException;

public class InvoiceConstructorTest {
    

	private static final float _value =1.5f;
	private static final LocalDate _date ;
	private static final String _itemType ="token" ;
	private static final Seller _seller ;
	private static final Buyer _buyer ;
	private static final float _iva;


	@Test
	public void success() {

		Invoice Invoice = new Invoice(_value, _date, _itemType, _seller, _buyer);

		Assert.assertEquals(_value, Invoice.get_value());
		Assert.assertEquals(_date, Invoice.get_date());
		Assert.assertEquals(_itemType, Invoice.get_itemType());
		Assert.assertEquals(_seller, Invoice.get_seller());
		Assert.assertEquals(_buyer, Invoice.get_buyer());
	}

	@Test(expected = InvoiceException.class)
	public void notString_value() {
		new Invoice(null, _date, _itemType, _seller, _buyer);
	}

	@Test
	public void ivaTax_value(){
		ItemType it = new ItemType (_itemType, 20);
		Invoice invoice = new Invoice(_value, _date, _itemType, _seller, _buyer);

		int tax = ItemType.itemtypes.get(_itemType);

		Assert.assertEquals(it.getTax/100, invoice.getIva())
	}
	
	@Test(expected = InvoiceException.class)
	public void notEmpty_value() {
		new Invoice("", _date, _itemType, _seller, _buyer);
	}
	
	@Test(expected = InvoiceException.class)
	public void notStringDATA() {
		new Invoice(_value, null, _itemType , _seller, _buyer);
	}
	
	@Test(expected = InvoiceException.class)
	public void notEmptyDATA() {
		new Invoice(_value, "", _itemType , _seller, _buyer);
	}
	
	@Test(expected = InvoiceException.class)
	public void notValidDATA() {
		new Invoice(_value, "13/3/1969", _itemType , _seller, _buyer);
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
	public void notEmpty_seller() {
		new Invoice(_value, _date, _itemType, "", _buyer);
	}
	
	@Test(expected = InvoiceException.class)
	public void notString_buyer() {
		new Invoice(_value, _date, _itemType, _seller, null);
	}
	
	@Test(expected = InvoiceException.class)
	public void notEmpty_buyer() {
		new Invoice(_value, _date, _itemType, _seller, "");
	}
	

	@Test
	public void notUnique() {
		new Invoice(_value, _date, _itemType, _seller, _buyer);
		try {
			new Invoice(_value, _date, _itemType, _seller, _buyer);
			Assert.fail();
		} catch (InvoiceException tpe) {
			Assert.assertEquals(1, Invoice.Invoices.size());
		}
	}

	
}
