package pt.ulisboa.tecnico.softeng.tax.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.BuyerData;
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
			return "taxpayers";
		} catch (TaxException te) {
			model.addAttribute("error", "Error: it was not possible to create the buyer");
			model.addAttribute("buyer", buyer);
			model.addAttribute("buyers", TaxInterface.getBuyers());
			model.addAttribute("sellers", TaxInterface.getSellers());
			return "taxpayers";
		}

	}

	@RequestMapping(value = "/seller", method = RequestMethod.POST)
	public String sellerSubmit(Model model, @ModelAttribute SellerData seller) {
		logger.info("taxPayerSubmit nif:{}, name:{}, address:{}", seller.getNif(), seller.getName(), seller.getAddress());
		try {
			TaxInterface.createSeller(seller);
			return "taxpayers";
		} catch (TaxException te) {
			model.addAttribute("error", "Error: it was not possible to create the seller");
			model.addAttribute("seller", seller);
			model.addAttribute("buyers", TaxInterface.getBuyers());
			model.addAttribute("sellers", TaxInterface.getSellers());
			return "taxpayers";
		}
	}
}
