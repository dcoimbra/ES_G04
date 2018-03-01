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

	public String getITEM_TYPE() {

		return _itemtype;
	}

	public int getTax() {

		return _tax;
	}

	public static ItemType getItemTypeByITEM_TYPE(String ITEM_TYPE) {

		for (ItemType it : _itemtypes) {

			if (it.getITEM_TYPE().equals(ITEM_TYPE)) {

				return it;
			}
		}
	} 
}