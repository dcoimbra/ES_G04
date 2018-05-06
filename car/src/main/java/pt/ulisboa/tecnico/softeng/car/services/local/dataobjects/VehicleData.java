package pt.ulisboa.tecnico.softeng.car.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleData {

	private String rentACarCode;
	private String rentACarName;
	private String plate;
	private int kilometers;
	private double price;

	private List<RentingData> rentings;

	public VehicleData() {

	}

	public VehicleData(Vehicle v) {
		this.rentACarCode = v.getRentACar().getCode();
		this.rentACarName = v.getRentACar().getName();
		this.plate = v.getPlate();
		this.kilometers = v.getKilometers();
		this.price = v.getPrice();
		this.rentings = v.getRentingSet().stream().sorted((b1, b2) -> b1.getBegin().compareTo(b2.getEnd()))
				.map(b -> new RentingData(b)).collect(Collectors.toList());
	}

	public String getRentACarCode() {
		return rentACarCode;
	}

	public void setRentACarCode(String rentACarCode) {
		this.rentACarCode = rentACarCode;
	}

	public String getRentACarName() {
		return rentACarName;
	}

	public void setRentACarName(String rentACarName) {
		this.rentACarName = rentACarName;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getPlate() {

		return this.plate;
	}

	public void setKilometers(int kilometers) {
		this.kilometers = kilometers;
	}

	public int getKilometers() {

		return this.kilometers;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice() {

		return this.price;
	}

	public List<RentingData> getRentings() {
		return rentings;
	}

	public void setRentings(List<RentingData> rentings) {
		this.rentings = rentings;
	}
}
