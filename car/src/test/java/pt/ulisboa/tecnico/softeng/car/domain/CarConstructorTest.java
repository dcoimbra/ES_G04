package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class CarConstructorTest {
	private static final String VEHICLE_PLATE = "XX-XX-XX";
	private static final int VEHICLE_KM = 1;
	private static final String RENTACARNAME = "AlugamISTo";
	private static final String RENTACARCODE = "001";

	private RentACar _rentACar;
	
	@Before
	public void setUp() {
		this._rentACar = new RentACar(RENTACARNAME, RENTACARCODE);
	}
	
	@Test
	public void success() {
		Vehicle vehicle = new Car(VEHICLE_PLATE, VEHICLE_KM, this._rentACar);
		Assert.assertEquals(VEHICLE_KM, vehicle.getKilometers());
		Assert.assertEquals(VEHICLE_PLATE, vehicle.getPlate());
	}
	
	@Test(expected = CarException.class)
	public void invalidPlate() {
		new Car("12345678", VEHICLE_KM, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void invalidPlate2() {
		new Car("XX-*+-.3", VEHICLE_KM, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void invalidPlate3() {
		new Car("XX-XX-XXX", VEHICLE_KM, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void invalidPlate4() {
		new Car("XX-XX-X", VEHICLE_KM, this._rentACar);
	}

	@Test(expected = CarException.class)
	public void blankPlate() {
		new Car("    ", VEHICLE_KM, this._rentACar);
	}

	@Test(expected = CarException.class)
	public void emptyPlate() {
		new Car("", VEHICLE_KM, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void duplicatedPlate() {
		new Car(VEHICLE_PLATE, VEHICLE_KM, this._rentACar);
		new Car(VEHICLE_PLATE, VEHICLE_KM, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void nullPlate() {
		new Car(null, VEHICLE_KM, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void nullRentACar() {
		new Car(VEHICLE_PLATE, VEHICLE_KM, null);
	}
	
	@Test
	public void zeroKilometers() {
		try {
			new Car(VEHICLE_PLATE, 0, this._rentACar);
		} catch (CarException ce) {
			Assert.fail();
		}
	}
	
	@Test(expected = CarException.class)
	public void negativeKilometers() {
		new Car(VEHICLE_PLATE, -VEHICLE_KM, this._rentACar);
	}
	
	@After
	public void tearDown() {
		RentACar.getAllVehicles().clear();
		RentACar.rents.clear();
	}
}
