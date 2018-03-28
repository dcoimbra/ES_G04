package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class BrokerClientConstructorMethodTest {
	private static final int AGE = 20;
	private static final String NIF = "123456789";
	private static final String IBAN = "BK011234567";
	private static  BrokerClient client;

	@Before
	public void setUp() {
		client = new BrokerClient(IBAN, NIF, AGE);
	}

	@Test
	public void success() {
		Assert.assertEquals(20, client.getAGE());
		Assert.assertEquals(IBAN, client.getIBAN());
		Assert.assertEquals(NIF, client.getNIF());
	}

	@Test
	public void successEqual18() {
		client = new BrokerClient(IBAN, NIF, 18);

		Assert.assertEquals(18, client.getAGE());
		Assert.assertEquals(IBAN, client.getIBAN());
		Assert.assertEquals(NIF, client.getNIF());
	}

	@Test(expected = BrokerException.class)
	public void lessThan18Age() {
		new BrokerClient(IBAN, NIF, 17);
	}

	@Test
	public void successEqual100() {
		client = new BrokerClient(IBAN,NIF,100);

		Assert.assertEquals(100, client.getAGE());
		Assert.assertEquals(IBAN, client.getIBAN());
		Assert.assertEquals(300, client.getNIF());
	}

	@Test(expected = BrokerException.class)
	public void over100() {
		new BrokerClient(IBAN, NIF, 101);
	}

}
