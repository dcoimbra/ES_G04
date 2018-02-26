package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TaxPayerConstructorTest {

	private static final String TAXPAYER_NIF = "123456789";
	private static final String TAXPAYER_NAME = "Jose Toni";
	private static final String TAXPAYER_ADDRESS = "Quinta das Lagrimas";


	@Test
	public void success() {

		TaxPayer taxpayer = new TaxPayer(TAXPAYER_NIF, TAXPAYER_NAME, TAXPAYER_ADDRESS);

		Assert.assertEquals(TAXPAYER_NIF, taxpayer.getNIF());
		Assert.assertEquals(TAXPAYER_NAME, taxpayer.getName());
		Assert.assertEquals(TAXPAYER_ADDRESS, taxpayer.getAddress());
	}

	@Test(expected = TaxPayerException.class)
	public void inconsistentNIFSmaller() {
		new TaxPayer("12345678", TAXPAYER_NAME, TAXPAYER_ADDRESS);
	}

	@Test(expected = TaxPayerException.class)
	public void inconsistentNIFBigger() {
		new TaxPayer("1234567891", TAXPAYER_NAME, TAXPAYER_ADDRESS);
	}

	@Test
	public void notUniqueNIF() {
		new TaxPayer(TAXPAYER_NIF, TAXPAYER_NAME, TAXPAYER_ADDRESS);
		try {
			new TaxPayer(TAXPAYER_NIF, TAXPAYER_NAME, TAXPAYER_ADDRESS);
			Assert.fail();
		} catch (TaxPayerException tpe) {
			Assert.assertEquals(1, TaxPayer.taxpayers.size());
		}
	}
}
