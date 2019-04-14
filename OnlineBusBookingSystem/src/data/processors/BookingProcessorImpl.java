package data.processors;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import data.Booking;
import data.City;
import data.TravelOption;
import data.persist.BookingHandler;
import data.persist.CityHandler;
import data.persist.DataFactory;
import data.persist.DistanceHandler;
import data.persist.TravelOptionHandler;
import data.persist.file.FileDistanceHandler;

public class BookingProcessorImpl implements BookingProcessor{
	TravelOptionHandler travelOptionHandler = DataFactory.getTravelOptionHandler();
	BookingHandler bookingHandler = DataFactory.getBookingHandler();
	CityHandler cityHandler = DataFactory.getCityHandler();
	DistanceHandler distanceHandler = new FileDistanceHandler();

	/*
	 * src - source city
	 * dest - destination city
	 * date - Date for which the travel options are sought
	 * 
	 * @returns Map with key as TravelOption and Value as No. Of Seats available for that option 
	 */
	public Map<TravelOption, Integer> getTravelOption(City src, City dest, LocalDate date){
		return travelOptionHandler.getTravelOptions(src, dest, date.getDayOfWeek()).
				stream().collect(Collectors.toMap(
						k->k, //keyMapper
						x->{  //valueMapper
							//total seats - total booked seats.
							return  travelOptionHandler.getTotalSeats(x.travelOptionId) -
									(bookingHandler.getAllBookings(x.travelOptionId, date).
										stream().mapToInt(b-> b.travellers.size()).sum());
						}
					));
	}
	
	public int getAvailableSeats(TravelOption top, LocalDate date){
		//total seats - total booked seats.
		return  travelOptionHandler.getTotalSeats(top.travelOptionId) -
					bookingHandler.getAllBookings(top.travelOptionId, date).stream().mapToInt(b-> b.travellers.size()).sum();
	}

	@Override
	public List<City> getAllCities() {
		return cityHandler.getAllCities();
	}

	@Override
	public List<City> getAllCities(City sourceCity) {
		return cityHandler.getAllCities(sourceCity);
	}
	
	public String nextBookingId(){
		return bookingHandler.nextBookingId();
	}
	
	public boolean storeBooking(Booking booking){
		return bookingHandler.storeBooking(booking);
	}
	
	public double getTicketPrice(String sourceCity, String destCity){
		return distanceHandler.getDistance(sourceCity, destCity);
	}
}




class TravelOptionProcessorTester{
	public static void main(String args[]){
		/*Map<TravelOption, Integer> hm = new HashMap<>();
		BookingProcessorImpl top = new BookingProcessorImpl();
		TravelOption to = new TravelOption("to2", new City("Mumbai"), new City("Delhi"), DayOfWeek.TUESDAY, LocalTime.of(10, 30));
		int seats = new BookingProcessorImpl().getAvailableSeats(to, LocalDate.of(2019, 03, 05));
		System.out.println(seats);*/

		BookingProcessorImpl top = new BookingProcessorImpl();
		Map<TravelOption, Integer> hm = new HashMap<>();
		hm = top.getTravelOption(new City("Mumbai"), new City("Delhi"), LocalDate.of(2019, 03, 12));
		hm.entrySet().stream().forEach(e->System.out.println(e.getValue()));
		
		System.out.println(top.bookingHandler.getAllBookings("to1", LocalDate.of(2019, 03, 12)));
		System.out.println(top.bookingHandler.getAllBookings("to2", LocalDate.of(2019, 03, 12)));
		System.out.println(top.bookingHandler.getAllBookings("to3", LocalDate.of(2019, 03, 12)));
		System.out.println(top.bookingHandler.getAllBookings("to4", LocalDate.of(2019, 03, 12)));
		System.out.println(top.bookingHandler.getAllBookings("to5", LocalDate.of(2019, 03, 12)));
		
	}
}