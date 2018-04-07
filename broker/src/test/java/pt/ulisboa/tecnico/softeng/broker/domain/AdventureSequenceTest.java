package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

@RunWith(JMockit.class)
public class AdventureSequenceTest {
	private static final String IBAN = "BK01987654321";
	private static final int AMOUNT = 300;
	private static final int AGE = 20;
    private static final String NIF = "123456789";
	private static final String PAYMENT_CONFIRMATION = "PaymentConfirmation";
	private static final String PAYMENT_CANCELLATION = "PaymentCancellation";
	private static final String ACTIVITY_CONFIRMATION = "ActivityConfirmation";
	private static final String ACTIVITY_CANCELLATION = "ActivityCancellation";
	private static final String ROOM_CONFIRMATION = "RoomConfirmation";
	private static final String ROOM_CANCELLATION = "RoomCancellation";
	private static final LocalDate arrival = new LocalDate(2016, 12, 19);
	private static final LocalDate departure = new LocalDate(2016, 12, 21);
	private static final String DRIVING_LICENSE = "IMT1234";

	@Injectable
	private Broker broker;

	private Client client;


	@Test
	public void successSequenceOne(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface) {
		new Expectations() {
			{
			    System.out.println("SQ1");
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;

				ActivityInterface.reserveActivity(arrival, departure, AGE,NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure,NIF, IBAN);
				this.result = ROOM_CONFIRMATION;

				BankInterface.getOperationData(PAYMENT_CONFIRMATION);

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);

				HotelInterface.getRoomBookingData(ROOM_CONFIRMATION);
			}
		};

		client = new Client(broker, IBAN, NIF, DRIVING_LICENSE , AGE);
		Adventure adventure = new Adventure(this.broker, arrival, departure, this.client, AMOUNT, true);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CONFIRMED, adventure.getState());
	}

	@Test
	public void successSequenceTwo(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
                System.out.println("SQ2");
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;

				ActivityInterface.reserveActivity(arrival, arrival, AGE,NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				BankInterface.getOperationData(PAYMENT_CONFIRMATION);

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
			}
		};

		client = new Client(broker, IBAN, NIF, DRIVING_LICENSE , AGE);
        Adventure adventure = new Adventure(this.broker, arrival, arrival, this.client, AMOUNT, false);

		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CONFIRMED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceOne(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface) {
		new Expectations() {
			{
                System.out.println("USQ1");
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = new BankException();
			}
		};

		client = new Client(broker, IBAN, NIF, DRIVING_LICENSE , AGE);
        Adventure adventure = new Adventure(this.broker, arrival, departure, this.client, AMOUNT, true);

		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceTwo(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface) {
		new Expectations() {
			{
                System.out.println("USQ2");
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;

				ActivityInterface.reserveActivity(arrival, departure, AGE, NIF, IBAN);
				this.result = new ActivityException();

				BankInterface.cancelPayment(PAYMENT_CONFIRMATION);
				this.result = PAYMENT_CANCELLATION;
			}
		};

		client = new Client(broker, IBAN, NIF, DRIVING_LICENSE , AGE);

        Adventure adventure = new Adventure(this.broker, arrival, departure, this.client, AMOUNT, true);

		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceThree(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface) {
		new Expectations() {
			{
                System.out.println("USQ3");
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;

				ActivityInterface.reserveActivity(arrival, departure, AGE ,NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure ,NIF, IBAN);
 				this.result = new HotelException();

				BankInterface.cancelPayment(PAYMENT_CONFIRMATION);
				this.result = PAYMENT_CANCELLATION;

				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;
			}
		};

		client = new Client(broker, IBAN, NIF, DRIVING_LICENSE , AGE);

        Adventure adventure = new Adventure(this.broker, arrival, departure, this.client, AMOUNT, true);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceFour(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface) {
		new Expectations() {
			{
                System.out.println("USQ4");
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;

				ActivityInterface.reserveActivity(arrival, departure, AGE,NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure,NIF, IBAN);
				this.result = ROOM_CONFIRMATION;

				BankInterface.getOperationData(PAYMENT_CONFIRMATION);
				this.result = new BankException();
				this.times = ConfirmedState.MAX_BANK_EXCEPTIONS;

				BankInterface.cancelPayment(PAYMENT_CONFIRMATION);
				this.result = PAYMENT_CANCELLATION;

				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;

				HotelInterface.cancelBooking(ROOM_CONFIRMATION);
				this.result = ROOM_CANCELLATION;
			}
		};

		client = new Client(broker, IBAN, NIF, DRIVING_LICENSE , AGE);

        Adventure adventure = new Adventure(this.broker, arrival, departure, this.client, AMOUNT, true);

		adventure.process();
		adventure.process();
		adventure.process();
		for (int i = 0; i < ConfirmedState.MAX_BANK_EXCEPTIONS; i++) {
			adventure.process();
		}
		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

}