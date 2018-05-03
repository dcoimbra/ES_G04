package pt.ulisboa.tecnico.softeng.car.services.local.dataobjects;

import java.util.List;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.softeng.car.domain.RentACar;

public class RentACarData {
	private String code;
	private String name;
	private String nif;
	private String iban;
	
	private List<VehicleData> vehicles;
	
	
	public RentACarData() {
	}
	
	
	public RentACarData(RentACar rentacar) {
		this.code = rentacar.getCode();
		this.name = rentacar.getName();
		this.nif = rentacar.getNif();
		this.iban = rentacar.getIban();

		this.vehicles = rentacar.getVehicleSet().stream().sorted((v1, v2) -> v1.getPlate().compareTo(v2.getPlate()))
				.map(v -> new VehicleData(v)).collect(Collectors.toList());
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getNif() {
		return nif;
	}


	public void setNif(String nif) {
		this.nif = nif;
	}


	public String getIban() {
		return iban;
	}


	public void setIban(String iban) {
		this.iban = iban;
	}


	public List<VehicleData> getVehicles() {
		return vehicles;
	}


	public void setVehicles(List<VehicleData> vehicles) {
		this.vehicles = vehicles;
	}

	
	

	
	
	
}



