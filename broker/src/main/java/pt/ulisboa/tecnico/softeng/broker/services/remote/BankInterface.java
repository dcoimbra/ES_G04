package pt.ulisboa.tecnico.softeng.broker.services.remote;

import org.springframework.web.client.RestTemplate;
import pt.ulisboa.tecnico.softeng.broker.services.remote.dataobjects.BankOperationData;

import org.joda.time.DateTime;

public class BankInterface {
	public static String processPayment(String IBAN, double amount) {
		DateTime time = DateTime.now();
		BankOperationData bankData = new BankOperationData();
		bankData.setIban(IBAN);
		bankData.setType("WITHDRAW");
		bankData.setTime(time);
		bankData.setValue(amount);

		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.postForObject("http://localhost:8082/banks/processPayment", bankData , String.class);
	}

	public static String cancelPayment(String reference) {

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject("http://localhost:8082/banks/cancelPayment", reference , String.class);
	}

	public static BankOperationData getOperationData(String reference) {

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject("http://localhost:8082/banks/operationData/" + reference, BankOperationData.class);
	}
}
