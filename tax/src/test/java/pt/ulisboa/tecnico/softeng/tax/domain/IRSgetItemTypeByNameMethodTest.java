package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.IRSException;
import pt.ulisboa.tecnico.softeng.tax.exception.ItemTypeException;

public class IRSgetItemTypeByNameMethodTest {
    
	private ItemType it1 = new ItemType("token1", 20);

	@Before
	public void setUp() {
		IRS._itemtypes.clear();
		IRS.getIRS().addItemType(it1);
		
	}

	@Test(expected = ItemTypeException.class)
	public void noItemType(){
		IRS.getIRS().getItemTypeByName("token2");
	}

	@Test
	public void existsItemType(){
		IRS.getIRS().getItemTypeByName("token1");
	}

	@After 
	public void tearDown() {
		IRS._itemtypes.clear();
	}
}
