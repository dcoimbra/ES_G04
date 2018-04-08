package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.interfaces.*;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

@RunWith(JMockit.class)
public class AdventureSequenceTest {
	private static final String IBAN = "BK01987654321";
	private static final int AMOUNT = 300;
	private static final double MARGIN = 0.3;
	private static final int AGE = 20;
    private static final String NIF = "123456789";
	private static final String PAYMENT_CONFIRMATION = "PaymentConfirmation";
	private static final String PAYMENT_CANCELLATION = "PaymentCancellation";
	private static final String ACTIVITY_CONFIRMATION = "ActivityConfirmation";
	private static final String ACTIVITY_CANCELLATION = "ActivityCancellation";
	private static final String ROOM_CONFIRMATION = "RoomConfirmation";
	private static final String ROOM_CANCELLATION = "RoomCancellation";
    private static final String RENT_CONFIRMATION = "RentConfirmation";
    private static final String RENT_CANCELLATION = "RentCancellation";
    private static final String TAX_CONFIRMATION = "TaxConfirmation";
    private static final String TAX_CANCELLATION = "TaxCancellation";
	private static final LocalDate arrival = new LocalDate(2016, 12, 19);
	private static final LocalDate departure = new LocalDate(2016, 12, 21);
	private static final String DRIVING_LICENSE = "IMT1234";
	private Adventure adventure;

	@Injectable
	private Broker broker;

	private Client client;

	@Before
	public void setUp() {
		this.client = new Client(broker, IBAN, NIF, DRIVING_LICENSE , AGE);
		this.adventure = new Adventure(this.broker, arrival, departure, this.client, MARGIN, true);
	}

	@Test
	public void successSequenceOne(@Mocked final BankInterface bankInterface,
                                   @Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface,
                                   @Mocked final CarInterface carInterface, @Mocked final TaxInterface taxInterface) {
		new Expectations() {
			{
			    broker.getIBAN();
			    this.result = IBAN;

                broker.getBuyer();
                this.result = NIF;

				BankInterface.processPayment(IBAN,0);
				this.result = PAYMENT_CONFIRMATION;

				ActivityInterface.reserveActivity(arrival, departure, AGE,NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure,NIF, IBAN);
				this.result = ROOM_CONFIRMATION;

                CarInterface.rentVehicle(DRIVING_LICENSE, arrival, departure, IBAN, NIF);
                this.result = RENT_CONFIRMATION;

                TaxInterface.submitInvoice((InvoiceData) this.any);
                this.result = TAX_CONFIRMATION;

				BankInterface.getOperationData(PAYMENT_CONFIRMATION);

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);

				HotelInterface.getRoomBookingData(ROOM_CONFIRMATION);

				CarInterface.getRentingData(RENT_CONFIRMATION);
			}
		};

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CONFIRMED, adventure.getState());
	}

	@Test
	public void successSequenceTwo(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface hotelInterface,
								   @Mocked final TaxInterface taxInterface) {

		new Expectations() {
			{

				broker.getIBAN();
				this.result = IBAN;

				broker.getBuyer();
				this.result = NIF;

				BankInterface.processPayment(IBAN, 0);
				this.result = PAYMENT_CONFIRMATION;

				ActivityInterface.reserveActivity(arrival, departure, AGE,NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure,NIF, IBAN);
				this.result = ROOM_CONFIRMATION;


				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.result = TAX_CONFIRMATION;

				BankInterface.getOperationData(PAYMENT_CONFIRMATION);

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);

				HotelInterface.getRoomBookingData(ROOM_CONFIRMATION);
			}
		};

		Adventure noCarAdventure = new Adventure(this.broker,arrival, departure, this.client, MARGIN,false);

		noCarAdventure.process();
		noCarAdventure.process();
		noCarAdventure.process();
		noCarAdventure.process();

		Assert.assertEquals(State.CONFIRMED, noCarAdventure.getState());
	}

	@Test
	public void unsuccessSequenceOne(@Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{

				broker.getIBAN();
				this.result = IBAN;

				broker.getBuyer();
				this.result = NIF;

				ActivityInterface.reserveActivity(arrival, departure, AGE,NIF, IBAN);
				this.result = new ActivityException();
			}
		};

		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceTwo(@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface) {
		new Expectations() {
			{

				broker.getIBAN();
				this.result = IBAN;

				broker.getBuyer();
				this.result = NIF;

				ActivityInterface.reserveActivity(arrival, departure, AGE,NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure,NIF, IBAN);
				this.result = new HotelException();

				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;
			}
		};

		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceThree(@Mocked final CarInterface carInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface) {
		new Expectations() {
			{

                broker.getIBAN();
                this.result = IBAN;

                broker.getBuyer();
                this.result = NIF;

				ActivityInterface.reserveActivity(arrival, departure, AGE ,NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure ,NIF, IBAN);
 				this.result = ROOM_CONFIRMATION;

 				CarInterface.rentVehicle(DRIVING_LICENSE,arrival,departure,IBAN,NIF);
 				this.result = new CarException();

				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;

				HotelInterface.cancelBooking(ROOM_CONFIRMATION);
				this.result = ROOM_CANCELLATION;
			}
		};

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();


		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceFour(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface, @Mocked final CarInterface carInterface) {
		new Expectations() {
			{

				broker.getIBAN();
				this.result = IBAN;

				broker.getBuyer();
				this.result = NIF;

				ActivityInterface.reserveActivity(arrival, departure, AGE ,NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure ,NIF, IBAN);
				this.result = ROOM_CONFIRMATION;

				CarInterface.rentVehicle(DRIVING_LICENSE,arrival,departure,IBAN,NIF);
				this.result = RENT_CONFIRMATION;

				BankInterface.processPayment(IBAN, 0);
				this.result = new BankException();

			}
		};

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceFive(@Mocked final BankInterface bankInterface,
									  @Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface,
									  @Mocked final CarInterface carInterface, @Mocked final TaxInterface taxInterface) {
		new Expectations() {
			{

				broker.getIBAN();
				this.result = IBAN;

				broker.getBuyer();
				this.result = NIF;

				ActivityInterface.reserveActivity(arrival, departure, AGE ,NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure ,NIF, IBAN);
				this.result = ROOM_CONFIRMATION;

				CarInterface.rentVehicle(DRIVING_LICENSE,arrival,departure,IBAN,NIF);
				this.result = RENT_CONFIRMATION;

				BankInterface.processPayment(IBAN, 0);
				this.result = PAYMENT_CONFIRMATION;

				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.result = new TaxException();

			}
		};

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

}