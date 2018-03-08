package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarContructorTest {
	private static final String RENT_A_CAR_NAME = "Benecar";
	private static final String RENT_A_CAR_CODE = "01";
	private static final String VEHICLE_PLATE = "XX-XX-XX";
	private static final int VEHICLE_KM = 1;
	private RentACar _rentacar;

	@Before
	public void setUp() {
		this._rentacar = new RentACar(RENT_A_CAR_NAME, RENT_A_CAR_CODE);
		new Motorcycle(VEHICLE_PLATE, VEHICLE_KM, this._rentacar);
	}

	@Test
	public void success() {		
		Assert.assertEquals(RENT_A_CAR_CODE, _rentacar.getCode());
		Assert.assertTrue(_rentacar.plateIsFree("YY-YY-YY"));
		Assert.assertEquals(_rentacar.getAllVehicles().size(), 1);
	}

	@Test(expected = CarException.class)
	public void nullRentACarName() {
		new RentACar(null, RENT_A_CAR_CODE);
	}

	@Test(expected = CarException.class)
	public void emptyRentACarName() {
		new RentACar("  ", RENT_A_CAR_CODE);
	}
	@Test(expected = CarException.class)
	public void nullRentACarCode() {
		new RentACar(RENT_A_CAR_NAME , null);
	}

	@Test(expected = CarException.class)
	public void emptyRentACarCode() {
		new RentACar(RENT_A_CAR_NAME, "  ");
	}

	@Test(expected = CarException.class)
	public void plateAlreadyExists() {
		_rentacar.plateIsFree("XX-XX-XX");
	}

	@After
	public void tearDown() {
		RentACar.rents.clear();
	}


}
