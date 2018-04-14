package pt.ulisboa.tecnico.softeng.car.domain;

public class VehicleAndPlate extends VehicleAndPlate_Base {
    
    public VehicleAndPlate(Vehicle v, RentACar rentACar) {
        super();
        init(v);
        setVehicle(v);
        this.setRentACar(rentACar);
    }
    
    private void init(Vehicle v) {
    	setPlate(v.getPlate());
    }
}
