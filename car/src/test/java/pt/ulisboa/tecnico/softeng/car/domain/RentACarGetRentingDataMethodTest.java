package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetRentingDataMethodTest {
	private static final String DRIVINGLICENSE = "VN12345";
	private static final LocalDate BEGIN = new LocalDate(2018,5,2);
	private static final LocalDate END = new LocalDate(2018,5,12);
	private static final String RENTACARCODE = "1";
	private static final String REFERENCE = "0012";
	private static final String PLATE = "XX-XX-XX";
	private static final int KM = 1;

	@Test
	public void success() {
		RentACar rentCar = new RentACar("Benecar");
		Vehicle m1 = new Motorcycle(PLATE, KM, rentCar);
		m1.rent(DRIVINGLICENSE, new LocalDate(2014,1,1) ,  new LocalDate(2014, 1, 3));
		
		for(Renting r : m1.getRentings())
			Assert.assertNotNull(RentACar.getRentingData(r.getReference()));
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

}