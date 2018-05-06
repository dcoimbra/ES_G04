package pt.ulisboa.tecnico.softeng.broker.services.remote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.broker.services.remote.dataobjects.RoomBookingData;
import pt.ulisboa.tecnico.softeng.broker.services.remote.exception.HotelException;
import pt.ulisboa.tecnico.softeng.broker.services.remote.exception.RemoteAccessException;

public class HotelInterface {
	
	public static enum Type {
		SINGLE, DOUBLE
	}
	
	private static Logger logger = LoggerFactory.getLogger(HotelInterface.class);

	private static String ENDPOINT = "http://localhost:8085/hotels/rest/";

	public static String reserveRoom(Type type, LocalDate arrival, LocalDate departure, String buyerNif, String buyerIban) {
		logger.info("reserveRoom arrival:{}, departure:{}, buyerNif:{}, buyerIban:{}", arrival, departure, buyerNif, buyerIban);
		
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			return restTemplate.postForObject( ENDPOINT + "reserve?type=" + type + "&arrival=" + arrival + 
					"&departure=" + departure + "&buyerNif=" + buyerNif + "&buyerNif=" + buyerIban, null, String.class);
		} catch (HttpClientErrorException e) {
			throw new HotelException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}

	public static String cancelBooking(String roomConfirmation) {
		logger.info("cancelBooking roomConfirmation:{}", roomConfirmation);
		RestTemplate restTemplate = new RestTemplate();
		try {
			return restTemplate.postForObject(ENDPOINT + "cancel?reference=" + roomConfirmation, null, String.class);
		} catch (HttpClientErrorException e) {
			throw new HotelException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}

	public static RoomBookingData getRoomBookingData(String reference) {
		logger.info("getRoomBookingData reference:{}", reference);
		RestTemplate restTemplate = new RestTemplate();
		try {
			return restTemplate.getForObject(ENDPOINT + "booking?reference=" + reference, RoomBookingData.class);
		} catch (HttpClientErrorException e) {
			throw new HotelException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}

	public static Set<String> bulkBooking(int number, LocalDate arrival, LocalDate departure, String buyerNif, String buyerIban) {
		logger.info("reserveRoom arrival:{}, departure:{}, buyerNif:{}, buyerIban:{}", arrival, departure, buyerNif, buyerIban);
		
		RestTemplate restTemplate = new RestTemplate();
		try {
			String[] result = restTemplate.postForObject(
					ENDPOINT + "bulk?number=" + number + "&arrival=" + arrival + "&departure=" + departure + 
					"&buyerNif=" + buyerNif + "&buyerNif=" + buyerIban, null, String[].class);
			
			return new HashSet<>(Arrays.asList(result));
		} catch (HttpClientErrorException e) {
			throw new HotelException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}

}
