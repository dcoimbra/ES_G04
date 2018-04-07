package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;
import pt.ulisboa.tecnico.softeng.tax.domain.Buyer;
import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.Seller;

public class BrokerClientConstructorMethodTest {
	private static final int AGE = 20;
	private static final String NIF = "123456789";
	private static final String IBAN = "BK011234567";
	private static  Client client;

	private static final String SELLER_ADDRESS = "Somewhere";
	private static final String SELLER_NAME = "José Vendido";
	private static final String SELLER_NIF = "123456789";

	private static final String BUYER_ADDRESS = "Narnia";
	private static final String BUYER_NAME = "Jacinto Costa";
	private static final String BUYER_NIF = "987654321";
	private static final String DRIVING_LICENSE = "IMT1234";

	private Broker broker;

	IRS irs;

	@Before
	public void setUp() {

		this.irs = IRS.getIRS();

		Seller seller = new Seller(IRS.getIRS(), SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);
		Buyer buyer = new Buyer(IRS.getIRS(), BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);

		this.broker = new Broker("BR01", "eXtremeADVENTURE", SELLER_NIF, BUYER_NIF, IBAN);
		this.client = new Client(broker, IBAN, NIF, DRIVING_LICENSE ,AGE);
	}

	@Test
	public void success() {
		Assert.assertEquals(20, client.getAGE());
		Assert.assertEquals(IBAN, client.getIBAN());
		Assert.assertEquals(NIF, client.getNIF());
	}

	@Test (expected = BrokerException.class)
	public void nullBroker(){
		client = new Client(null, IBAN, NIF, DRIVING_LICENSE ,18);
	}

	@Test (expected = BrokerException.class)
	public void emptyIBAN(){
		client = new Client(broker, "", NIF, DRIVING_LICENSE ,18);
	}

	@Test (expected = BrokerException.class)
	public void nullIBAN(){
		client = new Client(broker, null, NIF, DRIVING_LICENSE ,18);
	}

	@Test (expected = BrokerException.class)
	public void nullNIF(){
		client = new Client(broker, IBAN, null, DRIVING_LICENSE ,18);
	}

	@Test (expected = BrokerException.class)
	public void wrongNIF(){
		client = new Client(broker, IBAN, "1234", DRIVING_LICENSE ,18);
	}

	@Test (expected = BrokerException.class)
	public void wrongDrivingLicense(){
		client = new Client(broker, IBAN, NIF, "Narnia" ,18);
	}

	@Test (expected = BrokerException.class)
	public void nullLicense(){
		client = new Client(broker, IBAN, NIF, null ,18);
	}


	@Test
	public void successEqual18() {
		client = new Client(broker, IBAN, NIF, DRIVING_LICENSE ,18);

		Assert.assertEquals(18, client.getAGE());
		Assert.assertEquals(IBAN, client.getIBAN());
		Assert.assertEquals(NIF, client.getNIF());
	}

	@Test(expected = BrokerException.class)
	public void lessThan18Age() {
		new Client(broker, IBAN, NIF, DRIVING_LICENSE ,17);
	}

	@Test
	public void successEqual100() {
		client = new Client(broker, IBAN,NIF,DRIVING_LICENSE,100);

		Assert.assertEquals(100, client.getAGE());
		Assert.assertEquals(IBAN, client.getIBAN());
		Assert.assertEquals(NIF, client.getNIF());
	}

	@Test(expected = BrokerException.class)
	public void over100() {
		new Client(broker, IBAN, NIF, DRIVING_LICENSE,101);
	}

}
