package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;

public class VehicleAndPlate extends VehicleAndPlate_Base {
    
    public VehicleAndPlate(RentACar rentACar, String plate, Vehicle vehicle) {
    	setRentACar(rentACar);
    	setPlate(plate);
    	setVehicle(vehicle);
    }
    
    public void delete() {
		setRentACar(null);
		deleteDomainObject();
	}
    
}
