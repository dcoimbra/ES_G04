package pt.ulisboa.tecnico.softeng.tax.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.BuyerData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.SellerData;
import pt.ulisboa.tecnico.softeng.tax.services.local.TaxInterface;

@Controller
@RequestMapping(value = "/taxpayers")
public class TaxPayerController {
	private static Logger logger = LoggerFactory.getLogger(TaxPayerController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String taxPayerForm(Model model) {
		logger.info("taxPayerForm");
		model.addAttribute("buyer", new BuyerData());
		model.addAttribute("seller", new SellerData());
		model.addAttribute("buyers", TaxInterface.getBuyers());
		model.addAttribute("sellers", TaxInterface.getSellers());
		return "taxpayers";
	}

	@RequestMapping(value = "/buyer" ,method = RequestMethod.POST)
	public String buyerSubmit(Model model, @ModelAttribute BuyerData buyer) {
		logger.info("taxPayerSubmit nif:{}, name:{}, address:{}", buyer.getNif(), buyer.getName(), buyer.getAddress());
		try {
			TaxInterface.createBuyer(buyer);
		} catch (TaxException te) {
			model.addAttribute("error", "Error: it was not possible to create the buyer");
			model.addAttribute("buyer", buyer);
			model.addAttribute("seller", new SellerData());
			model.addAttribute("buyers", TaxInterface.getBuyers());
			model.addAttribute("sellers", TaxInterface.getSellers());
			return "taxpayers";
		}
		
		return "redirect:/taxpayers";

	}

	@RequestMapping(value = "/seller", method = RequestMethod.POST)
	public String sellerSubmit(Model model, @ModelAttribute SellerData seller) {
		logger.info("taxPayerSubmit nif:{}, name:{}, address:{}", seller.getNif(), seller.getName(), seller.getAddress());
		try {
			TaxInterface.createSeller(seller);
		} catch (TaxException te) {
			model.addAttribute("error2", "Error: it was not possible to create the seller");
			model.addAttribute("buyer", new BuyerData());
			model.addAttribute("seller", seller);
			model.addAttribute("buyers", TaxInterface.getBuyers());
			model.addAttribute("sellers", TaxInterface.getSellers());
			return "taxpayers";
		}
		return "redirect:/taxpayers";
	}

	@RequestMapping(value = "/submitinvoice", method = RequestMethod.POST)
	public String submitInvoice(Model model, @ModelAttribute InvoiceData invoiceData) {
		logger.info("invoiceSubmit buyerNif:{}, sellerNif:{}, itemType:{}, value:{}, date:{}", invoiceData.getBuyerNIF(), invoiceData.getSellerNIF(), invoiceData.getItemType(), invoiceData.getValue(), invoiceData.getDate());

		String response;
		try {
			response = TaxInterface.submitInvoice(invoiceData);
		} catch (TaxException te) {
			model.addAttribute("Error", "Error: it was not possible to submit invoice");
			model.addAttribute("InvoiceData", invoiceData);
			return "Error" + te.getMessage();

		}
		return response;
	}

	@RequestMapping(value = "/cancelinvoice", method = RequestMethod.POST)
	public void cancelInvoice(Model model, @ModelAttribute String reference) {
		logger.info("cancelInvoice reference:{}", reference);
		try {
			TaxInterface.cancelInvoice(reference);
		} catch (TaxException e){
			model.addAttribute("Error", "Error: it was not possible to cancel invoice");
			model.addAttribute("Reference", reference);
		}

	}
}
