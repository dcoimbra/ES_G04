package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class BrokerClientConstructorMethodTest {
	private static final int AGE = 20;
	private static final String NIF = "123456789"
	private static final String IBAN = "BK011234567";
	private static final BrokerClient client;

	@Before
	public void setUp() {
		this.client = new BrokerClient(IBAN, NIF, AGE);
	}

	@Test
	public void success() {
		Assert.assertEquals(20, this.client.getAge());
		Assert.assertEquals(IBAN, this.client.getIBAN());
		Assert.assertEquals(NIF, this.client.getNIF());
	}

	@Test
	public void successEqual18() {
		this.client = new Client(IBAN, NIF, 18);

		Assert.assertEquals(18, this.client.getAGE());
		Assert.assertEquals(IBAN, this.client.getIBAN());
		Assert.assertEquals(NIF, this.client.getNIF());
	}

	@Test(expected = BrokerException.class)
	public void lessThan18Age() {
		new BrokerClient(IBAN, NIF, 17);
	}

	@Test
	public void successEqual100() {
		this.client = new BrokerClient(IBAN,NIF,100);

		Assert.assertEquals(100, this.client.getAGE()));
		Assert.assertEquals(IBAN, this.client.getIBAN());
		Assert.assertEquals(300, this.client.getNIF());
	}

	@Test(expected = BrokerException.class)
	public void over100() {
		new BrokerClient(IBAN, NIF, 101);
	}

}
