package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;

/* IRS class implements the Singleton design pattern. */ 
public class IRS {
	
	private static final IRS _irs = new IRS();
	
	private Set<TaxPayer> _taxpayers = new HashSet<>();
	private Set<ItemType> _itemtypes = new HashSet<>();

	private IRS() {}

	public static IRS getIRS() {

		return _irs;
	}

	public void addTaxPayer(TaxPayer taxPayer) {

		_taxpayers.add(taxPayer);
	}

	public void addItemType(ItemType itemType) {

		_itemtypes.add(itemType);
	}

	public TaxPayer getTaxPayerByNIF(String BUYER_NIF){
		for(TaxPayer tp : _taxpayers){
			if(tp.getNIF().equals(BUYER_NIF)){
				return tp;
			}
		}

		throw new IRSException;
	}

	public ItemType getItemTypeByName(String ITEM_TYPE){
		for(ItemType it : _itemtypes){
			if(it.getITEM_TYPE().equals(ITEM_TYPE)){
				return it;
			}
		}
		
		throw new IRSException;
	}
}