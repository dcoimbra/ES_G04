package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

@RunWith(JMockit.class)
public class HotelHasVacancyMethodTest {
	private static final String IBAN = "IBAN";
	private static final String NIF = "123456789";
	private static final double PRICE_SINGLE = 20.0;
	private static final double PRICE_DOUBLE = 30.0;
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 21);
	private Hotel hotel;
	private Room room;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Paris", "NIF", IBAN, PRICE_SINGLE, PRICE_DOUBLE);
		this.room = new Room(this.hotel, "01", Type.DOUBLE);
	}
	@Test
	public void hasVacancy() {
		Room room = this.hotel.hasVacancy(Type.DOUBLE, this.arrival, this.departure);

		Assert.assertNotNull(room);
		Assert.assertEquals("01", room.getNumber());
	}

	@Test
	public void noVacancy() {
		this.room.reserve(Type.DOUBLE, this.arrival, this.departure, NIF);

		assertNull(this.hotel.hasVacancy(Type.DOUBLE, this.arrival, this.departure));
	}

	@Test
	public void noVacancyEmptyRoomSet() {
		Hotel otherHotel = new Hotel("XPTO124", "Paris Germain", "NIF", IBAN, PRICE_SINGLE, PRICE_DOUBLE);

		assertNull(otherHotel.hasVacancy(Type.DOUBLE, this.arrival, this.departure));
	}

	@Test(expected = HotelException.class)
	public void nullType() {
		this.hotel.hasVacancy(null, this.arrival, this.departure);
	}

	@Test(expected = HotelException.class)
	public void nullArrival() {
		this.hotel.hasVacancy(Type.DOUBLE, null, this.departure);
	}

	@Test(expected = HotelException.class)
	public void nullDeparture() {
		this.hotel.hasVacancy(Type.DOUBLE, this.arrival, null);
	}
	
	@Test
	public void hasCancelledBookings(@Mocked final TaxInterface taxInterface,
			@Mocked final BankInterface bankInterface) {
		
		this.room = new Room(this.hotel, "02", Type.DOUBLE);
		
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);

				TaxInterface.submitInvoice((InvoiceData) this.any);
			}
		};

		Hotel.reserveRoom(Type.DOUBLE, this.arrival, this.departure, NIF);
		
		String reference = Hotel.reserveRoom(Type.DOUBLE, this.arrival, this.departure, NIF);
		Hotel.cancelBooking(reference);

		Assert.assertNotNull(this.hotel.hasVacancy(Type.DOUBLE, arrival, arrival));
	}

	@Test
	public void hasCancelledBookingsButFull(@Mocked final TaxInterface taxInterface,
			@Mocked final BankInterface bankInterface) {
		
		Room room2 = new Room(this.hotel, "02", Type.DOUBLE);
		
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);

				TaxInterface.submitInvoice((InvoiceData) this.any);
			}
		};
		
		Hotel.reserveRoom(Type.DOUBLE, this.arrival, this.departure, NIF);
		
		String reference = Hotel.reserveRoom(Type.DOUBLE, this.arrival, this.departure, NIF);
		Hotel.cancelBooking(reference);
		
		Hotel.reserveRoom(Type.DOUBLE, this.arrival, this.departure, NIF);
		
		Assert.assertNull(this.hotel.hasVacancy(Type.DOUBLE, arrival, arrival));
	}

	@After
	public void tearDown() {
		this.hotel.removeRooms();
		Hotel.hotels.clear();
	}

}
