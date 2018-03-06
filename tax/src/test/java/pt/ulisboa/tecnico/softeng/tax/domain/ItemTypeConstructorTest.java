package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.ItemTypeException;

public class ItemTypeConstructorTest {

	private static final String ITEM_TYPE = "TOKEN";
	private static final int TAX = 31;


	@Test
	public void success() {

		ItemType itemtype = new ItemType(ITEM_TYPE, TAX);

		Assert.assertEquals(ITEM_TYPE, itemtype.getITEM_TYPE());
		Assert.assertEquals(TAX, itemtype.getTAX());
	}


	@Test
	public void notUniqueITEM_TYPE() {
		new ItemType(ITEM_TYPE, TAX);
		try {
			new ItemType(ITEM_TYPE, TAX);
			Assert.fail();
		} catch (ItemTypeException tpe) {
			Assert.assertEquals(1, ItemType._itemtypes.size());
		}
	}

	@Test(expected =  ItemTypeException.class)
	public void alreadyExistingITEMTYPE() {
		new ItemType("Desporto", 25);
		new ItemType("Nuno", 2);
		new ItemType("Desporto", 25);
	}

	@Test(expected =  ItemTypeException.class)
	public void notPositiveTAX() {
		new ItemType(ITEM_TYPE, -20);
	}

	@After 
	public void tearDown() {
		ItemType._itemtypes.clear();
	}
	
}
