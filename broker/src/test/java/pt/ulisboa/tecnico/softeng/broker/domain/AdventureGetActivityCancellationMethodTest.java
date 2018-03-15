package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Assert;

public class AdventureGetActivityCancellationMethodTest {

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

		this._adventure.setActivityCancellation("Cancelled Activity");
	}

	@Test
	public void success() {
		Assert.assertEquals("Cancelled Activity", this._adventure.getActivityCancellation());
	}

	@After
	public void tearDown() {
		Broker.brokers.clear();
	}
}