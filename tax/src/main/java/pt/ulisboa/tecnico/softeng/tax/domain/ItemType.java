package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;

public class ItemType {

	public static Set<ItemType> _itemtypes = new HashSet<>(); 

	private String _itemtype;
	private int _tax; 

	public ItemType(String ITEM_TYPE, int TAX) {

		_itemtype = ITEM_TYPE;
		_tax = TAX;

		_itemtypes.add(this);
	}
}