package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxPayerException;

public abstract class TaxPayer {
	
	public static Set<TaxPayer> _taxpayers = new HashSet<>();
	
	private String _nif;
	private String _name;
	private String _address;
	
	public TaxPayer(String NIF, String NAME, String ADDRESS) {
		checkArguments(NIF, NAME, ADDRESS);
		_nif = NIF;
		_name = NAME;
		_address = ADDRESS;
		_taxpayers.add(this);
	}
	
	private void checkArguments(String NIF, String NAME, String ADDRESS) {
		if(NIF.length()!= 9) {
			throw new TaxPayerException();
		}
		
		if(NAME=="" || NAME==null || ADDRESS=="" || ADDRESS==null) {
			throw new TaxPayerException();
		}
		
		for (TaxPayer tp : _taxpayers) {
			if (tp.getNIF().equals(NIF)) {
				throw new TaxPayerException();
			}
		}
	}
	
	public String getNIF() {
		return this._nif;
	}

	public String getNAME() {
		return this._name;
	}

	public String getADDRESS() {
		return this._address;
	}
}

