package pt.ulisboa.tecnico.softeng.car.services.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.DateTime;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import pt.ulisboa.tecnico.softeng.car.services.remote.dataobjects.BankOperationData;

public class BankInterface {

	static RestTemplate restTemplate = new RestTemplate();

	private static Logger logger = LoggerFactory.getLogger(BankInterface.class);

	public static String processPayment(String IBAN, double amount) {
		DateTime time = DateTime.now();
		BankOperationData bankData = new BankOperationData();
		bankData.setIban(IBAN);
		bankData.setType("WITHDRAW");
		bankData.setTime(time);
		bankData.setValue(amount);
		String url = "http://localhost:8082/banks/processPayment";
		String response;

		try {
			response = restTemplate.postForObject(url, bankData , String.class);

		} catch (RestClientException e){
			logger.error("Failed to get {} due to error: {}", url, e.getMessage());
			return null;
		}

		return response;
	}

	public static String cancelPayment(String reference) {
		String url = "http://localhost:8082/banks/cancelPayment";
		String response;

		try{
			response = restTemplate.postForObject(url, reference , String.class);

		} catch (RestClientException e){
			logger.error("Failed to get {} due to error: {}", url, e.getMessage());
			return null;
		}

		return response;
	}
}
