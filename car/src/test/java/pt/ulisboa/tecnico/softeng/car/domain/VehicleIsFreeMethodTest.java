package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class VehicleIsFreeMethodTest {
	
	private static final String VEHICLE_PLATE = "XX-XX-XX";
	private static final int VEHICLE_KM = 1;
	private static final String RENTACARNAME = "AlugamISTo";
	private static final String RENTACARCODE = "001";
	private static final String DRIVINGLICENCE = "VC1234";
	
	RentACar r = new RentACar(RENTACARNAME, RENTACARCODE);
	Vehicle c;

	@Before
	public void setUp() {
		c = new Car(VEHICLE_PLATE, VEHICLE_KM, r);
	}
	
	@Test(expected = CarException.class)
	public void invalidDate1() {
		c.isFree(null, null);
	}

	@Test(expected = CarException.class)
	public void invalidDate2() {
		c.isFree(null, new LocalDate(2018,1,1));
	}

	@Test(expected = CarException.class)
	public void invalidDate3() {
		c.isFree(new LocalDate(2018,1,1), null);
	}

	@Test(expected = CarException.class)
	public void invalidDate4() {
		c.isFree(new LocalDate(2018,1,2), new LocalDate(2018,1,1));
	}

	@Test
	public void invalidDate5() {
		c.rent(DRIVINGLICENCE, new LocalDate(2018,1,1), new LocalDate(2018,1,1));
		if(c.isFree(new LocalDate(2018,1,1), new LocalDate(2018,1,1)) == true)
			Assert.fail();
	}

	@Test
	public void invalidDate6() {
		c.rent(DRIVINGLICENCE, new LocalDate(2018,1,1), new LocalDate(2018,1,1));
		c.rent(DRIVINGLICENCE, new LocalDate(2018,1,2), new LocalDate(2018,1,2));
		if(c.isFree(new LocalDate(2018,1,1), new LocalDate(2018,1,2)) == true)
			Assert.fail();
	}

	@Test
	public void invalidDate7() {
		Vehicle c2 = new Car("YY-YY-YY", VEHICLE_KM, r);
		c.rent(DRIVINGLICENCE, new LocalDate(2018,1,1), new LocalDate(2018,1,10));
		c2.rent(DRIVINGLICENCE, new LocalDate(2018,1,11), new LocalDate(2018,1,15));
		if (c.isFree(new LocalDate(2018,1,12), new LocalDate(2018,1,14)) == false)
			Assert.fail();
		if (c.isFree(new LocalDate(2018,1,2), new LocalDate(2018,1,5)) == true)
			Assert.fail();
		if (c.isFree(new LocalDate(2018,1,13), new LocalDate(2018,1,16)) == false)
			Assert.fail();
		if (c.isFree(new LocalDate(2017,12,15), new LocalDate(2018,1,7)) == true)
			Assert.fail();
		if (c.isFree(new LocalDate(2017,12,12), new LocalDate(2018,1,31)) == true)
			Assert.fail();
		if (c.isFree(new LocalDate(2017,12,12), new LocalDate(2017,12,31)) == false)
			Assert.fail();
		if (c.isFree(new LocalDate(2018,1,16), new LocalDate(2018,1,20)) == false)
			Assert.fail();
	}

	@Test
	public void invalidDate8() {
		if (c.isFree(new LocalDate(2018,1,1), new LocalDate(2018,1,1)) == false || 
			c.isFree(new LocalDate(2018,1,2), new LocalDate(2018,1,3)) == false || 
			c.isFree(new LocalDate(2018,1,3), new LocalDate(2018,1,4)) == false)
			Assert.fail();
	}
	
	@After
	public void tearDown() {
		c.getRentings().clear();
	}
}
