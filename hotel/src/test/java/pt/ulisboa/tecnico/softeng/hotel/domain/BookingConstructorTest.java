package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;

public class BookingConstructorTest {
	private static final String IBAN = "IBAN";
	private static final String NIF = "123456789";
	private static final double PRICE_SINGLE = 20.0;
	private static final double PRICE_DOUBLE = 30.0;
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 21);
	private Hotel hotel;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Londres", "NIF", IBAN, PRICE_SINGLE, PRICE_DOUBLE);
	}

	@Test
	public void success() {
		Booking booking = new Booking(Type.DOUBLE, this.hotel, this.arrival, this.departure, NIF);

		Assert.assertTrue(booking.getReference().startsWith(this.hotel.getCode()));
		Assert.assertTrue(booking.getReference().length() > Hotel.CODE_SIZE);
		Assert.assertEquals(this.arrival, booking.getArrival());
		Assert.assertEquals(this.departure, booking.getDeparture());
	}

	@Test(expected = HotelException.class)
	public void nullType() {
		new Booking(null, this.hotel, this.arrival, this.departure, NIF);
	}
	
	@Test(expected = HotelException.class)
	public void nullHotel() {
		new Booking(Type.DOUBLE, null, this.arrival, this.departure, NIF);
	}

	@Test(expected = HotelException.class)
	public void nullArrival() {
		new Booking(Type.DOUBLE, this.hotel, null, this.departure, NIF);
	}

	@Test(expected = HotelException.class)
	public void nullDeparture() {
		new Booking(Type.DOUBLE, this.hotel, this.arrival, null, NIF);
	}

	@Test(expected = HotelException.class)
	public void departureBeforeArrival() {
		new Booking(Type.DOUBLE, this.hotel, this.arrival, this.arrival.minusDays(1), NIF);
	}

	@Test
	public void arrivalEqualDeparture() {
		new Booking(Type.DOUBLE, this.hotel, this.arrival, this.arrival, NIF);
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
