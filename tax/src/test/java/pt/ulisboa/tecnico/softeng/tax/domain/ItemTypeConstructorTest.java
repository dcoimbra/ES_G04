package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.bank.exception.ItemTypeException;

public class ItemTypeConstructorTest {

	private static final String ITEM_TYPE = "TOKEN";
	private static final int TAX = "31";


	@Test
	public void success() {

		ItemType itemtype = new ItemType(ITEM_TYPE, TAX);

		Assert.assertEquals(ITEM_TYPE, itemtype.getITEM_TYPE());
		Assert.assertEquals(TAX, itemtype.getTAX());
	}

	@Test(expected = ItemTypeException.class)
	public void notStringITEM_TYPE() {
		new ItemType(123, TAX);
	}
	
	@Test
	public void notUniqueITEM_TYPE() {
		new ItemType(ITEM_TYPE, TAX);
		try {
			new ItemType(ITEM_TYPE, TAX);
			Assert.fail();
		} catch (ItemTypeException tpe) {
			Assert.assertEquals(1, ItemType.itemtypes.size());
		}
	}

	@Test(expected =  ItemTypeException.class)
	public void notPositiveTAX() {
		new TaxPayer(ITEM_TYPE, -20);
	}

}
