package data.processors;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import data.Booking;
import data.City;
import data.TravelOption;

public interface BookingProcessor {
	
	public Map<TravelOption, Integer> getTravelOption(City src, City dest, LocalDate date);
	
	public int getAvailableSeats(TravelOption top, LocalDate date);
	
	public List<City> getAllCities();
	
	public List<City> getAllCities(City sourceCity);
	
	public String nextBookingId();
	
	public boolean storeBooking(Booking booking);
	
	public double getTicketPrice(String sourceCity, String destCity);
}
