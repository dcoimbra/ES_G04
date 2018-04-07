package pt.ulisboa.tecnico.softeng.broker.domain;

import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class BookRoomState extends AdventureState {
	public static final int MAX_REMOTE_ERRORS = 10;

	@Override
	public State getState() {
		return State.BOOK_ROOM;
	}

	@Override
	public void process(Adventure adventure) {
		try {
			String reference = HotelInterface.reserveRoom(Room.Type.SINGLE, adventure.getBegin(), adventure.getEnd());
			adventure.setRoomConfirmation(reference);
			//adventure.setTotalPrice(HotelInterface.getRoomBookingData(reference).getAmount());
		} catch (HotelException he) {
			System.out.println("h1");
			adventure.setState(State.UNDO);
			return;
		} catch (RemoteAccessException rae) {
			incNumOfRemoteErrors();
			if (getNumOfRemoteErrors() == MAX_REMOTE_ERRORS) {
				System.out.println("h2");
				adventure.setState(State.UNDO);
			}
			return;
		}

		if(adventure.getCls())
			adventure.setState(State.RENT_VEHICLE);
		else
			adventure.setState(State.PROCESS_PAYMENT);
	}

}
