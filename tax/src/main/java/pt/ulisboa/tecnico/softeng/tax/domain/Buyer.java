package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;


public abstract class Buyer {
	
	public static Set<Buyer> Buyers = new HashSet<>();
	
	private String _nif;
	private String _name;
	private String _address;
	
	public Buyer(String NIF, String NAME, String ADDRESS) {
		checkArguments(NIF, NAME, ADDRESS);
		_nif = NIF;
		_name = NAME;
		_address = ADDRESS;
		Buyers.add(this);
	}
	
	private void checkArguments(String NIF, String NAME, String ADDRESS) {
		if(NIF.length()!= 9) {
			throw new BuyerException();
		}
		
		if(NAME=="" || NAME==null || ADDRESS=="" || ADDRESS==null) {
			throw new BuyerException();
		}
		
		for (Buyer tp : Buyers) {
			if (tp.getNIF().equals(NIF)) {
				throw new BuyerException();
			}
		}
	}
	
	public String getNIF() {
		return this._nif;
	}
}

