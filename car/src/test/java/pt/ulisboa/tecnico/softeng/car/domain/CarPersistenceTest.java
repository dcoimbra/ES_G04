package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;

public class CarPersistenceTest {
	private static final String RENTACAR_NAME = "RentACar_Name";
	private static final String NIF = "NIF";
	private static final String IBAN = "IBAN";
	private static final String CAR_PLATE = "aa-00-11";
	private static final String DRIVING_LICENSE = "br112233";
	private static final String BUYER_IBAN = "IBAN2";
	private static final String BUYER_NIF = "NIF2";
	private final LocalDate begin = new LocalDate(2017, 04, 01);
	private final LocalDate end = new LocalDate(2017, 04, 15);
	private static final int CAR_PRICE= 10;
	private static final int CAR_KILOMETERS = 10;;
	


	@Test
	public void success() {
		atomicProcess();
		atomicAssert();
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess() {
		RentACar rentACar = new RentACar(RENTACAR_NAME, NIF, IBAN);
		Vehicle car = new Car(CAR_PLATE, CAR_KILOMETERS, CAR_PRICE, rentACar);
		new Renting(DRIVING_LICENSE, begin, end, car, BUYER_NIF, BUYER_IBAN);
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssert() {
		assertEquals(1, FenixFramework.getDomainRoot().getRentACarSet().size());

		List<RentACar> rentACars = new ArrayList<>(FenixFramework.getDomainRoot().getRentACarSet());
		RentACar rentACar = rentACars.get(0);

		assertEquals(RENTACAR_NAME, rentACar.getName());
		assertEquals(IBAN, rentACar.getIban());
		assertEquals(NIF, rentACar.getNif());
		assertTrue(rentACar.getCode().startsWith(NIF));
		assertEquals(1, rentACar.getVehicleAndPlateSet().size());

		List<VehicleAndPlate> vehiclesAndPlates = new ArrayList<>(rentACar.getVehicleAndPlateSet());
		Vehicle car = vehiclesAndPlates.get(0).getVehicle();

		assertEquals(CAR_PLATE, car.getPlate());
		assertEquals(CAR_KILOMETERS, car.getKilometers());
		assertEquals(CAR_PRICE, car.getKilometers());
		assertEquals(1, car.getRentingSet().size());

		List<Renting> rentings = new ArrayList<>(car.getRentingSet());
		Renting renting = rentings.get(0);

		assertNotNull(renting.getReference());
		assertNull(renting.getCancellationReference());
		assertNull(renting.getCancellationDate());
	}

	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		for (RentACar rentACar : FenixFramework.getDomainRoot().getRentACarSet()) {
			for (VehicleAndPlate vap : rentACar.getVehicleAndPlateSet()) {
				vap.getVehicle().getPlatesSet().clear();
			}
			rentACar.getVehicleAndPlateSet().clear();
			rentACar.delete();
		}
		
	}

}
