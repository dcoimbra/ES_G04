package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.bank.exception.SellerException;

public class SellerConstructorTest {

	private static final String SELLER_NIF = "123456789";
	private static final String SELLER_NAME = "Jose Toni";
	private static final String SELLER_ADDRESS = "Quinta das Lagrimas";


	@Test
	public void success() {

		Seller seller = new Seller(SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);

		Assert.assertEquals(SELLER_NIF, seller.getNIF());
		Assert.assertEquals(SELLER_NAME, seller.getName());
		Assert.assertEquals(SELLER_ADDRESS, seller.getAddress());
	}

	@Test(expected = SellerException.class)
	public void inconsistentNIFSmaller() {
		new Seller("12345678", SELLER_NAME, SELLER_ADDRESS);
	}

	@Test(expected = SellerException.class)
	public void inconsistentNIFBigger() {
		new Seller("1234567891", SELLER_NAME, SELLER_ADDRESS);
	}

	@Test
	public void notUniqueNIF() {
		new Seller(SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);
		try {
			new Seller(SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);
			Assert.fail();
		} catch (SellerException se) {
			Assert.assertEquals(1, TaxPayer.taxpayers.size());
		}
	}

	@After 
	public void tearDown() {

		TaxPayer.taxpayers.clear();
	}
}
