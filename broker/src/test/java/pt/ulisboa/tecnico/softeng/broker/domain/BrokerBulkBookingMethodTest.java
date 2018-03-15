 package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class BrokerBulkBookingMethodTest {

	private Broker _broker;

	private static final int BOOKING_NUMBER = 10;
	private static final LocalDate BOOKING_ARRIVAL = new LocalDate(2017, 02, 02);
	private static final LocalDate BOOKING_DEPARTURE = new LocalDate(2017, 02, 10);

	@Before
	public void setUp () {
		this._broker = new Broker("BR01", "WeExplore");
	}

	@Test
	public void success () {
		this._broker.bulkBooking(BOOKING_NUMBER, BOOKING_ARRIVAL, BOOKING_DEPARTURE);
	}

	@Test
	public void zeroNumber () {
		try {
			this._broker.bulkBooking(0, BOOKING_ARRIVAL, BOOKING_DEPARTURE);
		} catch (BrokerException e) {
			Assert.fail();
		}
	}

	@Test
	public void negativeNumber () {
		try {
			this._broker.bulkBooking(-BOOKING_NUMBER, BOOKING_ARRIVAL, BOOKING_DEPARTURE);
		} catch (BrokerException e) {
			Assert.fail();
		}
	}

	@Test
	public void nullArrivalDate () {
		try {
			this._broker.bulkBooking(BOOKING_NUMBER, null, BOOKING_DEPARTURE);
		} catch (BrokerException e) {
			Assert.fail();
		}
	}

	@Test
	public void nullDepartureDate () {
		try {
			this._broker.bulkBooking(BOOKING_NUMBER, BOOKING_ARRIVAL, null);
		} catch (BrokerException e) {
			Assert.fail();
		}
	}

	@Test
	public void nullBothDates () {
		try {
			this._broker.bulkBooking(BOOKING_NUMBER, null, null);
		} catch (BrokerException e) {
			Assert.fail();
		}
	}

	@Test
	public void invalidDates () {
		try {
			this._broker.bulkBooking(BOOKING_NUMBER, BOOKING_DEPARTURE, BOOKING_ARRIVAL);
		} catch (BrokerException e) {
			Assert.fail();
		}
	}
	
	@After
	public void tearDown() {
		Broker.brokers.clear();
	}
}