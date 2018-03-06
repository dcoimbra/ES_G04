package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentingCheckoutTest {
	
	private static final String DRIVINGLICENSE = "VN12345";
	private static final LocalDate BEGIN = new LocalDate(2018,5,2);
	private static final LocalDate END = new LocalDate(2018,5,12);
	private static final int KILOMETERS = 1;
	private static final String RENTACARNAME = "AlugamISTo";
	private static final String RENTACARCODE = "001";
	private RentACar _rentacar;
	private Renting _renting;


	@Before
	public void setUp() {
		_rentacar = new RentACar(RENTACARNAME, RENTACARCODE);		
		_renting = new Renting(_rentacar, DRIVINGLICENSE, BEGIN, END, KILOMETERS);
	}

	@Test(expected = CarException.class)
	public void negativeKilometers() {
		_renting.checkout(-1);
	}

	@Test
	public void positiveKilometers() {
		_renting.checkout(3);
		
	}
	
	@Test(expected = CarException.class)
	public void negativeReturn() {
		_renting.checkout(-1);
	}
	
	@Test
	public void zeroReturn() {
		_renting.checkout(2);
	}
	
	@Test
	public void positiveReturn() {
		_renting.checkout(3);
	}
	
	
	@After
	public void tearDown() {
		RentACar.rents.clear();
		_rentacar.getAllVehicles().clear();
	}
}