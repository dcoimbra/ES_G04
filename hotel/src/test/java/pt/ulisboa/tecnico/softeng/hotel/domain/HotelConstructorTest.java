package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelConstructorTest {
	private static final String HOTEL_NAME = "Londres";
	private static final String HOTEL_CODE = "XPTO123";
	private static final String IBAN = "IBAN";
	private static final String NIF = "NIF";

	@Test
	public void success() {
		Hotel hotel = new Hotel(HOTEL_CODE, HOTEL_NAME, NIF, IBAN);

		Assert.assertEquals(HOTEL_NAME, hotel.getName());
		Assert.assertTrue(hotel.getCode().length() == Hotel.CODE_SIZE);
		Assert.assertEquals(0, hotel.getNumberOfRooms());
		Assert.assertEquals(NIF, hotel.getNif());
		Assert.assertEquals(IBAN, hotel.getIban());
		Assert.assertEquals(1, Hotel.hotels.size());
	}

	@Test(expected = HotelException.class)
	public void nullCode() {
		new Hotel(null, HOTEL_NAME, NIF, IBAN);
	}

	@Test(expected = HotelException.class)
	public void blankCode() {
		new Hotel("      ", HOTEL_NAME, NIF, IBAN);
	}

	@Test(expected = HotelException.class)
	public void emptyCode() {
		new Hotel("", HOTEL_NAME, NIF, IBAN);
	}

	@Test(expected = HotelException.class)
	public void nullName() {
		new Hotel(HOTEL_CODE, null, NIF, IBAN);
	}

	@Test(expected = HotelException.class)
	public void blankName() {
		new Hotel(HOTEL_CODE, "  ", NIF, IBAN);
	}

	@Test(expected = HotelException.class)
	public void emptyName() {
		new Hotel(HOTEL_CODE, "", NIF, IBAN);
	}

	@Test(expected = HotelException.class)
	public void codeSizeLess() {
		new Hotel("123456", HOTEL_NAME, NIF, IBAN);
	}

	@Test(expected = HotelException.class)
	public void codeSizeMore() {
		new Hotel("12345678", HOTEL_NAME, NIF, IBAN);
	}

	@Test(expected = HotelException.class)
	public void codeNotUnique() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIF, IBAN);
		new Hotel(HOTEL_CODE, HOTEL_NAME + " City", NIF, IBAN);
	}

	@Test
	public void noteUniqueNIF() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIF, IBAN);

		try {
			new Hotel("123456", "jdgdsk", NIF, IBAN);
			Assert.fail();
		} catch (HotelException ae) {
			Assert.assertEquals(1, Hotel.hotels.size());
		}
	}

	@Test(expected = HotelException.class)
	public void nullNIF() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, null, IBAN);
	}

	@Test(expected = HotelException.class)
	public void emptyNIF() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, "   ", IBAN);
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
