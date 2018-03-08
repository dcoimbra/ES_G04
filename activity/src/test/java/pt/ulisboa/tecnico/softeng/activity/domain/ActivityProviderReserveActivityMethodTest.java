package pt.ulisboa.tecnico.softeng.activity.domain;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ActivityProviderReserveActivityMethodTest {
	private ActivityProvider provider;
	private ActivityOffer offer;

	@Before
	public void setUp() {
		this.provider = new ActivityProvider("XtremX", "ExtremeAdventure");
		Activity activity = new Activity(this.provider, "Bush Walking", 18, 80, 3);

		LocalDate begin = new LocalDate(2016, 12, 19);
		LocalDate end = new LocalDate(2016, 12, 21);

		this.offer = new ActivityOffer(activity, begin, end);
	}

	@Test(expected = ActivityException.class)
	public void offerExists() {

		LocalDate begin = new LocalDate(2016, 12, 18);
		LocalDate end = new LocalDate(2016, 12, 22);

		ActivityProvider.reserveActivity(begin, end, 18);
	}

	@Test
	public void offerDoesNotExist() {
		
		LocalDate begin = new LocalDate(2016, 12, 19);
		LocalDate end = new LocalDate(2016, 12, 21);

		ActivityProvider.reserveActivity(begin, end, 18);
	}

	@After
	public void tearDown() {
		ActivityProvider.providers.clear();
	}

}
