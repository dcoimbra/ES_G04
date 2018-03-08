package pt.ulisboa.tecnico.softeng.car.dataobjects;

import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;

import org.joda.time.LocalDate;


public class RentingData{

	private String _reference;
	private String _license;
	private LocalDate _begin;
	private LocalDate _end;
	private int _kilometersRenting;
	private String _plate;
	private int _kilometersVehicle;
	private String _name;
	private String _code;
	
	
	public RentingData() {
	}
	
	public RentingData(Vehicle vehicle, Renting renting) {
		
		this._reference = renting.getReference();
		this._license = renting.getLicense();
		this._begin = renting.getBegin();
		this._end = renting.getEnd();
		this._kilometersRenting = renting.getKilometers();
		this._plate = vehicle.getPlate();
		this._kilometersVehicle = vehicle.getKilometers();
		this._name = vehicle.getRentACar().getName();
		this._code = vehicle.getRentACar().getCode();
		
	}
	
	public String getReference() {
		return _reference;
	}
	
	public void setReference(String reference) {
		_reference = reference;
	}
	
	public String getLicense() {
		return _license;
	}
	
	public void setLicense(String license) {
		_license = license;
	}
	
	public LocalDate getBegin() {
		return _begin;
	}
	
	public void setBegin(LocalDate begin) {
		_begin = begin; 
	}
	
	public LocalDate getEnd() {
		return _end;
	}
	
	public void setEnd(LocalDate end) {
		_end = end; 
	}
	
	public int getKilometersRenting() {
		return _kilometersRenting;
	}
	
	public void setKilometersRenting(int kilometers) {
		_kilometersRenting = kilometers; 
	}
	
	public String getPlate() {
		return _plate;
	}
	
	public void setPlate(String plate) {
		_plate = plate;
	}
	
	public int getKilometersVehicle() {
		return _kilometersVehicle;
	}
	
	public void setKilometersVehicle(int kilometers) {
		_kilometersVehicle = kilometers;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public String getCode() {
		return _code;
	}
	
	public void setCode(String code) {
		_code = code;
	}
	
	
	
	
}


