 package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.domain.Booking;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;

public class BulkRoomBookingGetReferenceTest {

	private Hotel _hotel;
	private Room _room;
	private Booking _booking;
	private BulkRoomBooking _bulkRoomBooking;

	private static final int BOOKING_NUMBER = 1;
	private static final LocalDate BOOKING_ARRIVAL = new LocalDate(2017, 02, 02);
	private static final LocalDate BOOKING_DEPARTURE = new LocalDate(2017, 02, 10);

	@Before
	public void setUp() {
		this._hotel = new Hotel("XPTO345", "Lisboa");
	}

	@Test
	public void success () {
		this._room = new Room(this._hotel, "01", Type.SINGLE);
		this._bulkRoomBooking = new BulkRoomBooking(BOOKING_NUMBER, BOOKING_ARRIVAL, BOOKING_DEPARTURE);
		this._bulkRoomBooking.processBooking();

		Assert.assertNotEquals(0, this._bulkRoomBooking.getReferences().size());
		Assert.assertEquals("XPTO3451", this._bulkRoomBooking.getReference("SINGLE"));
		Assert.assertEquals(0, this._bulkRoomBooking.getReferences().size());
	}

	@Test
	public void invalidReference () {
		this._room = new Room(this._hotel, "01", Type.SINGLE);
		this._bulkRoomBooking = new BulkRoomBooking(BOOKING_NUMBER, BOOKING_ARRIVAL, BOOKING_DEPARTURE);
		this._bulkRoomBooking.processBooking();

		Assert.assertNull(this._bulkRoomBooking.getReference("ABCDEF"));
	}

	@Test
	public void cancelledBooking () {
		this._room = new Room(this._hotel, "01", Type.SINGLE);
		this._bulkRoomBooking = new BulkRoomBooking(-1, BOOKING_ARRIVAL, BOOKING_DEPARTURE);

		this._bulkRoomBooking.processBooking();
		this._bulkRoomBooking.processBooking();
		this._bulkRoomBooking.processBooking();

		Assert.assertNull(this._bulkRoomBooking.getReference("SINGLE"));
	}

	@After
	public void tearDown () {
		this._bulkRoomBooking.getReferences().clear();
		Hotel.hotels.clear();
	}
}