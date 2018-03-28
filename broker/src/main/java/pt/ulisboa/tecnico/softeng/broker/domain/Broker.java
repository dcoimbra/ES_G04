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
	private final String seller;
	private final String buyer;
	private final Set<Adventure> adventures = new HashSet<>();
	private final Set<BulkRoomBooking> bulkBookings = new HashSet<>();

	public Broker(String code, String name, String seller, String buyer) {
		checkCode(code);
		this.code = code;

		checkName(name);
		this.name = name;
		
		checkSeller(code);
		this.seller = seller;
		
		checkBuyer(code);
		this.buyer = buyer;

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
		return this.seller;
	}
	
	String getBuyer() {
		return this.buyer;
	}

	
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
