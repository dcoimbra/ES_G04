package pt.ulisboa.tecnico.softeng.car.domain;

public class Plate extends Plate_Base {
    
    public Plate(Vehicle vehicle, String plate) {
    	setVehicle(vehicle);
    	setPlate(plate);
    }
    
	public void delete() {
		setVehicle(null);

		deleteDomainObject();
	}
    
}
