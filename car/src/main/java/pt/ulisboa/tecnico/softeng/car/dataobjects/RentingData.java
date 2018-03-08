package pt.ulisboa.tecnico.softeng.car.dataobjects;

import org.joda.time.LocalDate;


public class RentingData{

	private String _reference;
	private String _plate;
	private String _drivingLicense;
	private String _rentACarCode;
	private LocalDate _begin;
	private LocalDate _end;

	public RentingData(String reference, String plate, String drivingLicense, String rentACarCode, LocalDate begin, LocalDate end) {
		
		this._reference = reference;
		this._plate = plate;
		this._drivingLicense = drivingLicense;
		this._rentACarCode = rentACarCode;
		this._begin = begin;
		this._end = end;

	}
	
	public String getReference() {
		return _reference;
	}
	
	public void setReference(String reference) {
		_reference = reference;
	}
	
	public String getPlate() {
		return _plate;
	}
	
	public void setPlate(String plate) {
		_plate = plate;
	}
	
	public String getLicense() {
		return _drivingLicense;
	}
	
	public void setLicense(String license) {
		_drivingLicense = license;
	}
	
	public String getCode() {
		return _rentACarCode;
	}
	
	public void setCode(String code) {
		_rentACarCode = code;
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
	
	
	
	
}


