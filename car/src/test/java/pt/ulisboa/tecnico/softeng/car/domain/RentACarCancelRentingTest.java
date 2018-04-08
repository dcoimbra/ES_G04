package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.car.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
@RunWith(JMockit.class)
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
        this.vehicle = new Car(PLATE_CAR, 10, this.rentACar, PRICE);
    }

   
    @Test
	public void sucess(@Mocked final TaxInterface taxInterface,
			@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyInt);
				
				TaxInterface.submitInvoice((InvoiceData) this.any);
			
			}
		};

        this.renting = this.vehicle.rent(DRIVING_LICENSE, begin, end, IBAN, NIF);
        this.rentACar.getProcessor().submitRenting(this.renting);

        String cancel = RentACar.cancelRenting(this.renting.getReference());

        Assert.assertTrue(this.renting.isCancelled());
        Assert.assertEquals(cancel, this.renting.getCancellation());
    }
    

    @Test(expected = CarException.class)
    public void doesNotExist(@Mocked final TaxInterface taxInterface,
			@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyInt);
				
				TaxInterface.submitInvoice((InvoiceData) this.any);
			
			}
		};
		
		this.renting = this.vehicle.rent(DRIVING_LICENSE, begin, end, IBAN, NIF);
        this.rentACar.getProcessor().submitRenting(this.renting);
        RentACar.cancelRenting("XPTO");
    }

    @Test(expected = CarException.class)
    public void nullReference (@Mocked final TaxInterface taxInterface,
			@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyInt);
				
				TaxInterface.submitInvoice((InvoiceData) this.any);
			
			}
		};
		
		this.renting = this.vehicle.rent(DRIVING_LICENSE, begin, end, IBAN, NIF);
        this.rentACar.getProcessor().submitRenting(this.renting);
        RentACar.cancelRenting(null);
    }

    @Test(expected = CarException.class)
	public void emptyReference(@Mocked final TaxInterface taxInterface,
			@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyInt);
				
				TaxInterface.submitInvoice((InvoiceData) this.any);
			
			}
		};
		
		this.renting = this.vehicle.rent(DRIVING_LICENSE, begin, end, IBAN, NIF);
        this.rentACar.getProcessor().submitRenting(this.renting);
        RentACar.cancelRenting("");
    }

    @After
    public void tearDown() {
    	Vehicle.plates.clear();
        RentACar.rentACars.clear();
    }
}
