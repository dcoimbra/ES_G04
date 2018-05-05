package pt.ulisboa.tecnico.softeng.broker.services.remote.dataobjects;

import org.joda.time.LocalDate;
import pt.ulisboa.tecnico.softeng.broker.services.remote.CarInterface;

public class RentingData {
	private CarInterface.Type vehicleType;
	private String nif;
	private String iban;
	private String reference;
	private String plate;
	private String drivingLicense;
	private String rentACarCode;
	private double price;
	private LocalDate begin;
	private LocalDate end;
	private String paymentReference;
	private String invoiceReference;

	/**
	 * @return the renting reference
	 */
	public String getReference() {
		return this.reference;
	}

	/**
	 * @return the vehicle plate
	 */
	public String getPlate() {
		return this.plate;
	}

	/**
	 * @return the drivingLicense
	 */
	public String getDrivingLicense() {
		return this.drivingLicense;
	}

	/**
	 * @return the rentACarCode
	 */
	public String getRentACarCode() {
		return this.rentACarCode;
	}

	/**
	 * @return the begin
	 */
	public LocalDate getBegin() {
		return this.begin;
	}

	/**
	 * @return the end
	 */
	public LocalDate getEnd() {
		return this.end;
	}

	public String getPaymentReference() {
		return this.paymentReference;
	}

	public String getInvoiceReference() {
		return this.invoiceReference;
	}

	public double getPrice() {
		return this.price;
	}

	public void setBegin(LocalDate begin) {
		this.begin = begin;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public void setInvoiceReference(String invoiceReference) {
		this.invoiceReference = invoiceReference;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public void setRentACarCode(String rentACarCode) {
		this.rentACarCode = rentACarCode;
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

	public CarInterface.Type getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(CarInterface.Type vehicleType) {
		this.vehicleType = vehicleType;
	}

}
