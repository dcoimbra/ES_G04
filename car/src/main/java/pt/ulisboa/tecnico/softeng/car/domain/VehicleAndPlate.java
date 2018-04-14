package pt.ulisboa.tecnico.softeng.car.domain;

public class VehicleAndPlate extends VehicleAndPlate_Base {
    
    public VehicleAndPlate(RentACar rentACar) {
    	setRentACar(rentACar);
    }
    
    public void delete() {
		setRentACar(null);
		deleteDomainObject();
	}
}
