package pt.ulisboa.tecnico.softeng.tax.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.BuyerData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.services.local.TaxInterface;

@Controller
@RequestMapping(value = "/taxpayers/buyer/{nif}/invoices")
public class BuyerInvoicesController {
    private static Logger logger = LoggerFactory.getLogger(SellerInvoicesController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String invoiceForm(Model model, @PathVariable String nif) {
        logger.info("buyerInvoiceForm nif:{}", nif);

        BuyerData buyerData = TaxInterface.getBuyerDataByNif(nif);
        if (buyerData == null) {
            model.addAttribute("error", "Error: it does not exist a buyer with the nif " + nif);
            model.addAttribute("buyer", new BuyerData());
            model.addAttribute("buyers", TaxInterface.getBuyers());
            return "buyers";
        }

        model.addAttribute("invoice", new InvoiceData());
        model.addAttribute("buyer", buyerData);
        return "buyerInvoices";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String invoiceBuyerSubmit(Model model, @PathVariable String nif, @ModelAttribute InvoiceData invoiceData) {
        invoiceData.setBuyerNIF(nif);

        logger.info("invoiceBuyerSubmit sellerNif:{}, buyerNif:{}, itemType:{}, value:{}, date:{}",
                invoiceData.getSellerNIF(), invoiceData.getBuyerNIF(), invoiceData.getItemType(), invoiceData.getValue(), invoiceData.getDate());

        try {
            TaxInterface.createInvoice(invoiceData);
        } catch (TaxException be) {
            model.addAttribute("error", "Error: it was not possible to create the invoice");
            model.addAttribute("invoice", invoiceData);
            model.addAttribute("buyer", TaxInterface.getBuyerDataByNif(nif));
            return "buyerInvoices";
        }

        return "redirect:/taxpayers/buyer/" + nif + "/invoices";
    }
}
