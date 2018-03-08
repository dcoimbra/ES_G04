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
			for(Renting r : v.getRentings())
				if(r.getReference().equals(reference))
					return r;
		}
		throw new CarException();

	}

	public static Renting getRentingByReference(String reference) {
		for (RentACar rent : RentACar.rents) {
			Renting r = rent.getRenting(reference);
			if (r != null) {
				return r;
			}
		}
		return null;
	}

	public List<Vehicle> getAllAvailableCars(LocalDate begin, LocalDate end){
		if( end.isBefore(begin) || begin == null || end == null )
			throw new CarException();

		List<Vehicle> l = new ArrayList<>();
		for(Vehicle v: this._vehicles)
			if (v instanceof Car)
				if (v.isFree(begin, end) )
					l.add(v);
		return l;
	}

	public List<Vehicle> getAllAvailableMotorCycles(LocalDate begin, LocalDate end){
		if( end.isBefore(begin) || begin == null || end == null )
			throw new CarException();

		List<Vehicle> l = new ArrayList<>();
		for(Vehicle v: this._vehicles)
			if (v instanceof Motorcycle)	
				if (v.isFree(begin, end) )
					l.add(v);
		
		return l;
	}

	public List<Vehicle> getAllVehicles(){
		return this._vehicles;
	}

	public void addVehicle(Vehicle v){
		this._vehicles.add(v);
	}

	public static RentingData getRentingData(String reference) {
		Renting r = getRentingByReference(reference);
		if (r != null) {
			return new RentingData(r);
		}
		throw new CarException();
	}

	public String getCode(){
		return this.code;
	}

	public String getName(){
		return this.name;
	}

	public Set<RentACar> getRents(){
		return RentACar.rents;
	}
	public boolean plateIsFree(String plate){
		for (RentACar rent : RentACar.rents) 
			for(Vehicle v : rent.getAllVehicles())
				if(v.getPlate().equals(plate))
					throw new CarException();
		return true;
	}
}