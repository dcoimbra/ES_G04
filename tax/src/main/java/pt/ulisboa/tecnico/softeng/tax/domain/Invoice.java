package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;
import java.sql.Timestamp;



public abstract class Invoice {
	
	public static Set<Invoice> invoices = new HashSet<>();
	
	private float _value ;
	private LocalDate _date ;
	private String _itemType ;
	private Seller _seller ;
	private Buyer _buyer ;
	private float _iva;
	private String _reference;
	
	
	public Invoice(float value, LocalDate date, String item_type,Seller seller, Buyer buyer) {
		checkArguments(value, date, item_type,seller,buyer);

		_iva = ItemType.itemtypes.get(item_type)/100;
		_value =value;
		_date = date;
		_itemType = item_type;
		_seller = seller;
		_buyer = buyer ;
		_reference= new Timestamp(0).toString();
		invoices.add(this);
	}
	
	private void checkArguments(float VALUE, LocalDate DATE, String ITEM_TYPE,Seller SELLER,Buyer BUYER) {
		
		if(DATE==null || ITEM_TYPE=="" || ITEM_TYPE==null || SELLER==null || BUYER==null) {
			throw new InvoiceException();
		}
		
		if(DATE.getYear() < 1970) {
			throw new InvoiceException();
		}
			
		
		for (Invoice tp : Invoices) {
			if (tp.getVALUE()==(VALUE) || tp.getDATE().equals(DATE)  || tp.getITEM_TYPE().equals(ITEM_TYPE) || tp.getSELLER().equals(SELLER)  || tp.getBUYER().equals(BUYER) ){
				throw new InvoiceException();
			}
		}
	}
	
	public float getVALUE() {
		return this._value;
	}
	
	public LocalDate getDATE() {
		return this._date;
	}
	
	public String getITEM_TYPE() {
		return this._itemType;
	}
	
	public Seller getSELLER() {
		return this._seller;
	}
	
	public Buyer getBUYER() {
		return this._buyer;
	}

	public float getIva(){
		return this._iva;
	}
}

