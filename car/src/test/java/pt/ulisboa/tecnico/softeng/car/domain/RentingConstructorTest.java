package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

import org.joda.time.LocalDate;

public class RentingConstructorTest {
	private static final String REFERENCE = "BFEB12836372FBB";
	private static final String DRIVINGLICENSE = "VN12345";
	private static final LocalDate BEGIN = new LocalDate(2018,5,2);
	private static final LocalDate END = new LocalDate(2018,5,12);
	private static final int KILOMETERS = 1;

	@Test
	public void success() {
		Renting _rent = new Renting(REFERENCE, DRIVINGLICENSE, BEGIN, END, KILOMETERS);
		
		Assert.assertEquals(REFERENCE, _rent.getReference());
		Assert.assertEquals(DRIVINGLICENSE, _rent.getLicense());
		Assert.assertEquals(BEGIN, _rent.getBegin());
		Assert.assertEquals(END, _rent.getEnd());
		Assert.assertEquals(KILOMETERS, _rent.getKilometers());
	}
	
//Reference Tests
	@Test(expected = CarException.class)
	public void nullReference() {
		new Renting(null, DRIVINGLICENSE, BEGIN, END, KILOMETERS);
	}

	@Test(expected = CarException.class)
	public void emptyReference() {
		new Renting("", DRIVINGLICENSE, BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void blankReference() {
		new Renting("    ", DRIVINGLICENSE, BEGIN, END, KILOMETERS);
	}
	

//License Tests
	@Test(expected = CarException.class)
	public void lettersLicense() {
		new Renting(REFERENCE, "ABCDEF", BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void numbersLicense() {
		new Renting(REFERENCE, "12345", BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void capsAndNumbersLicense() {
		new Renting(REFERENCE, "1234FGC", BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void notCapsAndNumbersLicense() {
		new Renting(REFERENCE, "asd1234", BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void mixedNumbersLettersLicense() {
		new Renting(REFERENCE, "N4A8Z5", BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void emptyLicense() {
		new Renting(REFERENCE, "", BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void nullLicense() {
		new Renting(REFERENCE, null, BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void blankLicense() {
		new Renting(REFERENCE, "    ", BEGIN, END, KILOMETERS);
	}
	
//Kilometers Tests
	@Test
	public void zeroKilometers() {
		try {
			new Renting(REFERENCE, DRIVINGLICENSE, BEGIN, END, 0);
		} catch (CarException ce) {
			Assert.fail();
		}
	}
	
	@Test(expected = CarException.class)
	public void negativeKm() {
		new Renting(REFERENCE, DRIVINGLICENSE, BEGIN, END, -KILOMETERS);
	}

//Date Tests
	@Test(expected = CarException.class)
	public void nullBeginDate() {
		new Renting(REFERENCE, DRIVINGLICENSE, null, END, KILOMETERS);
	}

	@Test(expected = CarException.class)
	public void nullEndDate() {
		new Renting(REFERENCE, DRIVINGLICENSE, BEGIN, null, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void swapDate() {
		new Renting(REFERENCE, DRIVINGLICENSE, END, BEGIN, KILOMETERS);
	}

	
}
