package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarConstructorTest {
	private static final String RENT_A_CAR_NAME = "Benecar";
	private static final String VEHICLE_PLATE = "XX-XX-XX";
	private static final int VEHICLE_KM = 1;
	private RentACar _rentacar;

	@Test
	public void success() {		
		this._rentacar = new RentACar(RENT_A_CAR_NAME);
		new Motorcycle(VEHICLE_PLATE, VEHICLE_KM, this._rentacar);
		Assert.assertTrue(_rentacar.plateIsFree("YY-YY-YY"));
		Assert.assertEquals(_rentacar.getAllVehicles().size(), 1);
		Assert.assertEquals(RENT_A_CAR_NAME, _rentacar.getName());
	}

	@Test(expected = CarException.class)
	public void nullRentACarName() {
		new RentACar(null);
	}

	@Test(expected = CarException.class)
	public void emptyRentACarName() {
		new RentACar("  ");
	}

	@Test
	public void plateAlreadyExists() {
		this._rentacar = new RentACar(RENT_A_CAR_NAME);
		new Motorcycle(VEHICLE_PLATE, VEHICLE_KM, this._rentacar);
		Assert.assertFalse(_rentacar.plateIsFree("XX-XX-XX"));
	}

	@After
	public void tearDown() {
		RentACar.rents.clear();
	}


}
