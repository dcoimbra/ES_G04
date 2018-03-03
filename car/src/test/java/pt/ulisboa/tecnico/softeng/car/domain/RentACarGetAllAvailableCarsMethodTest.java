package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;


import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetAllAvailableCarsMethodTest {
	private static final String PLATE1 = "XX-XX-XX";
	private static final String PLATE2 = "YY-YY-YY";
	private static final String PLATE3 = "WW-WW-WW";
	private static final String PLATE4 = "PP-PP-PP";
	private static final String PLATE5 = "AA-AA-AA";
	private static final String PLATE6 = "ZZ-ZZ-ZZ";
	private static final int KM = 100;
	private static final String DRIVING_LICENSE = "VC1";
	private RentACar _rentCar = new RentACar("Benecar", "01");


	@Test
	public void success() {
		Vehicle c1 = new Car(PLATE1, KM, this._rentCar);
		Vehicle c2 = new Car(PLATE2, KM, this._rentCar);
		Vehicle c3 = new Car(PLATE3, KM, this._rentCar);
		Vehicle c4 = new Car(PLATE4, KM, this._rentCar);
		Vehicle c5 = new Car(PLATE5, KM, this._rentCar);
		Vehicle m1 = new Motorcycle(PLATE6, KM, this._rentCar);

		List<Vehicle> availableCars = new ArrayList<>();

		c1.rent(DRIVING_LICENSE, new LocalDate(2014,1,1) ,  new LocalDate(2014, 1, 2));
		availableCars.add(c1);
		c1.rent(DRIVING_LICENSE, new LocalDate(2014,1,3) ,  new LocalDate(2014, 1, 4));
		c1.rent(DRIVING_LICENSE, new LocalDate(2014,1,3) ,  new LocalDate(2014, 1, 5));
		c1.rent(DRIVING_LICENSE, new LocalDate(2014,1,4) ,  new LocalDate(2014, 1, 5));
		c1.rent(DRIVING_LICENSE, new LocalDate(2014,1,5) ,  new LocalDate(2014, 1, 6));
		c1.rent(DRIVING_LICENSE, new LocalDate(2014,1,9) ,  new LocalDate(2014, 1,10));
		c1.rent(DRIVING_LICENSE, new LocalDate(2014,1,9) ,  new LocalDate(2014, 1,11));
		c1.rent(DRIVING_LICENSE, new LocalDate(2014,1,10),  new LocalDate(2014, 1,11));
		c1.rent(DRIVING_LICENSE, new LocalDate(2014,1,11),  new LocalDate(2014, 1,12));
		c2.rent(DRIVING_LICENSE, new LocalDate(2014,1,1) ,  new LocalDate(2014, 1, 2));
		availableCars.add(c2);
		c3.rent(DRIVING_LICENSE, new LocalDate(2014,1,11),  new LocalDate(2014, 1, 11));
		availableCars.add(c3);
		c4.rent(DRIVING_LICENSE, new LocalDate(2014,1,4) ,  new LocalDate(2014, 1, 4));
		c5.rent(DRIVING_LICENSE, new LocalDate(2014,1,10),  new LocalDate(2014, 1, 10));
		m1.rent(DRIVING_LICENSE, new LocalDate(2014,1,1) ,  new LocalDate(2014, 1, 2));
	
		Assert.assertEquals(availableCars, this._rentCar.getAllAvailableCars(new LocalDate(2014,1,4), new LocalDate(2014, 1, 10)));
	}

	@Test(expected = CarException.class)
	public void nullBeginDate() {
		this._rentCar.getAllAvailableCars(null, new LocalDate(2014,1,8));
	}

	@Test(expected = CarException.class)
	public void nullEndDate() {
		this._rentCar.getAllAvailableCars(new LocalDate(2014,1,8), null);
	}
	@Test(expected = CarException.class)
	public void invalidPeriod() {
		this._rentCar.getAllAvailableCars(new LocalDate(2014,1,8), new LocalDate(2014,1,7));
	}

}
