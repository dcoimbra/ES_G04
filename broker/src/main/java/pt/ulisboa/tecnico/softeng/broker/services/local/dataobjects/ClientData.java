package pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import pt.ulisboa.tecnico.softeng.broker.domain.Client;

public class ClientData {
	private String iban;
	private String nif;
	private String drivingLicense;
	private int age;
	private String brokerName;
	private String brokerCode;
	private List<AdventureData> adventures = new ArrayList<>();

	public ClientData() {
	}

	public ClientData(Client client) {
		this.iban = client.getIban();
		this.nif = client.getNif();
		this.drivingLicense = client.getDrivingLicense();
		this.age = client.getAge();
		this.brokerName = client.getBroker().getName();
		this.brokerCode = client.getBroker().getCode();
		this.adventures = client.getAdventureSet().stream().sorted((c1, c2) -> c1.getID().compareTo(c2.getID()))
				.map(c -> new AdventureData(c)).collect(Collectors.toList());
	}
	
	public List<AdventureData> getAdventures() {
		return this.adventures;
	}

	public void setAdventures(List<AdventureData> adventures) {
		this.adventures = adventures;
	}

	public String getBrokerName() {
		return this.brokerName;
	}
	
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}
	
	public String getBrokerCode() {
		return this.brokerName;
	}
	
	public void setBrokerCode(String brokerCode) {
		this.brokerCode = brokerCode;
	}
	
	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getDrivingLicense() {
		return this.drivingLicense;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
