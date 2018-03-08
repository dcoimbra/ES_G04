package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class VehicleRentMethodTest {
	
	private static final LocalDate BEGIN = new LocalDate(2018,1,1);
	private static final LocalDate END = new LocalDate(2018,1,2);
	private static final String VEHICLE_PLATE = "XX-XX-XX";
	private static final int VEHICLE_KM = 1;
	private static final String RENTACARNAME = "AlugamISTo";
	private static final String DRIVINGLICENCE = "VC1234";
	
	RentACar r;
	Vehicle c;
	
	@Before
	public void setUp() {
		r = new RentACar(RENTACARNAME);
		c = new Car(VEHICLE_PLATE, VEHICLE_KM, r);
	}
	
	@Test(expected = CarException.class)
	public void invalidBeginDate() {
		c.rent(DRIVINGLICENCE, null, END);
	}
	
	@Test(expected = CarException.class)
	public void invalidEndDate() {
		c.rent(DRIVINGLICENCE, BEGIN, null);
	}
	
	@Test(expected = CarException.class)
	public void swapDate() {
		c.rent(DRIVINGLICENCE, END, BEGIN);
	}
	
	@Test
	public void testAdd() {
		int initialSize = c.getRentings().size();
		c.rent(DRIVINGLICENCE, BEGIN, END);
		if (initialSize + 1 != c.getRentings().size()) {
			Assert.fail();
		}
	}
	
	@Test(expected = CarException.class)
	public void invalidRent() {
		c.rent(DRIVINGLICENCE, BEGIN, END);
		c.rent(DRIVINGLICENCE, BEGIN, END);
	}
	
	@Test(expected = CarException.class)
	public void invalidRent2() {
		c.rent(DRIVINGLICENCE, new LocalDate(2018,1,1), new LocalDate(2018,1,3));
		c.rent(DRIVINGLICENCE, new LocalDate(2018,1,3), new LocalDate(2018,1,5));
	}
	
	@Test(expected = CarException.class)
	public void invalidRent3() {
		c.rent(DRIVINGLICENCE, new LocalDate(2018,1,2), new LocalDate(2018,1,3));
		c.rent(DRIVINGLICENCE, new LocalDate(2018,1,4), new LocalDate(2018,1,5));
		c.rent(DRIVINGLICENCE, new LocalDate(2018,1,1), new LocalDate(2018,1,2));
	}
	
	@Test
	public void validRent() {
		try {
			Vehicle c2 = new Car("AA-BB-SS", VEHICLE_KM, r);
			c2.rent(DRIVINGLICENCE, new LocalDate(2018,1,1), new LocalDate(2018,1,2));
			c.rent(DRIVINGLICENCE, new LocalDate(2018,1,1), new LocalDate(2018,1,2));
			c.rent(DRIVINGLICENCE, new LocalDate(2018,1,3), new LocalDate(2018,1,4));
		} catch (Exception e) {Assert.fail();}
	}
	
	@After
	public void tearDown() {
		RentACar.rents.clear();
	}
}
