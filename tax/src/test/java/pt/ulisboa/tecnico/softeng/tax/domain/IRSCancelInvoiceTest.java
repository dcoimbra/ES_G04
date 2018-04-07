package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRSCancelInvoiceTest {
	private static final String FOOD = "FOOD";
	private static final int VALUE = 16;
	private static final String SELLER_NIF = "123456789";
	private static final String BUYER_NIF = "987654321";
	private static final int TAX = 23;
	private final LocalDate date = new LocalDate(2018, 02, 13);

	private Seller seller;
	private Buyer buyer;
	private ItemType itemType;
	private Invoice invoice;

	private IRS irs;

	@Before
	public void setUp() {
		this.irs = IRS.getIRS();
		new Seller(this.irs, SELLER_NIF, "José Vendido", "Somewhere");
		new Buyer(this.irs, BUYER_NIF, "Manuel Comprado", "Anywhere");
		new ItemType(this.irs, FOOD, VALUE);
	}

	@Test
	public void success() {
		InvoiceData invoiceData = new InvoiceData(SELLER_NIF, BUYER_NIF, FOOD, VALUE, this.date);
		String invoiceReference = this.irs.submitInvoice(invoiceData);

		Invoice invoice = this.irs.getTaxPayerByNIF(SELLER_NIF).getInvoiceByReference(invoiceReference);

		assertEquals(invoiceReference, invoice.getReference());
		assertEquals(SELLER_NIF, invoice.getSeller().getNIF());
		assertEquals(BUYER_NIF, invoice.getBuyer().getNIF());
		assertEquals(FOOD, invoice.getItemType().getName());
		assertEquals(VALUE, invoice.getValue(), 0.0000);
		assertEquals(this.date, invoice.getDate());
		assertFalse(invoice.getCancelledInvoice());
		
		this.irs.cancelInvoice(invoiceReference);
		
		assertTrue(invoice.getCancelledInvoice());
		
	}

	@Test(expected = TaxException.class)
	public void nullReference() {
		this.irs.cancelInvoice(null);
	}

	@Test(expected = TaxException.class)
	public void emptyReference() {
		this.irs.cancelInvoice("");
	}

	@Test(expected = TaxException.class)
	public void doesNotExistReference() {
		this.irs.cancelInvoice(BUYER_NIF);
	}

	@After
	public void tearDown() {
		this.irs.clearAll();
	}
}
