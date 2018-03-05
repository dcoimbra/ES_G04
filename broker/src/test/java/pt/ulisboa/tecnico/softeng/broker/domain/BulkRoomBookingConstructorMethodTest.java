package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class BulkRoomBookingConstructorMethodTest {

	private static final int BOOKING_NUMBER = 10;
	private static final LocalDate  BOOKING_ARRIVAL = new LocalDate(2017, 02, 02);
	private static final LocalDate BOOKING_DEPARTURE = new LocalDate(2017, 02, 10);

	@Test
	public void success () {
		BulkRoomBooking bulkRoomBooking = new BulkRoomBooking(BOOKING_NUMBER, BOOKING_ARRIVAL, BOOKING_DEPARTURE);

		Assert.assertEquals(BOOKING_NUMBER, bulkRoomBooking.getNumber());
		Assert.assertEquals(BOOKING_ARRIVAL, bulkRoomBooking.getArrival());
		Assert.assertEquals(BOOKING_DEPARTURE, bulkRoomBooking.getDeparture());
	}

	@Test(expected = BrokerException.class)
	public void zeroNumber () {
		new BulkRoomBooking(0, BOOKING_ARRIVAL, BOOKING_DEPARTURE);
	}

	@Test(expected = BrokerException.class)
	public void negativeNumber () {
		new BulkRoomBooking(-BOOKING_NUMBER, BOOKING_ARRIVAL, BOOKING_DEPARTURE);
	}

	@Test(expected = BrokerException.class)
	public void nullArrivalDate () {
		new BulkRoomBooking(BOOKING_NUMBER, null, BOOKING_DEPARTURE);
	}

	@Test(expected = BrokerException.class)
	public void nullDepartureDate () {
		new BulkRoomBooking(BOOKING_NUMBER, BOOKING_ARRIVAL, null);
	}

	@Test(expected = BrokerException.class)
	public void nullBothDates () {
		new BulkRoomBooking(BOOKING_NUMBER, null, null);
	}

	//Tests if Arrival Date is before than Departure Date
	@Test(expected = BrokerException.class)
	public void invalidDates () {
		new BulkRoomBooking(BOOKING_NUMBER, BOOKING_DEPARTURE, BOOKING_ARRIVAL);
	}

	@After
	public void tearDown() {

	}
}