package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;


public abstract class Invoice {
	
	public static Set<Invoice> Invoices = new HashSet<>();
	
	private String _value ;
	private String _date ;
	private String _itemType ;
	private String _seller ;
	private String _buyer ;
	
	public Invoice(String  VALUE, String DATE, String ITEM_TYPE,String SELLER,String BUYER) {
		checkArguments(VALUE, DATE, ITEM_TYPE,SELLER,BUYER);

		_value =VALUE;
		_date = DATE;
		_itemType = ITEM_TYPE;
		_seller = SELLER;
		_buyer = BUYER ;
		Invoices.add(this);
	}
	
	private void checkArguments(String  VALUE, String DATE, String ITEM_TYPE,String SELLER,String BUYER) {
		
		if(VALUE=="" ||VALUE==null || DATE=="" || DATE==null || ITEM_TYPE=="" || ITEM_TYPE==null || SELLER=="" || SELLER==null || BUYER=="" || BUYER==null) {
			throw new InvoiceException();
		}
		
		if(Integer.parseInt(DATE) < 1970) {
			throw new InvoiceException();
		}
			
		
		for (Invoice tp : Invoices) {
			if (tp.getVALUE().equals(VALUE) || tp.getDATE().equals(DATE)  || tp.getITEM_TYPE().equals(ITEM_TYPE) || tp.getSELLER().equals(SELLER)  || tp.getBUYER().equals(BUYER) ){
				throw new InvoiceException();
			}
		}
	}
	
	public String getVALUE() {
		return this._value;
	}
	
	public String getDATE() {
		return this._date;
	}
	
	public String getITEM_TYPE() {
		return this._itemType;
	}
	
	public String getSELLER() {
		return this._seller;
	}
	
	public String getBUYER() {
		return this._buyer;
	}
}

