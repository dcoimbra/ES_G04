package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACar {
	public static Set<RentACar> rents = new HashSet<>();
	private final String name;
	private final String code;

	public RentACar(String name, String code) {
		checkArguments(name, code);
		this.name = name;
		this.code = code;
		RentACar.rents.add(this);
	}
	
	private void checkArguments(String name, String code) {
		if (name == null || name.trim().equals("") || code == null || code.trim().equals("")) {
			throw new CarException();
		}
	}
}
