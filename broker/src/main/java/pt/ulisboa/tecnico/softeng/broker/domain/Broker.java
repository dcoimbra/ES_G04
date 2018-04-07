package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class Broker {
	private static Logger logger = LoggerFactory.getLogger(Broker.class);

	public static Set<Broker> brokers = new HashSet<>();

	private final String code;
	private final String name;
	private final String sellerNIF;
	private final String buyerNIF;
	private final String IBAN;
	private final Set<Adventure> adventures = new HashSet<>();
	private final Set<BulkRoomBooking> bulkBookings = new HashSet<>();

	public Broker(String code, String name, String sellerNIF, String buyerNIF, String IBAN) {
		checkCode(code);
		this.code = code;

		checkName(name);
		this.name = name;
		
		checkSeller(sellerNIF);
		this.sellerNIF = sellerNIF;

		checkBuyer(buyerNIF);
		this.buyerNIF = buyerNIF;

		checkIBAN(IBAN);
		this.IBAN = IBAN;

		Broker.brokers.add(this);
	}

	private void checkCode(String code) {
		if (code == null || code.trim().length() == 0) {
			throw new BrokerException();
		}

		for (Broker broker : Broker.brokers) {
			if (broker.getCode().equals(code)) {
				throw new BrokerException();
			}
		}
	}

	private void checkSeller(String seller) {
		if (seller == null || seller.trim().length() == 0) {
			throw new BrokerException("1");
		}

		for (Broker broker : Broker.brokers) {
			if (broker.getSeller().equals(seller)) {
				throw new BrokerException("2");
			}
		}
	}

	private void checkIBAN(String IBAN){
		if (IBAN == null || IBAN.trim().length() == 0)
			throw new BrokerException();
	}
	
	private void checkBuyer(String buyer) {
		if (buyer== null || buyer.trim().length() == 0) {
			throw new BrokerException();
		}

		for (Broker broker : Broker.brokers) {
			if (broker.getBuyer().equals(buyer)) {
				throw new BrokerException();
			}
		}
	}


	private void checkName(String name) {
		if (name == null || name.trim().length() == 0) {
			throw new BrokerException();
		}
	}

	String getCode() {
		return this.code;
	}

	String getName() {
		return this.name;
	}
    
	String getSeller() {
		return this.sellerNIF;
	}
	
	String getBuyer() {
		return this.buyerNIF;
	}

	String getIBAN(){ return this.IBAN; };

	
	public int getNumberOfAdventures() {
		return this.adventures.size();
	}

	public void addAdventure(Adventure adventure) {
		this.adventures.add(adventure);
	}

	public boolean hasAdventure(Adventure adventure) {
		return this.adventures.contains(adventure);
	}

	public void bulkBooking(int number, LocalDate arrival, LocalDate departure) {
		BulkRoomBooking bulkBooking = new BulkRoomBooking(number, arrival, departure);
		this.bulkBookings.add(bulkBooking);
		bulkBooking.processBooking();
	}

}
