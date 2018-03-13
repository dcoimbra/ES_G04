package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;


public class AdventureSetStateMethodTest {

	private static final LocalDate BOOKING_ARRIVAL = new LocalDate(2017, 02, 02);
	private static final LocalDate BOOKING_DEPARTURE = new LocalDate(2017, 02, 10);
	private static final int AGE = 20;
	private static final int AMOUNT = 300;
	private static final String IBAN = "BK011234567";

	private Broker _broker;
	private Adventure _adventure;

	@Before
	public void setUp() {
		this._broker = new Broker("BR01", "eXtremeADVENTURE");
		this._adventure = new Adventure(this._broker, BOOKING_ARRIVAL, BOOKING_DEPARTURE, AGE, IBAN, AMOUNT);
	}

	@Test
	public void setStateUndo() {
		this._adventure.setState(Adventure.State.UNDO);
		Assert.assertEquals(Adventure.State.UNDO, this._adventure.getState());
	}

	@Test
	public void setStateCancelled() {
		this._adventure.setState(Adventure.State.CANCELLED);
		Assert.assertEquals(Adventure.State.CANCELLED, this._adventure.getState());
	}

	@After
	public void tearDown () {
		Broker.brokers.clear();
	}
}