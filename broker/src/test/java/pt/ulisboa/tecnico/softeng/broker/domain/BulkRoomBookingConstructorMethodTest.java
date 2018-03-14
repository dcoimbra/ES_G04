package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class BulkRoomBookingConstructorMethodTest {

	private BulkRoomBooking _bulkRoomBooking;

	private static final int BOOKING_NUMBER = 10;
	private static final LocalDate  BOOKING_ARRIVAL = new LocalDate(2017, 02, 02);
	private static final LocalDate BOOKING_DEPARTURE = new LocalDate(2017, 02, 10);

	@Before
	public void setUp () {
		this._bulkRoomBooking = new BulkRoomBooking(BOOKING_NUMBER, BOOKING_ARRIVAL, BOOKING_DEPARTURE);
	}

	@Test
	public void success () {
		Assert.assertEquals(BOOKING_NUMBER, this._bulkRoomBooking.getNumber());
		Assert.assertEquals(BOOKING_ARRIVAL, this._bulkRoomBooking.getArrival());
		Assert.assertEquals(BOOKING_DEPARTURE, this._bulkRoomBooking.getDeparture());
	}

	@Test
	public void zeroNumber () {
		try {
			new BulkRoomBooking(0, BOOKING_ARRIVAL, BOOKING_DEPARTURE);
		} catch (BrokerException e) {
			Assert.fail();
		}
	}

	@Test
	public void negativeNumber () {
		try {
			new BulkRoomBooking(-BOOKING_NUMBER, BOOKING_ARRIVAL, BOOKING_DEPARTURE);
		} catch (BrokerException e) {
			Assert.fail();
		}
	}

	@Test
	public void nullArrivalDate () {
		try {
			new BulkRoomBooking(BOOKING_NUMBER, null, BOOKING_DEPARTURE);
		} catch (BrokerException e) {
			Assert.fail();
		}
	}

	@Test
	public void nullDepartureDate () {
		try {
			new BulkRoomBooking(BOOKING_NUMBER, BOOKING_ARRIVAL, null);
		} catch (BrokerException e) {
			Assert.fail();
		}
	}

	@Test
	public void nullBothDates () {
		try {
			new BulkRoomBooking(BOOKING_NUMBER, null, null);
		} catch (BrokerException e) {
			Assert.fail();
		}
	}

	@Test
	public void invalidDates () {
		try {
			new BulkRoomBooking(BOOKING_NUMBER, BOOKING_DEPARTURE, BOOKING_ARRIVAL);
		} catch (BrokerException e) {
			Assert.fail();
		}
	}
}