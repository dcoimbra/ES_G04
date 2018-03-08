package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.fail;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelReserveRoomMethodTest {



	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 21);

	@Before
	public void setUp() {
		Hotel.hotels.clear();
	    Hotel hotel = new Hotel("XPTO123", "Lisboa");
	    Hotel.hotels.add(hotel);
	    Room room = new Room(hotel,"150", Type.SINGLE);
	
	}
	
	
	@Test
	public void acceptReserve() {
		Hotel.reserveRoom(Type.SINGLE ,arrival,departure);
	}
	
	
	@Test(expected = HotelException.class)
	public void noReserve() {
		Hotel.reserveRoom(Type.SINGLE ,departure,arrival);
	}
	
	
	
	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
}
