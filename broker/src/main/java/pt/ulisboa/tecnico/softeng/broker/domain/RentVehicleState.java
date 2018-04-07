package pt.ulisboa.tecnico.softeng.broker.domain;

import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentVehicleState extends AdventureState {
	public static final int MAX_REMOTE_ERRORS = 5;
	
	@Override
	public State getState() {
		return State.RENT_VEHICLE;
	}

	@Override
	public void process(Adventure adventure) {
		try {
		    String reference = CarInterface.rentVehicle(adventure.getDrivingLicense(), adventure.getBegin(), adventure.getEnd(),
                    adventure.getBroker().getIBAN(), adventure.getBroker().getBuyer());
			adventure.setVehicleConfirmation(reference);
            adventure.setTotalPrice(CarInterface.getRentingData(reference).getPrice());
		} catch (CarException ce) {
			System.out.println("c1");
			adventure.setState(State.UNDO);
			return;
		} catch (RemoteAccessException rae) {
			incNumOfRemoteErrors();
			if (getNumOfRemoteErrors() == MAX_REMOTE_ERRORS) {
				System.out.println("c2");
				adventure.setState(State.UNDO);
			}
			return;
		}

		adventure.setState(State.PROCESS_PAYMENT);
	}

}