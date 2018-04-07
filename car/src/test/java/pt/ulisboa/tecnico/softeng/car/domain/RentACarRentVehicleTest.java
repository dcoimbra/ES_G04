package pt.ulisboa.tecnico.softeng.car.domain;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarRentVehicleTest {

    private static final String NAME = "eartz";
    private static final String PLATE_CAR = "22-33-HZ";
    private static final String DRIVING_LICENSE = "br123";
    private static final String IBAN = "BK123456789";
    private static final String NIF = "123456789";
    private static final int PRICE = 20;
    private static final LocalDate date1 = LocalDate.parse("2018-01-06");
    private static final LocalDate date2 = LocalDate.parse("2018-01-07");
    private RentACar rentACar;
    private Vehicle vehicle;

    @Before
    public void setUp() {
        this.rentACar = new RentACar(NAME, NIF, IBAN);
    }

    @Test
    public void success() {

        this.vehicle = new Car(PLATE_CAR, 10, this.rentACar, PRICE);
        RentACar.rentVehicle( DRIVING_LICENSE, date1, date2, IBAN, NIF);
    }

    @Test(expected = CarException.class)
    public void noRentACars() {

        RentACar.rentACars.clear();
        RentACar.rentVehicle(DRIVING_LICENSE, date1, date2, IBAN, NIF);
    }


    @Test(expected = CarException.class)
    public void noVehicles() {

        rentACar.removeVehicles();
        RentACar.rentVehicle(DRIVING_LICENSE, date1, date2, IBAN, NIF);
    }

    @After
    public void tearDown() {

        RentACar.rentACars.clear();
    }
}
