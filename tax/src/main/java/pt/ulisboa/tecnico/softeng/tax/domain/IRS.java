package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;

/* IRS class implements the Singleton design pattern. */ 
public class IRS {
	
	private static final IRS _irs = new IRS();
	
	private Set<TaxPayer> _taxpayers = new HashSet<>();
	private Set<String> _itemtypes = new HashSet<>();

	private IRS() {}

	public static IRS getIRS() {

		return _irs;
	}

	public void addTaxPayer(TaxPayer taxPayer) {

		_taxpayers.add(taxPayer);
	}

	public void addItemType(String itemType) {

		_itemtypes.add(itemType);
	}
}