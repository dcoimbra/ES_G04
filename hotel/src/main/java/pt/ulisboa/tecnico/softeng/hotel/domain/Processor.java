package pt.ulisboa.tecnico.softeng.hotel.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.hotel.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.hotel.exception.RemoteAccessException;

public class Processor {

	private Set<Booking> bookingsToProcess = new HashSet<>();

	public void submitBooking (Booking b) {

		this.bookingsToProcess.add(b);
		processBookings();
	}

	public void processBookings () {

		Set<Booking> failedBookings = new HashSet<>();

		for (Booking booking: this.bookingsToProcess) {
			if (!booking.isCancelled()) {
				if (booking.getPaymentReference() == null) {
					try {
						booking.setPaymentReference(BankInterface.processPayment(booking.getIban(), booking.getAmount()));
					} catch (BankException | RemoteAccessException e) {
						failedBookings.add(booking);
						continue;
					}
				}
				InvoiceData invoice = new InvoiceData(booking.getProviderNif(), booking.getBuyerNif(), booking.getType(), booking.getAmount(), booking.getArrival());

				try {
					booking.setInvoiceReference(TaxInterface.submitInvoice(invoice));
				} catch (TaxException | RemoteAccessException e) {
					failedBookings.add(booking);
					continue;
				}
			}

			else {
				try {
					if (booking.getCancelledPaymentReference() == null) {
						booking.setCancelledPaymentReference(BankInterface.cancelPayment(booking.getPaymentReference()));
					}
					TaxInterface.cancelInvoice(booking.getInvoiceReference());
					booking.setCancelledInvoice(true);
				} catch (BankException | TaxException | RemoteAccessException e) {
					failedBookings.add(booking);
				}
			}
		}

		this.bookingsToProcess.clear();
		this.bookingsToProcess.addAll(failedBookings);
	}

	public void clean () {
		this.bookingsToProcess.clear();
	}
}