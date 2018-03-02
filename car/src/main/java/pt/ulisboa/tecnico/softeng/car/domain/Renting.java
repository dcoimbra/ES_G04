package pt.ulisboa.tecnico.softeng.car.domain;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

import org.joda.time.LocalDate;


public class Renting{

	private final String _license;
	private final LocalDate _begin;
	private final LocalDate _end;
	private final int _kilometers;


	public Renting(String license, LocalDate begin, LocalDate end, int kilometers) {
		checkArguments(license, begin, end, kilometers);
		
		this._license = license;
		this._begin = begin;
		this._end = end;
		this._kilometers = kilometers;
		
	}
	
	private void checkArguments(String license, LocalDate begin, LocalDate end, int kilometers) {
		
		if (license == null || license.trim().equals("") || license.trim().equals("    ") || this.checkLicense(license)) {
			throw new CarException();
			
		}
		
		if (begin == null || end == null || end.isBefore(begin)) {
			throw new CarException();
		}
		
		if (kilometers < 0) {
			throw new CarException();
		}
		
	}
	
	private boolean checkLicense(String license) {
		
		int i;
		int ascii;
		int len = license.length();
		boolean letter = true; 
		
		int firstLetter = license.charAt(0);
		
		if (firstLetter < 65 && firstLetter > 90) {
			return true;
		}

		for (i = 1; i < len; i++) {
			
			ascii = license.charAt(i);
			
			if (letter) {
			
				if (ascii < 65 && ascii > 90) {
					letter = false;
					
					i--; 					
				}			
			}
			
			else {
				if (ascii < 48 && ascii > 57) {
					return true;
				}
				
			}	
		}
				
		return false;
	}
	
	public String getReference() {
		return "";
	}
	
	public String getLicense() {
		return _license;
	}
	
	public LocalDate getBegin() {
		return _begin;
	}
	
	public LocalDate getEnd() {
		return _end;
	}
	
	public int getKilometers() {
		return _kilometers;
	}
	
	

}

