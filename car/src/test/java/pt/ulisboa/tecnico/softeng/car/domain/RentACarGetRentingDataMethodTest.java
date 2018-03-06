package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetRentingDataMethodTest {
	private static final String DRIVINGLICENSE = "VN12345";
	private static final LocalDate BEGIN = new LocalDate(2018,5,2);
	private static final LocalDate END = new LocalDate(2018,5,12);
	private static final String RENTACARCODE = "001";
	private static final String PLATE = "XX-XX-XX";

	@Test
	public void success() {
		RentingData rd = RentingData(REFERENCE, PLATE, DRIVINGLICENSE, RENTACARCODE, BEGIN, END)
		assertEquals(RentACar.getRentingData(REFERENCE), rd);
	}

	@Test(expected = CarException.class)
	public void nullReference() {
		RentACar.getRentingData(null);
	}

	@Test(expected = CarException.class)
	public void emptyReference() {
		RentACar.getRentingData("");
	}

	@Test(expected = CarException.class)
	public void referenceNotExists() {
		RentACar.getRentingData("XPTO");
	}

}