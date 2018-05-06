package pt.ulisboa.tecnico.softeng.hotel.services.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import pt.ulisboa.tecnico.softeng.hotel.services.remote.exceptions.BankException;
import pt.ulisboa.tecnico.softeng.hotel.services.remote.exceptions.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.hotel.services.remote.dataobjects.BankOperationData;


public class BankInterface {
	
	private static String ENDPOINT = "http://localhost:8082";
	
	private static Logger logger = LoggerFactory.getLogger(BankInterface.class);
	
	public static String processPayment(String iban, double amount) {			
		logger.info("processPayment iban:{}, amount:{}", iban, amount);

		RestTemplate restTemplate = new RestTemplate();
		try {
			String result = restTemplate.postForObject(
					ENDPOINT + "/rest/banks/accounts/" + iban + "/processPayment?amount=" + amount, null, String.class);
			return result;
			
		} catch (HttpClientErrorException e) {
			throw new BankException();
			
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}

	public static String cancelPayment(String reference) {
        
		logger.info("cancelPayment reference:{}", reference);

		RestTemplate restTemplate = new RestTemplate();
		try {
			String result = restTemplate.postForObject(ENDPOINT + "/rest/banks/cancel?reference=" + reference, null,
					String.class);
			return result;
			
		} catch (HttpClientErrorException e) {
			throw new BankException();
			
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}
	
	public static BankOperationData getOperationData(String reference) {
		logger.info("getOperationData reference:{}", reference);

		RestTemplate restTemplate = new RestTemplate();
		try {
			BankOperationData result = restTemplate
					.getForObject(ENDPOINT + "/rest/banks/operation?reference=" + reference, BankOperationData.class);
			logger.info("getOperationData iban:{}", result.getIban());
			return result;
		} catch (HttpClientErrorException e) {
			throw new BankException();
		} catch (Exception e) {
			logger.info("getOperationData REMOTE");

			throw new RemoteAccessException();
		}
	}
	
}
