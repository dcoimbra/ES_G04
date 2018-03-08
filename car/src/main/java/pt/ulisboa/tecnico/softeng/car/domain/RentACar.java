package pt.ulisboa.tecnico.softeng.car.domain;
import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;


public class RentACar {
	private static int counter = 0;
	public static final Set<RentACar> rents = new HashSet<>();
	private final List<Vehicle> _vehicles = new ArrayList<>();
	private final String name;
	private final String code;

	public RentACar(String name) {
		checkArguments(name);
		this.name = name;
		this.code = Integer.toString(++RentACar.counter);
		RentACar.rents.add(this);
	}
	
	private void checkArguments(String name) {
		if (name == null || name.trim().equals("") ) {
			throw new CarException();
		}
	}
	public Renting getRenting(String reference){
		if(reference == null || reference.trim().equals("")){
			throw new CarException();
		}
		for(Vehicle v : this._vehicles){
			for(Renting r : v.getRentings())
				if(r.getReference().equals(reference))
					return r;
		}
		throw new CarException();

	}

	public List<Vehicle> getAllAvailableCars(LocalDate begin, LocalDate end){
		if(  begin == null || end == null || end.isBefore(begin) )
			throw new CarException();

		List<Vehicle> l = new ArrayList<>();
		for(Vehicle v: this._vehicles)
			if (v instanceof Car)
				if (v.isFree(begin, end) )
					l.add(v);
		return l;
	}

	public List<Vehicle> getAllAvailableMotorCycles(LocalDate begin, LocalDate end){
		if(  begin == null || end == null || end.isBefore(begin) )
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
		if (reference == null || reference.trim().equals("") ) {
			throw new CarException();
		}
		for (RentACar rac : rents) {
			for(Vehicle v : rac.getAllVehicles())
				for(Renting r : v.getRentings())
					if(r.getReference().equals(reference))
						return new RentingData(reference, v.getPlate(), r.getLicense(), rac.getCode(), r.getBegin(), r.getEnd());						
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
		if (plate == null || plate.trim().equals("") ) {
			throw new CarException();
		}
		for (RentACar rent : RentACar.rents) 
			for(Vehicle v : rent.getAllVehicles())
				if(v.getPlate().equals(plate))
					throw new CarException();
		return true;
	}
}