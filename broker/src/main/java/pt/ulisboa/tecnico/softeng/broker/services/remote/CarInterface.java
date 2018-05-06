package pt.ulisboa.tecnico.softeng.broker.services.remote;

import org.joda.time.LocalDate;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ulisboa.tecnico.softeng.broker.services.remote.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.broker.services.remote.exception.CarException;
import pt.ulisboa.tecnico.softeng.broker.services.remote.exception.RemoteAccessException;

public class CarInterface {

    private static  Logger logger = LoggerFactory.getLogger(ActivityInterface.class);

	private static String ENDPOINT = "http://localhost:8084/rest/car/";

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

		RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.postForObject(ENDPOINT + "rentcar", rentingData, String.class);
        } catch (HttpClientErrorException e) {
			throw new CarException();
        } catch (Exception e) {
			logger.info("rentCar REMOTE");
			throw new RemoteAccessException();
		}

	}

	public static String cancelRenting(String rentingReference) {
		RestTemplate restTemplate = new RestTemplate();

		try {
            return restTemplate.postForObject(ENDPOINT + "cancel?Renting="+ rentingReference, null,String.class);
        } catch (HttpClientErrorException e) {
			throw new CarException();
		} catch (Exception e) {
			logger.info("cancelRenting REMOTE");
			throw new RemoteAccessException();
		}
	}

	public static RentingData getRentingData(String reference) {
		RestTemplate restTemplate = new RestTemplate();

		try {
            RentingData result = restTemplate.getForObject(ENDPOINT + "operation?reference=" + reference, RentingData.class);
			logger.info("getRentingData reference:{}", result.getReference());
			return  result;
		} catch (HttpClientErrorException e) {
			throw new CarException();
		} catch (Exception e) {
			logger.info("getRentingData REMOTE");
			throw new RemoteAccessException();
		}
	}

}

