package pt.ulisboa.tecnico.softeng.car.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;

import java.util.List;

public class VehicleData {

	private String plate;
	private int kilometers;
	private double price;

	private List<RentingData> rentings;

	public VehicleData() {

	}

	public VehicleData(Vehicle v) {
		this.plate = v.getPlate();
		this.kilometers = v.getKilometers();
		this.price = v.getPrice();
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
