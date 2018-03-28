package pt.ulisboa.tecnico.softeng.broker.interfaces;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;

public class CarInterface {
	public static String rentVehicle(String plate, String drivingLicense, LocalDate begin, LocalDate end) {
		//TODO: call implemented rentVehicle method
		return "RENTING";
	}

	public static String cancelRenting(String reference) {
		//TODO: call implemented cancelRentingMethod
		return "CANCELED";
	}

	public static RentingData getRentingData(String reference) {
		return RentACar.getRentingData(reference);
	}
}
