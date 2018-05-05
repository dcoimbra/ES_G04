package pt.ulisboa.tecnico.softeng.broker.services.remote;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pt.ulisboa.tecnico.softeng.broker.services.remote.dataobjects.InvoiceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaxInterface {
	private static Logger logger = LoggerFactory.getLogger(TaxInterface.class);

	public static String submitInvoice(InvoiceData invoiceData) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8086/taxpayers/submitinvoice";
		String response;
		try{
			response = restTemplate.postForObject(url, invoiceData, String.class);
		} catch (RestClientException e){
			logger.error("Failed to get {} due to error: {}", url, e.getMessage());
			return null;
		}

		return response;
	}

	public static void cancelInvoice(String invoiceReference) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8086/taxpayers/submitinvoice";
		try{
			restTemplate.postForObject(url, invoiceReference, String.class);
		} catch (RestClientException e){
			logger.error("Failed to get {} due to error: {}", url, e.getMessage());
			return;
		}

		return;
	}
}