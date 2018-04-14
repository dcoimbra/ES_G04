package pt.ulisboa.tecnico.softeng.car.domain;

public class Car extends Car_Base {

	public Car(){}

	public Car(String plate, int kilometers, double price, RentACar rentACar){
		super();
		init(plate, kilometers, price, rentACar);
	}

	protected void init(String plate, int kilometers, double price, RentACar rentACar){
		super.init(plate,kilometers,price,rentACar);
	}
}
