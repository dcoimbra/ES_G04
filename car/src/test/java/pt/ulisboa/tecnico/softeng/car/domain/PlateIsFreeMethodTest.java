package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

import org.joda.time.LocalDate;

public class PlateIsFreeMethodTest {
	private RentACar _rentacar;
	private static final String RENTACARNAME = "Benecar";
	
	@Before
	public void setUp() {
		_rentacar = new RentACar(RENTACARNAME);		
	}
	
	@Test(expected = CarException.class)
	public void nullPlate() {
		_rentacar.plateIsFree(null);
	}
	
	@Test(expected = CarException.class)
	public void blankPlate() {
		_rentacar.plateIsFree("    ");
	}
	
	@Test(expected = CarException.class)
	public void emptyPlate() {
		_rentacar.plateIsFree("");
	}
	
	@After
	public void tearDown() {
		RentACar.rents.clear();
	}

	
}
