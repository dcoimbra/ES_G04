package pt.ulisboa.tecnico.softeng.broker.services.remote;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pt.ulisboa.tecnico.softeng.broker.services.remote.dataobjects.BankOperationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.joda.time.DateTime;

public class BankInterface {
	private static Logger logger = LoggerFactory.getLogger(BankInterface.class);

	public static String processPayment(String IBAN, double amount) {
		DateTime time = DateTime.now();
		BankOperationData bankData = new BankOperationData();
		bankData.setIban(IBAN);
		bankData.setType("WITHDRAW");
		bankData.setTime(time);
		bankData.setValue(amount);

		String url = "http://localhost:8082/banks/processPayment";
		RestTemplate restTemplate = new RestTemplate();
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

		RestTemplate restTemplate = new RestTemplate();
		String response;
		try{
			response = restTemplate.postForObject(url, reference , String.class);
		} catch (RestClientException e){
			logger.error("Failed to get {} due to error: {}", url, e.getMessage());
			return null;
		}

		return response;
	}

	public static BankOperationData getOperationData(String reference) {

		String url = "http://localhost:8082/banks/operationData/" + reference;

		RestTemplate restTemplate = new RestTemplate();
		BankOperationData bankdto;

		try{
			bankdto =  restTemplate.getForObject(url, BankOperationData.class);
		} catch (RestClientException e){
			logger.error("Failed to get {} due to error: {}", url, e.getMessage());
			return null;
		}

		return bankdto;
	}
}
