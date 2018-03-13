package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;

public class BulkRoomBookingProcessBookingTest {

	private BulkRoomBooking _bulkRoomBooking;

	private static final int BOOKING_NUMBER = 2;
	private static final LocalDate BOOKING_ARRIVAL = new LocalDate(2017, 02, 02);
	private static final LocalDate BOOKING_DEPARTURE = new LocalDate(2017, 02, 10);

	@Before
	public void setUp () {
		this._bulkRoomBooking = new BulkRoomBooking(BOOKING_NUMBER, BOOKING_ARRIVAL, BOOKING_DEPARTURE);
	}

	@Test
	public void success () {
		Hotel hotel = new Hotel("XPTO234", "Lisboa");
		Room room = new Room(hotel, "01", Type.SINGLE);
		Room room2 = new Room(hotel, "02", Type.SINGLE);

		Assert.assertEquals(0, this._bulkRoomBooking.getReferences().size());

		this._bulkRoomBooking.processBooking();

		Assert.assertNotEquals(0, this._bulkRoomBooking.getReferences().size());
	}

	@Test
	public void cancelledBooking () {
		this._bulkRoomBooking.processBooking();
		this._bulkRoomBooking.processBooking();
		this._bulkRoomBooking.processBooking();
		this._bulkRoomBooking.processBooking();
	}

	@After
	public void tearDown () {
		this._bulkRoomBooking.getReferences().clear();
		Hotel.hotels.clear();
	}
}