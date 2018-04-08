package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;
import pt.ulisboa.tecnico.softeng.tax.domain.Buyer;
import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.Seller;

public class BrokerConstructorMethodTest {

	private static final String SELLER_ADDRESS = "Somewhere";
	private static final String SELLER_NAME = "José Vendido";
	private static final String SELLER_NIF = "123456789";

	private static final String IBAN = "BK011234567";

	private static final String BUYER_ADDRESS = "Narnia";
	private static final String BUYER_NAME = "Jacinto Costa";
	private static final String BUYER_NIF = "987654321";

	IRS irs;

	@Before
    public void setUp() {

	    this.irs = IRS.getIRS();
    }

	@Test
	public void success() {

		Seller seller = new Seller(IRS.getIRS(), SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);
		Buyer buyer = new Buyer(IRS.getIRS(), BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);

		Broker broker = new Broker("BR01", "WeExplore", SELLER_NIF, BUYER_NIF, IBAN);

		Assert.assertEquals("BR01", broker.getCode());
		Assert.assertEquals("WeExplore", broker.getName());
		Assert.assertEquals(0, broker.getNumberOfAdventures());
		Assert.assertTrue(Broker.brokers.contains(broker));
	}

	@Test
	public void nullCode() {
		try {
			new Broker(null, "WeExplore", SELLER_NIF, BUYER_NIF, IBAN);
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptyCode() {
		try {
			new Broker("", "WeExplore", SELLER_NIF, BUYER_NIF, IBAN);
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankCode() {
		try {
			new Broker("  ", "WeExplore", SELLER_NIF, BUYER_NIF, IBAN);
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void uniqueCode() {
		Broker broker = new Broker("BR01", "WeExplore", SELLER_NIF, BUYER_NIF, IBAN);

		try {
			new Broker("BR01", "WeExploreX", SELLER_NIF, BUYER_NIF, IBAN);
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(1, Broker.brokers.size());
			Assert.assertTrue(Broker.brokers.contains(broker));
		}
	}

	@Test
	public void nullName() {
		try {
			new Broker("BR01", null, SELLER_NIF, BUYER_NIF, IBAN);
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptyName() {
		try {
			new Broker("BR01", "", SELLER_NIF, BUYER_NIF, IBAN);
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankName() {
		try {
			new Broker("BR01", "    ", SELLER_NIF, BUYER_NIF, IBAN);
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void nullSeller() {
		try {
			new Broker("BR01", "WeExplore", null, BUYER_NIF, IBAN);
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankSeller() {
		try {
			new Broker("BR01", "WeExplore", "    ", BUYER_NIF, IBAN);
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptySeller() {
		try {
			new Broker("BR01", "WeExplore", "", BUYER_NIF, IBAN);
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@After
	public void tearDown() {

        IRS.getIRS().clearAll();
		Broker.brokers.clear();
	}

}
