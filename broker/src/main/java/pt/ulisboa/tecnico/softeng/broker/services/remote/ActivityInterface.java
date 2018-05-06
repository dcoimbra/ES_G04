package pt.ulisboa.tecnico.softeng.broker.services.remote;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pt.ulisboa.tecnico.softeng.broker.services.remote.dataobjects.ActivityReservationData;
import pt.ulisboa.tecnico.softeng.broker.services.remote.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.broker.services.remote.exception.RemoteAccessException;

public class ActivityInterface {
	private static  Logger logger = LoggerFactory.getLogger(ActivityInterface.class);
	

	private static String ENDPOINT = "http://localhost:8081/providers/rest/";
	
	public static String reserveActivity(LocalDate begin, LocalDate end, int age, String nif, String iban) {
		logger.info("reserveActivity begin:{}, end:{}, age:{}, nif:{}, iban:{}", begin, end, age, nif, iban);
		ActivityReservationData activityData = new ActivityReservationData();
		
		activityData.setBegin(begin);
		activityData.setEnd(end);
		activityData.setAge(age);
		activityData.setNif(nif);
		activityData.setIban(iban);
		
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			return restTemplate.postForObject(ENDPOINT + "reserve?begin=" + begin + "&end=" + end + "&age=" + age
					+ "&nif=" + nif + "&iban=" + iban, null, String.class);
		} catch (HttpClientErrorException e) {
			throw new ActivityException();
		} catch (Exception e) {
			throw new RemoteAccessException();
}
	}

	public static String cancelReservation(String activityConfirmation) {
		logger.info("cancelReservation activityConfirmation:{}", activityConfirmation);
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			return restTemplate.postForObject(ENDPOINT + "cancel?reference=" + activityConfirmation, null, String.class);
		
		} catch (HttpClientErrorException e) {
			throw new ActivityException();
		} catch (Exception e) {
			throw new RemoteAccessException();
}
	}

	public static ActivityReservationData getActivityReservationData(String reference) {
		logger.info("getActivityReservationData reference:{}", reference);
		RestTemplate restTemplate = new RestTemplate();
		
		try{
			return restTemplate.getForObject(ENDPOINT + "reservation?reference=" + reference, ActivityReservationData.class);
		
		} catch (HttpClientErrorException e) {
			throw new ActivityException();
		} catch (Exception e) {
			throw new RemoteAccessException();
}
	}

}
