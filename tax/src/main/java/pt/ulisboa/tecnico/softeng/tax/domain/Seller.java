package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxPayerException;


public class Seller extends TaxPayer {
	
	public Seller(String NIF, String NAME, String ADDRESS) {
		super(NIF, NAME, ADDRESS);
	}

	public float toPay(int YEAR){
		checkArgumentstoPay(YEAR);

		float contador = 0;
		
		for (Invoice i : Invoice._invoices){
			if(i.getSELLER().getNIF() == this.getNIF())
				if (i.getDATE().getYear() == YEAR){
					contador += i.getVALUE()*i.getIVA();
				}
			}
		}

		return contador;
	}

	private void checkArgumentstoPay(int YEAR) {
		if(YEAR < 1970) {
			throw new TaxPayerException();
		}
		
	}
}
