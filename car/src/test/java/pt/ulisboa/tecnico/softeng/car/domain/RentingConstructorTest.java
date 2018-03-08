package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

import org.joda.time.LocalDate;

public class RentingConstructorTest {
	private static final String DRIVINGLICENSE = "VN12345";
	private static final LocalDate BEGIN = new LocalDate(2018,5,2);
	private static final LocalDate END = new LocalDate(2018,5,12);
	private static final int KILOMETERS = 1;
	private RentACar _rentacar;

	@Before
	public void setUp() {
		this._rentacar = new RentACar("Benecar");
	}

	@Test
	public void success() {
		Renting _rent = new Renting(this._rentacar, DRIVINGLICENSE, BEGIN, END, KILOMETERS);
		
		Assert.assertTrue(_rent.getReference().startsWith(this._rentacar.getCode()));
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
	
//License Tests
	@Test(expected = CarException.class)
	public void lettersLicense() {
		new Renting(this._rentacar, "ABCDEF", BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void numbersLicense() {
		new Renting(this._rentacar, "12345", BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void capsAndNumbersLicense() {
		new Renting(this._rentacar, "1234FGC", BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void notCapsAndNumbersLicense() {
		new Renting(this._rentacar, "asd1234", BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void mixedNumbersLettersLicense() {
		new Renting(this._rentacar, "N4A8Z5", BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void emptyLicense() {
		new Renting(this._rentacar, "", BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void nullLicense() {
		new Renting(this._rentacar, null, BEGIN, END, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void blankLicense() {
		new Renting(this._rentacar, "    ", BEGIN, END, KILOMETERS);
	}
	
//Kilometers Tests
	@Test
	public void zeroKilometers() {
		try {
			new Renting(this._rentacar, DRIVINGLICENSE, BEGIN, END, 0);
		} catch (CarException ce) {
			Assert.fail();
		}
	}
	
	@Test(expected = CarException.class)
	public void negativeKm() {
		new Renting(this._rentacar, DRIVINGLICENSE, BEGIN, END, -KILOMETERS);
	}

//Date Tests
	@Test(expected = CarException.class)
	public void nullBeginDate() {
		new Renting(this._rentacar, DRIVINGLICENSE, null, END, KILOMETERS);
	}

	@Test(expected = CarException.class)
	public void nullEndDate() {
		new Renting(this._rentacar, DRIVINGLICENSE, BEGIN, null, KILOMETERS);
	}
	
	@Test(expected = CarException.class)
	public void swapDate() {
		new Renting(this._rentacar, DRIVINGLICENSE, END, BEGIN, KILOMETERS);
	}
	
	@After
	public void tearDown() {
		RentACar.rents.clear();
		_rentacar.getAllVehicles().clear();
	}

	
}
