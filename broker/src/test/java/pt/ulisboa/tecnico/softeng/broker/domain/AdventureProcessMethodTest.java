package pt.ulisboa.tecnico.softeng.broker.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;
import pt.ulisboa.tecnico.softeng.bank.domain.Account;
import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.tax.domain.Buyer;
import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.Seller;
import pt.ulisboa.tecnico.softeng.car.domain.Car;

public class AdventureProcessMethodTest {

    private static final int AGE = 20;
    private static final int AMOUNT = 300;
    private static final String NIF = "123456789";

	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);

    private Client client;
	private Broker broker;
	private String IBAN;

	private static final String NAME_OF_RENT_A_CAR = "Drive Save";
	private static final String NIF_OF_RENT_A_CAR = "666666666";

	private static final String PLATE_CAR = "00-33-HZ";

    private static final String SELLER_ADDRESS = "Somewhere";
    private static final String SELLER_NAME = "José Vendido";
    private static final String SELLER_NIF = "123456789";

    private static final String BUYER_ADDRESS = "Narnia";
    private static final String BUYER_NAME = "Jacinto Costa";
    private static final String BUYER_NIF = "987654321";
	private static final String DRIVING_LICENSE = "IMT1234";
    
    private static final double PRICE_SINGLE = 20.0;
	private static final double PRICE_DOUBLE = 30.0;

	private static Car car;
	private RentACar rentACar;

    IRS irs;

	@Before
	public void setUp() {

        this.irs = IRS.getIRS();

        Seller seller = new Seller(IRS.getIRS(), SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);
        Buyer buyer = new Buyer(IRS.getIRS(), BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);
		Bank bank = new Bank("Money", "BK01");
		pt.ulisboa.tecnico.softeng.bank.domain.Client clientB = new pt.ulisboa.tecnico.softeng.bank.domain.Client(bank, "António");
		Account account = new Account(bank, clientB);

		this.IBAN = account.getIBAN();

		pt.ulisboa.tecnico.softeng.bank.domain.Client rentACarClient = new pt.ulisboa.tecnico.softeng.bank.domain.Client(
				bank, NAME_OF_RENT_A_CAR);

		Account rentACarAccount = new Account(bank, rentACarClient);

        this.broker = new Broker("BR01", "eXtremeADVENTURE", SELLER_NIF, BUYER_NIF, IBAN);

		rentACar = new RentACar(NAME_OF_RENT_A_CAR, NIF_OF_RENT_A_CAR,
				rentACarAccount.getIBAN());

		car = new Car(PLATE_CAR, 10, rentACar, 20);

		account.deposit(1000);

        this.client = new Client(broker, IBAN, NIF,DRIVING_LICENSE ,AGE);

		Hotel hotel = new Hotel("XPTO123", "Paris", "NIF_HOTEL", "IBAN_HOTEL", PRICE_SINGLE, PRICE_DOUBLE);
		new Room(hotel, "01", Type.SINGLE);

		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure", "NIF", "IBAN");
		Activity activity = new Activity(provider, "Bush Walking", 18, 80, 10);
		new ActivityOffer(activity, this.begin, this.end, 30);
		new ActivityOffer(activity, this.begin, this.begin, 30);
	}

	@Test
	public void success() {


		Adventure adventure = new Adventure(this.broker, this.begin, this.end, this.client, AMOUNT, true);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		assertEquals(Adventure.State.CONFIRMED, adventure.getState());
		assertNotNull(adventure.getPaymentConfirmation());
		assertNotNull(adventure.getRoomConfirmation());
		assertNotNull(adventure.getActivityConfirmation());
	}

	@Test
	public void successNoHotelBooking() {
        Adventure adventure = new Adventure(this.broker, this.begin, this.begin, this.client, AMOUNT, true);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		assertEquals(Adventure.State.CONFIRMED, adventure.getState());
		assertNotNull(adventure.getPaymentConfirmation());
		assertNotNull(adventure.getActivityConfirmation());
	}

	@After
	public void tearDown() {
        IRS.getIRS().clearAll();
		Bank.banks.clear();
		Hotel.hotels.clear();
		ActivityProvider.providers.clear();
		Broker.brokers.clear();
		Car.removePlates();
		rentACar.removeVehicles();
		RentACar.rentACars.clear();

	}
}
