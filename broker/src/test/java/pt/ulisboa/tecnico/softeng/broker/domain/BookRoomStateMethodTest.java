package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Delegate;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

@RunWith(JMockit.class)
public class BookRoomStateMethodTest {
	private static final String IBAN = "BK01987654321";
	private static final int AMOUNT = 300;
	private static final int AGE = 20;
    private static final String NIF = "123456789";
    private static final String ROOM_CONFIRMATION = "RoomConfirmation";
	private static final LocalDate arrival = new LocalDate(2016, 12, 19);
	private static final LocalDate departure = new LocalDate(2016, 12, 21);
	private Adventure adventure;
	private static final String DRIVING_LICENSE = "IMT1234";

    @Injectable
	private Broker broker;

	private Client client;

	@Before
	public void setUp() {
		this.client = new Client(this.broker,IBAN, NIF, DRIVING_LICENSE ,AGE);
		this.adventure = new Adventure(this.broker, arrival, departure, this.client, AMOUNT, true);
		this.adventure.setState(State.BOOK_ROOM);
	}

	@Test
	public void successBookRoom(@Mocked final HotelInterface hotelInterface) {
		new Expectations() {
			{
				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = ROOM_CONFIRMATION;
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.CONFIRMED, this.adventure.getState());
	}

	@Test
	public void hotelException(@Mocked final HotelInterface hotelInterface) {
		new Expectations() {
			{
				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure ,NIF, IBAN);
				this.result = new HotelException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}

	@Test
	public void singleRemoteAccessException(@Mocked final HotelInterface hotelInterface) {
		new Expectations() {
			{
				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure,NIF, IBAN);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.BOOK_ROOM, this.adventure.getState());
	}

	@Test
	public void maxRemoteAccessException(@Mocked final HotelInterface hotelInterface) {
		new Expectations() {
			{
				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure,NIF, IBAN);
				this.result = new RemoteAccessException();
				this.times = BookRoomState.MAX_REMOTE_ERRORS;
			}
		};

		for (int i = 0; i < BookRoomState.MAX_REMOTE_ERRORS; i++) {
			this.adventure.process();
		}

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}

	@Test
	public void maxMinusOneRemoteAccessException(@Mocked final HotelInterface hotelInterface) {
		new Expectations() {
			{
				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure,NIF, IBAN);
				this.result = new RemoteAccessException();
				this.times = BookRoomState.MAX_REMOTE_ERRORS - 1;
			}
		};

		for (int i = 0; i < BookRoomState.MAX_REMOTE_ERRORS - 1; i++) {
			this.adventure.process();
		}

		Assert.assertEquals(State.BOOK_ROOM, this.adventure.getState());
	}

	@Test
	public void fiveRemoteAccessExceptionOneSuccess(@Mocked final HotelInterface hotelInterface) {
		new Expectations() {
			{
				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure,NIF, IBAN);
				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 5) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							return ROOM_CONFIRMATION;
						}
					}
				};
				this.times = 6;
			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.CONFIRMED, this.adventure.getState());
	}

	@Test
	public void oneRemoteAccessExceptionOneActivityException(@Mocked final HotelInterface hotelInterface) {
		new Expectations() {
			{
				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure,NIF, IBAN);
				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 1) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							throw new HotelException();
						}
					}
				};
				this.times = 2;
			}
		};

		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}

}