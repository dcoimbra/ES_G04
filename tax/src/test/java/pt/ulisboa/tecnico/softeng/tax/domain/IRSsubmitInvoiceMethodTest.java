package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxPayerException;


public class IRSsubmitInvoiceMethodTest {


	@Test
	public void success(){
		ItemType  it = new ItemType("Desporto", 50);
		Seller seller = new Seller("111111111", "Alberto", "Lisboa");
		Buyer buyer = new Buyer("222222222", "Campos", "Oeiras");
		InvoiceData invoiceData = new InvoiceData("111111111","222222222","Desporto", 5.5f,new LocalDate().now());

		IRS.getIRS().submitInvoice(invoiceData);

	}

	@Test(expected =  TaxPayerException.class)
	public void nullSeller(){
		ItemType  it = new ItemType("Desporto", 50);
		Buyer buyer = new Buyer("222222222", "Campos", "Oeiras");
		InvoiceData invoiceData = new InvoiceData(null,"222222222","Desporto", 5.5f,new LocalDate().now());

		IRS.getIRS().submitInvoice(invoiceData);
	}

	@Test(expected =  TaxPayerException.class)
	public void nullBuyer(){
		ItemType  it = new ItemType("Desporto", 50);
		Seller seller = new Seller("111111111", "Alberto", "Lisboa");
		InvoiceData invoiceData = new InvoiceData("111111111",null,"Desporto", 5.5f,new LocalDate().now());

		IRS.getIRS().submitInvoice(invoiceData);
	}

	@After 
	public void tearDown() {
		IRS._itemtypes.clear();
		IRS.getIRS()._taxpayers.clear();
	}
}