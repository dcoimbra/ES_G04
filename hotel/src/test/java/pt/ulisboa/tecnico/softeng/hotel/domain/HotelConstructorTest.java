package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelConstructorTest {
	private static final String HOTEL_NAME = "Londres";
	private static final String HOTEL_CODE = "XPTO123";
	private static final double PRICE_SINGLE = 20.0;
	private static final double PRICE_DOUBLE = 30.0;

	@Test
	public void success() {
		Hotel hotel = new Hotel(HOTEL_CODE, HOTEL_NAME, "NIF", "IBAN", PRICE_SINGLE, PRICE_DOUBLE);

		Assert.assertEquals(HOTEL_NAME, hotel.getName());
		Assert.assertTrue(hotel.getCode().length() == Hotel.CODE_SIZE);
		Assert.assertEquals(0, hotel.getNumberOfRooms());
		Assert.assertEquals("NIF", hotel.getNif());
		Assert.assertEquals("IBAN", hotel.getIban());
		Assert.assertEquals(1, Hotel.hotels.size());
		Assert.assertEquals(PRICE_SINGLE, hotel.getPriceSingle(), 0.0);
		Assert.assertEquals(PRICE_DOUBLE, hotel.getPriceDouble(), 0.0);
	}

	@Test(expected = HotelException.class)
	public void nullCode() {
		new Hotel(null, HOTEL_NAME, "NIF", "IBAN", PRICE_SINGLE, PRICE_DOUBLE);
	}

	@Test(expected = HotelException.class)
	public void blankCode() {
		new Hotel("      ", HOTEL_NAME, "NIF", "IBAN", PRICE_SINGLE, PRICE_DOUBLE);
	}

	@Test(expected = HotelException.class)
	public void emptyCode() {
		new Hotel("", HOTEL_NAME, "NIF", "IBAN", PRICE_SINGLE, PRICE_DOUBLE);
	}

	@Test(expected = HotelException.class)
	public void nullName() {
		new Hotel(HOTEL_CODE, null, "NIF", "IBAN", PRICE_SINGLE, PRICE_DOUBLE);
	}

	@Test(expected = HotelException.class)
	public void blankName() {
		new Hotel(HOTEL_CODE, "  ", "NIF", "IBAN", PRICE_SINGLE, PRICE_DOUBLE);
	}

	@Test(expected = HotelException.class)
	public void emptyName() {
		new Hotel(HOTEL_CODE, "", "NIF", "IBAN", PRICE_SINGLE, PRICE_DOUBLE);
	}

	@Test(expected = HotelException.class)
	public void codeSizeLess() {
		new Hotel("123456", HOTEL_NAME, "NIF", "IBAN", PRICE_SINGLE, PRICE_DOUBLE);
	}

	@Test(expected = HotelException.class)
	public void codeSizeMore() {
		new Hotel("12345678", HOTEL_NAME, "NIF", "IBAN", PRICE_SINGLE, PRICE_DOUBLE);
	}

	@Test(expected = HotelException.class)
	public void codeNotUnique() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, "NIF", "IBAN", PRICE_SINGLE, PRICE_DOUBLE);
		new Hotel(HOTEL_CODE, HOTEL_NAME + " City", "NIF", "IBAN", PRICE_SINGLE, PRICE_DOUBLE);
	}

	@Test
	public void noteUniqueNIF() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, "NIF", "IBAN", PRICE_SINGLE, PRICE_DOUBLE);

		try {
			new Hotel("123456", "jdgdsk", "NIF", "IBAN", PRICE_SINGLE, PRICE_DOUBLE);
			Assert.fail();
		} catch (HotelException ae) {
			Assert.assertEquals(1, Hotel.hotels.size());
		}
	}

	@Test(expected = HotelException.class)
	public void nullNIF() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, null, "IBAN", PRICE_SINGLE, PRICE_DOUBLE);
	}

	@Test(expected = HotelException.class)
	public void emptyNIF() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, "   ", "IBAN", PRICE_SINGLE, PRICE_DOUBLE);
	}

	@Test(expected = HotelException.class)
	public void negativePriceSingle(){
		new Hotel(HOTEL_CODE, HOTEL_NAME, "NIF", "IBAN", -0.1, PRICE_DOUBLE);
	}

	@Test(expected = HotelException.class)
	public void zeroPriceSingle(){
		new Hotel(HOTEL_CODE, HOTEL_NAME, "NIF", "IBAN", 0, PRICE_DOUBLE);
	}

	public void positivePriceSingle(){
		new Hotel(HOTEL_CODE, HOTEL_NAME, "NIF", "IBAN", 0.1, PRICE_DOUBLE);
	}

	@Test(expected = HotelException.class)
	public void negativePriceDouble(){
		new Hotel(HOTEL_CODE, HOTEL_NAME, "NIF", "IBAN", PRICE_SINGLE, -0.1);
	}

	@Test(expected = HotelException.class)
	public void zeroPriceDouble(){
		new Hotel(HOTEL_CODE, HOTEL_NAME, "NIF", "IBAN", PRICE_SINGLE, 0);
	}

	public void positivePriceDouble(){
		new Hotel(HOTEL_CODE, HOTEL_NAME, "NIF", "IBAN", PRICE_SINGLE, 0.1);
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
