package pt.ulisboa.tecnico.softeng.broker.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;
import pt.ulisboa.tecnico.softeng.broker.services.local.BrokerInterface;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.AdventureData;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.BrokerData;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.BrokerData.CopyDepth;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.ClientData;

@Controller
@RequestMapping(value = "/brokers/{code}/clients/{nif}/adventures")
public class AdventureController {
	private static Logger logger = LoggerFactory.getLogger(AdventureController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String accountForm(Model model, @PathVariable String code, @PathVariable String nif) {
		logger.info("");

		ClientData clientData = BrokerInterface.getClientDataByNif(code, nif);

		if (clientData == null) {
			model.addAttribute("error",
					"Error: it does not exist a client with nif " + nif + " in broker with code " + code);
			model.addAttribute("broker", new BrokerData());
			model.addAttribute("brokers", BrokerInterface.getBrokers());
			return "brokers";
		} else {
			model.addAttribute("client", clientData);
			return "adventures";
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String accountSubmit(Model model, @PathVariable String code, @PathVariable String nif, @ModelAttribute AdventureData adventureData) {
		logger.info("");

		try {
			BrokerInterface.createAdventure(code, nif, adventureData);
		} catch (BrokerException be) {
			model.addAttribute("error", "Error: it was not possible to create de account");
			model.addAttribute("client", BrokerInterface.getClientDataByNif(code, nif));
			return "adventures";
		}

		return "redirect:/brokers/" + code + "/clients/" + nif + "/adventures";
	}
}