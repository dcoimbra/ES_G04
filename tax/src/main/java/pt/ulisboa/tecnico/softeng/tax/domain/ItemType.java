package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.tax.exception.ItemTypeException;

public class ItemType {

	public static Set<ItemType> _itemtypes = new HashSet<>(); 

	private String _itemtype;
	private int _tax; 

	public ItemType(String ITEM_TYPE, int TAX) {

		checkArguments(ITEM_TYPE, TAX);
		_itemtype = ITEM_TYPE;
		_tax = TAX;

		_itemtypes.add(this);
	}

	private void checkArguments(String ITEM_TYPE, int TAX){

		if(TAX < 0){
			throw new ItemTypeException();
		}

		for(ItemType it : _itemtypes){
			
			if(it.getITEM_TYPE() == ITEM_TYPE)
				
				throw new ItemTypeException("Item type already exists");
		}
	}

	public String getITEM_TYPE() {

		return _itemtype;
	}

	public int getTAX() {

		return _tax;
	}

	public static ItemType getItemTypeByName(String ITEM_TYPE) {

		for (ItemType it : _itemtypes) {

			if (it.getITEM_TYPE().equals(ITEM_TYPE)) {

				return it;
			}
		}

		throw new ItemTypeException("No such item type.");
	} 
}