package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentingConflictTest {
	
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

	@Test
	public void correctArguments() {
		Assert.assertFalse(this._renting.conflict(new LocalDate(2018,6,12), new LocalDate(2018,6,20)));
	}
	
	@Test
	public void equalBeginEnd() {
		Assert.assertTrue(this._renting.conflict(new LocalDate(2018,6,12), new LocalDate(2018,6,12)));
	}
	
	@Test(expected = CarException.class)
	public void endBeforeBegin() {
		this._renting.conflict(new LocalDate(2018,5,12), new LocalDate(2018,5,3));
	}
	
	@Test
	public void beginAndEndBeforeRented() {
		Assert.assertFalse(this._renting.conflict(new LocalDate(2018,4,21), new LocalDate(2018,5,1)));
	}
	
	@Test
	public void beginBeforeAndEndEqualsRented() {
		Assert.assertTrue(this._renting.conflict(new LocalDate(2018,4,29), BEGIN));
	}
	
	@Test
	public void beginAndEndAfterRented() {
		Assert.assertFalse(this._renting.conflict(new LocalDate(2018,5,13), new LocalDate(2018,5,29)));
	}
	
	@Test
	public void beginEqualsAndEndAfterRented() {
		Assert.assertTrue(this._renting.conflict(END, new LocalDate(2018,5,29)));
	}
	
	@Test
	public void beginBeforeAndEndAfterRented() {
		Assert.assertTrue(this._renting.conflict(new LocalDate(2018,5,1), new LocalDate(2018,5,13)));
	}
	
	@Test
	public void beginEqualAndArrivalAfterRented() {
		Assert.assertTrue(this._renting.conflict(BEGIN, new LocalDate(2018,5,13)));
	}
	
	@Test
	public void beginBeforeAndArrivalEqualsRented() {
		Assert.assertTrue(this._renting.conflict(new LocalDate(2018,5,1), END));
	}
	
	@Test
	public void beginBeforeAndEndBetweenRented() {
		Assert.assertTrue(this._renting.conflict(new LocalDate(2018,5,1), new LocalDate(2018,5,6)));
	}
	
	@Test
	public void beginBetweenAndEndAfterRented() {
		Assert.assertTrue(this._renting.conflict(new LocalDate(2018,5,6), new LocalDate(2018,5,13)));
	}
	
	
	@After
	public void tearDown() {
		RentACar.rents.clear();
		_rentacar.getAllVehicles().clear();
	}
}