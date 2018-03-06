package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxPayerException;

public class BuyerConstructorTest {

	private static final String BUYER_NIF = "123456789";
	private static final String BUYER_NAME = "Jose Toni";
	private static final String BUYER_ADDRESS = "Quinta das Lagrimas";


	@Test
	public void success() {

		Buyer buyer = new Buyer(BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);

		Assert.assertEquals(BUYER_NIF, buyer.getNIF());
		Assert.assertEquals(BUYER_NAME, buyer.getNAME());
		Assert.assertEquals(BUYER_ADDRESS, buyer.getADDRESS());
	}

	@Test(expected = TaxPayerException.class)
	public void inconsistentNIFSmaller() {
		new Buyer("12345678", BUYER_NAME, BUYER_ADDRESS);
	}

	@Test(expected = TaxPayerException.class)
	public void inconsistentNIFBigger() {
		new Buyer("1234567891", BUYER_NAME, BUYER_ADDRESS);
	}

	@Test
	public void notUniqueNIF() {
		new Buyer(BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);
		try {
			new Buyer(BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);
			Assert.fail();
		} catch (TaxPayerException tpe) {
			Assert.assertEquals(1, TaxPayer._taxpayers.size());
		}
	}

	@Test(expected = TaxPayerException.class)
	public void nullName() {
		new Buyer(BUYER_NIF, null, BUYER_ADDRESS);
	}

	@Test(expected = TaxPayerException.class)
	public void nullAddress() {
		new Buyer(BUYER_NIF, BUYER_NAME, null);
	}


	@Test(expected = TaxPayerException.class)
	public void voidName() {
		new Buyer(BUYER_NIF, "", BUYER_ADDRESS);
	}

	@Test(expected = TaxPayerException.class)
	public void voidAddress() {
		new Buyer(BUYER_NIF, BUYER_NAME, "");
	}


	@After 
	public void tearDown() {

		TaxPayer._taxpayers.clear();
	}
}
