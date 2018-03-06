package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.ItemTypeException;

public class ItemTypegetItemTypeByNameMethodTest {


	@Before
	public void setUp() {
		ItemType it = new ItemType("Desporto", 30);
	}

	@Test(expected = ItemTypeException.class)
	public void noObject(){
		ItemType.getItemTypeByName("Alberto");
	}

	@Test
	public void existsObject(){
		ItemType.getItemTypeByName("Desporto");
	}

	@After 
	public void tearDown() {
		ItemType._itemtypes.clear();
	}
}
