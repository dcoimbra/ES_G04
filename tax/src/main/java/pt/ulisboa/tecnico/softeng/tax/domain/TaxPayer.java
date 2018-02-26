package pt.ulisboa.tecnico.softeng.tax.domain;


public class TaxPayer {
	private String _nif;
	private String _name;
	private String _address;
	
	public TaxPayer(String NIF, String NAME, String ADDRESS) {
		_nif = NIF;
		_name = NAME;
		_address = ADDRESS;
	}
}