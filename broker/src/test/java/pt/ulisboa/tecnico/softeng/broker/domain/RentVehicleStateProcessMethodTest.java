package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Delegate;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

@RunWith(JMockit.class)
public class RentVehicleStateProcessMethodTest {
    private static final String IBAN = "BK01987654321";
    private static final int AMOUNT = 300;
    private static final int AGE = 20;
    private static final String NIF = "123456789";
    private static final String VEHICLE_CONFIRMATION = "VehicleConfirmation";
    private static final LocalDate begin = new LocalDate(2016, 12, 19);
    private static final LocalDate end = new LocalDate(2016, 12, 21);
    private Adventure adventure;
    private static final String DRIVING_LICENSE = "IMT1234";

    @Injectable
    private Broker broker;

    private Client client;

    @Before
    public void setUp() {
        this.client = new Client(this.broker,IBAN, NIF, DRIVING_LICENSE ,AGE);
        this.adventure = new Adventure(this.broker, begin, end, this.client, AMOUNT, true);
        this.adventure.setState(State.RENT_VEHICLE);
    }

    @Test
    public void successRentVehicle(@Mocked final CarInterface carInterface) {
        new Expectations() {
            {
                broker.getBuyer();
                this.result=NIF;
                broker.getIBAN();
                this.result=IBAN;

                CarInterface.rentVehicle(DRIVING_LICENSE, begin, end, IBAN, NIF);
                this.result = VEHICLE_CONFIRMATION;
            }
        };

        this.adventure.process();

        Assert.assertEquals(State.PROCESS_PAYMENT, this.adventure.getState());
    }

    @Test
    public void carException(@Mocked final CarInterface carInterface) {
        new Expectations() {
            {
                broker.getBuyer();
                this.result=NIF;
                broker.getIBAN();
                this.result=IBAN;

                CarInterface.rentVehicle(DRIVING_LICENSE, begin, end, IBAN, NIF);
                this.result = new CarException();
            }
        };

        this.adventure.process();

        Assert.assertEquals(State.UNDO, this.adventure.getState());
    }

    @Test
    public void singleRemoteAccessException(@Mocked final CarInterface carInterface) {
        new Expectations() {
            {
                broker.getBuyer();
                this.result=NIF;
                broker.getIBAN();
                this.result=IBAN;

                CarInterface.rentVehicle(DRIVING_LICENSE, begin, end, IBAN, NIF);
                this.result = new RemoteAccessException();
            }
        };

        this.adventure.process();

        Assert.assertEquals(State.RENT_VEHICLE, this.adventure.getState());
    }

    @Test
    public void maxRemoteAccessException(@Mocked final CarInterface carInterface) {
        new Expectations() {
            {
                broker.getBuyer();
                this.result=NIF;
                broker.getIBAN();
                this.result=IBAN;

                CarInterface.rentVehicle(DRIVING_LICENSE, begin, end, IBAN, NIF);
                this.result = new RemoteAccessException();
                this.times = RentVehicleState.MAX_REMOTE_ERRORS;
            }
        };

        for (int i = 0; i < RentVehicleState.MAX_REMOTE_ERRORS; i++) {
            this.adventure.process();
        }

        Assert.assertEquals(State.UNDO, this.adventure.getState());
    }

    @Test
    public void maxMinusOneRemoteAccessException(@Mocked final CarInterface carInterface) {
        new Expectations() {
            {
                broker.getBuyer();
                this.result=NIF;
                broker.getIBAN();
                this.result=IBAN;

                CarInterface.rentVehicle(DRIVING_LICENSE, begin, end, IBAN, NIF);
                this.result = new RemoteAccessException();
                this.times = RentVehicleState.MAX_REMOTE_ERRORS - 1;
            }
        };

        for (int i = 0; i < RentVehicleState.MAX_REMOTE_ERRORS - 1; i++) {
            this.adventure.process();
        }

        Assert.assertEquals(State.RENT_VEHICLE, this.adventure.getState());
    }

    @Test
    public void fourRemoteAccessExceptionOneSuccess(@Mocked final CarInterface carInterface) {
        new Expectations() {
            {

                broker.getBuyer();
                this.result=NIF;
                broker.getIBAN();
                this.result=IBAN;

                CarInterface.rentVehicle(DRIVING_LICENSE, begin, end, IBAN, NIF);
                this.result = new Delegate() {
                    int i = 0;

                    public void delegate() {
                        if (this.i < 5) {
                            this.i++;
                            throw new RemoteAccessException();
                        }

                    }
                };
                this.times = 5;
            }
        };

        this.adventure.process();
        this.adventure.process();
        this.adventure.process();
        this.adventure.process();
        this.adventure.process();

        Assert.assertEquals(State.UNDO, this.adventure.getState());
    }

    @Test
    public void oneRemoteAccessExceptionOneActivityException(@Mocked final CarInterface carInterface) {
        new Expectations() {
            {
                broker.getBuyer();
                this.result=NIF;
                broker.getIBAN();
                this.result=IBAN;

                CarInterface.rentVehicle(DRIVING_LICENSE, begin, end, IBAN, NIF);
                this.result = new Delegate() {
                    int i = 0;

                    public String delegate() {
                        if (this.i < 1) {
                            this.i++;
                            throw new RemoteAccessException();
                        } else {
                            throw new CarException();
                        }
                    }
                };
                this.times = 2;
            }
        };

        this.adventure.process();
        this.adventure.process();

        Assert.assertEquals(State.UNDO, this.adventure.getState());
    }

}