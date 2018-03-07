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
	public void setup () {
		this._broker = new Broker("BR01", "WeExplore");
		this._broker.bulkBooking(BOOKING_NUMBER, BOOKING_ARRIVAL, BOOKING_DEPARTURE);
	}

	@Test(expected = BrokerException.class)
	public void zeroNumber () {
		this._broker.bulkBooking(0, BOOKING_ARRIVAL, BOOKING_DEPARTURE);
	}

	@Test(expected = BrokerException.class)
	public void negativeNumber () {
		this._broker.bulkBooking(-BOOKING_NUMBER, BOOKING_ARRIVAL, BOOKING_DEPARTURE);
	}

	@Test(expected = BrokerException.class)
	public void nullArrivalDate () {
		this._broker.bulkBooking(BOOKING_NUMBER, null, BOOKING_DEPARTURE);
	}

	@Test(expected = BrokerException.class)
	public void nullDepartureDate () {
		this._broker.bulkBooking(BOOKING_NUMBER, BOOKING_ARRIVAL, null);
	}

	@Test(expected = BrokerException.class)
	public void nullBothDates () {
		this._broker.bulkBooking(BOOKING_NUMBER, null, null);
	}

	//Tests if Arrival Date is before than Departure Date
	@Test(expected = BrokerException.class)
	public void invalidDates () {
		this._broker.bulkBooking(BOOKING_NUMBER, BOOKING_DEPARTURE, BOOKING_ARRIVAL);
	}
}