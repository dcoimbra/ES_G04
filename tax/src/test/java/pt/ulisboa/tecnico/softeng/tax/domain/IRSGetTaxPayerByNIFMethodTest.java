package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxPayerException;

public class IRSGetTaxPayerByNIFMethodTest {

	@Before
	public void setUp() {
		IRS.getIRS()._taxpayers.clear();
		TaxPayer tp1 = new Buyer("123456789", "Jose Toni", "Quinta das Lagrimas");
	}

	@Test
	public void success() {
		IRS.getIRS().getTaxPayerByNIF("123456789");
	}

	@Test(expected =  TaxPayerException.class)
	public void noSuchTaxPayer() {
		IRS.getIRS().getTaxPayerByNIF("987654321");
	}

	@After
	public void tearDown() {

		IRS.getIRS()._taxpayers.clear();
	}
}