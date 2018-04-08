package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarCancelRentingTest {
    private static final String NAME = "eartz";
    private static final String PLATE_CAR = "aa-00-11";
    private static final String DRIVING_LICENSE = "br123";
    private static final int PRICE = 20;
    private final LocalDate begin = new LocalDate(2016, 12, 19);
    private final LocalDate end = new LocalDate(2016, 12, 21);
    private static final String IBAN = "BK123456789";
    private static final String NIF = "123456789";
    private RentACar rentACar;
    private Vehicle vehicle;
    private Renting renting;

    @Before
    public void setUp() {
        this.rentACar = new RentACar(NAME, NIF, IBAN);
    }

    @Test
    public void success() {

        this.vehicle = new Car(PLATE_CAR, 10, this.rentACar, PRICE);
        this.renting = this.vehicle.rent(DRIVING_LICENSE, begin, end, IBAN, NIF);

        String cancel = RentACar.cancelRenting(this.renting.getReference());

        Assert.assertTrue(this.renting.isCancelled());
        Assert.assertEquals(cancel, this.renting.getCancellation());
    }

    @Test(expected = CarException.class)
    public void doesNotExist() {
        RentACar.cancelRenting("XPTO");
    }

    @Test(expected = CarException.class)
    public void nullReference() {
        RentACar.cancelRenting(null);
    }

    @Test(expected = CarException.class)
    public void emptyReference() {
        RentACar.cancelRenting("");
    }

    @After
    public void tearDown() {
    	Vehicle.plates.clear();
        RentACar.rentACars.clear();
    }
}
