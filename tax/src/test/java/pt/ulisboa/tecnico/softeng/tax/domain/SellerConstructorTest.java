package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxPayerException;

public class SellerConstructorTest {

	private static final String SELLER_NIF = "123456789";
	private static final String SELLER_NAME = "Jose Toni";
	private static final String SELLER_ADDRESS = "Quinta das Lagrimas";


	@Test
	public void success() {

		Seller seller = new Seller(SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);

		Assert.assertEquals(SELLER_NIF, seller.getNIF());
		Assert.assertEquals(SELLER_NAME, seller.getNAME());
		Assert.assertEquals(SELLER_ADDRESS, seller.getADDRESS());
	}

	@Test(expected = TaxPayerException.class)
	public void inconsistentNIFSmaller() {
		new Seller("12345678", SELLER_NAME, SELLER_ADDRESS);
	}

	@Test(expected = TaxPayerException.class)
	public void inconsistentNIFBigger() {
		new Seller("1234567891", SELLER_NAME, SELLER_ADDRESS);
	}

	@Test
	public void notUniqueNIF() {
		new Seller(SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);
		try {
			new Seller(SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);
			Assert.fail();
		} catch (TaxPayerException se) {
			Assert.assertEquals(1, IRS.getIRS()._taxpayers.size());
		}
	}

	@After 
	public void tearDown() {

		IRS.getIRS()._taxpayers.clear();
	}
}
