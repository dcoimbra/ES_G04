package pt.ulisboa.tecnico.softeng.broker.services.remote;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import pt.ulisboa.tecnico.softeng.broker.services.remote.dataobjects.ActivityReservationData;

public class ActivityInterface {
	private static  Logger logger = LoggerFactory.getLogger(ActivityInterface.class);
	
	public static String reserveActivity(LocalDate begin, LocalDate end, int age, String nif, String iban) {
		ActivityReservationData activityData = new ActivityReservationData();
		String url = "http://localhost:8081/providers/reserveActivity";
		
		activityData.setBegin(begin);
		activityData.setEnd(end);
		activityData.setAge(age);
		activityData.setNif(nif);
		activityData.setIban(iban);
		
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			return restTemplate.postForObject("url", activityData, String.class);
		
		}catch(RestClientException rce){
			logger.error("Failed to post to {} due to error: {}", url, rce.getMessage());
			return null;
		}
	}

	public static String cancelReservation(String activityConfirmation) {
		String url = "http://localhost:8081/providers/cancelReservation";
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			return restTemplate.postForObject(url, activityConfirmation,  String.class);
		
		}catch(RestClientException rce){
			logger.error("Failed to post to {} due to error: {}", url, rce.getMessage());
			return null;
		}
	}

	public static ActivityReservationData getActivityReservationData(String reference) {
		String url = "http://localhost:8081/providers/reservationData/ + reference";
		RestTemplate restTemplate = new RestTemplate();
		
		try{
			return restTemplate.getForObject(url, ActivityReservationData.class);
		
		}catch(RestClientException rce){
			logger.error("Failed to get {} due to error: {}", url, rce.getMessage());
			return null;
		}
	}

}
