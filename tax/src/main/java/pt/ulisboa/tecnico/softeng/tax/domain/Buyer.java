package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxPayerException;

public class Buyer extends TaxPayer {
	
	public Buyer(String NIF, String NAME, String ADDRESS) {
		
		super(NIF, NAME, ADDRESS);
	}

	public float taxReturn(int YEAR) {
		checkArgumentsTaxReturn(YEAR);

		float amount = 0;
		
		for (Invoice i : Invoice._invoices) {
			
			if (i.getBUYER().getNIF() == this.getNIF()) {
				
				if (i.getDATE().getYear() == YEAR) {
					
					amount += (0.05 * (i.getVALUE() * i.getIVA()));
				}
			}
		}

		return amount;
	}

	private void checkArgumentsTaxReturn(int YEAR) {
		
		if (YEAR < 1970) {
			
			throw new TaxPayerException("Year must be after 1970");
		}
		
	}
}

