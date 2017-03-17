package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;

@RunWith(JMockit.class)
public class CancelledStateProcessMethodTest {
	private static final String PAYMENT_CANCELLATION = "PaymentCancellation";
	private static final String ACTIVITY_CANCELLATION = "ActivityCancellation";
	private static final String ROOM_CANCELLATION = "RoomCancellation";
	private CancelledState cancelledState;

	@Before
	public void setUp() {
		this.cancelledState = new CancelledState();
	}

	@Test
	public void didNotPayed(@Mocked final Adventure adventure) {
		new Expectations() {
			{
				adventure.getPaymentCancellation();
				this.result = null;

				adventure.getActivityCancellation();
				this.result = null;

				adventure.getRoomCancellation();
				this.result = null;
			}
		};

		this.cancelledState.process(adventure);

		Assert.assertEquals(Adventure.State.CANCELLED, this.cancelledState.getState());
	}

	@Test
	public void cancelledPayment(@Mocked final Adventure adventure, @Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				adventure.getPaymentCancellation();
				this.result = PAYMENT_CANCELLATION;

				BankInterface.getOperationData(this.anyString);

				BankInterface.getOperationData(PAYMENT_CANCELLATION);

				adventure.getActivityCancellation();
				this.result = null;

				adventure.getRoomCancellation();
				this.result = null;

			}
		};

		this.cancelledState.process(adventure);

		Assert.assertEquals(Adventure.State.CANCELLED, this.cancelledState.getState());
	}

	@Test
	public void cancelledActivity(@Mocked final Adventure adventure, @Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
				adventure.getPaymentCancellation();
				this.result = PAYMENT_CANCELLATION;

				BankInterface.getOperationData(this.anyString);

				BankInterface.getOperationData(PAYMENT_CANCELLATION);

				adventure.getActivityCancellation();
				this.result = ACTIVITY_CANCELLATION;

				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);

				adventure.getRoomCancellation();
				this.result = null;

			}
		};

		this.cancelledState.process(adventure);

		Assert.assertEquals(Adventure.State.CANCELLED, this.cancelledState.getState());
	}

	@Test
	public void cancelledRoom(@Mocked final Adventure adventure, @Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface hotelInterface) {
		new Expectations() {
			{
				adventure.getPaymentCancellation();
				this.result = PAYMENT_CANCELLATION;

				BankInterface.getOperationData(this.anyString);

				BankInterface.getOperationData(PAYMENT_CANCELLATION);

				adventure.getActivityCancellation();
				this.result = ACTIVITY_CANCELLATION;

				ActivityInterface.getActivityReservationData(ACTIVITY_CANCELLATION);

				adventure.getRoomCancellation();
				this.result = ROOM_CANCELLATION;

				HotelInterface.getRoomBookingData(ROOM_CANCELLATION);
			}
		};

		this.cancelledState.process(adventure);

		Assert.assertEquals(Adventure.State.CANCELLED, this.cancelledState.getState());
	}

}
