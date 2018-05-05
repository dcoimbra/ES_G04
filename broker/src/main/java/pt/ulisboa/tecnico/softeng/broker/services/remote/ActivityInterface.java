package pt.ulisboa.tecnico.softeng.broker.services.remote;

import org.joda.time.LocalDate;
import org.springframework.web.client.RestTemplate;

import pt.ulisboa.tecnico.softeng.broker.services.remote.dataobjects.ActivityReservationData;

public class ActivityInterface {
	public static String reserveActivity(LocalDate begin, LocalDate end, int age, String nif, String iban) {
		ActivityReservationData activityData = new ActivityReservationData();
		
		activityData.setBegin(begin);
		activityData.setEnd(end);
		activityData.setAge(age);
		activityData.setNif(nif);
		activityData.setIban(iban);
		
		RestTemplate restTemplate = new RestTemplate();
		
		return restTemplate.postForObject("http://localhost:8081/providers/reserveActivity", activityData, String.class);
	}

	public static String cancelReservation(String activityConfirmation) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject("http://localhost:8081/providers/cancelReservation", activityConfirmation,  String.class);
	}

	public static ActivityReservationData getActivityReservationData(String reference) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject("http://localhost:8081/providers/reservationData/ + reference", ActivityReservationData.class);
	}

}
