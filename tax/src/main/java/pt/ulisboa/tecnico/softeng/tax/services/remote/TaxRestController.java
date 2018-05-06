package pt.ulisboa.tecnico.softeng.tax.services.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.services.local.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.InvoiceData;

@RestController
@RequestMapping(value = "/rest/tax")
public class TaxRestController {
    private static Logger logger = LoggerFactory.getLogger(TaxRestController.class);

    @RequestMapping(value = "/submitinvoice", method = RequestMethod.POST)
    public ResponseEntity<String> submitInvoice(@RequestParam InvoiceData invoiceData) {
        logger.info("invoiceSubmit buyerNif:{}, sellerNif:{}, itemType:{}, value:{}, date:{}", invoiceData.getBuyerNIF(), invoiceData.getSellerNIF(), invoiceData.getItemType(), invoiceData.getValue(), invoiceData.getDate());

        try {
            return new ResponseEntity<>(TaxInterface.submitInvoice(invoiceData), HttpStatus.OK);
        } catch (TaxException te) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    @RequestMapping(value = "/cancelinvoice", method = RequestMethod.POST)
    public ResponseEntity<String> cancelInvoice(@RequestParam String reference) {
        logger.info("cancelInvoice reference:{}", reference);
        try {
            TaxInterface.cancelInvoice(reference);
            return new ResponseEntity<>("Sucess", HttpStatus.OK);
        } catch (TaxException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
