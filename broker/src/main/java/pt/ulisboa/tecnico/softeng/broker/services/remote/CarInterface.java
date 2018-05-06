package pt.ulisboa.tecnico.softeng.broker.services.remote;

import org.joda.time.LocalDate;

import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import pt.ulisboa.tecnico.softeng.broker.services.remote.dataobjects.RentingData;

public class CarInterface {

    private static  Logger logger = LoggerFactory.getLogger(ActivityInterface.class);

	public static enum Type {
		CAR, MOTORCYCLE
	}

	public static String rentCar(Type vehicleType, String drivingLicense, String nif, String iban, LocalDate begin,
			LocalDate end) {

		RentingData rentingData = new RentingData();
		rentingData.setVehicleType(vehicleType);
		rentingData.setDrivingLicense(drivingLicense);
		rentingData.setBegin(begin);
		rentingData.setEnd(end);
		rentingData.setNif(nif);
		rentingData.setIban(iban);

		String url = "http://localhost:8084/rentacars/rent";
		RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.postForObject(url, rentingData, String.class);
        } catch(RestClientException rce) {
            logger.error("Failed to post to {} due to error: {}", url, rce.getMessage());
            return null;
        }

	}

	public static String cancelRenting(String rentingReference) {
		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8084/rentacars/cancelRenting";
		try {
            return restTemplate.postForObject(url, rentingReference, String.class);
        } catch(RestClientException rce) {
            logger.error("Failed to post to {} due to error: {}", url, rce.getMessage());
            return null;
        }
	}

	public static RentingData getRentingData(String reference) {
		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8084/rentacars/rentingData/" + reference;

		try {
            return restTemplate.getForObject("http://localhost:8084/rentacars/rentingData/" + reference, RentingData.class);
        } catch(RestClientException rce) {
            logger.error("Failed to get {} due to error: {}", url, rce.getMessage());
            return null;
        }
	}

}
