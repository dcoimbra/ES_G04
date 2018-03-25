package pt.ulisboa.tecnico.softeng.broker.domain;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class UndoState extends AdventureState {

	@Override
	public State getState() {
		return State.UNDO;
	}

	@Override
	public void process(Adventure adventure) {
		if (adventure.shouldCancelPayment()) {
			try {
				adventure.setPaymentCancellation(BankInterface.cancelPayment(adventure.getPaymentConfirmation()));
			} catch (BankException | RemoteAccessException ex) {
				// does not change state
			}
		}

		if (adventure.shouldCancelActivity()) {
			try {
				adventure.setActivityCancellation(
						ActivityInterface.cancelReservation(adventure.getActivityConfirmation()));
			} catch (ActivityException | RemoteAccessException ex) {
				// does not change state
			}
		}

		if (adventure.shouldCancelRoom()) {
			try {
				adventure.setRoomCancellation(HotelInterface.cancelBooking(adventure.getRoomConfirmation()));
			} catch (HotelException | RemoteAccessException ex) {
				// does not change state
			}
		}

		if (adventure.shouldCancelVehicleRenting()) {
			try {
				adventure.setRentingCancellation(CarInterface.cancelRenting(adventure.getRentingConfirmation()));
			} catch (CarException | RemoteAccessException ex) {
				// does not change state
			}
		}
		
		if (adventure.shouldCancelInvoice()) {
			try {
				TaxInterface.cancelInvoice(adventure.getInvoiceReference());
				adventure.setInvoiceCancelled(true);
			} catch (TaxException | RemoteAccessException ex) {
				// does not change state
			}
		}

		if (adventure.roomIsCancelled()
				&& adventure.activityIsCancelled()
				&& adventure.paymentIsCancelled()
				&& adventure.rentingIsCancelled()
				&& adventure.invoiceIsCancelled()) {
			adventure.setState(State.CANCELLED);
		}
	}

}
