package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;

public class Booking {
	private static int counter = 0;
	private static final String type = "BOOKROOM";

	private final String reference;
	private String paymentReference;
	private String invoiceReference;
	private final String buyerNIF;
	private String cancellation;
	private String cancelledPaymentReference = null;
	private LocalDate cancellationDate;
	private Type roomType;
	private Hotel hotel;
	private final LocalDate arrival;
	private final LocalDate departure;

	Booking(Type roomType, Hotel hotel, LocalDate arrival, LocalDate departure, String buyerNIF) {
		checkArguments(roomType, hotel, arrival, departure, buyerNIF);

		this.roomType = roomType;
		this.hotel = hotel;
		this.reference = hotel.getCode() + Integer.toString(++Booking.counter);
		this.arrival = arrival;
		this.departure = departure;
		this.buyerNIF = buyerNIF;
	}

	private void checkArguments(Type roomType, Hotel hotel, LocalDate arrival, LocalDate departure, String buyerNIF) {
		if (roomType == null || hotel == null || arrival == null || departure == null || buyerNIF == null) {
			throw new HotelException();
		}

		if (departure.isBefore(arrival)) {
			throw new HotelException();
		}
	}

	public double getAmount() {
		if (getRoomType() == Type.DOUBLE)
			return getHotel().getPriceDouble() * (getDeparture().getDayOfYear() - getArrival().getDayOfYear());
		else
			return getHotel().getPriceSingle() * (getDeparture().getDayOfYear() - getArrival().getDayOfYear());
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public String getType() {
		return Booking.type;
	}
	
	public Type getRoomType() {
		return this.roomType;
	}

	public String getReference() {
		return this.reference;
	}

	public String getCancellation() {
		return this.cancellation;
	}

	public LocalDate getArrival() {
		return this.arrival;
	}

	public LocalDate getDeparture() {
		return this.departure;
	}

	public LocalDate getCancellationDate() {
		return this.cancellationDate;
	}

	public String getProviderNif() {
		return this.hotel.getNif();
	}

	public String getBuyerNif() {
		return this.buyerNIF;
	}

	public String getIban() {
		return this.hotel.getIban();
	}

	public String getPaymentReference() {
		return this.paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public String getInvoiceReference() {
		return this.invoiceReference;
	}

	public void setInvoiceReference(String invoiceReference) {
		this.invoiceReference = invoiceReference;
	}

	public String getCancelledPaymentReference() {
		return this.cancelledPaymentReference;
	}

	public void setCancelledPaymentReference(String cancelledPaymentReference) {
		this.cancelledPaymentReference = cancelledPaymentReference;
	}

	boolean conflict(LocalDate arrival, LocalDate departure) {
		if (isCancelled()) {
			return false;
		}

		if (arrival.equals(departure)) {
			return true;
		}

		if (departure.isBefore(arrival)) {
			throw new HotelException();
		}

		if ((arrival.equals(this.arrival) || arrival.isAfter(this.arrival)) && arrival.isBefore(this.departure)) {
			return true;
		}

		if ((departure.equals(this.departure) || departure.isBefore(this.departure))
				&& departure.isAfter(this.arrival)) {
			return true;
		}

		if ((arrival.isBefore(this.arrival) && departure.isAfter(this.departure))) {
			return true;
		}

		return false;
	}

	public String cancel() {
		this.cancellation = this.reference + "CANCEL";
		this.cancellationDate = new LocalDate();
		return this.cancellation;
	}

	public boolean isCancelled() {
		return this.cancellation != null;
	}

}
