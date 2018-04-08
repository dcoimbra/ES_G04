package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class VehicleRentTest {
	private static final String PLATE_CAR = "22-33-HZ";
	private static final String RENT_A_CAR_NAME = "Eartz";
	private static final String DRIVING_LICENSE = "lx1423";
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-09");
	private static final String IBAN = "BK123456789";
	private static final String NIF = "123456789";
	private static final int PRICE = 20;
	private Car car;
	private RentACar rentACar;
	@Before
	public void setUp() {
		rentACar = new RentACar(RENT_A_CAR_NAME, NIF, IBAN);
		this.car = new Car(PLATE_CAR, 10, rentACar, PRICE);
	}

	@Test(expected = CarException.class)
	public void doubleRent() {
		Renting renting = car.rent(DRIVING_LICENSE, date1, date2,IBAN, NIF);
		this.rentACar.getProcessor().submitRenting(renting);
		renting = car.rent(DRIVING_LICENSE, date1, date2,IBAN, NIF);
		this.rentACar.getProcessor().submitRenting(renting);
		renting = car.rent(DRIVING_LICENSE, date1, date2,IBAN, NIF);
		this.rentACar.getProcessor().submitRenting(renting);
	}

	@Test(expected = CarException.class)
	public void beginIsNull() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, NIF, IBAN);
		Vehicle car = new Car(PLATE_CAR, 10, rentACar, PRICE);
		Renting renting = car.rent(DRIVING_LICENSE, date1, date2,IBAN, NIF);
		this.rentACar.getProcessor().submitRenting(renting);
	}

	@Test(expected = CarException.class)
	public void endIsNull() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, NIF, IBAN);
		Vehicle car = new Car(PLATE_CAR, 10, rentACar, PRICE);
		Renting renting = car.rent(DRIVING_LICENSE, date1, date2,IBAN, NIF);
		this.rentACar.getProcessor().submitRenting(renting);
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}
