package pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects;

import java.util.List;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room;

public class RoomData {
	private String hotelCode;
	private String hotelName;
	private String number;
	private String nif;
	private String iban;
	private Room.Type type;
	private List<RoomBookingData> bookings;

	public RoomData() {
	}

	public RoomData(Room room) {
		this.hotelCode = room.getHotel().getCode();
		this.hotelName = room.getHotel().getName();
		this.number = room.getNumber();
		this.nif = room.getHotel().getNif();
		this.iban = room.getHotel().getIban();
		this.type = room.getType();

		this.bookings = room.getBookingSet().stream().sorted((b1, b2) -> b1.getArrival().compareTo(b2.getArrival()))
				.map(b -> new RoomBookingData(b)).collect(Collectors.toList());
	}

	public String getHotelCode() {
		return this.hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getHotelName() {
		return this.hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public Room.Type getType() {
		return this.type;
	}

	public void setType(Room.Type type) {
		this.type = type;
	}

	public List<RoomBookingData> getBookings() {
		return this.bookings;
	}

	public void setBookings(List<RoomBookingData> bookings) {
		this.bookings = bookings;
	}

}
