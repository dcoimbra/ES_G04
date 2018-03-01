package pt.ulisboa.tecnico.softeng.car.domain;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;


public class RentACar {
	public static final Set<RentACar> rents = new HashSet<>();
	private final List<Vehicle> _vehicles = new ArrayList<>();
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
	public Renting getRenting(String reference){
		for(Vehicle v : this._vehicles){
			for(Renting r : v._rentings)
				if(r.getReference().equals(reference))
					return r;
		}
		throw new CarException();

	}
	public List<Vehicle> getAllAvailableCars(LocalDate begin, LocalDate end){
		if( end.isBefore(begin) )
			throw new CarException();

		List<Vehicle> l = new ArrayList<>();
		for(Vehicle v: this._vehicles){
			if (v instanceof Car){	
				for(Renting r : v._rentings){
					if (v.isFree(begin, end) )
						l.add(v);
				}
			}
		}
		return l;
	}
	public List<Vehicle> getAllVehicles(){
		return this._vehicles;
	}
}
