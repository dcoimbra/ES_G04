package pt.ulisboa.tecnico.softeng.tax.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.SellerData;
import pt.ulisboa.tecnico.softeng.tax.services.local.TaxInterface;

@Controller
@RequestMapping(value = "/taxpayers/seller/{nif}/invoices")
public class SellerInvoicesController {
	private static Logger logger = LoggerFactory.getLogger(SellerInvoicesController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String invoiceForm(Model model, @PathVariable String nif) {
		logger.info("sellerInvoiceForm nif:{}", nif);

		SellerData sellerData = TaxInterface.getSellerDataByNif(nif);

		if (sellerData == null) {
			model.addAttribute("error", "Error: it does not exist a seller with the nif " + nif);
			model.addAttribute("seller", new SellerData());
			model.addAttribute("sellers", TaxInterface.getSellers());
			return "sellers";
		}

		model.addAttribute("invoice", new InvoiceData());
		model.addAttribute("seller", sellerData);
		return "sellerInvoices";
	}
}
