package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;



import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxPayerException;
import pt.ulisboa.tecnico.softeng.tax.exception.ItemTypeException;



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

		throw new TaxPayerException("No such tax payer");
	}

	public ItemType getItemTypeByName(String ITEM_TYPE){
		for(ItemType it : _itemtypes){
			if(it.getITEM_TYPE().equals(ITEM_TYPE)){
				return it;
			}
		}

		throw new ItemTypeException("No such item type");
	}
	
	public void submitInvoice(InvoiceData invoiceData) {
		TaxPayer tpseller = null;
		for(TaxPayer tp : _taxpayers){
			if(tp.getNIF() == invoiceData.getSellerNIF()) {
				tpseller = tp;
				break;
			}
		}
		TaxPayer buyer1 = null;
		for(TaxPayer buyer: _taxpayers){
			if(buyer.getNIF() == invoiceData.getBuyerNIF()) {
				buyer1=buyer;
				break;
			}
		}
		if(tpseller != null && (buyer1 != null)){
			 new Invoice(invoiceData.getValue(), invoiceData.getDate(),invoiceData.getItemType(), (Seller)tpseller, (Buyer)buyer1);
		}
		else {
			throw new TaxPayerException("seller or buyer dont exist");
		}
		
	}
}