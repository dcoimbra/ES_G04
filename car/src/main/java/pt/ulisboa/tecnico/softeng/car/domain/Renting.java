package pt.ulisboa.tecnico.softeng.car.domain;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

import org.joda.time.LocalDate;


public class Renting{
	private static int counter = 0;

	private final String _reference;
	private final String _license;
	private final LocalDate _begin;
	private final LocalDate _end;
	private final int _kilometers;
	
	public Renting(RentACar rentaCar, String license, LocalDate begin, LocalDate end, int kilometers) {
		checkArguments(rentaCar, license, begin, end, kilometers);
		
		this._reference = rentaCar.getCode() + Integer.toString(++Renting.counter);
		this._license = license;
		this._begin = begin;
		this._end = end;
		this._kilometers = kilometers;
		
	}
	
	private void checkArguments(RentACar rentacar, String license, LocalDate begin, LocalDate end, int kilometers) {
		
		if (rentacar == null || license == null || begin == null || end == null) {
			throw new CarException();
		}
				
		if (license.trim().equals("") || license.trim().equals("    ") || this.checkLicense(license)) {
			throw new CarException();
		}
		
		if (end.isBefore(begin)) {
			throw new CarException();
		}
		
		if (kilometers < 0) {
			throw new CarException();
		}
		
	}
	
	private boolean checkLicense(String licens) {
		
		int i;
		int ascii;
		int len = licens.length();
		boolean letter = true; 
		
		int firstLetter = licens.charAt(0);
		int lastLetter = licens.charAt(len - 1);
		
		if (firstLetter < 65 || firstLetter > 90) {
			return true;
		}

		for (i = 1; i < len; i++) {
			
			ascii = licens.charAt(i);
			
			if (letter) {
			
				if (ascii < 65 || ascii > 90) {
					letter = false;
					
					i--; 					
				}			
			}
			
			else {
				if (ascii < 48 || ascii > 57) {
					return true;
				}				
			}	
		}
		
		if (lastLetter >= 65 && lastLetter <= 90) {
			return true;
		}
		
		return false;
	}
	
	public String getReference() {
		return _reference;
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
	
	public int checkout(int kilometers) {
		if (kilometers < 0) 
			throw new CarException();
				
		if (kilometers - _kilometers < 0) 
			throw new CarException();		
		
		return kilometers - _kilometers;
		
	}
	
	public boolean conflict(LocalDate begin, LocalDate end) {
		
		if (end.isBefore(begin))
			throw new CarException();
		
		if ((begin.equals(_begin) || begin.isAfter(_begin)) && begin.isBefore(_end)) 
			return true;
		
		if ((end.equals(_end) || end.isBefore(_end)) && end.isAfter(_begin))
			return true;
		
		if (begin.equals(_end) || end.equals(_begin))
			return true;
		
		if (begin.isBefore(_begin) && end.isAfter(_end))
			return true;
		
		
		return false;
	}
}


