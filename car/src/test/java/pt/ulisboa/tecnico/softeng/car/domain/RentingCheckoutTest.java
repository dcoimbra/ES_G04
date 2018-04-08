package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.car.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;

public class RentingCheckoutTest {
	private static final String NAME1 = "eartz";
	private static final String PLATE_CAR1 = "aa-00-11";
	private static final String DRIVING_LICENSE = "br123";
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-07");
	private static final String IBAN = "BK123456789";
	private static final String NIF = "123456789";
	private static final int PRICE = 20;
	private Car car;
	RentACar rentACar1;

	@Before
	public void setUp() {
		rentACar1 = new RentACar(NAME1, NIF, IBAN);
		this.car = new Car(PLATE_CAR1, 10, rentACar1, PRICE);
	}

	@Test
	public void checkout(@Mocked final TaxInterface taxInterface,
			@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyInt);
				
				TaxInterface.submitInvoice((InvoiceData) this.any);
			
			}
		};
		Renting renting = car.rent(DRIVING_LICENSE, date1, date2, IBAN, NIF);
		this.rentACar1.getProcessor().submitRenting(renting);
		renting.checkout(100);
		assertEquals(110, car.getKilometers());
	}

	@Test(expected = CarException.class)
	public void failCheckout(@Mocked final TaxInterface taxInterface,
			@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyInt);
				
				TaxInterface.submitInvoice((InvoiceData) this.any);
			
			}
		};
		Renting renting = car.rent(DRIVING_LICENSE, date1, date2, IBAN, NIF);
		this.rentACar1.getProcessor().submitRenting(renting);
		renting.checkout(-10);
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}
