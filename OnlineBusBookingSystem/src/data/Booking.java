package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Booking implements Serializable{
	public String bookingId;
	public TravelOption travelOption;
	public PaymentInfo paymentInfo;
	public List<Traveller> travellers;
	public LocalDate travelDate;

	public Booking(String bookingId, TravelOption travelOption, PaymentInfo paymentInfo, List<Traveller> travellers,LocalDate travelDate ){
		this.bookingId = bookingId;
		this.travelOption = travelOption;
		this.paymentInfo = paymentInfo;
		this.travellers = travellers;
		this.travelDate = travelDate;
		
	}
}
