package pt.ulisboa.tecnico.softeng.tax.dataobjects;

import org.joda.time.LocalDate;



public class InvoiceData {
	private String _sellerNIF;
	private String _buyerNIF;
	private String _itemType;
	private float _value;
	private LocalDate _date;

	public InvoiceData() {
	}

	public InvoiceData(String sellerNIF, String buyerNIF, String itemType, float value, LocalDate date) {
		this._sellerNIF = sellerNIF;
		this._buyerNIF = buyerNIF;
		this._itemType = itemType;
		this._value = value;
		this._date = date;
	}

	public String getSellerNIF() {
		return this._sellerNIF;
	}

	public void setSellerNIF(String sellerNIF) {
		this._sellerNIF = sellerNIF;
	}

	public String getBuyerNIF() {
		return this._buyerNIF;
	}

	public void setBuyerNIF(String buyerNIF) {
		this._buyerNIF = buyerNIF;
	}

	public String getItemType() {
		return this._itemType;
	}

	public void setItemType(String itemType) {
		this._itemType = itemType;
	}

	public float getValue() {
		return this._value;
	}

	public void setValue(float value) {
		this._value = value;
	}

	public LocalDate getDate() {
		return this._date;
	}

	public void setDate(LocalDate date) {
		this._date= date;
	}
}
