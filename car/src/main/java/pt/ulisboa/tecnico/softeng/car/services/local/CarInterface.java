package pt.ulisboa.tecnico.softeng.car.services.local;

import java.util.List;
import java.util.stream.Collectors;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.Motorcycle;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentACarData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.VehicleData;

public class CarInterface {

	@Atomic(mode = TxMode.READ)
	public static List<RentACarData> getRentACars() {
		return FenixFramework.getDomainRoot().getRentACarSet().stream().map(r -> new RentACarData(r))
				.collect(Collectors.toList());
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createRentACar(RentACarData rentacarData) {
		new RentACar(rentacarData.getName(), rentacarData.getNif(), rentacarData.getIban());
	}

	@Atomic(mode = TxMode.READ)
	public static RentACarData getRentACarDataByCode(String code) {
		RentACar rentacar = getRentACarByCode(code);

		if (rentacar != null) {
			return new RentACarData(rentacar);
		}

		return null;
	}
	
	
	@Atomic(mode = TxMode.WRITE)
	public static void createCar(String rentacarCode, VehicleData vehicleData) {
		new Car(vehicleData.getPlate(), vehicleData.getKilometers(), vehicleData.getPrice(), getRentACarByCode(rentacarCode));
				
	}
	
	
	@Atomic(mode = TxMode.WRITE)
	public static void createMotorcycle(String rentacarCode, VehicleData vehicleData) {
		new Motorcycle(vehicleData.getPlate(), vehicleData.getKilometers(), vehicleData.getPrice(), getRentACarByCode(rentacarCode));
				
	}
	
	@Atomic(mode = TxMode.READ)
	public static VehicleData getVehicleDataByPlate(String code, String plate) {
		Vehicle vehicle = getVehicleByPlate(code, plate);
		if (vehicle == null) {
			return null;
		}

		return new VehicleData(vehicle);
	}


	
	private static Vehicle getVehicleByPlate(String code, String plate) {
		RentACar rentacar = getRentACarByCode(code);
		if (rentacar == null) {
			return null;
		}

		Vehicle vehicle = rentacar.getVehicleByPlate(plate);
		if (vehicle == null) {
			return null;
		}
		return vehicle;
	}

	
	private static RentACar getRentACarByCode(String code) {
		return FenixFramework.getDomainRoot().getRentACarSet().stream().filter(r -> r.getCode().equals(code)).findFirst()
				.orElse(null);
	}


		
}
