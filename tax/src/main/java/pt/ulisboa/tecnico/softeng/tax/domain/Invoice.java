package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;
import org.joda.time.LocalDate;
import java.sql.Timestamp;


import pt.ulisboa.tecnico.softeng.tax.exception.InvoiceException;

public class Invoice {
	
	public static Set<Invoice> _invoices = new HashSet<>();
	
	private float _value ;
	private LocalDate _date ;
	private String _itemType ;
	private Seller _seller ;
	private Buyer _buyer ;
	private float _iva;
	private String _reference;
	
	
	public Invoice(float value, LocalDate date, String item_type,Seller seller, Buyer buyer) {
		checkArguments(value, date, item_type,seller,buyer);


		_iva = ItemType.getItemTypeByName(item_type).getTAX()/100;
		_value =value;
		_date = date;
		_itemType = item_type;
		_seller = seller;
		_buyer = buyer ;
		_reference= new Timestamp(0).toString();
		_invoices.add(this);
	}
	
	private void checkArguments(float VALUE, LocalDate DATE, String ITEM_TYPE,Seller SELLER,Buyer BUYER) {
		
		if(DATE==null || ITEM_TYPE=="" || ITEM_TYPE==null || SELLER==null || BUYER==null) {
			throw new InvoiceException("1");
		}
		
		if(DATE.getYear() < 1970) {
			throw new InvoiceException("2");
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

	public float getIVA(){
		return this._iva;
	}
	
	public String getREFERENCE(){
		return this._reference;
	}
}

