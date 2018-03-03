package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class VehicleRentMethodTest {
	
	private static final LocalDate BEGIN = new LocalDate(2018,1,1);
	private static final LocalDate END = new LocalDate(2018,1,2);
	private static final String VEHICLE_PLATE = "XX-XX-XX";
	private static final int VEHICLE_KM = 1;
	private static final String RENTACARNAME = "AlugamISTo";
	private static final String RENTACARCODE = "001";
	private static final String DRIVINGLICENCE = "VC1234";
	
	RentACar r = new RentACar(RENTACARNAME, RENTACARCODE);
	Vehicle c = new Car(VEHICLE_PLATE, VEHICLE_KM, r);
	
	@Test(expected = CarException.class)
	public void invalidDrivingLicense() {
		c.rent(null, BEGIN, END);
	}
	
	@Test(expected = CarException.class)
	public void invalidBeginDate() {
		c.rent(DRIVINGLICENCE, null, END);
	}
	
	@Test(expected = CarException.class)
	public void invalidEndDate() {
		c.rent(DRIVINGLICENCE, BEGIN, null);
	}
	
	@Test(expected = CarException.class)
	public void swapDate() {
		c.rent(DRIVINGLICENCE, END, BEGIN);
	}
	
	@Test(expected = CarException.class)
	public void blankDrivingLicense() {
		c.rent("    ", BEGIN, END);
	}

	@Test(expected = CarException.class)
	public void emptyDrivingLicense() {
		c.rent("", BEGIN, END);
	}
}
