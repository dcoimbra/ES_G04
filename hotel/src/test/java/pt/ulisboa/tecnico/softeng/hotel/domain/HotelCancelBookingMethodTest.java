package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;

@RunWith(JMockit.class)
public class HotelCancelBookingMethodTest {
	private static final String IBAN = "BK01987654321";
	private static final String NIF = "123456789";
	private static final double PRICE_SINGLE = 20.0;
	private static final double PRICE_DOUBLE = 30.0;
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 21);
	private Hotel hotel;
	private Room room;
	private Booking booking;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Paris", "NIF", IBAN, PRICE_SINGLE, PRICE_DOUBLE);
		this.room = new Room(this.hotel, "01", Type.DOUBLE);
		this.booking = this.room.reserve(Type.DOUBLE, this.arrival, this.departure, NIF, IBAN);
	}

	@Test
	public void success(@Mocked final TaxInterface taxInterface, @Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);

				TaxInterface.submitInvoice((InvoiceData) this.any);
			}
		};
		
		this.room = new Room(this.hotel, "02", Type.DOUBLE);

		String reference = Hotel.reserveRoom(Type.DOUBLE, this.arrival, this.departure, NIF, IBAN);
		String cancel = Hotel.cancelBooking(reference);

		assertTrue(this.room.getBooking(reference).isCancelled());
		assertEquals(cancel, this.room.getBooking(reference).getCancellation());
	}

	@Test(expected = HotelException.class)
	public void doesNotExist(@Mocked final TaxInterface taxInterface, @Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);

				TaxInterface.submitInvoice((InvoiceData) this.any);
			}
		};

		this.hotel.getProcessor().submitBooking(new Booking(Type.DOUBLE, this.hotel, this.arrival, this.departure, NIF, IBAN));

		Hotel.cancelBooking("XPTO");
	}
	
	@Test(expected = HotelException.class)
	public void nullReference(@Mocked final TaxInterface taxInterface, @Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);

				TaxInterface.submitInvoice((InvoiceData) this.any);
			}
		};
		
		this.hotel.getProcessor().submitBooking(new Booking(Type.DOUBLE, this.hotel, this.arrival, this.departure, NIF, IBAN));
		Hotel.cancelBooking(null);
	}

	@Test(expected = HotelException.class)
	public void emptyReference(@Mocked final TaxInterface taxInterface, @Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);

				TaxInterface.submitInvoice((InvoiceData) this.any);
			}
		};
		
		this.hotel.getProcessor().submitBooking(new Booking(Type.DOUBLE, this.hotel, this.arrival, this.departure, NIF, IBAN));
		Hotel.cancelBooking("");
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
}
