package pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects;

import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;

public class ActivityOfferData {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate begin;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate end;
	private Integer capacity;
	private double amount;
	private String activityName;
	private String codeActivity;
	private String nameProvider;
	private String codeProvider;
	private List<ActivityReservationData> reservations;

	public ActivityOfferData() {
	}

	public ActivityOfferData(ActivityOffer offer) {
		this.begin = offer.getBegin();
		this.end = offer.getEnd();
		this.capacity = offer.getCapacity();
		this.amount = offer.getAmount();
		this.activityName = offer.getActivity().getName();
		this.codeActivity = offer.getActivity().getCode();
		this.nameProvider = offer.getActivity().getActivityProvider().getName();
		this.codeProvider = offer.getActivity().getActivityProvider().getCode();
		this.reservations = offer.getBookingSet().stream().map(b -> new ActivityReservationData(b))
				.collect(Collectors.toList());
	}

	public LocalDate getBegin() {
		return this.begin;
	}

	public void setBegin(LocalDate begin) {
		this.begin = begin;
	}

	public LocalDate getEnd() {
		return this.end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public String getActivityName() {
		return activityName;
	}

	public String getCodeActivity() {
		return codeActivity;
	}

	public String getNameProvider() {
		return nameProvider;
	}

	public String getCodeProvider() {
		return codeProvider;
	}

	public Integer getCapacity() {
		return this.capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public List<ActivityReservationData> getReservations() {
		return this.reservations;
	}

	public void setReservations(List<ActivityReservationData> reservations) {
		this.reservations = reservations;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
