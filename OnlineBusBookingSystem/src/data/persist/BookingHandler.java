package data.persist;

import java.time.LocalDate;
import java.util.List;

import data.Booking;

public interface BookingHandler {
	public boolean storeBooking(Booking booking);
	public Booking readBooking(String bookingId);
	public List<Booking> getAllBookings(String travelOptionId, LocalDate date);
	public String nextBookingId();
}
