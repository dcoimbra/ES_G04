package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class RoomConstructorMethodTest {
	private Hotel hotel;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Lisboa");
	}

	@Test
	public void success() {
		Room room = new Room(this.hotel, "01", Type.DOUBLE, 20);

		Assert.assertEquals(this.hotel, room.getHotel());
		Assert.assertEquals("01", room.getNumber());
		Assert.assertEquals(Type.DOUBLE, room.getType());
		Assert.assertEquals(1, this.hotel.getNumberOfRooms());
		Assert.assertEquals(20, room.getPrice(), 0.0);
	}

	@Test(expected = HotelException.class)
	public void nullHotel() {
		new Room(null, "01", Type.DOUBLE, 20);
	}

	@Test(expected = HotelException.class)
	public void nullRoomNumber() {
		new Room(this.hotel, null, Type.DOUBLE, 20);
	}

	@Test(expected = HotelException.class)
	public void emptyRoomNumber() {
		new Room(this.hotel, "", Type.DOUBLE, 20);
	}

	@Test(expected = HotelException.class)
	public void blankRoomNumber() {
		new Room(this.hotel, "     ", Type.DOUBLE, 20);
	}

	@Test(expected = HotelException.class)
	public void nonAlphanumericRoomNumber() {
		new Room(this.hotel, "JOSE", Type.DOUBLE, 20);
	}

	@Test
	public void nonUniqueRoomNumber() {
		new Room(this.hotel, "01", Type.SINGLE, 20);
		try {
			new Room(this.hotel, "01", Type.DOUBLE, 20);
			fail();
		} catch (HotelException he) {
			Assert.assertEquals(1, this.hotel.getNumberOfRooms());
		}
	}

	@Test(expected = HotelException.class)
	public void nullType() {
		new Room(this.hotel, "01", null, 20);
	}

	@Test(expected = HotelException.class)
	public void negativePrice(){
		new Room(this.hotel, "01", Type.DOUBLE, -1);
	}

	@Test(expected = HotelException.class)
	public void nullPrice(){
		new Room(this.hotel, "01", Type.DOUBLE, 0);
	}

	public void positivePrice(){
		new Room(this.hotel, "01", Type.DOUBLE, 1);
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
