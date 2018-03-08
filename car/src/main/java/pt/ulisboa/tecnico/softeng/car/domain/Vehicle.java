package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public abstract class Vehicle {
	private static final int hifenASCII = 45;
	private static final int A_ASCII = 65;
	private static final int Z_ASCII = 90;
	private static final int ZERO_ASCII = 48;
	private static final int NINE_ASCII = 57;
	private static final int PLATE_LEN = 8;

	private String _plate;
	private int _kilometers;
	private RentACar _rentACar;
	
	private final Set<Renting> _rentings = new HashSet<>();
	
	public Vehicle(String plate, int km, RentACar rentACar) {
		checkArguments(plate, km, rentACar);
		_plate = plate;
		_kilometers = km;
		_rentACar = rentACar;
		_rentACar.addVehicle(this);
	}

	public String getPlate() {
		return _plate;
	}
	
	public int getKilometers() {
		return _kilometers;
	}
	
	public Set<Renting> getRentings() {
		return _rentings;
	}
	
	public RentACar getRentACar() {
		return _rentACar;
	}

	private void checkArguments(String plate, int km, RentACar rentACar) {
		if (plate == null || km < 0 || rentACar == null) {
			throw new CarException();
		}
		
		if (plate.length() != PLATE_LEN || plate.trim().equals(""))
			throw new CarException();
		
		else {
			int i = 0;
			int asciiCode;
			for(; i < PLATE_LEN; i++) {
				asciiCode = (int) plate.charAt(i);
				if (i == 2 || i == 5) {
					if (asciiCode != hifenASCII) {
						throw new CarException();
					}
				}
				else {
					if ( !( (asciiCode >= A_ASCII && asciiCode <= Z_ASCII) || (asciiCode >= ZERO_ASCII && asciiCode <= NINE_ASCII) ) )
						throw new CarException();
				}
			}
		}
		
		if(!rentACar.plateIsFree(plate))
			throw new CarException();
	}

	public boolean isFree(LocalDate begin, LocalDate end) {
		if (begin == null || end == null)
			throw new CarException();
		
		if (end.isBefore(begin))
			throw new CarException();
		
		for(Renting r : _rentings) {			
			if (!( end.isBefore((r.getBegin())) || ((r.getEnd()).isBefore(begin)) )) {
				return false;
			}
		}
		return true;
	}

	public void rent(String drivingLicence, LocalDate begin, LocalDate end) {
		if (begin == null || end == null)
			throw new CarException();
		
		if (end.isBefore(begin))
			throw new CarException();
		
		if (this.isFree(begin, end)) {
			_rentings.add(new Renting(_rentACar, drivingLicence, begin, end, _kilometers));
		}
		else
			throw new CarException();
	}
}