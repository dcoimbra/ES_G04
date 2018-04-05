package pt.ulisboa.tecnico.softeng.broker.interfaces;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;

public class CarInterface {

	public static String rentVehicle(String plate, String drivingLicense, LocalDate begin, LocalDate end,String Iban, String Nif) {

		return RentACar.rentVehicle(plate, drivingLicense, begin, end, Iban, Nif);
	}

	public static String cancelRenting(String reference) {

		return RentACar.cancelRenting(reference);
	}

	public static RentingData getRentingData(String reference) {
		return RentACar.getRentingData(reference);
	}
}
