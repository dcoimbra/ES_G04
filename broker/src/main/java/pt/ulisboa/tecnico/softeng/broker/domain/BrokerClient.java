package pt.ulisboa.tecnico.softeng.broker.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class BrokerClient {
	private static Logger logger = LoggerFactory.getLogger(BrokerClient.class);

	private final String IBAN;
	private final String NIF;
	private final int age;

	public BrokerClient(String IBAN, String NIF, int age) {
		checkArguments(IBAN, NIF, age);

		this.IBAN = IBAN;
		this.NIF = NIF;
		this.age = age;
	}

	private void checkArguments(String IBAN, String NIF, int age){
		if (NIF == null || NIF.length() != 9) 
			throw new BrokerException();

		if (IBAN == null || IBAN.trim().length() == 0)
			throw new BrokerException();

		if (age < 18 || age > 100) {
			throw new BrokerException();
		}
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

}
