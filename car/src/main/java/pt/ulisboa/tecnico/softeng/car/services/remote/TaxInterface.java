package pt.ulisboa.tecnico.softeng.car.services.remote;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pt.ulisboa.tecnico.softeng.car.services.remote.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.car.services.remote.exceptions.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.car.services.remote.exceptions.TaxException;

public class TaxInterface {

	private static String ENDPOINT = "http://localhost:8086";

	public static String submitInvoice(InvoiceData invoiceData) {
		RestTemplate restTemplate = new RestTemplate();

		try{
			String response;
			response = restTemplate.postForObject(ENDPOINT + "/rest/tax/submitinvoice", invoiceData, String.class);
			return response;
		} catch (HttpClientErrorException e) {
			throw new TaxException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}

	public static void cancelInvoice(String invoiceReference) {
		RestTemplate restTemplate = new RestTemplate();
		try{
			restTemplate.postForObject(ENDPOINT + "/rest/tax/cancel?reference=" + invoiceReference,null, String.class);
		} catch (HttpClientErrorException e) {
			throw new TaxException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}
}