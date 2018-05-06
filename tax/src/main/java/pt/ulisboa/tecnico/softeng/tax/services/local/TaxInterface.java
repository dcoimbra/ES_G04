package pt.ulisboa.tecnico.softeng.tax.services.local;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ulisboa.tecnico.softeng.tax.domain.*;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.BuyerData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.ItemTypeData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.SellerData;

public class TaxInterface {

	@Atomic(mode = TxMode.WRITE)
	public static IRS getIrs() {
		return IRS.getIRSInstance();
	}
	
	@Atomic(mode = TxMode.READ)
	public static List<BuyerData> getBuyers() {
		return getIrs().getTaxPayerSet().stream().filter(b -> b instanceof Buyer).map(b -> new BuyerData((Buyer) b))
				.collect(Collectors.toList());
	}
	
	@Atomic(mode = TxMode.READ)
	public static List<SellerData> getSellers() {
		return getIrs().getTaxPayerSet().stream().filter(b -> b instanceof Seller).map(b -> new SellerData((Seller) b))
				.collect(Collectors.toList());
	}
	
	@Atomic(mode = TxMode.WRITE)
	public static void createBuyer(BuyerData buyerData) {
		new Buyer(getIrs(), buyerData.getNif(), buyerData.getName(), buyerData.getAddress());
	}
	
	@Atomic(mode = TxMode.WRITE)
	public static void createSeller(SellerData sellerData) {
		new Seller(getIrs(), sellerData.getNif(), sellerData.getName(), sellerData.getAddress());
	}

	@Atomic(mode = TxMode.READ)
	public static BuyerData getBuyerDataByNif(String nif) {
		TaxPayer tp = getIrs().getTaxPayerByNIF(nif);
		if (tp == null) {
			throw new TaxException();
		}
		else if(tp instanceof Buyer)
			return new BuyerData((Buyer)tp);
		else
			throw new TaxException();
	}

	@Atomic(mode = TxMode.READ)
	public static SellerData getSellerDataByNif(String nif) {
		TaxPayer tp = getIrs().getTaxPayerByNIF(nif);
		if (tp == null) {
			throw new TaxException();
		}
		else if(tp instanceof Seller)
			return new SellerData((Seller)tp);
		else
			throw new TaxException();
	}

	@Atomic(mode = TxMode.WRITE)
	public static void cancelInvoice(String invoiceConfirmation) {
		IRS.cancelInvoice(invoiceConfirmation);
	}

	@Atomic(mode = TxMode.WRITE)
	public static String submitInvoice(InvoiceData invoiceData) {
		return IRS.submitInvoice(invoiceData);
	}

	private static Invoice getInvoiceByReference(String reference) {
		Invoice invoice = getIrs().getInvoiceSet().stream().filter(b -> b.getReference().equals(reference))
				.findFirst().orElse(null);
		return invoice;
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createInvoice(InvoiceData invoiceData) {
		getIrs().submitInvoice(invoiceData);
	}

	@Atomic(mode = TxMode.READ)
    public static List<ItemTypeData> getItemTypes() {
		List<ItemTypeData> itemtypes = new ArrayList<>();
		for (ItemType itemtype : getIrs().getItemTypeSet()) {
			itemtypes.add(new ItemTypeData(itemtype));
		}
		return itemtypes;
    }

	public static void createItemType(ItemTypeData itemtype) {
		new ItemType(getIrs(), itemtype.getName(), itemtype.getTax());
	}
}
