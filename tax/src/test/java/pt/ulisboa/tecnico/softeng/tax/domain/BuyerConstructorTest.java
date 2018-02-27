package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BuyerConstructorTest {

	private static final String BUYER_NIF = "123456789";
	private static final String BUYER_NAME = "Jose Toni";
	private static final String BUYER_ADDRESS = "Quinta das Lagrimas";


	@Test
	public void success() {

		Buyer buyer = new Buyer(BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);

		Assert.assertEquals(BUYER_NIF, buyer.getNIF());
		Assert.assertEquals(BUYER_NAME, buyer.getName());
		Assert.assertEquals(BUYER_ADDRESS, buyer.getAddress());
	}

	@Test(expected = BuyerException.class)
	public void inconsistentNIFSmaller() {
		new Buyer("12345678", BUYER_NAME, BUYER_ADDRESS);
	}

	@Test(expected = BuyerException.class)
	public void inconsistentNIFBigger() {
		new Buyer("1234567891", BUYER_NAME, BUYER_ADDRESS);
	}

	@Test
	public void notUniqueNIF() {
		new Buyer(BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);
		try {
			new Buyer(BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);
			Assert.fail();
		} catch (BuyerException be) {
			Assert.assertEquals(1, TaxPayer.taxpayers.size());
		}
	}

	@After 
	public void tearDown() {

		TaxPayer.taxpayers.clear();
	}
}
