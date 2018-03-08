package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.Assert;
import org.junit.Test;
import org.joda.time.LocalDate;


import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetRentingMethodTest {
	private static final String DRIVING_LICENSE = "VC12345";
	private static final String PLATE = "XX-XX-XX";
	private static final int KM = 100;

	private RentACar _rentCar = new RentACar("Benecar", "01");

	@Test
	public void success() {
		Vehicle v = new Car(PLATE, KM, this._rentCar);
		v.rent(DRIVING_LICENSE, new LocalDate(2014,1,2), new LocalDate(2014, 1, 3));
		Assert.assertEquals(v.getRentings().iterator().next(), this._rentCar.getRenting(v.getRentings().iterator().next().getReference()));
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