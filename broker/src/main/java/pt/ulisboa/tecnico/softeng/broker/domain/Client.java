package pt.ulisboa.tecnico.softeng.broker.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class Client {
	private static Logger logger = LoggerFactory.getLogger(Client.class);
	private static String drivingLicenseFormat = "^[a-zA-Z]+\\d+$";

	private final Broker broker;
	private final String IBAN;
	private final String NIF;
	private final int age;
	private final String drivingLicense;

	public Client(Broker broker, String IBAN, String NIF, String drivingLicense, int age) {
		checkArguments(broker, IBAN, NIF, age, drivingLicense);

		this.broker = broker;
		this.IBAN = IBAN;
		this.NIF = NIF;
		this.age = age;
		this.drivingLicense = drivingLicense;
	}

	private void checkArguments(Broker broker, String IBAN, String NIF, int age, String drivingLicense) {
		if (broker == null)
			throw new BrokerException();

		if (NIF == null || NIF.length() != 9) 
			throw new BrokerException();

		if (IBAN == null || IBAN.trim().length() == 0)
			throw new BrokerException();

		if (age < 18 || age > 100) {
			throw new BrokerException();
		}

		if (drivingLicense == null || !drivingLicense.matches(drivingLicenseFormat))
			throw new BrokerException();
	}

	public String getIBAN() {
		return this.IBAN;
	}

	public String getNIF() {
		return this.NIF;
	}

	public int getAGE() {
		return this.age;
	}

	public String getDrivingLicense() {
		return this.drivingLicense;
	}
}
