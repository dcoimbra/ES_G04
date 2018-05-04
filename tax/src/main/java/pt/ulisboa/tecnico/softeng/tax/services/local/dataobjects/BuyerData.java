package pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects;

import java.util.List;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.softeng.tax.domain.Buyer;

public class BuyerData {
	private String nif;
	private String name;
	private String address;
	private List<InvoiceData> invoices;

	public BuyerData() {
	}

	public BuyerData(Buyer buyer) {
		this.nif = buyer.getNif();
		this.name = buyer.getName();
		this.address = buyer.getAddress();
		this.invoices = buyer.getInvoiceSet().stream().sorted((c1, c2) -> c1.getDate().compareTo(c2.getDate()))
				.map(c -> new InvoiceData(c.getSeller().getNif(),c.getBuyer().getNif(), c.getItemType().getName()
						, c.getValue(), c.getDate())).collect(Collectors.toList());
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<InvoiceData> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<InvoiceData> invoices) {
		this.invoices = invoices;
	}
	
	
	
}
