package pt.ulisboa.tecnico.softeng.broker.services.remote;

import java.util.Set;
import org.springframework.web.client.RestTemplate;
import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.broker.services.remote.dataobjects.RoomBookingData;

public class HotelInterface {
	public static enum Type {
		SINGLE, DOUBLE
	}

	public static String reserveRoom(Type type, LocalDate arrival, LocalDate departure, String buyerNif,
			String buyerIban) {
         
		RoomBookingData hotelData  = new RoomBookingData ();
		
        hotelData.setArrival(arrival);
        hotelData.setDeparture(departure);
        hotelData.setRoomType( type.toString());
        hotelData.setBuyerNif(buyerNif);
        hotelData.setBuyerIban(buyerIban);
		RestTemplate restTemplate = new RestTemplate();
		
		return restTemplate.postForObject("http://localhost:8085/hotel/reserveRoom", hotelData , String.class);
		 	
	}

	public static String cancelBooking(String roomConfirmation) {
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject("http://localhost:8085/hotel/cancelBooking", roomConfirmation , String.class);
		 	
	}

	public static RoomBookingData getRoomBookingData(String reference) {
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject("http://localhost:8085/hotel/operationData/" + reference, RoomBookingData.class);
		
	}

	public static Set<String> bulkBooking(int number, LocalDate arrival, LocalDate departure, String buyerNif,
			String buyerIban) {
		// return Hotel.bulkBooking(number, arrival, departure, buyerNif, buyerIban);
		// TODO: implement in the final version as a rest invocation
		return null;
	}

}
