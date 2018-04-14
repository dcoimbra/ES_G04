package pt.ulisboa.tecnico.softeng.car.domain;

public class Motorcycle extends Vehicle {
	public Motorcycle(String plate, int kilometers, double price, RentACar rentACar) {
		super();
		init(plate, kilometers, price, rentACar);
	}
	
	protected void init(String plate, int kilometers, double price, RentACar rentACar) {
	     super.init(plate, kilometers, price, rentACar);
	   }

}
