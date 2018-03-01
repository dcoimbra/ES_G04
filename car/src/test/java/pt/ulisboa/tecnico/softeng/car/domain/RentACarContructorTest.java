package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarContructorTest {
	private static final String RENT_A_CAR_NAME = "Benecar";
	private static final String RENT_A_CAR_CODE = "01";

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

}
