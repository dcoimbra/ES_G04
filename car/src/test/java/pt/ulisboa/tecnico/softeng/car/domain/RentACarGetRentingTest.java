package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.car.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.car.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;

@RunWith(JMockit.class)
public class RentACarGetRentingTest {
	private static final String NAME1 = "eartz";
	private static final String PLATE_CAR1 = "aa-99-11";
	private static final String DRIVING_LICENSE = "br123";
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-07");
	private static final LocalDate date3 = LocalDate.parse("2018-01-08");
	private static final LocalDate date4 = LocalDate.parse("2018-01-09");
	private static final String IBAN = "BK123456789";
	private static final String NIF = "123456789";
	private static final int PRICE = 20;
	private Renting renting;
	RentACar rentACar1;
	Vehicle car1;

	@Before
	public void setUp() {
		rentACar1 = new RentACar(NAME1, NIF, IBAN);
		car1 = new Car(PLATE_CAR1, 10, rentACar1, PRICE);
	}

	@Test
	public void getRenting(@Mocked final TaxInterface taxInterface,
			@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyInt);
				
				TaxInterface.submitInvoice((InvoiceData) this.any);
			
			}
		};
		this.renting = car1.rent(DRIVING_LICENSE, date1, date2, IBAN, NIF);
		rentACar1.getProcessor().submitRenting(renting);
		
		rentACar1.getProcessor().submitRenting(car1.rent(DRIVING_LICENSE, date3, date4, IBAN, NIF));
		
		assertEquals(this.renting, RentACar.getRenting(this.renting.getReference()));
	}
	
	@Test
	public void nonExistent(@Mocked final TaxInterface taxInterface,
			@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyInt);
				
				TaxInterface.submitInvoice((InvoiceData) this.any);
			
			}
		};
		this.renting = car1.rent(DRIVING_LICENSE, date1, date2, IBAN, NIF);
		rentACar1.getProcessor().submitRenting(renting);
		
		rentACar1.getProcessor().submitRenting(car1.rent(DRIVING_LICENSE, date3, date4, IBAN, NIF));

		assertNull(RentACar.getRenting(null));
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}
