package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentingCheckoutTest {
	
	private static final String DRIVINGLICENSE = "VN12345";
	private static final LocalDate BEGIN = new LocalDate(2018,5,2);
	private static final LocalDate END = new LocalDate(2018,5,12);
	private static final int KILOMETERS = 1;
	private static final String RENTACARNAME = "AlugamISTo";
	private RentACar _rentacar;
	private Renting _renting;


	@Before
	public void setUp() {
		_rentacar = new RentACar(RENTACARNAME);		
		_renting = new Renting(_rentacar, DRIVINGLICENSE, BEGIN, END, KILOMETERS);
	}

	@Test(expected = CarException.class)
	public void negativeKilometers() {
		_renting.checkout(-1);
	}

	@Test
	public void positiveKilometers() {
		try {
			_renting.checkout(3);
		}catch(Exception e) {
			Assert.fail();
		}
	}
	
	@Test(expected = CarException.class)
	public void negativeReturn() {
		_renting.checkout(-1);
	}
	
	@Test
	public void zeroReturn() {
		try {
			_renting.checkout(2);	
		}catch(Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void positiveReturn() {
		try {
			_renting.checkout(3);
		}catch(Exception e) {
			Assert.fail();
		}
	}
	
	
	@After
	public void tearDown() {
		RentACar.rents.clear();
		_rentacar.getAllVehicles().clear();
	}
}