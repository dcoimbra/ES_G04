package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.After;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetRentingDataMethodTest {
	private static final String DRIVINGLICENSE = "VN12345";
	private static final String PLATE1 = "XX-XX-XX";
	private static final String PLATE2 = "XX-XX-XX";
	private static final int KM = 1;

	@Test
	public void success() {
		RentACar rentCar = new RentACar("Benecar");
		RentACar rentCar2 = new RentACar("AutoLDA");
		Vehicle m1 = new Motorcycle(PLATE1, KM, rentCar);
		Vehicle m2 = new Motorcycle(PLATE2, KM, rentCar2);
		m1.rent(DRIVINGLICENSE, new LocalDate(2014,1,1) ,  new LocalDate(2014, 1, 3));
		m1.rent(DRIVINGLICENSE, new LocalDate(2014,1,4) ,  new LocalDate(2014, 1, 6));
		m1.rent(DRIVINGLICENSE, new LocalDate(2014,1,8) ,  new LocalDate(2014, 1, 10));
		m2.rent(DRIVINGLICENSE, new LocalDate(2014,2,1) ,  new LocalDate(2014, 2, 3));
		m2.rent(DRIVINGLICENSE, new LocalDate(2014,2,4) ,  new LocalDate(2014, 2, 6));
		m2.rent(DRIVINGLICENSE, new LocalDate(2014,2,8) ,  new LocalDate(2014, 2, 10));
		String reference = null;
		
		for(Renting r : m1.getRentings())
			reference = r.getReference();
		Assert.assertNotNull(RentACar.getRentingData(reference));
	}
	
	@Test(expected = CarException.class)
	public void nullReference() {
		RentACar.getRentingData(null);
	}

	@Test(expected = CarException.class)
	public void emptyReference() {
		RentACar.getRentingData("");
	}

	@Test(expected = CarException.class)
	public void blankReference() {
		RentACar.getRentingData("    ");
	}

	@Test(expected = CarException.class)
	public void referenceNotExists() {
		RentACar.getRentingData("XPTO");
	}

	@After
	public void tearDown() {
		RentACar.rents.clear();
	}

}