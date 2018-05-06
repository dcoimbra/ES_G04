package pt.ulisboa.tecnico.softeng.bank.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.bank.services.local.BankInterface;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankData;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankOperationData;

@Controller
@RequestMapping(value = "/banks")
public class BankController {
	private static Logger logger = LoggerFactory.getLogger(BankController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String bankForm(Model model) {
		logger.info("bankForm");
		model.addAttribute("bank", new BankData());
		model.addAttribute("banks", BankInterface.getBanks());
		return "banks";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String bankSubmit(Model model, @ModelAttribute BankData bank) {
		logger.info("bankSubmit name:{}, code:{}", bank.getName(), bank.getCode());

		try {
			BankInterface.createBank(bank);
		} catch (BankException be) {
			model.addAttribute("error", "Error: it was not possible to create the bank");
			model.addAttribute("bank", bank);
			model.addAttribute("banks", BankInterface.getBanks());
			return "banks";
		}

		return "redirect:/banks";
	}
	
	@RequestMapping(value="/processPayment", method = RequestMethod.POST)
	public String confirmPayment(Model model, @ModelAttribute BankOperationData bankOpData) {
		logger.info("Bank processPayment");
		
		try {
			String reference = BankInterface.processPayment(bankOpData.getIban(), bankOpData.getValue());
			return reference;
			
		} catch(BankException be) {
			model.addAttribute("error", "Error: it was not possible to process the payment");
			return "";
		}

	}
	
	@RequestMapping(value="/cancelPayment", method = RequestMethod.POST)
	public String cancelPayment(Model model, @ModelAttribute String reference) {
		logger.info("Bank cancelPayment");
		
		try {
			String cancelReference = BankInterface.cancelPayment(reference);
			return cancelReference;
			
		} catch(BankException be) {
			model.addAttribute("error", "Error: it was not possible to cancel the Payment");
			return "";
		}

	}
	
	@RequestMapping(value="/operationData/{reference}", method = RequestMethod.GET)
	public BankOperationData bankOperationData(Model model, @PathVariable String reference) {
		logger.info("Bank operationData");
		
		try {
			BankOperationData boData = BankInterface.getOperationData(reference);
			return boData;
			
		} catch(BankException be) {
			model.addAttribute("error", "Error: it was not possible to obtain the bank operation data");
			return null;
		}

	}

}
