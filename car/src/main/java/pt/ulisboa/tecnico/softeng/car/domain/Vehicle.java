package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

abstract class Vehicle {
	private String _plate;
	private int _kilometers;
	private RentACar _rentACar;
	
	private final Set<Renting> _rentings = new HashSet<>();
	
	public Vehicle(String plate, int km, RentACar rentACar) {
		checkArguments(plate, km, rentACar);
		_plate = plate;
		_kilometers = km;
		_rentACar = rentACar;
	}

	public String getPlate() {
		return _plate;
	}
	
	public int getKilometers() {
		return _kilometers;
	}
}