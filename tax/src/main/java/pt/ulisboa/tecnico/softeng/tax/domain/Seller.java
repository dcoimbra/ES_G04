package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxPayerException;
import pt.ulisboa.tecnico.softeng.tax.exception.InvoiceException;


public class Seller extends TaxPayer {
	
	public Seller(String NIF, String NAME, String ADDRESS) {
		
		super(NIF, NAME, ADDRESS);
	}

	public float toPay(int YEAR) {
		checkArgumentstoPay(YEAR);

		float amount = 0;
		
		for (Invoice i : Invoice._invoices) {
			if (i.getSELLER().getNIF() == this.getNIF()) {
				if (i.getDATE().getYear() == YEAR) {
					amount += i.getVALUE()*i.getIVA();
				}
			}
		}

		return amount;
	}

	private void checkArgumentstoPay(int YEAR) {
		if (YEAR < 1970) {
			throw new TaxPayerException("Year must be before 1970");
		}
		
	}
	
	public Invoice getInvoiceByReference(String INVOICE_REFERENCE) {
		for (Invoice i : Invoice._invoices) {
			if (i.getREFERENCE() == INVOICE_REFERENCE) {
				return i;
			}
		}
		throw new InvoiceException("There is no Invoice with the given Reference");
	}
	
	
}
