package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.Assert;
import org.junit.Test;
import org.joda.time.LocalDate;


import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetRentingMethodTest {
	private static final String DRIVING_LICENSE = "VC12345";
	private static final String PLATE = "XX-XX-XX";
	private static final int KM = 100;

	private RentACar _rentCar = new RentACar("Benecar");

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