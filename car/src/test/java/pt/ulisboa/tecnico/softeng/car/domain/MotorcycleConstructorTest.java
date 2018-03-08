package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class MotorcycleConstructorTest {
	private static final String VEHICLE_PLATE = "GH-IJ-KL";
	private static final int VEHICLE_KM = 1;
	private static final String RENTACARNAME = "AlugamISTo";
	private static final int PLATE_LEN = 8;

	private RentACar _rentACar;
	
	@Before
	public void setUp() {
		this._rentACar = new RentACar(RENTACARNAME);
	}
	
	@Test
	public void success() {
		Vehicle vehicle = new Motorcycle(VEHICLE_PLATE, VEHICLE_KM, this._rentACar);
		Assert.assertEquals(VEHICLE_KM, vehicle.getKilometers());
		Assert.assertEquals(VEHICLE_PLATE, vehicle.getPlate());
		Assert.assertEquals(_rentACar, vehicle.getRentACar());
	}
	
	@Test(expected = CarException.class)
	public void invalidPlate() {
		new Motorcycle("12345678", VEHICLE_KM, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void invalidPlate2() {
		new Motorcycle("XX-*+-.3", VEHICLE_KM, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void invalidPlate3() {
		new Motorcycle("XX-XX-XXX", VEHICLE_KM, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void invalidPlate4() {
		new Motorcycle("XX-XX-X", VEHICLE_KM, this._rentACar);
	}

	@Test(expected = CarException.class)
	public void blankPlate() {
		new Motorcycle("    ", VEHICLE_KM, this._rentACar);
	}

	@Test(expected = CarException.class)
	public void emptyPlate() {
		new Motorcycle("", VEHICLE_KM, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void duplicatedPlate() {
		new Motorcycle(VEHICLE_PLATE, VEHICLE_KM, this._rentACar);
		new Motorcycle(VEHICLE_PLATE, VEHICLE_KM, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void nullPlate() {
		new Motorcycle(null, VEHICLE_KM, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void nullRentACar() {
		new Motorcycle(VEHICLE_PLATE, VEHICLE_KM, null);
	}
	
	@Test
	public void zeroKilometers() {
		try {
			new Motorcycle(VEHICLE_PLATE, 0, this._rentACar);
		} catch (CarException ce) {
			Assert.fail();
		}
	}
	
	@Test(expected = CarException.class)
	public void negativeKilometers() {
		new Motorcycle(VEHICLE_PLATE, -VEHICLE_KM, this._rentACar);
	}
	
	@Test
	public void edgeValidPlate() {
		try {
			new Motorcycle("AA-09-ZZ", VEHICLE_KM, this._rentACar);
		} catch (Exception e) {Assert.fail();}
	}
	
	@Test(expected = CarException.class)
	public void edgeInvalidPlate() {
		StringBuilder sb = new StringBuilder(PLATE_LEN);
		char ch = (char) 64;
		sb.append(ch);sb.append(ch);
		sb.append("-/:-[[");
		
		new Motorcycle(sb.toString(), VEHICLE_KM, this._rentACar);
	}
	
	@After
	public void tearDown() {
		_rentACar.getAllVehicles().clear();
		RentACar.rents.clear();
	}
}
