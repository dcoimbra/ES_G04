package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.IRSException;

public class IRSAddTaxPayerMethodTest {

	@Test
	public void testAddTaxPayerMethod(){
		TaxPayer tp = new Buyer("244291985","Nuno","Vendas Novas");
		IRS.getIRS().addTaxPayer(tp);
	}
}
