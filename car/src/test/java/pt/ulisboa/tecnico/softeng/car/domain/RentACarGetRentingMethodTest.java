package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.Assert;
import org.junit.Test;
import org.joda.time.LocalDate;


import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetRentingMethodTest {
	private static final int VEHICLE_KM = 10;
	private static final String VEHICLE_PLATE = "XX-XX-XX";
	private static final String DRIVING_LICENSE = "VC1";
	
	
	private RentACar _rentCar = new RentACar("Benecar");

	@Test
	public void success() {	
		Motorcycle m1 = new Motorcycle(VEHICLE_PLATE, VEHICLE_KM, this._rentCar);
		m1.rent(DRIVING_LICENSE, new LocalDate(2014,1,4) ,  new LocalDate(2014, 1, 5));
		m1.rent(DRIVING_LICENSE, new LocalDate(2014,1,10) ,  new LocalDate(2014, 1, 11));
		m1.rent(DRIVING_LICENSE, new LocalDate(2014,1,15) ,  new LocalDate(2014, 1, 16));
		m1.rent(DRIVING_LICENSE, new LocalDate(2014,1,20) ,  new LocalDate(2014, 1, 21));

		Assert.assertEquals(m1.getRentings().iterator().next().getReference(), _rentCar.getRenting(m1.getRentings().iterator().next().getReference()).getReference());
	}
	
	
	@Test(expected = CarException.class)
	public void nullReference() {
		this._rentCar.getRenting(null);
	}

	@Test(expected = CarException.class)
	public void emptyReference() {
		this._rentCar.getRenting("");
	}

	@Test(expected = CarException.class)
	public void blankReference() {
		this._rentCar.getRenting("    ");
	}

	@Test(expected = CarException.class)
	public void emptySetOfReferences() {
		this._rentCar.getRenting("XPTO");
	}

}