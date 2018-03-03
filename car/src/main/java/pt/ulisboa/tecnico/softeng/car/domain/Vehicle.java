package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

abstract class Vehicle {
	private static final int hifenASCII = 45;
	private static final int A_ASCII = 45;
	private static final int Z_ASCII = 45;
	private static final int ZERO_ASCII = 45;
	private static final int NINE_ASCII = 45;
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

	private void checkArguments(String plate, int km, RentACar rentACar) {
		if (plate == null || km < 0) {
			throw new CarException();
		}
		
		if (plate.length() != PLATE_LEN) {
			throw new CarException();
		}
		else {
			int i = 0;
			for(; i < PLATE_LEN; i++) {  // 01234567 index
				if (i == 2 || i == 5) {  // XX-XX-XX
					if ((int) plate.charAt(i) != hifenASCII) {
						throw new CarException();
					}
				}
				else {
					int asciiCode = plate.charAt(i);
					if (! ( (asciiCode >= A_ASCII && asciiCode <= Z_ASCII) || (asciiCode >= ZERO_ASCII && asciiCode <= NINE_ASCII) ))
						throw new CarException();
				}
			}
		}

		if(!rentACar.plateIsFree(plate))
			throw new CarException();
	}

	public boolean isFree(LocalDate begin, LocalDate end) {
		if (end.isBefore(begin))
			throw new CarException();
		
		for(Renting r : _rentings) {
			if (!( end.isBefore((r.getBegin())) || ((r.getEnd()).isBefore(begin)) )) {
				throw new CarException();
			}
		}
	}

	public void rent(String drivingLicence, LocalDate begin, LocalDate end) {
		if (end.isBefore(begin))
			throw new CarException();
		
		for (Vehicle v: _rentACar.getAllVehicles()) {
			if (v.isFree(begin, end)) {
				_rentings.add(new Renting("1234", drivingLicence, begin, end, _kilometers));
			}
		}
	}
}